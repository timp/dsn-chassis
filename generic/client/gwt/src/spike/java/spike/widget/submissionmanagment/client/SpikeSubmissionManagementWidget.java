/**
 * 
 */
package spike.widget.submissionmanagment.client;

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
		SimplePanel displayCanvas = new SimplePanel();
		vPanel.add(menuCanvas);
		vPanel.add(displayCanvas);
		
		RootPanel.get().add(vPanel);
		
		SubmissionManagementWidgetAPI widget = new SubmissionManagementWidget(displayCanvas, "bob@example.com");
		menuCanvas.add(widget.getMenuCanvas());

	}

}
