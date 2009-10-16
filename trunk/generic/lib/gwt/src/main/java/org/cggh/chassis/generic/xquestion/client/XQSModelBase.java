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

	
	
	
	protected void construct() {

		for (Element e : XML.elements(definition.getChildNodes())) {
			
			if (e.getTagName().equals(XQS.ELEMENT_ELEMENT)) {
				constructElement(e);
			}
			
		}
		
	}
	
	
	
	
	public void init() {
		// nothing to do
	}
	
	
	
	
	/**
	 * @param e
	 */
	protected void constructElement(Element e) {
		
		if (this.element != null) {
			throw new XQuestionFormatException("bad model definition, found more than one element");
		}

		elementName = e.getAttribute(XQS.ATTR_NAME);
		elementPrefix = e.getAttribute(XQS.ATTR_PREFIX);
		if (elementPrefix == null ) {
			elementPrefix = this.defaultPrefix;
		}
		elementNamespaceUri = e.getAttribute(XQS.ATTR_NAMESPACEURI);
		if (elementNamespaceUri == null) {
			elementNamespaceUri = this.defaultNamespaceUri;
		}
		
		this.element = XMLNS.createElementNS(elementName, elementPrefix, elementNamespaceUri);
		
	}
	
	
	

	/**
	 * @return
	 */
	public Element getElement() {
		return this.element;
	}



}
