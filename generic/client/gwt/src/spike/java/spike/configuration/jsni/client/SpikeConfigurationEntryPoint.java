/**
 * 
 */
package spike.configuration.jsni.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.configuration.client.Module;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeConfigurationEntryPoint implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		RootPanel root = RootPanel.get("gwtcontent");

		root.add(new HTML("<p>userDetailsServiceEndpointURL: "+Configuration.getUserDetailsServiceEndpointURL()+"</p>"));
		root.add(new HTML("<p>studyFeedURL: "+Configuration.getStudyFeedURL()+"</p>"));
		root.add(new HTML("<p>submissionFeedURL: "+Configuration.getSubmissionFeedURL()+"</p>"));
		
		JsArray<Module> modules = Configuration.getModules();
		root.add(new HTML("<h2>Modules</h2>"));
		String content = "<ul>";
		for (int i=0; i<modules.length(); i++) {
			Module module = modules.get(i);
			content += "<li>id: "+module.getId();
			content += "; labels: [en] "+module.getLabel("en");
			content += "</li>";
		}
		content += "</ul>";
		root.add(new HTML(content));

	}

}
