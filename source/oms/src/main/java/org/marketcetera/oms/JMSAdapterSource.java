package org.marketcetera.oms;

import org.jcyclone.core.internal.ISystemManager;
import org.jcyclone.core.stage.IStageManager;
import org.marketcetera.core.*;
import org.marketcetera.jcyclone.FIXStageOutput;
import org.marketcetera.jcyclone.JCyclonePluginSource;
import org.marketcetera.jcyclone.JMSOutputInfo;
import org.marketcetera.jms.JMSAdapter;
import org.marketcetera.quickfix.ConnectionConstants;

import javax.jms.*;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Starting stage for all the incoming JMS messages that are later
 * thrown on the JCyclone pipe to order manager
 * @author gmiller
 * @author toli
 * @version $Id$
 */

@ClassVersion("$Id$")
public class JMSAdapterSource extends JCyclonePluginSource {

    public static final String INCOMING_QUEUE_NAME = "incomingQ";
    public static final String OUTGOING_TOPIC_NAME = "outgoingTopic";
    private JMSAdapter jmsAdapter;
    private static final String FIX_PREAMBLE = "8=FIX";

    public JMSAdapterSource(){
        // jcyclone constructor
    }

    public void initialize(IStageManager stagemgr, ISystemManager sysmgr, String pluginName) throws Exception {
        super.initialize(stagemgr, sysmgr, pluginName);

        OrderManagementSystem oms = OrderManagementSystem.getOMS();
        ConfigData props = oms.getInitProps();
        String incomingQueueName = props.get(ConnectionConstants.JMS_INCOMING_QUEUE_KEY, "");
        String outgoingTopicName = props.get(ConnectionConstants.JMS_OUTGOING_TOPIC_KEY, "");
        String connectionFactory = props.get(ConnectionConstants.JMS_CONNECTION_FACTORY_KEY, "");
        String initialContextFactory = props.get(ConnectionConstants.JMS_CONTEXT_FACTORY_KEY, "");
        String url = props.get(ConnectionConstants.JMS_URL_KEY, "");

        jmsAdapter = new JMSAdapter(initialContextFactory, url, connectionFactory, true);

        if(incomingQueueName != null) {
            jmsAdapter.connectIncomingQueue(INCOMING_QUEUE_NAME, incomingQueueName, Session.AUTO_ACKNOWLEDGE);
        } else {
            throw new InitializationException(MessageKey.JMS_QUEUE_CONNECT_ERROR.getLocalizedMessage(incomingQueueName));
        }

        if(outgoingTopicName != null) {
            jmsAdapter.connectOutgoingTopic(OUTGOING_TOPIC_NAME, outgoingTopicName, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = jmsAdapter.getOutgoingTopicPublisher(OUTGOING_TOPIC_NAME);
            Session session = jmsAdapter.getOutgoingTopicSession(OUTGOING_TOPIC_NAME);
            oms.registerOutgoingJMSInfo(new JMSOutputInfo(producer, session, outgoingTopicName));
        } else {
            throw new InitializationException(MessageKey.JMS_TOPIC_CONNECT_ERROR.getLocalizedMessage(outgoingTopicName));
        }

        setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    quickfix.Message qfMessage = null;
                    if(message instanceof TextMessage) {
                        if(LoggerAdapter.isDebugEnabled(this)) {
                            LoggerAdapter.debug("Received JMS msg: "+message, this);
                        }
                        // todo: handle validation when creating quickfix message
                        qfMessage = new quickfix.Message(((TextMessage)message).getText());
                    } else if (message instanceof BytesMessage){
                        LoggerAdapter.debug("Received JMS msg: "+message, this);
                        try {
                            BytesMessage bytesMessage = ((BytesMessage)message);
                            int length = (int)bytesMessage.getBodyLength();
                            byte [] buf = new byte[length];

                            String possibleString = new String(buf, "UTF-16");
                            if (possibleString.startsWith(FIX_PREAMBLE)){
                                qfMessage = new quickfix.Message(possibleString);
                            }
                        } catch (Exception ex) {
                            LoggerAdapter.error(OMSMessageKey.ERROR_DECODING_MESSAGE.getLocalizedMessage(), ex, this);

                        }
                    }
                    if (qfMessage != null){
                        getNextStage().enqueue(new FIXStageOutput(qfMessage, OrderManagementSystem.getOMS().getDefaultSessionID()));
                    }
                } catch (Exception ex) {
                    LoggerAdapter.error(OMSMessageKey.ERROR_SENDING_QF_MESSAGE.getLocalizedMessage(), ex, this);
                    // TODO: panic
                }
            }
        });
        jmsAdapter.start();
    }

    // used by stages and plugins
    public void destroy() throws Exception {
        jmsAdapter.shutdown();
        super.destroy();
    }

    public void setMessageListener(MessageListener listener) throws JMSException {
        jmsAdapter.getIncomingQueueReceiver(INCOMING_QUEUE_NAME).setMessageListener(listener);
    }
}
