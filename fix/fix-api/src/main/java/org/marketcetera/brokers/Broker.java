package org.marketcetera.brokers;

import java.util.List;
import java.util.Set;

import org.marketcetera.algo.BrokerAlgoSpec;
import org.marketcetera.fix.FixSession;
import org.marketcetera.quickfix.FIXVersion;
import org.marketcetera.trade.BrokerID;

/* $License$ */

/**
 *
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
public interface Broker
{
    /**
     * 
     *
     *
     * @return a <code>BrokerID</code> value
     */
    BrokerID getBrokerId();
    /**
     * 
     *
     *
     * @return a <code>FixSession</code> value
     */
    FixSession getFixSession();
    /**
     * 
     *
     *
     * @return
     */
    List<MessageModifier> getOrderModifiers();
    /**
     * 
     *
     *
     * @return
     */
    List<MessageModifier> getResponseModifiers();
    /**
     * 
     *
     *
     * @return
     */
    FIXVersion getFixVersion();
    /**
     *
     *
     * @return
     */
    Set<BrokerAlgoSpec> getBrokerAlgos();
}
