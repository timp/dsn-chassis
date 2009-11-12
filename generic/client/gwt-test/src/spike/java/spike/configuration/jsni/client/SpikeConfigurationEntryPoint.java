/**
 * 
 */
package spike.configuration.jsni.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
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

		root.add(new HTML("<p>userDetailsServiceEndpointURL: "+JsConfiguration.getUserDetailsServiceEndpointUrl()+"</p>"));
		root.add(new HTML("<p>studyCollectionUrl: "+JsConfiguration.getStudyCollectionUrl()+"</p>"));
		root.add(new HTML("<p>submissionCollectionUrl: "+JsConfiguration.getSubmissionCollectionUrl()+"</p>"));
		
		JsArray<Module> modules = JsConfiguration.getModules();
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
