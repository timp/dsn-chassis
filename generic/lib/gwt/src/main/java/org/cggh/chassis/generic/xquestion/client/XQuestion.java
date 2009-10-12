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
public class XQuestion extends Composite {

	
	
	
	private Element definition;
	private XQuestionModel model;
	private XQuestionView view;
	private XQuestionnaire parentQuestionnaire;

	
	
	
	/**
	 * @param documentElement
	 */
	public XQuestion(Element definition) {
		
		this.definition = definition;
		
		init();

	}

	

	
	/**
	 * @param e
	 * @param owner
	 */
	public XQuestion(Element definition, XQuestionnaire parent) {
		
		this.definition = definition;
		this.parentQuestionnaire = parent;
		
		init();

	}




	/**
	 * 
	 */
	private void init() {

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
			throw new XQuestionFormatException("bad question definition, found more than one model");
		}

		this.model = new XQuestionModel(modelDef, this.parentQuestionnaire);
		
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
			throw new XQuestionFormatException("bad question definition, found more than one view");
		}

		this.view = new XQuestionView(viewDef, this.model);

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
	
	
	
}
