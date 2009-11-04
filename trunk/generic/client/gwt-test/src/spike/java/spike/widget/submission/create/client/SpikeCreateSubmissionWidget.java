/**
 * 
 */
package spike.widget.submission.create.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidget;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author raok
 *
 */
public class SpikeCreateSubmissionWidget implements EntryPoint {

	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
//		LogFactory.hide("*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.application.client.ChassisClient");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.userdetails.*");
	}

	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		UserDetailsTO user = new UserDetailsTO();
		user.setId("alice@example.com");
		ChassisUser.setCurrentUser(user);

		RootPanel.get().add(new CreateSubmissionWidget());
		
	}

}
