/**
 * 
 */
package spike.widget.submission.create.client;

import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetAPI;

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

		
		CreateSubmissionWidgetAPI widget = new CreateSubmissionWidget(RootPanel.get(), "bob@example.com");
		
		widget.setUpNewSubmission();
		
	}

}
