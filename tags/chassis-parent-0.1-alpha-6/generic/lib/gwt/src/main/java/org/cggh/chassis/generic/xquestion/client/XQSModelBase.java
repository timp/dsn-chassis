/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public abstract class XQSModelBase {

	
	
	
	protected Element definition;
	protected Element element;
	protected String defaultPrefix;
	protected String defaultNamespaceUri;
	protected String elementName;
	protected String elementPrefix;
	protected String elementNamespaceUri;
	protected XQuestionnaire parentQuestionnaire;
	protected String elementType;

	
	
	
	public XQSModelBase(Element definition) {
		
		this.definition = definition;
		
		construct();
		
	}
	
	

	
	public XQSModelBase(Element definition, XQuestionnaire parentQuestionnaire) {
		
		this.definition = definition;
		this.parentQuestionnaire = parentQuestionnaire;
		
		construct();
		
	}
	
	
	
	protected void construct() {

		for (Element e : XML.elements(definition.getChildNodes())) {
			
			if (e.getTagName().equals(XQS.ELEMENT_ELEMENT)) {
				this.elementName = e.getAttribute(XQS.ATTR_NAME);
				this.elementNamespaceUri = e.getAttribute(XQS.ATTR_NAMESPACEURI);
				this.elementPrefix = e.getAttribute(XQS.ATTR_PREFIX);
				this.elementType = e.getAttribute(XQS.ATTR_TYPE);
			}
			
		}
		
		if (this.parentQuestionnaire != null) {
			this.defaultPrefix = parentQuestionnaire.getDefaultPrefix();
			this.defaultNamespaceUri = parentQuestionnaire.getDefaultNamespaceUri();
		}

		if (this.elementPrefix == null ) {
			this.elementPrefix = this.defaultPrefix;
		}

		if (this.elementNamespaceUri == null) {
			this.elementNamespaceUri = this.defaultNamespaceUri;
		}
		
	}
	
	
	
	
	public void init() {
		
		initElement();

	}
	
	
	
	/**
	 * @param data
	 */
	public void init(Element data) {

		initElement(data);
		
	}
	
	

	

	
	
	
	
	/**
	 * @param elementDefinition
	 */
	protected void initElement() {
		
		if (this.element != null) {
			throw new XQuestionFormatException("element already initialised");
		}

		this.element = XMLNS.createElementNS(elementName, elementPrefix, elementNamespaceUri);
		
	}
	
	
	
	
	protected void initElement(Element data) {

		// disable... why did we do this?
//		if (this.element != null) {
//			if (this.element.getParentNode() != null) {
//				this.element.getParentNode().removeChild(this.element);
//			}
////			throw new XQuestionFormatException("element already initialised");
//		}

		// check data matches definition
		if (
//			this.elementPrefix.equals(data.getPrefix()) && // don't need to compare prefixes
			this.elementName.equals(XML.getLocalName(data)) &&
			this.elementNamespaceUri.equals(data.getNamespaceURI())
		) {
			this.element = data;
		}
		else {
			String message = "data element does not match model definition; expected ["+this.elementName+", "+this.elementNamespaceUri+"], found ["+XML.getLocalName(data)+", "+data.getNamespaceURI()+"]";
			throw new XQuestionDataException(message);
		}

	}
	
	
	

	/**
	 * @return
	 */
	public Element getElement() {
		return this.element;
	}


	

	/**
	 * @return
	 */
	public String getElementName() {
		return this.elementName;
	}




	/**
	 * @return
	 */
	public String getElementNamespaceUri() {
		return this.elementNamespaceUri;
	}
	
	
	
	public String getElementType() {
		return this.elementType;
	}





}
