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
	 * 
	 */
	private void initRepeatable() {
		Button repeatButton = new Button();
		repeatButton.setText("+");
//		this.canvas.add(repeatButton);
		this.widgets.add(repeatButton);
		repeatButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				owner.repeat();
			}

		});
	}

	
	
	
	/**
	 * @param e
	 */
	private void renderQuestion(Element e) {
		log.enter("renderQuestion");
		
		XQuestion q = new XQuestion(e, owner);
		
		this.questions.add(q);
		
//		this.canvas.add(q);
		this.widgets.add(q);
		
		log.leave();
	}




	/**
	 * @param e
	 */
	private void renderQuestionnaire(Element e) {
		log.enter("renderQuestionnaire");
		
		XQuestionnaire q = new XQuestionnaire(e, owner);
		
		this.nestedQuestionnaires.add(q);
		
//		this.canvas.add(q);
		this.widgets.add(q);
		
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
			
		// finally, refresh
		refresh();

	}
	
	

}
