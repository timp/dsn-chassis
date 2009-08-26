/**
 * 
 */
package spike.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;

import com.google.gwt.core.client.EntryPoint;
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
		
		UserDetailsWidget widget = new UserDetailsWidget(RootPanel.get());
		widget.refreshUserDetails();
		
	}

}
