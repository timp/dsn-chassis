/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionnaireView extends XQSViewBase {

	
	
	
	private static final String STYLENAME = "xquestionnaire";
	private XQuestionnaire owner;
	private List<XQuestion> questions = new ArrayList<XQuestion>();
	private List<XQuestionnaire> nestedQuestionnaires = new ArrayList<XQuestionnaire>();
	private Log log = LogFactory.getLog(this.getClass());
	private boolean repeatable;
	
	
	
	/**
	 * @param definition
	 * @param model
	 */
	public XQuestionnaireView(Element definition, XQuestionnaire owner) {

		this.definition = definition;
		this.owner = owner;
		this.canvas = new VerticalPanel();
		this.canvas.addStyleName(STYLENAME);
		this.repeatable = owner.isRepeatable();

		String classAttr = definition.getAttribute(XQS.ATTR_CLASS);
		if (classAttr != null) {
			this.canvas.addStyleName(classAttr);
		}
				
	}
	
	
	
	
	public void init() {
		
		this.canvas.clear();
		this.widgets = new ArrayList<Widget>();
		this.questions = new ArrayList<XQuestion>();
		this.nestedQuestionnaires = new ArrayList<XQuestionnaire>();

		for (Element e : XML.elements(definition.getChildNodes())) {

			if (e.getTagName().equals(XQS.ELEMENT_QUESTION)) {
				
				renderQuestion(e);
			
			}

			else if (e.getTagName().equals(XQS.ELEMENT_QUESTIONNAIRE)) {
				
				renderQuestionnaire(e);
			
			}
			
			else {
				
				render(e);
				
			}

		}
		
		if (this.repeatable) {
			initRepeatable();
		}
		
		refresh();

	}
	
	
	

	/**
	 * @param data
	 */
	public void init(Element data) {

		this.canvas.clear();
		this.widgets = new ArrayList<Widget>();
		this.questions = new ArrayList<XQuestion>();
		this.nestedQuestionnaires = new ArrayList<XQuestionnaire>();

		for (Element childDefinition : XML.elements(definition.getChildNodes())) {

			if (childDefinition.getTagName().equals(XQS.ELEMENT_QUESTION)) {
				
				renderQuestion(childDefinition, data);
			
			}

			else if (childDefinition.getTagName().equals(XQS.ELEMENT_QUESTIONNAIRE)) {
				
				renderQuestionnaire(childDefinition, data);
			
			}
			
			else {
				
				render(childDefinition);
				
			}

		}
		
		if (this.repeatable) {
			initRepeatable();
		}
		
		refresh();

	}
	
	

	/**
	 * 
	 */
	private void initRepeatable() {

		Panel buttonPanel = new HorizontalPanel();
		this.widgets.add(buttonPanel);
		
		Button repeatButton = new Button();
		repeatButton.setText("+");
		buttonPanel.add(repeatButton);
		repeatButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				owner.repeat();
			}

		});
		
		List<XQuestionnaire> repeats = owner.getRepeats();
		if (repeats.size() > 1) {

			Button removeButton = new Button();
			removeButton.setText("-");
			buttonPanel.add(removeButton);
			removeButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					owner.remove();
				}

			});
			
		}

	}

	
	
	
	
	/**
	 * @param questionDefinition
	 */
	private void renderQuestion(Element questionDefinition) {
		log.enter("renderQuestion");
		
		XQuestion q = new XQuestion(questionDefinition, owner);
		
		this.questions.add(q);
		
		q.init();
		
		this.widgets.add(q);
		
		log.leave();
	}




	/**
	 * @param e
	 */
	private void renderQuestion(Element questionDefinition, Element dataParent) {
		log.enter("renderQuestion");
		
		log.trace("create prototype");
		XQuestion prototype = new XQuestion(questionDefinition, owner);
		this.questions.add(prototype);
		this.widgets.add(prototype);

		// we need to know, is it repeatable, and if so, how many matching data elements?
		
		log.trace("find expected element for definition");
		String expectedElementName = prototype.getModel().getElementName();
		String expectedElementNamespaceUri = prototype.getModel().getElementNamespaceUri();
		log.trace("look for potentially matching data elements ["+expectedElementNamespaceUri+" :: "+expectedElementName+"]");
		List<Element> dataElements = new ArrayList<Element>();
		for (Element e : XML.elements(dataParent.getChildNodes())) {
			log.trace("examining element: "+e.toString());
			if (
				expectedElementName.equals(XML.getLocalName(e)) &&
				expectedElementNamespaceUri.equals(e.getNamespaceURI())
			) {
				dataElements.add(e);
			}
		}

		log.trace("found "+dataElements.size()+" data elements");
		
		if (dataElements.size() == 0) {
			log.trace("no matching data elements, use prototype");
			prototype.init(); // initialise without data
		}
		else if (dataElements.size() >= 1) {
			log.trace("found at least one matching data element, initialising prototype with first data element");
			prototype.init(dataElements.get(0));
		}
		
		if (dataElements.size() > 1 && prototype.isRepeatable()) {
			log.trace("found more than one matching data element ("+dataElements.size()+"), and prototype is repeatable");
			
			for (int i=1; i<dataElements.size(); i++) {
				Element dataElement = dataElements.get(i);
				XQuestion clone = prototype.clone();
				this.questions.add(clone);
				this.widgets.add(clone);
				log.trace("init clone "+i+" with data");
				clone.init(dataElement);				
			}
			
		}
		
		log.leave();
	}




	/**
	 * @param questionnaireDefinition
	 */
	private void renderQuestionnaire(Element questionnaireDefinition) {
		log.enter("renderQuestionnaire");
		
		XQuestionnaire q = new XQuestionnaire(questionnaireDefinition, owner);
		q.init();
		
		this.nestedQuestionnaires.add(q);
		
		this.widgets.add(q);
		
		log.leave();
	}




	/**
	 * @param e
	 */
	private void renderQuestionnaire(Element questionnaireDefinition, Element dataParent) {
		log.enter("renderQuestionnaire");
		
		log.trace("create prototype");
		XQuestionnaire prototype = new XQuestionnaire(questionnaireDefinition, owner);
		this.nestedQuestionnaires.add(prototype);
		this.widgets.add(prototype);

		// we need to know, is it repeatable, and if so, how many matching data elements?
		
		// TODO code smell
		log.trace("find expected element for definition");
		String expectedElementName = prototype.getModel().getElementName();
		String expectedElementNamespaceUri = prototype.getModel().getElementNamespaceUri();
		log.trace("look for potentially matching data elements ["+expectedElementNamespaceUri+" :: "+expectedElementName+"]");
		List<Element> dataElements = new ArrayList<Element>();
		for (Element e : XML.elements(dataParent.getChildNodes())) {
			log.trace("examining element: "+e.toString());
			if (
				expectedElementName.equals(XML.getLocalName(e)) &&
				expectedElementNamespaceUri.equals(e.getNamespaceURI())
			) {
				dataElements.add(e);
			}
		}
		log.trace("found "+dataElements.size()+" data elements");
		
		if (dataElements.size() == 0) {
			log.trace("no matching data elements, use prototype");
			prototype.init(); // initialise without data
		}
		else if (dataElements.size() >= 1) {
			log.trace("found at least one matching data element, initialising prototype with first data element");
			prototype.init(dataElements.get(0));
		}
		
		if (dataElements.size() > 1 && prototype.isRepeatable()) {
			log.trace("found more than one matching data element ("+dataElements.size()+"), and prototype is repeatable");
			
			for (int i=1; i<dataElements.size(); i++) {
				Element dataElement = dataElements.get(i);
				XQuestionnaire clone = prototype.clone();
				log.trace("init clone "+i+" with data");
				this.nestedQuestionnaires.add(clone);
				this.widgets.add(clone);
				clone.init(dataElement);
			}
		}
		
		log.leave();
	}




	/**
	 * @return
	 */
	public List<XQuestion> getQuestions() {
		return this.questions;
	}

	
	
	
	/**
	 * @return
	 */
	public Widget getCanvas() {
		return this.canvas;
	}




	/**
	 * @return
	 */
	public List<XQuestionnaire> getNestedQuestionnaires() {
		return this.nestedQuestionnaires;
	}




	/**
	 * @param clone
	 * @param xQuestion
	 */
	public void addQuestion(XQuestion newQuestion, XQuestion previousSibling) {
		
		// need to do two things
		
		// first add to list of questions
		int qindex = this.questions.indexOf(previousSibling) + 1;
		this.questions.add(qindex, newQuestion);
		
		// next, add to widgets
		int windex = this.widgets.indexOf(previousSibling) + 1;
		this.widgets.add(windex, newQuestion);
		
		// initialise new question
		newQuestion.init();
			
		// finally, refresh
		refresh();
		
	}




	/**
	 * @param clone
	 * @param xQuestionnaire
	 */
	public void addQuestionnaire(XQuestionnaire newQuestionnaire, XQuestionnaire previousSibling) {

		// need to do two things
		
		// first add to list of questions
		int qindex = this.nestedQuestionnaires.indexOf(previousSibling) + 1;
		this.nestedQuestionnaires.add(qindex, newQuestionnaire);
		
		// next, add to widgets
		int windex = this.widgets.indexOf(previousSibling) + 1;
		this.widgets.add(windex, newQuestionnaire);
		
		// initialise new questionnaire
		newQuestionnaire.init();
			
		// finally, refresh
		refresh();

	}




	public void removeQuestion(XQuestion question) {
		
		this.questions.remove(question);
		this.widgets.remove(question);
		refresh();

	}




	public void removeQuestion(XQuestionnaire questionnaire) {
		
		this.nestedQuestionnaires.remove(questionnaire);
		this.widgets.remove(questionnaire);
		refresh();
		
	}




}
