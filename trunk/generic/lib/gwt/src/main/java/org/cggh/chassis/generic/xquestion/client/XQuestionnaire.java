/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionnaire extends Composite {

	
	
	
	private Element definition;
	private XQuestionnaireModel model;
	private XQuestionnaireView view;
	private XQuestionnaire parentQuestionnaire;
	private String defaultPrefix;
	private String defaultNamespaceUri;

	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition) {

		this.definition = definition;
		
		init();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, String defaultPrefix, String defaultNamespaceUri) {

		this.definition = definition;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		
		init();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		
		init();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent, String defaultPrefix, String defaultNamespaceUri) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		
		init();

	}
	
	
	
	
	private void init() {
		
		String defaultPrefix = definition.getAttribute(XQS.ATTR_DEFAULTPREFIX);
		if (defaultPrefix != null) {
			this.setDefaultPrefix(defaultPrefix);
		}
		else if (parentQuestionnaire != null) {
			this.setDefaultPrefix(parentQuestionnaire.getDefaultPrefix()); // inherit from parent
		}

		String defaultNamespaceUri = definition.getAttribute(XQS.ATTR_DEFAULTNAMESPACEURI);
		if (defaultNamespaceUri != null) {
			this.setDefaultNamespaceUri(defaultNamespaceUri);
		}
		else if (parentQuestionnaire != null) {
			this.setDefaultNamespaceUri(parentQuestionnaire.getDefaultNamespaceUri()); // inherit from parent
		}

		for (Element e : XML.elements(definition.getChildNodes())) {
			
			if (e.getTagName().equals(XQS.ELEMENT_MODEL)) {
				initModel(e);
			}

			if (e.getTagName().equals(XQS.ELEMENT_VIEW)) {
				initView(e);
			}
			
		}

		initWidget(this.view.getCanvas());

	}
	
	
	
	
	/**
	 * 
	 */
	private void initModel(Element modelDef) {
		
		if (this.model != null) {
			throw new XQuestionFormatException("bad questionnaire definition, found more than one model");
		}

		this.model = new XQuestionnaireModel(modelDef, this);

		if (this.parentQuestionnaire != null) {
			
			this.parentQuestionnaire.getModel().addChild(this.model);
			
		}


	}

	
	
	
	/**
	 * @param e 
	 * 
	 */
	private void initView(Element viewDef) {

		if (this.view != null) {
			throw new XQuestionFormatException("bad questionnaire definition, found more than one view");
		}

		this.view = new XQuestionnaireView(viewDef, this);

	}
	
	


	public XQuestionnaireView getView() {
		return this.view;
	}
	
	
	
	public XQuestionnaireModel getModel() {
		return this.model;
	}




	/**
	 * @param defaultPrefix the defaultPrefix to set
	 */
	public void setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
	}




	/**
	 * @return the defaultPrefix
	 */
	public String getDefaultPrefix() {
		return defaultPrefix;
	}




	/**
	 * @param defaultNamespaceUri the defaultNamespaceUri to set
	 */
	public void setDefaultNamespaceUri(String defaultNamespaceUri) {
		this.defaultNamespaceUri = defaultNamespaceUri;
	}




	/**
	 * @return the defaultNamespaceUri
	 */
	public String getDefaultNamespaceUri() {
		return defaultNamespaceUri;
	}

}
