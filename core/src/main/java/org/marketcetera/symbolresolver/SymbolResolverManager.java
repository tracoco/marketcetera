package org.marketcetera.symbolresolver;

import java.util.List;

import org.marketcetera.trade.Instrument;
import org.marketcetera.util.misc.ClassVersion;

/* $License$ */

/**
 * Resolves symbols to instruments.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id: SymbolResolverManager.java 82384 2012-07-20 19:09:59Z colin $
 * @since $Release$
 */
@ClassVersion("$Id: SymbolResolverManager.java 82384 2012-07-20 19:09:59Z colin $")
public interface SymbolResolverManager
{
    /**
     * Gets the symbol resolvers to use to resolve symbols in the order they are to be applied.
     * 
     * <p>Changes made to the returned list are not reflected in the underlying list.
     *
     * @return a <code>List&lt;SymbolResolver&gt;</code>
     */
    public List<SymbolResolver> getSymbolResolvers();
    /**
     * Sets the symbol resolvers to use to resolve symbols in the order they are to be applied.
     * 
     * <p>Changes made to the passed list are not reflected in the underlying list.
     *
     * @param inResolvers a <code>List&lt;SymbolResolver&gt;</code> value
     */
    public void setSymbolResolvers(List<SymbolResolver> inResolvers);
    /**
     * Resolves the given symbol to an <code>Instrument</code>.
     *
     * @param inSymbol a <code>String</code> value
     * @return an <code>Instrument</code> value
     * @throws NoInstrumentForSymbol if the symbol cannot be resolved
     */
    public Instrument resolve(String inSymbol);
    /**
     * Resolves the given symbol to an <code>Instrument</code> using the given context.
     *
     * @param inSymbol a <code>String</code> value
     * @param inContext an <code>Object</code> value
     * @return an <code>Instrument</code> value
     * @throws NoInstrumentForSymbol if the symbol cannot be resolved
     */
    public Instrument resolve(String inSymbol,
                              Object inContext);
}
