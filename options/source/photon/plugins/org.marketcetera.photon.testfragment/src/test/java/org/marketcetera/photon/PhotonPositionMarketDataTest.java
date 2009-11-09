package org.marketcetera.photon;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.marketcetera.core.position.impl.BigDecimalMatchers.comparesEqualTo;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.marketcetera.core.position.MarketDataSupport.SymbolChangeEvent;
import org.marketcetera.core.position.MarketDataSupport.SymbolChangeListener;
import org.marketcetera.module.ExpectedFailure;
import org.marketcetera.photon.marketdata.IMarketData;
import org.marketcetera.photon.marketdata.IMarketDataReference;
import org.marketcetera.photon.model.marketdata.MDFactory;
import org.marketcetera.photon.model.marketdata.MDLatestTick;
import org.marketcetera.photon.model.marketdata.MDMarketstat;
import org.marketcetera.photon.model.marketdata.MDPackage;
import org.marketcetera.trade.Equity;
import org.marketcetera.trade.Option;
import org.marketcetera.trade.OptionType;

/* $License$ */

/**
 * Test {@link PhotonPositionMarketData}.
 *
 * @author <a href="mailto:will@marketcetera.com">Will Horn</a>
 * @version $Id$
 * @since 1.5.0
 */
public class PhotonPositionMarketDataTest {
    private static final Equity IBM = new Equity("IBM");
    private static final Option METC = new Option("METC", "201210", BigDecimal.TEN, OptionType.Call);
	private PhotonPositionMarketData mFixture;
	private IMarketData mMockMarketData;
	private MDLatestTick mIBMTick;
	private IMarketDataReference<MDLatestTick> mMockIBMReference;
	private MDLatestTick mMETCTick;
	private IMarketDataReference<MDLatestTick> mMockMETCReference;
	private MDMarketstat mIBMStat;
	private MDMarketstat mMETCStat;
	private IMarketDataReference<MDMarketstat> mMockIBMStatReference;
	private IMarketDataReference<MDMarketstat> mMockMETCStatReference;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		mMockMarketData = mock(IMarketData.class);
		mFixture = new PhotonPositionMarketData(mMockMarketData);
		// latest tick stubbing
		mIBMTick = MDFactory.eINSTANCE.createMDLatestTick();
		mIBMTick.eSet(MDPackage.Literals.MD_ITEM__INSTRUMENT, IBM);
		mMETCTick = MDFactory.eINSTANCE.createMDLatestTick();
		mMETCTick.eSet(MDPackage.Literals.MD_ITEM__INSTRUMENT, METC);
		mMockIBMReference = mock(IMarketDataReference.class);
		mMockMETCReference = mock(IMarketDataReference.class);
		when(mMockIBMReference.get()).thenReturn(mIBMTick);
		when(mMockMETCReference.get()).thenReturn(mMETCTick);
		when(mMockMarketData.getLatestTick(IBM)).thenReturn(mMockIBMReference);
		when(mMockMarketData.getLatestTick(METC)).thenReturn(mMockMETCReference);
		// statistic stubbing
		mIBMStat = MDFactory.eINSTANCE.createMDMarketstat();
		mIBMStat.eSet(MDPackage.Literals.MD_ITEM__INSTRUMENT, IBM);
		mMETCStat = MDFactory.eINSTANCE.createMDMarketstat();
		mMETCStat.eSet(MDPackage.Literals.MD_ITEM__INSTRUMENT, METC);
		mMockIBMStatReference = mock(IMarketDataReference.class);
		mMockMETCStatReference = mock(IMarketDataReference.class);
		when(mMockIBMStatReference.get()).thenReturn(mIBMStat);
		when(mMockMETCStatReference.get()).thenReturn(mMETCStat);
		when(mMockMarketData.getMarketstat(IBM)).thenReturn(mMockIBMStatReference);
		when(mMockMarketData.getMarketstat(METC)).thenReturn(mMockMETCStatReference);
	}

	@Test
	public void testNulls() throws Exception {
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				new PhotonPositionMarketData(null);
			}
		};
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				mFixture.getLastTradePrice(null);
			}
		};
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				mFixture.getClosingPrice(null);
			}
		};
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				mFixture.addSymbolChangeListener(null, mock(SymbolChangeListener.class));
			}
		};
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				mFixture.addSymbolChangeListener(IBM, null);
			}
		};
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				mFixture.removeSymbolChangeListener(null, mock(SymbolChangeListener.class));
			}
		};
		new ExpectedFailure<IllegalArgumentException>() {
			@Override
			protected void run() throws Exception {
				mFixture.removeSymbolChangeListener(IBM, null);
			}
		};
	}

	@Test
	public void testAddingMultipleListenersGetsSingleReference() {
		mFixture.addSymbolChangeListener(IBM, mock(SymbolChangeListener.class));
		mFixture.addSymbolChangeListener(IBM, mock(SymbolChangeListener.class));
		verify(mMockMarketData, times(1)).getLatestTick(new Equity("IBM"));
	}

	@Test
	public void testNotificationGeneratesEvent() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(METC, mockListener);
		changeLatestTick(mMETCTick, 5);
		verify(mockListener).symbolTraded(argThat(hasNewPrice(5)));
		changeMultiplier(mMETCTick, 8);
		verify(mockListener).optionMultiplierChanged(8);
		
	}

	@Test
	public void testMultipleListenersGetNotified() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(IBM, mockListener2);
		changeLatestTick(mIBMTick, 12);
		verify(mockListener).symbolTraded(argThat(hasNewPrice(12)));
		verify(mockListener2).symbolTraded(argThat(hasNewPrice(12)));
	}

	@Test
	public void testDuplicateListenerIgnored() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		changeLatestTick(mIBMTick, 7);
		verify(mockListener, times(1)).symbolTraded(argThat(hasNewPrice(7)));
	}

	@Test
	public void testRemoveNonExistantListenerIgnored() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.removeSymbolChangeListener(IBM, mockListener2);
		mFixture.removeSymbolChangeListener(METC, mockListener);
		verify(mMockIBMReference, never()).dispose();
	}

	@Test
	public void testNoMoreListenersDisposesDataAndRemovesAdapter() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(IBM, mockListener2);
		mFixture.removeSymbolChangeListener(IBM, mockListener);
		verify(mMockIBMReference, never()).dispose();
		mFixture.removeSymbolChangeListener(IBM, mockListener2);
		verify(mMockIBMReference).dispose();
		assertThat(mIBMTick.eAdapters().size(), is(0));
	}

	@Test
	public void testGetLastTradePrice() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(METC, mockListener2);
		changeLatestTick(mIBMTick, 18);
		changeLatestTick(mMETCTick, 19);
		assertThat(mFixture.getLastTradePrice(IBM), comparesEqualTo(18));
		assertThat(mFixture.getLastTradePrice(METC), comparesEqualTo(19));
	}

	@Test
	public void testGetClosingPrice() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(METC, mockListener2);
		changePreviousClose(mIBMStat, 20);
		changePreviousClose(mMETCStat, 25);
		verify(mockListener, atLeastOnce()).closePriceChanged(argThat(hasNewPrice(20)));
		verify(mockListener2, atLeastOnce()).closePriceChanged(argThat(hasNewPrice(25)));
		assertThat(mFixture.getClosingPrice(IBM), comparesEqualTo(20));
		assertThat(mFixture.getClosingPrice(METC), comparesEqualTo(25));
		changePreviousClose(mIBMStat, null);
		changePreviousClose(mMETCStat, null);
		verify(mockListener, atLeastOnce()).closePriceChanged(argThat(hasNullNewPrice()));
		verify(mockListener2, atLeastOnce()).closePriceChanged(argThat(hasNullNewPrice()));
		assertThat(mFixture.getClosingPrice(IBM), nullValue());
		assertThat(mFixture.getClosingPrice(METC), nullValue());
	}

    @Test
    public void testGetOptionMultiplier() throws Exception {
        SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
        mFixture.addSymbolChangeListener(METC, mockListener);
        changeMultiplier(mMETCTick, 1000);
        assertThat(mFixture.getOptionMultiplier(METC), is(1000));
    }
	
	@Test
	public void testListenerNotNotifiedAfterRemoved() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		changeLatestTick(mIBMTick, 1);
		verify(mockListener).symbolTraded(argThat(hasNewPrice(1)));
		// remove it
		mFixture.removeSymbolChangeListener(IBM, mockListener);
		changeLatestTick(mIBMTick, 2);
		verify(mockListener, never()).symbolTraded(argThat(hasNewPrice(2)));
	}

	@Test
	public void testSeparateNotifications() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(METC, mockListener2);
		changeLatestTick(mIBMTick, 12);
		changeLatestTick(mMETCTick, 150);
		verify(mockListener).symbolTraded(argThat(hasNewPrice(12)));
		verify(mockListener2).symbolTraded(argThat(hasNewPrice(150)));
		// remove one
		mFixture.removeSymbolChangeListener(IBM, mockListener);
		changeLatestTick(mIBMTick, 13);
		changeLatestTick(mMETCTick, 151);
		verify(mockListener, never()).symbolTraded(argThat(hasNewPrice(13)));
		verify(mockListener2).symbolTraded(argThat(hasNewPrice(151)));
	}

	@Test
	public void testDispose() throws Exception {
		SymbolChangeListener mockListener = mock(SymbolChangeListener.class);
		SymbolChangeListener mockListener2 = mock(SymbolChangeListener.class);
		mFixture.addSymbolChangeListener(IBM, mockListener);
		mFixture.addSymbolChangeListener(METC, mockListener2);
		changeLatestTick(mIBMTick, 12);
		changeLatestTick(mMETCTick, 150);
		assertThat(mFixture.getLastTradePrice(IBM), comparesEqualTo(12));
		assertThat(mFixture.getLastTradePrice(METC), comparesEqualTo(150));
		mFixture.dispose();
		changeLatestTick(mIBMTick, 15);
		changeLatestTick(mMETCTick, 151);
		verify(mockListener, never()).symbolTraded(argThat(hasNewPrice(15)));
		verify(mockListener2, never()).symbolTraded(argThat(hasNewPrice(151)));
		assertThat(mFixture.getLastTradePrice(IBM), nullValue());
		assertThat(mFixture.getLastTradePrice(METC), nullValue());
	}

	private void changeLatestTick(MDLatestTick tick, int newValue) {
		// use reflection since setLatestTick isn't API
		tick.eSet(MDPackage.Literals.MD_LATEST_TICK__PRICE, new BigDecimal(newValue));
	}

	private void changePreviousClose(MDMarketstat stat, BigDecimal newPrice) throws Exception {
		// use reflection since setLatestTick isn't API
		stat.eSet(MDPackage.Literals.MD_MARKETSTAT__PREVIOUS_CLOSE_PRICE, newPrice);
	}
	
	private void changeMultiplier(MDLatestTick tick, int newValue) {
        // use reflection since setLatestTick isn't API
        tick.eSet(MDPackage.Literals.MD_LATEST_TICK__MULTIPLIER, newValue);
    }

	private void changePreviousClose(MDMarketstat stat, int newPrice) throws Exception {
		changePreviousClose(stat, new BigDecimal(newPrice));
	}

	private static Matcher<SymbolChangeEvent> hasNewPrice(final int newPrice) {
		return new BaseMatcher<SymbolChangeEvent>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("with a new price of").appendValue(newPrice);

			}

			@Override
			public boolean matches(Object item) {
				return comparesEqualTo(newPrice).matches(((SymbolChangeEvent) item).getNewPrice());
			}
		};
	}

	private static Matcher<SymbolChangeEvent> hasNullNewPrice() {
		return new BaseMatcher<SymbolChangeEvent>() {

			@Override
			public void describeTo(Description description) {
				description.appendText("with a null new price");

			}

			@Override
			public boolean matches(Object item) {
				return nullValue().matches(((SymbolChangeEvent) item).getNewPrice());
			}
		};
	}

}
