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
		
		construct();

	}

	
		
	

	/**
	 * @param model
	 */
	public void addChild(XQSModelBase child) {
		this.element.appendChild(child.getElement());
	}





	/**
	 * @param child
	 * @param previousSibling
	 */
	public void addChild(XQuestionModel child, XQuestionModel previousSibling) {
		if (previousSibling != null) {
			this.element.insertBefore(child.getElement(), previousSibling.getElement().getNextSibling());
		}
		else {
			this.addChild(child);
		}
	}





	/**
	 * @param model
	 * @param model2
	 */
	public void addChild(XQuestionnaireModel child, XQuestionnaireModel previousSibling) {
		if (previousSibling != null) {
			this.element.insertBefore(child.getElement(), previousSibling.getElement().getNextSibling());
		}
		else {
			this.addChild(child);
		}
	}





	/**
	 * @param data
	 */
	public void init(Element data) {
		
		// check data matches definition
		if (
//			this.elementPrefix.equals(data.getPrefix()) && // don't need to compare prefixes
			this.elementName.equals(XML.getLocalName(data)) &&
			this.elementNamespaceUri.equals(data.getNamespaceURI())
		) {
			this.element = data;
		}
		else {
			String message = "data element does not match model definition; expected ["+this.elementPrefix+", "+this.elementName+", "+this.elementNamespaceUri+"], found ["+data.getPrefix()+", "+XML.getLocalName(data)+", "+element.getNamespaceURI()+"]";
			throw new XQuestionDataException(message);
		}
		
	}




}
