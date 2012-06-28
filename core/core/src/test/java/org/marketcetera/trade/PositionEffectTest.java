package org.marketcetera.trade;

import static org.marketcetera.trade.PositionEffect.Close;
import static org.marketcetera.trade.PositionEffect.Open;
import static org.marketcetera.trade.PositionEffect.Unknown;
import static org.marketcetera.trade.PositionEffect.values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.marketcetera.core.Pair;
import org.marketcetera.core.attributes.ClassVersion;

/* $License$ */
/**
 * Tests {@link PositionEffect}
 *
 * @author anshul@marketcetera.com
 * @version $Id: PositionEffectTest.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.0.0
 */
@ClassVersion("$Id: PositionEffectTest.java 16063 2012-01-31 18:21:55Z colin $") //$NON-NLS-1$
public class PositionEffectTest extends FIXCharEnumTestBase<PositionEffect> {

    @Override
    protected PositionEffect getInstanceForFIXValue(Character inFIXValue) {
        return PositionEffect.getInstanceForFIXValue(inFIXValue);
    }

    @Override
    protected Character getFIXValue(PositionEffect e) {
        return e.getFIXValue();
    }

    @Override
    protected PositionEffect unknownInstance() {
        return Unknown;
    }

    @Override
    protected List<PositionEffect> getValues() {
        return Arrays.asList(values());
    }
    @Override
    protected List<Pair<PositionEffect,Character>> knownValues()
    {
        List<Pair<PositionEffect,Character>> values = new ArrayList<Pair<PositionEffect,Character>>();
        values.add(new Pair<PositionEffect, Character>(Open, quickfix.field.PositionEffect.OPEN));
        values.add(new Pair<PositionEffect, Character>(Close, quickfix.field.PositionEffect.CLOSE));
        return values;
    }
}