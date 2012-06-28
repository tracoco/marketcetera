package org.marketcetera.util.ws.stateful;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.marketcetera.core.attributes.ClassVersion;
import org.marketcetera.util.ws.stateless.StatelessClientContext;

/**
 * An authenticator which accepts a single pair of user name/password
 * credentials.
 * 
 * @author tlerios@marketcetera.com
 * @since 1.0.0
 * @version $Id: FixedAuthenticator.java 82324 2012-04-09 20:56:08Z colin $
 */

/* $License$ */

@ClassVersion("$Id: FixedAuthenticator.java 82324 2012-04-09 20:56:08Z colin $")
public class FixedAuthenticator
    implements Authenticator
{

    // CLASS DATA.

    private static final String DEFAULT_USER=
        "metc"; //$NON-NLS-1$
    private static final char[] DEFAULT_PASSWORD=
        "metc".toCharArray(); //$NON-NLS-1$


    // Authenticator.

    @Override
    public boolean shouldAllow
        (StatelessClientContext context,
         String user,
         char[] password)
    {
        return (ObjectUtils.equals(DEFAULT_USER,user) &&
                ArrayUtils.isEquals(DEFAULT_PASSWORD,password));
    }
}
