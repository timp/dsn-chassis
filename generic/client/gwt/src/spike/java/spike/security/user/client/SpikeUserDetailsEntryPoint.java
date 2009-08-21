/**
 * 
 */
package spike.security.user.client;

import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
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
		target.setServiceEntryPoint("/chassis-generic-service-user/gwtrpc/userdetails");
		
		// make service call
		userService.getAuthenticatedUserDetails(new AsyncCallback<UserDetailsTO>() {

			public void onFailure(Throwable ex) {
				RootPanel.get().clear();
				RootPanel.get().add(new Label("Error: "+ex.getLocalizedMessage()));
			}

			public void onSuccess(UserDetailsTO user) {
				RootPanel.get().clear();
				RootPanel.get().add(new Label("Authenticated user: "+user.getId()));
				for (String role : user.getRoles()) {
					RootPanel.get().add(new Label(" ["+role+"] "));
				}
			}
			
		});

	}

}
