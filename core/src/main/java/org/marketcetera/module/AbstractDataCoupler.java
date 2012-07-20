package org.marketcetera.module;

import org.marketcetera.util.misc.ClassVersion;
import org.marketcetera.util.log.I18NBoundMessage;
import org.marketcetera.util.log.SLF4JLoggerProxy;
import org.marketcetera.util.except.I18NException;

import java.util.concurrent.atomic.AtomicLong;

/* $License$ */
/**
 * Base class for couplers. A coupler receives data from an emitter
 * and supplies it to a receiver at the other end.
 *
 * A data flow consists of two or more modules connected with each
 * other through couplers. 
 *
 * A coupler keeps the statistics on number of data objects
 * received from the emitter module and received by the receiver
 * module. It also keeps statistics on the number of errors
 * generated by the modules when emitting / receiving data and
 * the last emit / receive error text.
 *
 * Subclasses typically implement the {@link #process(Object)} method.
 * The <code>process()</code> method is called when the emitter module
 * emits data. From within <code>process()</code> method subclasses
 * eventually invoke {@link #receive(Object)} method to supply
 * the emitted data to the receiver module. 
 *
 * @author anshul@marketcetera.com
 * @version $Id: AbstractDataCoupler.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
@ClassVersion("$Id: AbstractDataCoupler.java 16063 2012-01-31 18:21:55Z colin $")   //$NON-NLS-1$
abstract class AbstractDataCoupler implements DataEmitterSupport {

    @Override
    public final void send(Object inData) {
        //ignore data if the request has been canceled
        if(mRequestCanceled) {
            return;
        }
        mEmitted.incrementAndGet();
        SLF4JLoggerProxy.debug(this,"Module {} emitted \"{}\"",  //$NON-NLS-1$
                mEmitter.getURN(), inData);
        process(inData);
    }

    @Override
    public final void dataEmitError(I18NBoundMessage inMessage,
                              boolean inStopDataFlow) {
        if(mRequestCanceled) {
            return;
        }
        mEmitErrors.incrementAndGet();
        mLastEmitError = inMessage.getText();
        Messages.LOG_MODULE_EMIT_ERROR.warn(this, mEmitter.getURN(),
                inMessage.getText());
        if(inStopDataFlow) {
            cancelDataFlow(mEmitter);
        }
    }

    /**
     * The request ID associated with this request.
     *
     * @return the request ID.
     */
    @Override
    public final RequestID getRequestID() {
        return mRequestID;
    }

    /**
     * The flowID uniquely identifying this data flow.
     *
     * @return the flowID uniquely identifying this data flow.
     */
    @Override
    public final DataFlowID getFlowID() {
        return mFlowID;
    }

    /**
     * The number of data instances received by this coupler.
     *
     * @return number of data instances received.
     */
    public final long getReceived() {
        return mReceived.longValue();
    }

    /**
     * The number of data instances emitted to this coupler.
     *
     * @return number of data instances emitted.
     */
    public final long getEmitted() {
        return mEmitted.longValue();
    }

    /**
     * The number of data receive errors encountered.
     *
     * @return number of data receive errors encountered.
     */
    public final long getReceiveErrors() {
        return mReceiveErrors.longValue();
    }

    /**
     * The number of data emit errors encountered.
     *
     * @return number of data receive errors encountered.
     */
    public final long getEmitErrors() {
        return mEmitErrors.longValue();
    }

    /**
     * The text from the last data receive error.
     *
     * @return text from the last data receive error.
     */
    public final String getLastReceiveError() {
        return mLastReceiveError;
    }

    /**
     * The text from the last data emit error.
     *
     * @return text from the last data emit error.
     */
    public final String getLastEmitError() {
        return mLastEmitError;
    }

    /**
     * This method is implemented by the subclasses to receive
     * the data emitted by the emitter module. The implementation
     * of this method should eventually call {@link #receive(Object)}
     * to deliver the data object to receiver on this coupling.
     *
     * @param inData the data.
     */
    protected abstract void process(Object inData);

    /**
     * This method is invoked to supply the data emitted by
     * the emitter to the receiver of this data. This method
     * is typically invoked by the subclasses from within
     * the implementation of {@link #process(Object)}
     *
     * @param inData the data object, can be null
     */
    protected final void receive(Object inData) {
        try {
            mReceived.incrementAndGet();
            boolean failed = true;
            try {
                ((DataReceiver)mReceiver).receiveData(mFlowID,inData);
                failed = false;
                SLF4JLoggerProxy.debug(this, "{} received {}",  //$NON-NLS-1$
                        mReceiver.getURN(),
                        mReceived);
            } finally {
                if(failed) {
                    //This counter needs to be incremented before
                    //data flow is cancelled.
                    mReceiveErrors.incrementAndGet();
                }
            }
        } catch (Throwable t) {
            if(t instanceof I18NException) {
                mLastReceiveError = ((I18NException)t).getLocalizedDetail();
            } else {
                mLastReceiveError = t.getLocalizedMessage();
            }
            Messages.LOG_DATA_RECEIVE_ERROR.warn(this, t,
                    mReceiver.getURN(), inData);
            if(t instanceof StopDataFlowException) {
                Messages.LOG_CANCELING_DATA_FLOW.info(this, t,
                        mFlowID, getReceiverURN());
                cancelDataFlow(mReceiver);
            }
        }
    }

    /**
     * Creates new instance.
     *
     * @param inManager the module manager instance
     * @param inEmitter the emitter module instance
     * @param inReceiver the receiver module instance
     * @param inFlowID the data flow ID
     */
    protected AbstractDataCoupler(ModuleManager inManager,
                                  Module inEmitter,
                                  Module inReceiver,
                                  DataFlowID inFlowID) {
        mManager = inManager;
        mEmitter = inEmitter;
        mReceiver = inReceiver;
        mFlowID = inFlowID;
    }

    /**
     * Initiates a request with the data emitter using the request parameter
     * in specified request.
     *
     * @param inRequestID the requestID uniquely identifying request.
     * @param inRequest the request.
     *
     * @throws RequestDataException if the module is unable to fulfill
     * this request.
     */
    final void initiateRequest(RequestID inRequestID, DataRequest inRequest)
            throws RequestDataException {
        mRequestID = inRequestID;
        sNestedFlowCall.set(Boolean.TRUE);
        try {
            ((DataEmitter)mEmitter).requestData(inRequest, this);
        } finally {
            sNestedFlowCall.set(Boolean.FALSE);
        }
    }

    /**
     * Cancels the request with the emitter module and
     * disables all the communication through the coupling
     */
    final void cancelRequest() {
        try {
            sNestedFlowCall.set(Boolean.TRUE);
            try {
                ((DataEmitter)mEmitter).cancel(mFlowID, mRequestID);
            } finally {
                sNestedFlowCall.set(Boolean.FALSE);
            }
        } catch(Throwable t) {
            Messages.LOG_UNEXPECTED_ERROR_CANCELING_REQ.warn(
                    this,t, mRequestID);
        } finally {
            mRequestCanceled = true;
        }
    }

    /**
     * The emitter module's URN.
     *
     * @return emitter module's URN.
     */
    final ModuleURN getEmitterURN() {
        return mEmitter.getURN();
    }

    /**
     * The receiver module's URN.
     *
     * @return receiver module's URN.
     */
    final ModuleURN getReceiverURN() {
        return mReceiver.getURN();
    }

    /**
     * Returns true, if this method is being invoked from within a
     * {@link DataEmitter#requestData(DataRequest, DataEmitterSupport)} or
     * a {@link DataEmitter#cancel(DataFlowID, RequestID)}.
     *
     * @return true if this method has <code>DataEmitter.requestData()</code>
     * or <code>DataEmitter.cancel()</code> on its calling stack.
     */
    static boolean isNestedFlowCall() {
        return sNestedFlowCall.get();
    }

    /**
     * Cancels the data flow as requested by either the emitter
     * or receiver module.
     *
     * @param inRequester the module requesting cancellation of
     * the data flow.
     */
    private void cancelDataFlow(Module inRequester) {
        try {
            mManager.cancel(mFlowID,inRequester);
        } catch (Exception e) {
            Messages.LOG_UNEXPECTED_ERROR_CANCELING_FLOW.error(this, e,
                    mFlowID, inRequester.getURN());
        }
    }

    private final ModuleManager mManager;
    private final Module mEmitter;
    private final Module mReceiver;
    private final AtomicLong mReceived = new AtomicLong(0);
    private final AtomicLong mEmitted = new AtomicLong(0);
    private final AtomicLong mReceiveErrors = new AtomicLong(0);
    private final AtomicLong mEmitErrors = new AtomicLong(0);
    private final DataFlowID mFlowID;

    /*
     * The following variables are kept as volatile to avoid overhead
     * due to synchronization. Its assumed that these variables
     * are updated from a single thread but may be read from multiple
     * threads, in which case, we shouldn't have any data consistency
     * issues.
     */

    private volatile String mLastReceiveError = null;
    private volatile String mLastEmitError = null;
    private volatile boolean mRequestCanceled = false;
    private volatile RequestID mRequestID;
    private final static ThreadLocal<Boolean> sNestedFlowCall =
            new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };
}
