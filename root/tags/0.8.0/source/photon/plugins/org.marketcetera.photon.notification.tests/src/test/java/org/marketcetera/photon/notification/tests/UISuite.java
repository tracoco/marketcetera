package org.marketcetera.photon.notification.tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.marketcetera.photon.notification.NotificationConsoleControllerTest;

/* $License$ */

/**
 * Test suite that must be run as a Plug-in JUnit suite. 
 * 
 * Note: {@link HeadlessSuite Headless tests} are also run.
 *
 * @author <a href="mailto:will@marketcetera.com">Will Horn</a>
 * @version $Id$
 * @since $Release$
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	HeadlessSuite.class,
	NotificationConsoleControllerTest.class
})
public class UISuite {

}
