/**
 * 
 */
package spike.widget.submissionmanagment.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidget;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author raok
 *
 */
public class SpikeSubmissionManagementWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		
		FlowPanel mainPanel = new FlowPanel();
		FlowPanel menuCanvas = new FlowPanel();
		mainPanel.add(menuCanvas);
		
		RootPanel.get("holdall").add(mainPanel);
		
		UserDetailsTO user = new UserDetailsTO();
		user.setId("alice@example.com");
		ChassisUser.setCurrentUser(user);

		SubmissionManagementWidget widget = new SubmissionManagementWidget();
		MenuBar m = new MenuBar();
		m.addItem("submissions", widget.getMenu());
		menuCanvas.add(m);

		mainPanel.add(widget);

	}

}
