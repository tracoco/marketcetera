package org.marketcetera.util.ws.stateful;

import javax.jws.WebService;
import org.marketcetera.util.misc.ClassVersion;
import org.marketcetera.util.ws.stateless.StatelessClientContext;
import org.marketcetera.util.ws.wrappers.RemoteException;
import org.marketcetera.util.ws.tags.SessionId;

/**
 * An authentication service interface.
 * 
 * @author tlerios@marketcetera.com
 * @since $Release$
 * @version $Id$
 */

/* $License$ */

@WebService
@ClassVersion("$Id$") //$NON-NLS-1$
public interface AuthService
    extends ServiceBase
{

    /**
     * Logs in the client with the given context, provided the given
     * credentials are acceptable.
     *
     * @param context The context.
     * @param user The user name.
     * @param password The password. Upon return, its contents are
     * cleared (on the server) by overwriting all prior characters
     * with the nul ('\0') character.
     *
     * @return The ID of the new session.
     *
     * @throws RemoteException Thrown if the operation cannot be
     * completed.
     */

    SessionId login
        (StatelessClientContext context,
         String user,
         char[] password)
        throws RemoteException;

    /**
     * Logs out the client with the given context. This method is a
     * no-op if there is no active session for that client. 
     *
     * @param context The context.
     *
     * @throws RemoteException Thrown if the operation cannot be
     * completed.
     */

    void logout
        (ClientContext context)
        throws RemoteException;
}
