package org.marketcetera.module;

import org.marketcetera.core.attributes.ClassVersion;
import org.marketcetera.util.log.I18NBoundMessage;

/* $License$ */
/**
 * Instances of this exception are thrown when data emitters are
 * not able to fulfill a request to emit data.
 *
 * @author anshul@marketcetera.com
 * @version $Id: RequestDataException.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 * @see DataEmitter#requestData(DataRequest, DataEmitterSupport)
 */
@ClassVersion("$Id: RequestDataException.java 16063 2012-01-31 18:21:55Z colin $") //$NON-NLS-1$
public class RequestDataException extends ModuleException {
    /**
     * Creates an instance.
     *
     * @param inCause the underlying cause.
     */
    public RequestDataException(Throwable inCause) {
        super(inCause);
    }

    /**
     * Creates an instance.
     *
     * @param inMessage the exception message.
     */
    public RequestDataException(I18NBoundMessage inMessage) {
        super(inMessage);
    }

    /**
     * Creates an instance.
     *
     * @param inCause the underlying cause.
     * @param inMessage the exception message.
     */
    public RequestDataException(Throwable inCause, I18NBoundMessage inMessage) {
        super(inCause, inMessage);
    }

    private static final long serialVersionUID = 1L;
}
