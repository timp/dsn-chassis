/**
 * 
 */
package spike.widget.application.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationWidget;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author raok
 *
 */
public class SpikeApplicationWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		// instantiate service
		GWTUserDetailsServiceAsync userService = GWT.create(GWTUserDetailsService.class);
		
		// set service URL
		ServiceDefTarget target = (ServiceDefTarget) userService;
		target.setServiceEntryPoint(Configuration.getUserDetailsServiceEndpointURL());
		
		
		ApplicationWidget widget = new ApplicationWidget(RootPanel.get(), userService);
				
		
	}

}
