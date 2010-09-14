/**
 * 
 */
package spike.xfui.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class Question extends HTMLPanel {

	private Log log = LogFactory.getLog(Question.class);
	
	private String modelName, modelNamespaceUri, modelPrefix;
	private Element model;
	
	private boolean registered = false;
	
	/**
	 * @param tag
	 * @param html
	 */
	public Question(String tag, String html) {
		super(tag, html);
	}

	/**
	 * @param html
	 */
	public Question(String html) {
		super(html);
	}

	/**
	 * @param input
	 */
	public void register(Input input) {
		log.enter("register");
		
		assert !registered : "only one form control can be registered per question";
		
		input.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			public void onValueChange(ValueChangeEvent<String> arg0) {
				log.enter("onValueChange");
				
				XML.setSimpleContent(model, arg0.getValue());
				
				log.leave();
			}
		});
		
		registered = true;
		
		log.leave();
	}

	public void setModelPrefix(String modelPrefix) {
		this.modelPrefix = modelPrefix;
	}

	public String getModelPrefix() {
		return modelPrefix;
	}

	public void setModelNamespaceUri(String modelNamespaceUri) {
		this.modelNamespaceUri = modelNamespaceUri;
	}

	public String getModelNamespaceUri() {
		return modelNamespaceUri;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}
	
	public void bind() {
		model = XMLNS.createElementNS(modelName, modelPrefix, modelNamespaceUri);
	}
	
	public Element getModel() {
		return model;
	}

}
