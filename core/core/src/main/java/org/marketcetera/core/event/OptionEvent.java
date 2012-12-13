package org.marketcetera.core.event;

import java.math.BigDecimal;

import org.marketcetera.core.options.ExpirationType;

/* $License$ */

/**
 * Indicates that the implementing class represents an option event.
 *
 * @version $Id$
 * @since 2.0.0
 */
public interface OptionEvent
        extends HasUnderlyingInstrument, HasOption, Event, HasProviderSymbol
{
    /**
     * Gets the expiration type of the event. 
     *
     * @return an <code>ExpirationType</code> value
     */
    public ExpirationType getExpirationType();
    /**
     * Gets the multiplier value of the option event.
     *
     * @return a <code>BigDecimal</code> value
     */
    public BigDecimal getMultiplier();
    /**
     * Indicates if the option event has deliverables. 
     *
     * @return a <code>boolean</code> value
     */
    public boolean hasDeliverable();
    /**
     * Returns the original provider symbol of the option, if available. 
     *
     * @return a <code>String</code> value or <code>null</code> if the option event
     *  did not have a provider symbol
     */
    public String getProviderSymbol();
}
