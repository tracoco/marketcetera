package org.marketcetera.core.position;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.marketcetera.core.trade.Equity;
import org.marketcetera.core.trade.Instrument;
import org.marketcetera.core.trade.Option;
import org.marketcetera.core.trade.OptionType;

/* $License$ */

/**
 * Simple implementation of the {@link Trade} interface.
 * 
 * @version $Id: MockTrade.java 16063 2012-01-31 18:21:55Z colin $
 * @since 1.5.0
 */
public class MockTrade<T extends Instrument> implements Trade<T> {

    protected final PositionKey<T> mKey;
    protected final BigDecimal mPrice;
    protected final BigDecimal mQuantity;
    protected final long mSequence;
    protected static final AtomicLong mSequenceGenerator = new AtomicLong();

    public static MockTrade<Equity> createEquityTrade(String symbol,
            String account, String traderId, String quantity, String price) {
        return createTrade(PositionKeyFactory.createEquityKey(symbol, account,
                traderId), quantity, price);
    }

    public static MockTrade<Option> createOptionTrade(String symbol,
            String expiry, String strikePrice, OptionType type, String account,
            String traderId, String quantity, String price) {
        return createTrade(PositionKeyFactory.createOptionKey(symbol, expiry,
                new BigDecimal(strikePrice), type, account, traderId),
                quantity, price);
    }

    public static <T extends Instrument> MockTrade<T> createTrade(T instrument,
            String account, String traderId, String quantity, String price) {
        return createTrade(PositionKeyFactory.createKey(instrument, account,
                traderId), quantity, price);
    }

    public static <T extends Instrument> MockTrade<T> createTrade(
            PositionKey<T> key, String quantity, String price) {
        return new MockTrade<T>(key, new BigDecimal(quantity), new BigDecimal(
                price));
    }

    public MockTrade(PositionKey<T> key, BigDecimal quantity, BigDecimal price) {
        this(key, quantity, price, mSequenceGenerator.incrementAndGet());
    }

    public MockTrade(PositionKey<T> key, BigDecimal quantity, BigDecimal price,
            long sequence) {
        mKey = key;
        mPrice = price;
        mQuantity = quantity;
        mSequence = sequence;
    }

    @Override
    public PositionKey<T> getPositionKey() {
        return mKey;
    }

    @Override
    public BigDecimal getPrice() {
        return mPrice;
    }

    @Override
    public BigDecimal getQuantity() {
        return mQuantity;
    }

    @Override
    public long getSequenceNumber() {
        return mSequence;
    }

}