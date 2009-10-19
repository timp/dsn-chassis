/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionnaire extends Composite {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Element definition;
	private XQuestionnaireModel model;
	private XQuestionnaireView view;
	private XQuestionnaire parentQuestionnaire;
	private String defaultPrefix;
	private String defaultNamespaceUri;
	private boolean repeatable;
	private XQuestionnaire previousSibling;

	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition) {

		this.definition = definition;
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, String defaultPrefix, String defaultNamespaceUri) {

		this.definition = definition;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent, String defaultPrefix, String defaultNamespaceUri) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		
		construct();

	}
	
	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestionnaire(Element definition, XQuestionnaire parent, String defaultPrefix, String defaultNamespaceUri, XQuestionnaire previousSibling) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		this.setDefaultPrefix(defaultPrefix);
		this.setDefaultNamespaceUri(defaultNamespaceUri);
		this.previousSibling = previousSibling;
		
		construct();

	}
	
	
	
	private void construct() {

		String repeatable = definition.getAttribute(XQS.ATTR_REPEATABLE);
		if (repeatable != null && XQS.YES.equals(repeatable)) {
			this.repeatable = true;
		}

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
				constructModel(e);
			}

			if (e.getTagName().equals(XQS.ELEMENT_VIEW)) {
				constructView(e);
			}
			
		}

		initWidget(this.view.getCanvas());

	}
	
	
	
	public void init() {

		this.model.init();

		// TODO move this to model.init() ?
		if (this.parentQuestionnaire != null) {
			
			if (this.previousSibling != null) {
				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
			}
			else {
				this.parentQuestionnaire.getModel().addChild(this.model);				
			}

		}

		this.view.init();

	}
	
	
	
	/**
	 * @param documentElement
	 */
	public void init(Element data) {
		
		this.model.init(data);

		// TODO move this to model.init() ?
		if (this.parentQuestionnaire != null) {
			
			if (this.previousSibling != null) {
				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
			}
			else {
				this.parentQuestionnaire.getModel().addChild(this.model);				
			}

		}


		this.view.init(data);
	}
	

	
	
	/**
	 * 
	 */
	private void constructModel(Element modelDef) {
		
		if (this.model != null) {
			throw new XQuestionFormatException("bad questionnaire definition, found more than one model");
		}

		this.model = new XQuestionnaireModel(modelDef, this);

	}
	
	


	/**
	 * @param e 
	 * 
	 */
	private void constructView(Element viewDef) {

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

	
	
	public boolean isRepeatable() {
		return this.repeatable;
	}

	
	
	public XQuestionnaire clone() {
		log.enter("clone");
		
		log.trace("clone this XQuestion");
		XQuestionnaire clone = new XQuestionnaire(this.definition, this.parentQuestionnaire, this.defaultPrefix, this.defaultNamespaceUri, this);
		
		log.leave();
		return clone;
	}
	
	
	
	/**
	 * 
	 */
	public void repeat() {
		log.enter("repeat");
		
		if (this.repeatable && this.parentQuestionnaire != null) {
			
			log.trace("clone this XQuestion");
			XQuestionnaire clone = this.clone();
			
			log.trace("insert cloned XQuestion into parent view");
			this.parentQuestionnaire.getView().addQuestionnaire(clone, this);

		}
		
		log.leave();
	}




}
