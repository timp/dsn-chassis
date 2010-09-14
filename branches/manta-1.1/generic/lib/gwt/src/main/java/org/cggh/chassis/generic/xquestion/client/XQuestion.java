/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.shared.HandlerManager;
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
	private HandlerManager eventBus;

	
	
	
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

	
	
	public HandlerManager getEventBus() {
		return eventBus;
	}

	
	

	public void init() {
		
		this.init(false);
	}
	
	
	
	public void init(boolean readOnly) {

		this.model.init();
		
		// TODO move this to model.init() ?
		if (this.parentQuestionnaire != null) {
			
			eventBus = parentQuestionnaire.getEventBus();
			
			if (this.previousSibling != null) {
				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
			}
			else {
				this.parentQuestionnaire.getModel().addChild(this.model);				
			}
			
		}
		else {
			eventBus = new HandlerManager(this);
		}

		this.view.init(readOnly);
		
	}



	/**
	 * @param readOnly 
	 * @param element
	 */
	public void init(Element data) {
		this.init(data, false);
	}

	
	
	/**
	 * @param readOnly 
	 * @param element
	 */
	public void init(Element data, boolean readOnly) {

		this.model.init(data);

		// TODO move this to model.init() ?
		if (this.parentQuestionnaire != null) {
			
			eventBus = parentQuestionnaire.getEventBus();
			
			if (this.previousSibling != null) {
				this.parentQuestionnaire.getModel().addChild(this.model, this.previousSibling.getModel());
			}
			else {
				this.parentQuestionnaire.getModel().addChild(this.model);				
			}
			
		}
		else {
			eventBus = new HandlerManager(this);
		}
		
		this.view.init(data, readOnly);

	}
	
	
	



	public String getId() {
		return this.id;
	}

	
	
	
	private void constructModel(Element modelDef) {

		if (this.model != null) {
			throw new XQuestionFormatException("bad question definition, found more than one model");
		}

		this.model = new XQuestionModel(this, modelDef, this.parentQuestionnaire);
		
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
		
		log.debug("clone this XQuestion");
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
			
			log.debug("clone this XQuestion");
			XQuestion clone = this.clone();
			
			log.debug("insert cloned XQuestion into parent view");
			this.parentQuestionnaire.getView().addQuestion(clone, this);

		}
		
		log.leave();
	}


	
	public List<XQuestion> getRepeats() {
		List<XQuestion> siblings = this.parentQuestionnaire.getView().getQuestions();
		List<XQuestion> repeats = new ArrayList<XQuestion>();
		for (XQuestion q : siblings) {
			if (q.getDefinition() == this.definition) {
				repeats.add(q);
			}
		}
		return repeats;
	}




	private Element getDefinition() {
		return this.definition;
	}




	public void remove() {
		log.enter("remove");
		
		if (this.repeatable) {
			
			log.debug("remove question from view");
			this.parentQuestionnaire.getView().removeQuestion(this);

			log.debug("remove question from model");
			this.parentQuestionnaire.getModel().removeChild(this.getModel());
			
		}
		
		log.leave();
	}




}
