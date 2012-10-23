package org.marketcetera.core.position;

import java.math.BigDecimal;
import java.util.Map;

/* $License$ */

/**
 * Interface for providing incoming position data to a {@link PositionEngine}.
 * The incoming position is used to calculate
 * {@link PositionMetrics#getPositionPL() position PL}.
 * 
 * @version $Id: IncomingPositionSupport.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.5.0
 */
public interface IncomingPositionSupport {

    /**
     * Returns the size of the incoming position for the given position key.
     * 
     * The returned value should be the size of the incoming position at the
     * time the method is called. Implementations are assumed to have an
     * understanding of the time period that defines the incoming position.
     * 
     * @param key
     *            the position key tuple
     * @return the size of the incoming position, cannot be null
     */
    BigDecimal getIncomingPositionFor(PositionKey<?> key);

    /**
     * Returns all incoming positions.
     * 
     * The returned values should be the size of the incoming positions at the
     * time the method is called. Implementations are assumed to have an
     * understanding of the time period that defines the incoming position.
     * 
     * @return the incoming positions, cannot be null
     */
    Map<? extends PositionKey<?>, BigDecimal> getIncomingPositions();
}
