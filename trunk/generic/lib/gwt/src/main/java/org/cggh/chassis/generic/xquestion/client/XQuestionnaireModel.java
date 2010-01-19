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

	
	
	
	/**
	 * @param definition
	 */
	public XQuestionnaireModel(Element definition, XQuestionnaire owner) {
		super(definition);
		this.defaultPrefix = owner.getDefaultPrefix();
		this.defaultNamespaceUri = owner.getDefaultNamespaceUri();

		if (this.elementPrefix == null ) {
			this.elementPrefix = this.defaultPrefix;
		}

		if (this.elementNamespaceUri == null) {
			this.elementNamespaceUri = this.defaultNamespaceUri;
		}
		
		// TODO figure out how to refactor this bit, handling defaults
		
	}

	
		
	

	/**
	 * @param model
	 */
	public void addChild(XQSModelBase child) {
		if ( !XML.elements(element.getChildNodes()).contains(child.getElement()) ) {
			this.element.appendChild(child.getElement());
		}
	}





	/**
	 * @param child
	 * @param previousSibling
	 */
	public void addChild(XQuestionModel child, XQuestionModel previousSibling) {
		if (previousSibling != null) {
			if ( !XML.elements(element.getChildNodes()).contains(child.getElement()) ) {
				this.element.insertBefore(child.getElement(), previousSibling.getElement().getNextSibling());
			}
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





	public void removeChild(XQuestionModel model) {
		this.element.removeChild(model.getElement());
	}





	public void removeChild(XQuestionnaireModel model) {
		this.element.removeChild(model.getElement());
	}





}
