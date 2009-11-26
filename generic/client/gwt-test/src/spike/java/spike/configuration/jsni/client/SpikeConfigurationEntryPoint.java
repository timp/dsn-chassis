/**
 * 
 */
package spike.configuration.jsni.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.JsConfiguration;
import org.cggh.chassis.generic.client.gwt.configuration.client.Module;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

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

		root.add(p("userDetailsServiceEndpointURL: "+JsConfiguration.getUserDetailsServiceEndpointUrl()+""));
		root.add(p("studyCollectionUrl: "+JsConfiguration.getStudyCollectionUrl()+""));
		root.add(p("submissionCollectionUrl: "+JsConfiguration.getSubmissionCollectionUrl()+""));
		
		JsArray<Module> modules = JsConfiguration.getModules();
		root.add(h2("Modules"));
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
