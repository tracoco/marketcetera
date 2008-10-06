package org.marketcetera.client;

import org.marketcetera.util.misc.ClassVersion;
import org.marketcetera.module.DisplayName;

/* $License$ */
/**
 * Management interface for the Client Module Provider.
 *
 * @author anshul@marketcetera.com
 * @version $Id$
 * @since 1.0.0
 */
@DisplayName("Management Interface for Client Module Provider")  //$NON-NLS-1$
@ClassVersion("$Id$") //$NON-NLS-1$
public interface ClientModuleFactoryMXBean {
    /**
     * The Server URL to connect to.
     *
     * @return the Server URL
     */
    @DisplayName("The Server URL")  //$NON-NLS-1$
    String getURL();

    /**
     * Sets the Server URL to connect to.
     *
     * @param inURL the Server URL
     */
    @DisplayName("The Server URL")  //$NON-NLS-1$
    void setURL(
            @DisplayName("The Server URL")  //$NON-NLS-1$
            String inURL);

    /**
     * The username to use when connecting to the server.
     *
     * @return the username.
     */
    @DisplayName("The Username for connecting to the server")  //$NON-NLS-1$
    String getUsername();

    /**
     * Sets the user name to use when connecting to the server.
     *
     * @param inUsername the username.
     */
    @DisplayName("The Username for connecting to the server")  //$NON-NLS-1$
    void setUsername(
            @DisplayName("The Username for connecting to the server")  //$NON-NLS-1$
            String inUsername);

    /**
     * Sets the password to use when connecting to the server.
     *
     * @param inPassword the password.
     */
    @DisplayName("The Password for connecting to the server")  //$NON-NLS-1$
    void setPassword(String inPassword);

    /**
     * The port number of the server.
     *
     * @return the server port number.
     */
    @DisplayName("The server port number")   //$NON-NLS-1$
    public int getPort();

    /**
     * Sets the server port number.
     *
     * @param inPort the server port number.
     */
    @DisplayName("The server port number")   //$NON-NLS-1$
    public void setPort(
            @DisplayName("The server port number")   //$NON-NLS-1$
            int inPort);

    /**
     * The server hostname.
     *
     * @return the server hostname.
     */
    @DisplayName("The server host name")   //$NON-NLS-1$
    public String getHostname();

    /**
     * Sets the server host name.
     *
     * @param inHostname the server hostname.
     */
    @DisplayName("The server host name")  //$NON-NLS-1$
    public void setHostname(
            @DisplayName("The server host name")   //$NON-NLS-1$
            String inHostname);

    /**
     * The string that should be prefixed to any orderIDs generated by
     * the client based on IDs issued by the server.
     *
     * @return the orderID prefix.
     */
    @DisplayName("The order ID prefix")  //$NON-NLS-1$
    String getIDPrefix();

    /**
     * Sets the string that should be prefixed to any orderIDs generated by
     * the client based on IDs issued by the server.
     *
     * @param inIDPrefix the orderID prefix.
     */
    @DisplayName("The order ID prefix")  //$NON-NLS-1$
    void setIDPrefix(
            @DisplayName("The order ID prefix")  //$NON-NLS-1$
            String inIDPrefix);
}