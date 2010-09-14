/**
 * 
 */
package spike.widget.datafile.create.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.NewDataFileWidget;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class SpikeCreateDataFileEntryPoint implements EntryPoint {
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
//		LogFactory.hide("*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.application.client.ChassisClient");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.userdetails.*");
	}

	
	
	private Log log = LogFactory.getLog(SpikeCreateDataFileEntryPoint.class);

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");

		UserDetailsTO user = new UserDetailsTO();
		user.setId("alice@example.com");
		ChassisUser.setCurrentUser(user);
		
		Widget w = new NewDataFileWidget();

		RootPanel.get().add(w);
		
		log.leave();

	}

}
