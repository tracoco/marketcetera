package org.marketcetera.clienttest;

import java.util.Deque;

import org.marketcetera.core.PlatformServices;
import org.marketcetera.event.Event;
import org.marketcetera.marketdata.AssetClass;
import org.marketcetera.marketdata.Content;
import org.marketcetera.marketdata.MarketDataRequestBuilder;
import org.marketcetera.mdclient.MarketDataContextClassProvider;
import org.marketcetera.mdclient.rpc.client.MarketDataRpcClient;
import org.marketcetera.mdclient.rpc.client.MarketDataRpcClientFactory;
import org.marketcetera.mdclient.rpc.client.MarketDataRpcClientParameters;
import org.marketcetera.util.log.SLF4JLoggerProxy;

/* $License$ */

/**
 * Demonstrates how to connect to MATP market data services from an external application.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
public class MarketDataClientTest
{
    /**
     * Main run method.
     *
     * @param inArgs a <code>String[]</code> value
     */
    public static void main(String[] inArgs)
    {
        SLF4JLoggerProxy.info(ClientTest.class,
                              "Starting market data client test");
        try {
            MarketDataRpcClientParameters parameters = new MarketDataRpcClientParameters();
            parameters.setUsername("user");
            parameters.setPassword("password");
            parameters.setHostname("locahost");
            parameters.setPort(8998);
            parameters.setContextClassProvider(new MarketDataContextClassProvider());
            MarketDataRpcClient marketDataClient = new MarketDataRpcClientFactory().create(parameters);
            marketDataClient.start();
            SLF4JLoggerProxy.info(ClientTest.class,
                                  "Connected to market data nexus: {}",
                                  marketDataClient.isRunning());
            MarketDataRequestBuilder requestBuilder = MarketDataRequestBuilder.newRequest();
            requestBuilder = requestBuilder.withContent(Content.TOP_OF_BOOK,Content.LATEST_TICK).withSymbols("METC").withAssetClass(AssetClass.EQUITY);
            long requestId = marketDataClient.request(requestBuilder.create(),
                                                      true);
            for(int i=0;i<10;i++) {
                Thread.sleep(1000);
                Deque<Event> events = marketDataClient.getEvents(requestId);
                SLF4JLoggerProxy.info(ClientTest.class,
                                      "Retrieved {}",
                                      events);
            }
            marketDataClient.cancel(requestId);
            marketDataClient.stop();
        } catch (Exception e) {
            PlatformServices.handleException(MarketDataClientTest.class,
                                             "Error executing market data client test",
                                             e);
        }
        SLF4JLoggerProxy.info(ClientTest.class,
                              "Ending market data client test");
    }
}
