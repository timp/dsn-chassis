/**
 * 
 */
package spike.configuration.jsni.client;

import org.cggh.chassis.generic.client.gwt.client.Configuration;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeConfigurationEntryPoint implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		RootPanel root = RootPanel.get("gwtcontent");

		root.add(new HTML("<p>userDetailsServiceEndpointURL: "+Configuration.getUserDetailsServiceEndpointURL()+"</p>"));

	}

}
