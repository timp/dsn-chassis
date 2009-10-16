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
public class XQuestion extends Composite {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Element definition;
	private XQuestionModel model;
	private XQuestionView view;
	private XQuestionnaire parentQuestionnaire;
	private boolean repeatable = false;
	private XQuestion previousSibling;
	private String id;

	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestion(Element definition) {
		
		this.definition = definition;
		
		construct();

	}

	

	
	/**
	 * @param e
	 * @param owner
	 */
	public XQuestion(Element definition, XQuestionnaire parent) {
		
		this.definition = definition;
		this.parentQuestionnaire = parent;
		
		construct();

	}




	/**
	 * @param definition2
	 * @param parentQuestionnaire2
	 * @param xQuestion
	 */
	public XQuestion(Element definition, XQuestionnaire parent, XQuestion previousSibling) {

		this.definition = definition;
		this.parentQuestionnaire = parent;
		this.previousSibling = previousSibling;
		
		construct();

	}

	
	
	
	private void construct() {

		String repeatable = definition.getAttribute(XQS.ATTR_REPEATABLE);
		if (repeatable != null && XQS.YES.equals(repeatable)) {
			this.repeatable = true;
		}

		this.id = definition.getAttribute(XQS.ATTR_ID);
		if (this.id == null) {
			throw new XQuestionFormatException("bad question definition, no id attribute found");
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



	/**
	 * 
	 */
	public void init() {
		
		this.model.init();
		this.view.init();
		
//		String repeatable = definition.getAttribute(XQS.ATTR_REPEATABLE);
//		if (repeatable != null && XQS.YES.equals(repeatable)) {
//			this.repeatable = true;
//		}
//
//		this.id = definition.getAttribute(XQS.ATTR_ID);
//		if (this.id == null) {
//			throw new XQuestionFormatException("bad question definition, no id attribute found");
//		}
//
//		for (Element e : XML.elements(definition.getChildNodes())) {
//			
//			if (e.getTagName().equals(XQS.ELEMENT_MODEL)) {
//				initModel(e);
//			}
//
//			if (e.getTagName().equals(XQS.ELEMENT_VIEW)) {
//				initView(e);
//			}
//			
//		}
//
//		initWidget(this.view.getCanvas());

	}

	
	
	/**
	 * @param element
	 */
	public void init(Element data) {
		this.model.init(data);
		this.view.init(data);
	}
	
	
	



	public String getId() {
		return this.id;
	}

	/**
	 * 
	 */
	private void initModel(Element modelDef) {
		
//		if (this.model != null) {
//			throw new XQuestionFormatException("bad question definition, found more than one model");
//		}
//
//		this.model = new XQuestionModel(modelDef, this.parentQuestionnaire);
//		
//		if (this.parentQuestionnaire != null) {
//			
//			if (this.previousSibling != null) {
//				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
//			}
//			else {
//				this.parentQuestionnaire.getModel().addChild(this.model);				
//			}
//			
//		}

	}
	
	
	
	private void constructModel(Element modelDef) {

		if (this.model != null) {
			throw new XQuestionFormatException("bad question definition, found more than one model");
		}

		this.model = new XQuestionModel(modelDef, this.parentQuestionnaire);
		
		if (this.parentQuestionnaire != null) {
			
			if (this.previousSibling != null) {
				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
			}
			else {
				this.parentQuestionnaire.getModel().addChild(this.model);				
			}
			
		}

	}

	
	
	
	/**
	 * @param e 
	 * 
	 */
	private void initView(Element viewDef) {

//		if (this.view != null) {
//			throw new XQuestionFormatException("bad question definition, found more than one view");
//		}
//
//		this.view = new XQuestionView(viewDef, this);

	}
	
	
	
	
	private void constructView(Element viewDef) {

		if (this.view != null) {
			throw new XQuestionFormatException("bad question definition, found more than one view");
		}
	
		this.view = new XQuestionView(viewDef, this);

	}
	


	public XQuestionView getView() {
		return this.view;
	}
	
	
	
	
	public XQuestionModel getModel() {
		return this.model;
	}



	public XQuestionnaire getParentQuestionnaire() {
		return this.parentQuestionnaire;
	}



	public boolean isRepeatable() {
		return this.repeatable;
	}

	
	
	public XQuestion clone() {
		log.enter("clone");
		
		log.trace("clone this XQuestion");
		XQuestion clone = new XQuestion(this.definition, this.parentQuestionnaire, this);
		
		log.leave();
		return clone;
	}
	
	
	/**
	 * 
	 */
	public void repeat() {
		log.enter("repeat");
		
		if (this.repeatable) {
			
			log.trace("clone this XQuestion");
			XQuestion clone = this.clone();
			
			log.trace("insert cloned XQuestion into parent view");
			this.parentQuestionnaire.getView().addQuestion(clone, this);

		}
		
		log.leave();
	}




}
