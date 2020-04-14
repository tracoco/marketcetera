package org.marketcetera.marketdata;

import java.util.Objects;

import org.marketcetera.trade.Instrument;

/* $License$ */

/**
 * Uniquely identifies a market data request component.
 *
 * @author <a href="mailto:colin@marketcetera.com">Colin DuPlantis</a>
 * @version $Id$
 * @since $Release$
 */
public class MarketDataRequestKey
{
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(content,
                            exchange,
                            instrument);
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MarketDataRequestKey)) {
            return false;
        }
        MarketDataRequestKey other = (MarketDataRequestKey) obj;
        return content == other.content && Objects.equals(exchange,other.exchange) && Objects.equals(instrument,other.instrument);
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("MarketDataRequestKey [instrument=").append(instrument).append(", content=").append(content)
                .append(", exchange=").append(exchange).append("]");
        return builder.toString();
    }
    /**
     * Create a new MarketDataRequestKey instance.
     *
     * @param inInstrument an <code>Instrument</code> value
     * @param inContent a <code>Content</code> value
     * @param inExchange a <code>String</code> value or <code>null</code>
     */
    public MarketDataRequestKey(Instrument inInstrument,
                                Content inContent,
                                String inExchange)
    {
        instrument = inInstrument;
        content = inContent;
        exchange = inExchange==null?ALL_EXCHANGES:inExchange;
    }
    /**
     * Get the instrument value.
     *
     * @return an <code>Instrument</code> value
     */
    public Instrument getInstrument()
    {
        return instrument;
    }
    /**
     * Get the content value.
     *
     * @return a <code>Content</code> value
     */
    public Content getContent()
    {
        return content;
    }
    /**
     * Get the exchange value.
     *
     * @return a <code>String</code> value
     */
    public String getExchange()
    {
        return exchange;
    }
    /**
     * instrument value
     */
    private final Instrument instrument;
    /**
     * content value
     */
    private final Content content;
    /**
     * exchange value
     */
    private final String exchange;
    /**
     * indicates that a request is for all exchanges
     */
    public static final String ALL_EXCHANGES = "**ALL**";
}
