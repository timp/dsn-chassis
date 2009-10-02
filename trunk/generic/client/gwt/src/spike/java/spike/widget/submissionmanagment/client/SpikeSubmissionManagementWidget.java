/**
 * 
 */
package spike.widget.submissionmanagment.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.configuration.client.Module;
import org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.SubmissionManagementWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
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

		//Set here for now
		String feedURL = "http://example.com/atom/feeds/example";
		
		// instantiate service
		MockAtomFactory factory = new MockAtomFactory(); // use mock for now
		MockAtomService service = new MockAtomService(factory); // use mock for now
		service.createFeed(feedURL, "my first atom feed"); // bootstrap mock service with a feed, not needed for real service

		//get modules from config and convert to more useable format
		JsArray<Module> modules = Configuration.getModules();
		
		Map<String, String> modulesMap = new HashMap<String, String>();
		for (int i = 0; i < modules.length(); ++i) {
			
			String id = modules.get(i).getId();
			String label = modules.get(i).getLabel("en");
			
			modulesMap.put(id, label);
		}
		
		VerticalPanel vPanel = new VerticalPanel();
		SimplePanel menuCanvas = new SimplePanel();
		SimplePanel displayCanvas = new SimplePanel();
		vPanel.add(menuCanvas);
		vPanel.add(displayCanvas);
		
		RootPanel.get().add(vPanel);
		
		
		
		SubmissionManagementWidget widget = new SubmissionManagementWidget(menuCanvas, displayCanvas, service, feedURL, modulesMap);

	}

}
