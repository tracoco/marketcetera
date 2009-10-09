package org.marketcetera.trade;

import java.io.Serializable;
import javax.annotation.concurrent.Immutable;

import org.marketcetera.util.misc.ClassVersion;

/* $License$ */
/**
 * A financial instrument.
 * <p>
 * This interface is implemented by any instrument that is traded on
 * the platform.
 * <p>
 * Each instrument has a symbol attribute. Sub-types may add more
 * attributes as needed to uniquely identify the instrument being traded.
 * 
 * @author <a href="mailto:will@marketcetera.com">Will Horn</a>
 * @version $Id$
 * @since $Release$
 */
@ClassVersion("$Id$")
@Immutable
public abstract class Instrument implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Returns the symbol value.
	 * 
	 * @return the symbol value.
	 */
	public abstract String getSymbol();
	/**
	 * Returns the security type for this Instrument.
	 * 
	 * @return the security type.
	 */
	public abstract SecurityType getSecurityType();

}