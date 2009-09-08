/**
 * 
 */
package spike.widget.studymanagment.client;

import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class SpikeStudyManagementWidget implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		
		
		SimplePanel menuCanvas = new SimplePanel();
		SimplePanel displayCanvas = new SimplePanel();
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(menuCanvas);
		verticalPanel.add(displayCanvas);
		
		//Use mock service
		MockStudyFactory mockFactory = new MockStudyFactory();
		MockAtomService mockService = new MockAtomService(mockFactory);
		
		// bootstrap mock service with study feed
		String feedURL = "http://example.com/studies";
		((MockAtomService)mockService).createFeed(feedURL, "all studies");
		
		StudyManagementWidget studyManagementWidget = new StudyManagementWidget(menuCanvas, displayCanvas, mockService);
		
		RootPanel.get().add(verticalPanel);
		
		
	}

}
