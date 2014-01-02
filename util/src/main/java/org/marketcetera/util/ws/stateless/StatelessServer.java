package org.marketcetera.util.ws.stateless;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.marketcetera.util.log.SLF4JLoggerProxy;
import org.marketcetera.util.misc.ClassVersion;

/* $License$ */

/**
 * A server node for stateless communication.
 * 
 * @author tlerios@marketcetera.com
 * @since 1.0.0
 * @version $Id$
 */
@ClassVersion("$Id$")
public class StatelessServer
        extends Node
{
    /**
     * Create a new StatelessServer instance.
     *
     * @param inHost a <code>String</code> value
     * @param inPort an <code>int</code> value
     * @param inContextClasses a <code>Class&lt;?&gt;...</code> value
     */
    public StatelessServer(String inHost,
                           int inPort,
                           Class<?>...inContextClasses)
    {
        super(inHost,
              inPort);
        contextClasses = inContextClasses;
    }
    /**
     * Creates a new server node with the given server host name and
     * port.
     *
     * @param host The host name.
     * @param port The port.
     */    
    public StatelessServer
        (String host,
         int port)
    {
        this(host,
             port,
             (Class<?>[])null);
    }
    /**
     * Creates a new server node with the default server host name and
     * port.
     */    
    public StatelessServer()
    {
        this(DEFAULT_HOST,DEFAULT_PORT);
    }
    /**
     * Publishes the given service interface, supported by the given
     * implementation, and returns a handle that can be used to stop
     * the interface.
     *
     * @param impl The implementation.
     * @param iface The interface class.
     *
     * @return The handle.
     */
    public <T extends StatelessServiceBase> ServiceInterface publish(T impl,
                                                                     Class<T> iface)
    {
        factory = new JaxWsServerFactoryBean();
        Map<String,Object> props = factory.getProperties();
        if(props == null) {
            props = new HashMap<String,Object>();
        }
        if(contextClasses != null) {
            SLF4JLoggerProxy.debug(this,
                                   "Using additional context: {}", //$NON-NLS-1$
                                   Arrays.toString(contextClasses));
            props.put("jaxb.additionalContextClasses",  //$NON-NLS-1$
                      contextClasses);
        }
        factory.setProperties(props); 
        factory.setServiceClass(iface);
        factory.setAddress(getConnectionUrl(iface));
        factory.setServiceBean(impl);
        server = factory.create();
        return new ServiceInterface(server);
    }
    /**
     * Shuts down the receiver.
     */
    public void stop()
    {
        if(server != null) {
            server.stop();
            server.destroy();
            server = null;
        }
        if(factory != null) {
            factory.getBus().shutdown(true);
            factory = null;
        }
    }
    /**
     * context classes to add to the server context, if any
     */
    private final Class<?>[] contextClasses;
    /**
     * published server object
     */
    private Server server;
    /**
     * factory used to create server objects
     */
    private JaxWsServerFactoryBean factory;
}
