package org.marketcetera.rpc.base;

import java.math.BigDecimal;
import java.math.RoundingMode;

/* $License$ */

/**
 * Provides common behaviors for {@link BaseRpc} services.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
public abstract class BaseUtil
{
    /**
     * Get the value represented by the given qty object.
     *
     * @param inQty a <code>BaseRpc.Qty</code> value
     * @return a <code>BigDecimal</code> value
     */
    public static BigDecimal getScaledQuantity(BaseRpc.Qty inQty)
    {
        BigDecimal base = new BigDecimal(inQty.getQty());
        int scale = inQty.getScale();
        base = base.setScale(scale,
                             RoundingMode.HALF_UP);
        base = base.movePointLeft(scale);
        return base;
    }
    /**
     * Get a qty value from the given input.
     *
     * @param inValue a <code>BigDecimal</code>value
     * @return a <code>BaseRpc.Qty</code> value
     */
    public static BaseRpc.Qty getQtyValueFrom(BigDecimal inValue)
    {
        BigDecimal quantity = inValue.setScale(6,
                                               RoundingMode.HALF_UP);
        quantity = quantity.movePointRight(6);
        BaseRpc.Qty.Builder qtyBuilder = BaseRpc.Qty.newBuilder();
        qtyBuilder.setQty(quantity.longValue());
        qtyBuilder.setScale(6);
        return qtyBuilder.build();
    }
}
