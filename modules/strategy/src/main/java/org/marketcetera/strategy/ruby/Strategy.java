package org.marketcetera.strategy.ruby;

import java.math.BigDecimal;
import java.util.*;

import org.marketcetera.client.brokers.BrokerStatus;
import org.marketcetera.core.ClassVersion;
import org.marketcetera.core.notifications.Notification;
import org.marketcetera.core.position.PositionKey;
import org.marketcetera.event.*;
import org.marketcetera.marketdata.MarketDataRequest;
import org.marketcetera.module.DataFlowID;
import org.marketcetera.module.DataFlowSupport;
import org.marketcetera.module.DataRequest;
import org.marketcetera.module.ModuleURN;
import org.marketcetera.strategy.AbstractRunningStrategy;
import org.marketcetera.strategy.RunningStrategy;
import org.marketcetera.trade.*;
import org.marketcetera.trade.Currency;

import quickfix.Message;

/* $License$ */

/**
 * {@link RunningStrategy} implementation for Ruby strategies to extend.
 * 
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since 1.0.0
 */
@ClassVersion("$Id$")
public class Strategy
        extends AbstractRunningStrategy
{
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.IStrategy#onAsk(org.marketcetera.event.AskEvent)
     */
    @Override
    public final void onAsk(AskEvent inAsk)
    {
        on_ask(inAsk);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.IStrategy#onBid(org.marketcetera.event.BidEvent)
     */
    @Override
    public final void onBid(BidEvent inBid)
    {
        on_bid(inBid);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.RunningStrategy#onMarketstat(org.marketcetera.event.MarketstatEvent)
     */
    @Override
    public void onMarketstat(MarketstatEvent inStatistics)
    {
        on_marketstat(inStatistics);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.RunningStrategy#onDividend(org.marketcetera.event.DividendEvent)
     */
    @Override
    public void onDividend(DividendEvent inDividend)
    {
        on_dividend(inDividend);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.IStrategy#onCallback()
     */
    @Override
    public final void onCallback(Object inData)
    {
        on_callback(inData);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.IStrategy#onExecutionReport(org.marketcetera.event.ExecutionReport)
     */
    @Override
    public final void onExecutionReport(ExecutionReport inExecutionReport)
    {
        on_execution_report(inExecutionReport);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.RunningStrategy#onCancel(org.marketcetera.trade.OrderCancelReject)
     */
    @Override
    public final void onCancelReject(OrderCancelReject inCancel)
    {
        on_cancel_reject(inCancel);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.RunningStrategy#onreceiveBrokerStatus(org.marketcetera.brokers.BrokerStatus)
     */
    @Override
    public void onReceiveBrokerStatus(BrokerStatus inStatus)
    {
        on_receive_status(inStatus);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.IStrategy#onTrade(org.marketcetera.event.TradeEvent)
     */
    @Override
    public final void onTrade(TradeEvent inTrade)
    {
        on_trade(inTrade);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.AbstractStrategy#onOther(java.lang.Object)
     */
    @Override
    public final void onOther(Object inEvent)
    {
        on_other(inEvent);
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.RunningStrategy#onStart()
     */
    @Override
    public final void onStart()
    {
        on_start();
    }
    /* (non-Javadoc)
     * @see org.marketcetera.strategy.RunningStrategy#onStop()
     */
    @Override
    public final void onStop()
    {
        on_stop();
    }
    // callbacks that Ruby scripts may override
    /**
     * Invoked when the <code>Strategy</code> receives an {@link AskEvent}.
     * 
     * @param inAsk an <code>AskEvent</code> value
     */
    protected void on_ask(AskEvent inAsk)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives a {@link BidEvent}.
     * 
     * @param inBid a <code>BidEvent</code> value
     */
    protected void on_bid(BidEvent inBid)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives a {@link MarketstatEvent}.
     * 
     * @param inStatistics a <code>MarketstatEvent</code> value
     */
    protected void on_marketstat(MarketstatEvent inStatistics)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives a {@link DividendEvent}.
     * 
     * @param inStatistics a <code>DividendEvent</code> value
     */
    protected void on_dividend(DividendEvent inStatistics)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives an {@link ExecutionReport}.
     * 
     * @param inExecutionReport an <code>ExecutionReport</code> value
     */
    protected void on_execution_report(ExecutionReport inExecutionReport)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives an {@link OrderCancelReject}.
     * 
     * @param inCancelReject an <code>OrderCancelReject</code> value
     */
    protected void on_cancel_reject(OrderCancelReject inCancelReject)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives a {@link BrokerStatus}.
     * 
     * @param inStatus a <code>BrokerStatus</code> value
     */
    protected void  on_receive_status(BrokerStatus inStatus)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives a {@link TradeEvent}.
     * 
     * @param inTrade a <code>TradeEvent</code> value
     */
    protected void on_trade(TradeEvent inTrade)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives an object that does not fit any of the other categories.
     * 
     * @param inEvent an <code>Object</code> value
     */
    protected void on_other(Object inEvent)
    {
    }
    /**
     * Invoked when the <code>Strategy</code> receives a callback requested via {@link #request_callback_at(Date, Object)}
     * or {@link #request_callback_after(long, Object)}.
     * 
     * @param inData an <code>Object</code> value which was passed to the request, may be null
     */
    protected void on_callback(Object inData)
    {
    }
    /**
     * Called when a strategy is started.
     */
    protected void on_start()
    {
    }
    /**
     * Called when a strategy is about to be stopped.
     */
    protected void on_stop()
    {
    }
    // services provided to Ruby scripts
    /**
     * Sets the given key to the given value in the storage area common to all running strategies.
     * 
     * @param inKey a <code>String</code> value
     * @param inValue a <code>String</code> value
     */
    public final void set_property(String inKey,
                                   String inValue)
    {
        setProperty(inKey,
                    inValue);
    }
    /**
     * Gets the parameter associated with the given name.
     * 
     * @param inName a <code>String</code> value containing the key of a parameter key/value value
     * @return a <code>String</code> value or null if no parameter is associated with the given name
     */
    public final String get_parameter(String inName)
    {
        return getParameter(inName);
    }
    /**
     * Gets the property associated with the given name.
     * 
     * @param inName a <code>String</code> value containing the key of a property key/value value
     * @return a <code>String</code> value or null if no property is associated with the given name
     */
    public final String get_property(String inName)
    {
        return getProperty(inName);
    }
    /**
     * Requests a callback after a specified delay in milliseconds.
     *
     * <p>The callback will be executed as close to the specified millisecond
     * as possible.  There is no guarantee that the timing will be exact.  If
     * more than one callback is requested by the same {@link RunningStrategy}
     * for the same millisecond, the requests will be processed serially in
     * FIFO order.  This implies that a long-running callback request may
     * delay other callbacks from the same {@link RunningStrategy} unless the
     * caller takes steps to mitigate the bottleneck.
     *
     * @param inDelay a <code>long</code> value indicating how many milliseconds
     *   to wait before executing the callback.  A value &lt;= 0 will be interpreted
     *   as a request for an immediate callback.
     * @param inData an <code>Object</code> value to deliver along with the callback,
     *   may be null
     */
    public final void request_callback_after(long inDelay,
                                             Object inData)
    {
        requestCallbackAfter(inDelay,
                             inData);
    }
    /**
     * Requests a callback periodically after a specified period in milliseconds.
     *
     * <p>The callback will be executed as close to the specified millisecond
     * as possible.  There is no guarantee that the timing will be exact.  If
     * more than one callback is requested by the same {@link RunningStrategy}
     * for the same millisecond, the requests will be processed serially in
     * FIFO order.  This implies that a long-running callback request may
     * delay other callbacks from the same {@link RunningStrategy} unless the
     * caller takes steps to mitigate the bottleneck.
     * @param inDelay a <code>long</code> value indicating how many milliseconds
     *   to wait before executing the first callback. A value &lt;= 0 will be interpreted
     *   as a request for an immediate callback.
     * @param inPeriod a <code>long</code> value indicating how many milliseconds
     *   to wait before executing the second callback, and thereafter repeatedly
     *   The value must be &gt; 0.
     * @param inData an <code>Object</code> value to deliver along with the callback,
     *   may be null
     */
    public final void request_callback_every(long inDelay,
                                             long inPeriod,
                                             Object inData)
    {
        requestCallbackEvery(inDelay, inPeriod, inData);
    }
    /**
     * Requests a callback at a specific point in time.
     *
     * <p>The callback will be executed as close to the specified millisecond
     * as possible.  There is no guarantee that the timing will be exact.  If
     * more than one callback is requested by the same {@link RunningStrategy}
     * for the same millisecond, the requests will be processed serially in
     * FIFO order.  This implies that a long-running callback request may
     * delay other callbacks from the same {@link RunningStrategy} unless the
     * caller takes steps to mitigate the bottleneck.
     *
     * @param inDate a <code>Date</code> value at which to execute the callback.  A date
     *   value earlier than the present will be interpreted as a request for an
     *   immediate callback.
     * @param inData an <code>Object</code> value to deliver with the callback or null
     */
    public final void request_callback_at(Date inDate,
                                          Object inData)
    {
        requestCallbackAt(inDate,
                          inData);
    }
    /**
     * Creates a market data request.
     * 
     * <p>The <code>inRequest</code> object must refer to a started market data provider module.
     * 
     * @param inRequest a <code>MarketDataRequest</code> value containing the request to execute
     * @return an <code>int</code> value containing an identifier corresponding to this market data request or 0 if the request failed
     */
    public final int request_market_data(MarketDataRequest inRequest)
    {
        return requestMarketData(inRequest);
    }
    /**
     * Creates a market data request.
     * 
     * <p>The <code>inRequest</code> object must refer to a started market data provider module.
     * 
     * @param inRequest a <code>String</code> value containing the representation of a {@link MarketDataRequest} to execute
     * @return an <code>int</code> value containing an identifier corresponding to this market data request or 0 if the request failed
     */
    public final int request_market_data(String inRequest)
    {
        return requestMarketData(inRequest);
    }
    /**
     * Requests market data processed by the given complex event processor from the given source.
     *
     * @param inRequest a <code>MarketDataRequest</code> value containing the request to execute
     * @param inStatements a <code>String[]</code> value containing the statements to pass to the
     *   complex event processor.  The meaning of the statements varies according to the actual
     *   event processor that handles them.
     * @param inCepSource a <code>String</code> value containing the name of the complex event processor
     *   to which to send the query request
     * @return an <code>int</code> value containing the handle of the request or 0 if the request failed
     */
    public final int request_processed_market_data(MarketDataRequest inRequest,
                                                   String[] inStatements,
                                                   String inCepSource)
    {
        return requestProcessedMarketData(inRequest,
                                          inStatements,
                                          inCepSource);
    }
    /**
     * Requests market data processed by the given complex event processor from the given source.
     *
     * @param inRequest a <code>String</code> value containing the representation of a {@link MarketDataRequest} to execute
     * @param inStatements a <code>String[]</code> value containing the statements to pass to the
     *   complex event processor.  The meaning of the statements varies according to the actual
     *   event processor that handles them.
     * @param inCepSource a <code>String</code> value containing the name of the complex event processor
     *   to which to send the query request
     * @return an <code>int</code> value containing the handle of the request or 0 if the request failed
     */
    public final int request_processed_market_data(String inRequest,
                                                   String[] inStatements,
                                                   String inCepSource)
    {
        return requestProcessedMarketData(inRequest,
                                          inStatements,
                                          inCepSource);
    }
    /**
     * Cancels a given data request.
     * 
     * <p>If the given <code>inRequestID</code> identifier does not correspond to an active data
     * request, this method does nothing.
     *
     * @param inRequestID an <code>int</code> value identifying the data request to cancel
     */
    public final void cancel_data_request(int inRequestID)
    {
        cancelDataRequest(inRequestID);
    }
    /**
     * Cancels all data requests for this {@link Strategy}.
     *
     * <p>If there are no active data requests for this {@link Strategy}, this method does nothing.
     */
    public final void cancel_all_data_requests()
    {
        cancelAllDataRequests();
    }
    /**
     * Creates a complex event processor request.
     * 
     * @param inStatements a <code>String[]</code> value containing an array of statements that comprises the request
     * @param inSource a <code>String</code> value indicating what market data provider from which to request the data
     * @return an <code>int</code> value containing an identifier corresponding to this market data request or 0 if the request failed
     */
    public final int request_cep_data(String[] inStatements,
                                      String inSource)
    {
        return requestCEPData(inStatements,
                              inSource);
    }
    /**
     * Suggests a trade.
     *
     * @param inOrder an <code>OrderSingle</code> value containing the trade to suggest
     * @param inScore a <code>BigDecimal</code> value containing the score of this suggestion.  this value is determined by the user
     *   but is recommended to fit in the interval [0..1]
     * @param inIdentifier a <code>String</code> value containing a user-specified string to identify the suggestion
     */
    public final void suggest_trade(OrderSingle inOrder,
                                    BigDecimal inScore,
                                    String inIdentifier)
    {
        suggestTrade(inOrder,
                     inScore,
                     inIdentifier);
    }
    /**
     * Sends a <code>FIX</code> message to all subscribers to which orders are sent.
     *
     * @param inMessage a <code>Message</code> value
     * @param inBroker a <code>BrokerID</code> value
     */
    public final void send_message(Message inMessage,
                                   BrokerID inBroker)
    {
        sendMessage(inMessage,
                    inBroker);
    }
    /**
     * Sends the given event to the CEP module indicated by the provider.
     * 
     * <p>The corresponding CEP module must already exist or the message will not be sent.
     *
     * @param inEvent an <code>Event</code> value containing the event to be sent
     * @param inProvider a <code>String</code> value containing the name of a CEP provider
     */
    public final void send_event_to_cep(Event inEvent,
                                        String inProvider)
    {
        sendEventToCEP(inEvent,
                       inProvider);
    }
    /**
     * Sends the given event to the appropriate subscribers. 
     *
     * @param inEvent an <code>Event</code> value
     */
    public final void send_event(Event inEvent)
    {
        sendEvent(inEvent);
    }
    /**
     * Sends an order to order subscribers.
     * 
     * <p><code>OrderSingle</code> objects passed to this method will be added to the list of submitted orders
     * but other object types will not.  In order to track, for example, <code>OrderReplace</code> and <code>OrderCancel</code>
     * objects, they must have first been created via {@link #cancel_replace(OrderID, OrderSingle, boolean)} and
     * {@link #cancel_order(OrderID, boolean)} respectively. 
     * 
     * @param inData an <code>Object</code> value
     * @return a <code>boolean</code> value indicating whether the object was successfully transmitted or not
     */
    public final boolean send(Object inData)
    {
        return super.send(inData);
    }
    /**
     * Submits a request to cancel the <code>OrderSingle</code> with the given <code>OrderID</code>.
     * 
     * <p>The order must currently be open or this operation will fail. Note that the strategy's concept of
     * open orders is based on its report history origin date as {@link #getReportHistoryOriginDate() specified}.
     * 
     * @param inOrderID an <code>OrderID</code> value containing the ID of the open order to cancel
     * @param inSendOrder a <code>boolean</code> value indicating whether the <code>OrderCancel</code> should be submitted or just returned to the caller
     *   If <code>false</code>, it is the caller's responsibility to submit the <code>OrderCancel</code> with {@link #send(Object)}.
     * @return an <code>OrderCancel</code> value containing the cancel order or <code>null</code> if the <code>OrderCancel</code> could not be constructed
     */
    public final OrderCancel cancel_order(OrderID inOrderID,
                                          boolean inSendOrder)
    {
        return cancelOrder(inOrderID,
                           inSendOrder);
    }
    /**
     * Submits a cancel-replace order for the given <code>OrderID</code> with
     * the given <code>Order</code>.
     * 
     * <p>The order must be open or this call will have no effect.
     * 
     * <p>If <code>inSendOrder</code> is <code>false</code>, it is the caller's responsibility
     * to submit the <code>OrderReplace</code>.
     * 
     * @param inOrderID an <code>OrderID</code> value containing the order to cancel
     * @param inNewOrder an <code>OrderSingle</code> value containing the order with which to replace the existing order
     * @param inSendOrder a <code>boolean</code> value indicating whether the <code>OrderReplace</code> should be submitted or just returned to the caller.  If <code>false</code>,
     *   it is the caller's responsibility to submit the <code>OrderReplace</code> with {@link #send(Object)}.
     * @return an <code>OrderReplace</code> value containing the new order or <code>null</code> if the old order could not be canceled and the new one could not be sent
     */
    public final OrderReplace cancel_replace(OrderID inOrderID,
                                             OrderSingle inNewOrder,
                                             boolean inSendOrder)
    {
        return cancelReplace(inOrderID,
                             inNewOrder,
                             inSendOrder);
    }
    /**
     * Submits cancel requests for all <code>OrderSingle</code> open orders owned by the strategy's owner.
     * 
     * <p> This method will make a best-effort attempt to cancel all orders. If an
     * attempt to cancel one order fails, that order will be skipped and the
     * others will still be attempted in their turn.
     * 
     * @return an <code>int</code> value containing the number of orders for which cancels were submitted
     */
    public final int cancel_all_orders()
    {
        return cancelAllOrders();
    }
    /**
     * Gets the <code>OrderStatus</code> for the given <code>OrderID</code>.
     * 
     * <p>The given <code>OrderID</code> may be any part of the order chain. For example, if an order is replaced,
     * either the original <code>OrderID</code> or the current <code>OrderID</code> will return the same value,
     * although only the current <code>OrderID</code> is open.
     *
     * @param inOrderID an <code>OrderID</code> value or <code>null</code> if the given order cannot be found
     * @return an <code>OrderStatus</code> value
     */
    public final OrderStatus get_order_status(OrderID inOrderID)
    {
        return getOrderStatus(inOrderID);
    }
    /**
     * Gets the collection of open orders represented by the most recent <code>ExecutionReport</code>.
     *
     * @return a <code>Collection&lt;ExecutionReport&gt;</code> value
     */
    public final Collection<ExecutionReport> get_open_orders()
    {
        return getOpenOrders();
    }
    /**
     * Returns the list of open order IDs created during this session in the order they
     * were submitted.
     * 
     * <p>Returns all order IDs regardless of their state.
     * 
     * @return a <code>Set&lt;OrderID&gt;</code> value
     */
    public final Set<OrderID> get_submitted_order_ids()
    {
        return getSubmittedOrderIDs();
    }
    /**
     * Returns the list of <code>OrderID</code> values for open orders created in this
     * session in the order they were submitted.
     *
     * <p>Returns IDs of open orders only.  Orders that were canceled, replaced, filled, or
     * otherwise are no longer open will not be returned.  For orders submitted
     * via {@link AbstractRunningStrategy#cancelReplace(OrderID, OrderSingle, boolean)},
     * the ID of the {@link OrderReplace} value sent to the broker is returned, not the 
     * {@link OrderSingle} value used to create the <code>OrderReplace</code>.
     *
     * @return a <code>Set&lt;OrderID&gt;</code> value
     */
    public final Set<OrderID> get_open_order_ids()
    {
        return getOpenOrderIDs();
    }
    /**
     * Initiates a data flow request.
     * 
     * <p>See {@link DataFlowSupport#createDataFlow(DataRequest[], boolean)}. 
     *
     * @param inAppendDataSink a <code>boolean</code> value indicating if the sink module should be appended to the
     *   data pipeline, if it's not already requested as the last module and the last module is capable of emitting data.
     * @param inRequests a <code>DataRequest[]</code> value containing the ordered list of requests. Each instance
     *   identifies a stage of the data pipeline. The data from the first stage is piped to the next.
     * @return a <code>DataFlowID</code> value containing a unique ID identifying the data flow. The ID can be used to cancel
     *   the data flow request and get more details on it.  Returns <code>null</code> if the request could not be created.
     */
    public final DataFlowID create_data_flow(boolean inAppendDataSink,
                                             DataRequest[] inRequests)
    {
        return createDataFlow(inAppendDataSink,
                              inRequests);
    }
    /**
     * Cancels a data flow identified by the supplied data flow ID.
     *
     * <p>See {@link DataFlowSupport#cancel(DataFlowID)}.
     *
     * @param inDataFlowID a <code>DataFlowID</code> value containing the request handle that was returned from
     *   a prior call to {@link #create_data_flow(boolean, DataRequest[])}
     */
    public final void cancel_data_flow(DataFlowID inDataFlowID)
    {
        cancelDataFlow(inDataFlowID);
    }
    /**
     * Gets the <code>ReportBase</code> values representing the order history of the given <code>OrderID</code>.
     * 
     * <p>The <code>ReportBase</code> objects returned by this call represent the history of the order represented
     * by the given <code>OrderID</code>. if there is no order history for the given <code>OrderID</code>, this operation
     * will return an empty collection.
     * 
     * <p>The values returned by this operation are sorted from newest to oldest: the order's current status is represented
     * by the first element in the collection.
     * 
     * <p>The collection returned by this operation will be updated as the underlying report history changes. The collection itself
     * may not be modified.
     * 
     * <p>The contents of the returned collection are limited by the value returned by {@link #getReportHistoryOriginDate()}. The
     * default value is all reports. No reports with a sending time before the origin date will be returned.
     * 
     * @param inOrderID an <code>OrderID</code> value corresponding to an existing order, either open or closed
     * @return a <code>Deque&lt;ReportBase&gt;</code> value containing the <code>ReportBase</code> objects
     */
    public final Deque<ReportBase> get_execution_reports(OrderID inOrderID)
    {
        return getExecutionReports(inOrderID);
    }
    /**
     * Creates and issues a {@link Notification} at low priority.
     *
     * @param inSubject a <code>String</code> value
     * @param inBody a <code>String</code> value
     */
    public final void notify_low(String inSubject,
                                 String inBody)
    {
        sendNotification(Notification.debug(inSubject,
                                          inBody,
                                          this.toString()));
    }
    /**
     * Creates and issues a {@link Notification} at medium priority.
     *
     * @param inSubject a <code>String</code> value
     * @param inBody a <code>String</code> value
     */
    public final void notify_medium(String inSubject,
                                    String inBody)
    {
        sendNotification(Notification.warn(inSubject,
                                             inBody,
                                             this.toString()));
    }
    /**
     * Creates and issues a {@link Notification} at high priority.
     *
     * @param inSubject a <code>String</code> value
     * @param inBody a <code>String</code> value
     */
    public final void notify_high(String inSubject,
                                  String inBody)
    {
        sendNotification(Notification.error(inSubject,
                                           inBody,
                                           this.toString()));
    }
    /**
     * Emits the given debug message to the strategy log output.
     *
     * @param inMessage a <code>String</code> value
     */
    @Override
    public final void debug(String inMessage)
    {
        super.debug(inMessage);
    }
    /**
     * Emits the given info message to the strategy log output.
     *
     * @param inMessage a <code>String</code> value
     */
    @Override
    public final void info(String inMessage)
    {
        super.info(inMessage);
    }
    /**
     * Emits the given warn message to the strategy log output.
     *
     * @param inMessage a <code>String</code> value
     */
    @Override
    public final void warn(String inMessage)
    {
        super.warn(inMessage);
    }
    /**
     * Emits the given error message to the strategy log output.
     *
     * @param inMessage a <code>String</code> value
     */
    @Override
    public final void error(String inMessage)
    {
        super.error(inMessage);
    }
    /**
     * Returns the list of brokers known to the system.
     *
     * <p>These values can be used to create and send orders with {@link #send_message(Message, BrokerID)}
     * or {@link #send(Object)}.
     *
     * @return a <code>BrokerStatus[]</code> value
     */
    public final BrokerStatus[] get_brokers()
    {
        return getBrokers();
    }
    /**
     * Gets the position in the given <code>Equity</code> at the given point in time.
     *
     * <p>Note that this method will not retrieve <code>Option</code> positions.  To retrieve
     * <code>Option</code> positions, use {@link #get_option_position_as_of(Date, String, String, BigDecimal, OptionType)}. 
     * 
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @param inSymbol a <code>String</code> value containing the <code>Equity</code> symbol
     * @return a <code>BigDecimal</code> value or <code>null</code> if no position could be found 
     */
    public final BigDecimal get_position_as_of(Date inDate,
                                               String inSymbol)
    {
        return getPositionAsOf(inDate,
                               inSymbol);
    }
    /**
     * Gets all open <code>Equity</code> positions at the given point in time.
     *
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @return a <code>Map&lt;PositionKey&lt;Equity&gt;,BigDecimal&gt;</code> value
     */
    public final Map<PositionKey<Equity>,BigDecimal> get_all_positions_as_of(Date inDate)
    {
        return getAllPositionsAsOf(inDate);
    }
    /**
     * Gets the position in the given <code>Option</code> at the given point in time.
     *
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @param inOptionRoot a <code>String</code> value
     * @param inExpiry a <code>String</code> value
     * @param inStrikePrice a <code>BigDecimal</code> value
     * @param inType an <code>OptionType</code> value
     * @return a <code>BigDecimal</code> value or <code>null</code> if no position could be found
     */
    public final BigDecimal get_option_position_as_of(Date inDate,
                                                      String inOptionRoot,
                                                      String inExpiry,
                                                      BigDecimal inStrikePrice,
                                                      OptionType inType)
    {
        return getOptionPositionAsOf(inDate,
                                     inOptionRoot,
                                     inExpiry,
                                     inStrikePrice,
                                     inType);
    }
    /**
     * Gets all open <code>Option</code> positions at the given point in time.
     *
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @return a <code>Map&lt;PositionKey&lt;Option&gt;,BigDecimal&gt;</code> value
     */
    public final Map<PositionKey<Option>,BigDecimal> get_all_option_positions_as_of(Date inDate)
    {
        return getAllOptionPositionsAsOf(inDate);
    }
    /**
     * Gets open positions for the options specified by the given option roots at the given point in time. 
     *
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @param inOptionRoots a <code>String[]</code> value containing the specific option roots for which to search
     * @return a <code>Map&lt;PositionKey&lt;Option&gt;,BigDecimal&gt;</code> value
     */
    public final Map<PositionKey<Option>,BigDecimal> get_option_positions_as_of(Date inDate,
                                                                                String[] inOptionRoots)
    {
        return getOptionPositionsAsOf(inDate,
                                      inOptionRoots);
    }
    /**
     * Gets the underlying symbol for the given option root, if available.
     *
     * @param inOptionRoot a <code>String</code> value containing an option root
     * @return a <code>String</code> value containing the symbol for the underlying instrument or <code>null</code> if
     *  no underlying instrument could be found
     */
    public final String get_underlying(String inOptionRoot)
    {
        return getUnderlying(inOptionRoot);
    }
    /**
     * Gets the set of of known option roots for the given underlying symbol. 
     *
     * @param inUnderlying a <code>String</code> value containing the symbol of an underlying instrument
     * @return a <code>Collection&lt;String&gt;</code> value sorted lexicographically by option root or <code>null</code>
     *  if no option roots could be found
     */
    public final Collection<String> get_option_roots(String inUnderlying)
    {
        return getOptionRoots(inUnderlying);
    }
    /**
     * Gets all open <code>Future</code> positions at the given point in time.
     *
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @return a <code>Map&lt;PositionKey&lt;Option&gt;,BigDecimal&gt;</code> value
     */
    public final Map<PositionKey<Future>,BigDecimal> get_all_future_positions_as_of(Date inDate)
    {
        return getAllFuturePositionsAsOf(inDate);
    }
    /**
     * Gets the position in the given <code>Future</code> at the given point in time.
     *
     * <p>Note that this method will not retrieve <code>Option</code> or <code>Equity</code> positions.  To retrieve
     * <code>Option</code> positions, use {@link #getOptionPositionAsOf(Date, String, String, BigDecimal, OptionType)}.
     *   To retrieve
     * <code>Equity</code> positions, use {@link #getPositionAsOf(Date, String)}.
     * 
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @param inUnderlyingSymbol a <code>String</code> value containing the underlying <code>Future</code> symbol
     * @param inExpirationMonth a <code>FutureExpirationMonth</code> value
     * @param inExpirationYear an <code>int</code> value
     * @return a <code>BigDecimal</code> value or <code>null</code> if no position could be found 
     */
    public final BigDecimal get_future_position_as_of(Date inDate,
                                                      String inUnderlyingSymbol,
                                                      FutureExpirationMonth inExpirationMonth,
                                                      int inExpirationYear)
    {
        return getFuturePositionAsOf(inDate,
                                     inUnderlyingSymbol,
                                     inExpirationMonth,
                                     inExpirationYear);
    }
    /**
     * Gets all open <code>Currency</code> positions at the given point in time.
     *
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @return a <code>Map&lt;PositionKey&lt;Currency&gt;,BigDecimal&gt;</code> value
     */
    public final Map<PositionKey<Currency>,BigDecimal> get_all_currency_positions_as_of(Date inDate)
    {
        return getAllCurrencyPositionsAsOf(inDate);
    }
    /**
     * Gets the position in the given <code>Currency</code> at the given point in time.
     * 
     * @param inDate a <code>Date</code> value indicating the point in time for which to search
     * @param inUnderlyingSymbol a <code>String</code> value containing the underlying <code>Currency</code> symbol
     * @return a <code>BigDecimal</code> value or <code>null</code> if no position could be found 
     */
    public final BigDecimal get_currency_position_as_of(Date inDate,
                                                      String inUnderlyingSymbol)
    {
        return getCurrencyPositionAsOf(inDate,
                                     inUnderlyingSymbol);
    }
    
    
    /**
     * Gets the {@link ModuleURN} of this strategy.
     *
     * @return a <code>ModuleURN</code> value
     */
    public final ModuleURN get_urn()
    {
        return getURN();
    }
    /**
     * Gets the user data associated with the current user. 
     *
     * @return a <code>Properties</code> value
     */
    public final Properties get_user_data()
    {
        return getUserData();
    }
    /**
     * Sets the user data associated with the current user.
     *
     * @param inData a <code>Properties</code> value
     */
    public final void set_user_data(Properties inData)
    {
        setUserData(inData);
    }
}
