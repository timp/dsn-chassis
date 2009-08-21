/**
 * 
 */
package spike.helloworld.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class HelloWorldEntryPoint implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		GWT.log("It really really works!", null);
		RootPanel.get().add(new Label("It really really works!"));
	}

}
