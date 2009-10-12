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

	
	
	
	protected void init() {

		for (Element e : XML.elements(definition.getChildNodes())) {
			
			if (e.getTagName().equals(XQS.ELEMENT_ELEMENT)) {
				initElement(e);
			}
			
		}
		
	}
	
	
	
	
	/**
	 * @param e
	 */
	protected void initElement(Element e) {
		
		if (this.element != null) {
			throw new XQuestionFormatException("bad model definition, found more than one element");
		}

		String name = e.getAttribute(XQS.ATTR_NAME);
		String prefix = e.getAttribute(XQS.ATTR_PREFIX);
		if (prefix == null ) {
			prefix = this.defaultPrefix;
		}
		String namespaceUri = e.getAttribute(XQS.ATTR_NAMESPACEURI);
		if (namespaceUri == null) {
			namespaceUri = this.defaultNamespaceUri;
		}
		
		this.element = XMLNS.createElementNS(name, prefix, namespaceUri);
		
	}
	
	
	

	/**
	 * @return
	 */
	public Element getElement() {
		return this.element;
	}



}
