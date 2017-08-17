package org.marketcetera.trade.modules;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Deque;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.marketcetera.brokers.Broker;
import org.marketcetera.module.DataFlowID;
import org.marketcetera.modules.fix.FixDataRequest;
import org.marketcetera.modules.headwater.HeadwaterModule;
import org.marketcetera.quickfix.FIXMessageFactory;
import org.marketcetera.quickfix.FIXMessageUtil;
import org.marketcetera.quickfix.FIXVersion;
import org.marketcetera.trade.ExecutionTransType;
import org.marketcetera.trade.ExecutionType;
import org.marketcetera.trade.OrderSingle;
import org.marketcetera.trade.OrderStatus;
import org.marketcetera.trade.TradeMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import quickfix.Message;

/* $License$ */

/**
 * Test {@link TradeMessageConverterModule}.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/test.xml" })
public class TradeMessageConverterModuleTest
        extends TradeModulesTestBase
{
    /**
     * Test the wrong data type.
     *
     * @throws Exception if an unexpected failure occurs
     */
    @Test
    public void testWrongDataType()
            throws Exception
    {
        String headwaterInstance = generateHeadwaterInstanceName();
        Deque<Object> receivedData = Lists.newLinkedList();
        DataFlowID dataFlow = moduleManager.createDataFlow(getTradeMessageConverterDataRequest(headwaterInstance,
                                                                                               receivedData));
        dataFlows.add(dataFlow);
        HeadwaterModule.getInstance(headwaterInstance).emit(this,
                                                            dataFlow);
        assertTrue(receivedData.isEmpty());
    }
    /**
     * Test the conversion of a valid message.
     *
     * @throws Exception if an unexpected failure occurs
     */
    @Ignore@Test
    public void testValidMessage()
            throws Exception
    {
        Broker target = brokerService.getBroker(selector.getSelectedBrokerId());
        FixDataRequest fixDataRequest = new FixDataRequest();
        fixDataRequest.setIncludeAdmin(false);
        fixDataRequest.setIncludeApp(true);
        fixDataRequest.getMessageWhiteList().clear();
        fixDataRequest.getMessageBlackList().clear();
        // this data flow initiates flow to the outgoing FIX session through the order converter
        String initiatorHeadwaterInstance = generateHeadwaterInstanceName();
        dataFlows.add(moduleManager.createDataFlow(getFullInitiatorDataSendRequest(fixDataRequest,
                                                                                   initiatorHeadwaterInstance)));
        // the messages will go to the acceptor module, create a data flow on that side, too, that will catch the incoming messages
        Deque<Object> acceptorIncomingMessages = Lists.newLinkedList();
        dataFlows.add(moduleManager.createDataFlow(getAcceptorReceiveDataRequest(fixDataRequest,
                                                                                 acceptorIncomingMessages)));
        // this data flow sends messages through the acceptor back to the initiator
        String acceptorHeadwaterInstance = generateHeadwaterInstanceName();
        dataFlows.add(moduleManager.createDataFlow(getAcceptorSendDataRequest(fixDataRequest,
                                                                              acceptorHeadwaterInstance)));
        // this data flow will receive messages from the initiator (TradeMessage types)
        Deque<Object> initiatorIncomingMessages = Lists.newLinkedList();
        dataFlows.add(moduleManager.createDataFlow(getFullInitiatorReceiveDataRequest(fixDataRequest,
                                                                                      initiatorIncomingMessages)));
        OrderSingle order = generateOrder();
        HeadwaterModule.getInstance(initiatorHeadwaterInstance).emit(order);
        waitForMessages(1,
                        acceptorIncomingMessages);
        Message receivedOrder = (Message)acceptorIncomingMessages.removeFirst();
        Message receivedOrderAck = FIXMessageUtil.createExecutionReport(receivedOrder,
                                                                        OrderStatus.New,
                                                                        ExecutionType.New,
                                                                        ExecutionTransType.New,
                                                                        "Ack");
        FIXMessageFactory messageFactory = FIXVersion.FIX42.getMessageFactory();
        messageFactory.addTransactionTimeIfNeeded(receivedOrderAck);
        FIXMessageUtil.setSessionId(receivedOrderAck,
                                    FIXMessageUtil.getReversedSessionId(target.getSessionId()));
        HeadwaterModule.getInstance(acceptorHeadwaterInstance).emit(receivedOrderAck);
        waitForMessages(1,
                        initiatorIncomingMessages);
        TradeMessage receivedExecutionReport = (TradeMessage)initiatorIncomingMessages.removeFirst();
        assertNotNull(receivedExecutionReport);
    }
}
