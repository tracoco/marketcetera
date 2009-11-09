package org.marketcetera.photon.views;

import java.math.BigDecimal;

import org.marketcetera.photon.commons.databinding.ITypedObservableValue;
import org.marketcetera.photon.ui.databinding.OptionObservable;
import org.marketcetera.trade.Instrument;
import org.marketcetera.trade.OptionType;
import org.marketcetera.trade.OrderCapacity;
import org.marketcetera.trade.PositionEffect;
import org.marketcetera.util.misc.ClassVersion;

/* $License$ */

/**
 * The model for an option order ticket.
 * 
 * @author gmiller
 * @author <a href="mailto:will@marketcetera.com">Will Horn</a>
 * @version $Id$
 * @since 1.0.0
 */
@ClassVersion("$Id")
public class OptionOrderTicketModel extends OrderTicketModel {

    private final ITypedObservableValue<OrderCapacity> mOrderCapacity;
    private final ITypedObservableValue<PositionEffect> mPositionEffect;
    private final ITypedObservableValue<String> mSymbol;
    private final ITypedObservableValue<String> mOptionExpiry;
    private final ITypedObservableValue<OptionType> mOptionType;
    private final ITypedObservableValue<BigDecimal> mStrikePrice;

    /**
     * Constructor.
     */
    public OptionOrderTicketModel() {
        ITypedObservableValue<Instrument> instrument = getOrderObservable()
                .observeInstrument();
        OptionObservable optionObservable = new OptionObservable(instrument);
        mSymbol = optionObservable.observeSymbol();
        mOptionExpiry = optionObservable.observeExpiry();
        mStrikePrice = optionObservable.observeStrikePrice();
        mOptionType = optionObservable.observeOptionType();
        mOrderCapacity = getOrderObservable().observeOrderCapacity();
        mPositionEffect = getOrderObservable().observePositionEffect();
    }

    @Override
    public final ITypedObservableValue<String> getSymbol() {
        return mSymbol;
    }

    /**
     * Returns an observable that tracks the expiry of the current order.
     * 
     * @return the option expiry observable
     */
    public final ITypedObservableValue<String> getOptionExpiry() {
        return mOptionExpiry;
    }

    /**
     * Returns an observable that tracks the strike price of the current order.
     * 
     * @return the option strike price observable
     */
    public final ITypedObservableValue<BigDecimal> getStrikePrice() {
        return mStrikePrice;
    }

    /**
     * Returns an observable that tracks the option type of the current order.
     * 
     * @return the option type observable
     */
    public final ITypedObservableValue<OptionType> getOptionType() {
        return mOptionType;
    }

    /**
     * Returns an observable that tracks the order capacity of the current
     * order.
     * 
     * @return the order capacity observable
     */
    public final ITypedObservableValue<OrderCapacity> getOrderCapacity() {
        return mOrderCapacity;
    }

    /**
     * Returns an observable that tracks the position effect of the current
     * order.
     * 
     * @return the position effect observable
     */
    public final ITypedObservableValue<PositionEffect> getPositionEffect() {
        return mPositionEffect;
    }
}
