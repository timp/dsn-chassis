/**
 * 
 */
package spike.widget.submission.create.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author raok
 *
 */
public class SpikeCreateSubmissionWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		
		ChassisUser.setCurrentUserEmail("bob@example.com");
		CreateSubmissionWidget widget = new CreateSubmissionWidget();

		ChassisUser.setCurrentUserEmail("bob@example.com");
//		widget.setUpNewSubmission("bob@example.com");
		
		RootPanel.get().add(new CreateSubmissionWidget());
		
	}

}
