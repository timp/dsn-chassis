/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;


import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionnaireModel extends XQSModelBase {

	
	
	
	private XQuestionnaire owner;



	/**
	 * @param definition
	 */
	public XQuestionnaireModel(Element definition, XQuestionnaire owner) {

		this.definition = definition;
		this.owner = owner;
		this.defaultPrefix = owner.getDefaultPrefix();
		this.defaultNamespaceUri = owner.getDefaultNamespaceUri();
		
		init();

	}

	
		
	

	/**
	 * @param model
	 */
	public void addChild(XQSModelBase child) {
		this.element.appendChild(child.getElement());
	}




}
