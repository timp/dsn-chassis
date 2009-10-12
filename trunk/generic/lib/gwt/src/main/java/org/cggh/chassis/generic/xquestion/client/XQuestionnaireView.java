/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionnaireView extends XQSViewBase {

	
	
	
	private XQuestionnaire owner;
	private List<XQuestion> questions = new ArrayList<XQuestion>();
	private List<XQuestionnaire> nestedQuestionnaires = new ArrayList<XQuestionnaire>();
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	/**
	 * @param definition
	 * @param model
	 */
	public XQuestionnaireView(Element definition, XQuestionnaire owner) {

		this.definition = definition;
		this.owner = owner;
		this.canvas = new VerticalPanel();

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
	}

	
	
	
	/**
	 * @param e
	 */
	private void renderQuestion(Element e) {
		log.enter("renderQuestion");
		
		XQuestion q = new XQuestion(e, owner);
		
		this.questions.add(q);
		
		this.canvas.add(q);
		
		log.leave();
	}




	/**
	 * @param e
	 */
	private void renderQuestionnaire(Element e) {
		log.enter("renderQuestionnaire");
		
		XQuestionnaire q = new XQuestionnaire(e, owner);
		
		this.nestedQuestionnaires.add(q);
		
		this.canvas.add(q);
		
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

}
