package org.marketcetera.photon.actions;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

/**
 * WebHelpAction opens the Marketcetera help site in an external 
 * browser, using {@link IWorkbenchBrowserSupport#createBrowser(String)}
 * and {@link IWebBrowser#openURL(URL)}.
 * 
 * @author gmiller
 *
 */
public class WebHelpAction extends Action implements IWorkbenchAction {

	public static final String ID = "org.marketcetera.photon.actions.HelpBrowserAction";
	private static final String MAIN_HELP_URL = "http://trac.marketcetera.org/trac.fcgi/wiki/Marketcetera/PhotonGuide";
	private final IWorkbenchWindow window;
	
	/**
	 * Create the default instance of HelpBrowserAction, setting the ID, text,
	 * tool-tip text, and image to the defaults.
	 */
	public WebHelpAction(IWorkbenchWindow window){
		this.window = window;
		setId(ID);
		setText("&Help");
		setToolTipText("Open help in a browser");
		//setImageDescriptor(PhotonPlugin.getImageDescriptor(IImageKeys.RECONNECT_JMS_HISTORY));
	}
	/**
	 *  
	 * Default implementation does nothing.
	 * 
	 * @see org.eclipse.ui.actions.ActionFactory$IWorkbenchAction#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Attempt to open help in a browser
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {

		//maybe do this at some point?
		//window.getWorkbench().getHelpSystem().displayHelpResource(MAIN_HELP_URL);
		// for now, just show it in an external browser
		
		IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench()
				.getBrowserSupport();
		IWebBrowser browser;
		try {
			browser = browserSupport.createBrowser("_blank");
			browser.openURL(new URL(MAIN_HELP_URL));
			
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


