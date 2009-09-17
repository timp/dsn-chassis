/**
 * 
 */
package spike.widget.studymanagment.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
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
//		MockStudyFactory factory = new MockStudyFactory();
//		MockAtomService service = new MockAtomService(factory);
		
		// bootstrap mock service with study feed
//		String feedURL = "http://example.com/studies";
//		((MockAtomService)service).createFeed(feedURL, "all studies");
		
		// use real thing
		StudyFactory factory = new StudyFactoryImpl();
		AtomService service = new AtomServiceImpl(factory);
		
		StudyManagementWidget studyManagementWidget = new StudyManagementWidget(menuCanvas, displayCanvas, service);
		
		RootPanel.get().add(verticalPanel);
		
		
	}

}
