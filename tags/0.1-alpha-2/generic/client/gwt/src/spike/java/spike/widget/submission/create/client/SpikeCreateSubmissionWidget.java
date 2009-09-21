/**
 * 
 */
package spike.widget.submission.create.client;

import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidget;
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
		//Set here for now
		String feedURL = "http://example.com/atom/feeds/example";
		
		// instantiate service
		MockAtomFactory factory = new MockAtomFactory(); // use mock for now
		MockAtomService service = new MockAtomService(factory); // use mock for now
		service.createFeed(feedURL, "my first atom feed"); // bootstrap mock service with a feed, not needed for real service
		
		CreateSubmissionWidgetAPI widget = new CreateSubmissionWidget(RootPanel.get(), service);
		
		widget.setUpNewSubmission(feedURL);
		
	}

}
