/**
 * 
 */
package spike.widget.submissionmanagment.client;

import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidgetAPI;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class SpikeSubmissionManagementWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		
		VerticalPanel vPanel = new VerticalPanel();
		SimplePanel menuCanvas = new SimplePanel();
		vPanel.add(menuCanvas);
		
		RootPanel.get().add(vPanel);
		
		ChassisUser.setCurrentUserEmail("bob@example.com");
		SubmissionManagementWidget widget = new SubmissionManagementWidget();
		menuCanvas.add(widget.getMenuCanvas());

		vPanel.add(widget);

	}

}
