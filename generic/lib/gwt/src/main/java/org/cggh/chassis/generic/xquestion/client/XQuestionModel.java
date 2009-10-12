/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionModel extends XQSModelBase {

	
	
	
	private XQuestionnaire parentQuestionnaire;

	
	
	
	/**
	 * @param parentQuestionnaire 
	 * @param e
	 */
	public XQuestionModel(Element definition, XQuestionnaire parentQuestionnaire) {
		
		this.definition = definition;
		this.parentQuestionnaire = parentQuestionnaire;
		if (this.parentQuestionnaire != null) {
			this.defaultPrefix = parentQuestionnaire.getDefaultPrefix();
			this.defaultNamespaceUri = parentQuestionnaire.getDefaultNamespaceUri();
		}
		
		init();
		
	}

	
	
	
	public void setValue(String value) {
		
		if (this.element != null) {
			XML.setSimpleContent(element, value);
		}
		
	}
	
	

	
}
