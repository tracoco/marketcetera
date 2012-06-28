package org.marketcetera.trade;

import org.marketcetera.core.attributes.ClassVersion;

/* $License$ */

/**
 * Indicates that the implementer can produce a CFI code.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: HasCFICode.java 16063 2012-01-31 18:21:55Z colin $
 * @since 2.1.0
 */
@ClassVersion("$Id: HasCFICode.java 16063 2012-01-31 18:21:55Z colin $")
public interface HasCFICode
{
    /**
     * Gets the CFI Code.
     *
     * @return a <code>char</code> value
     */
    public char getCfiCode();
}
