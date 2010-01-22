/**
 * 
 */
package spike.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;
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
public class SpikeUserDetailsEntryPoint implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		// instantiate service
		GWTUserDetailsServiceAsync userService = GWT.create(GWTUserDetailsService.class);
		
		// set service URL
		ServiceDefTarget target = (ServiceDefTarget) userService;
		target.setServiceEntryPoint(JsConfiguration.getUserDetailsServiceEndpointUrl());
		
		UserDetailsWidget widget = new UserDetailsWidget();
		
		RootPanel.get().add(widget);

		widget.refreshCurrentUserDetails();

	}

}
