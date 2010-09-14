/**
 * 
 */
package spike.security.user.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsService;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
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
		target.setServiceEntryPoint(JsConfiguration.getUserDetailsServiceEndpointUrl());
		
		// get root panel
		final RootPanel root = RootPanel.get("gwtcontent");
		
		// make service call
		userService.getAuthenticatedUserDetails(new AsyncCallback<UserDetailsTO>() {

			public void onFailure(Throwable ex) {
				root.clear();
				root.add(new Label("Error: "+ex.getLocalizedMessage()));
			}

			public void onSuccess(UserDetailsTO user) {
				root.clear();
				root.add(new HTML("<p>Authenticated user: <strong>"+user.getId()+"</strong></p>"));
				root.add(pWidget("Roles:"));
				String content = "<ul>";
				for (String role : user.getRoles()) {
					content += "<li>"+role+"</li>";
				}
				content += "</ul>";
				root.add(new HTML(content));
			}
			
		});

	}

}
