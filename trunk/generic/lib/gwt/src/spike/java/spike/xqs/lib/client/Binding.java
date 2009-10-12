/**
 * 
 */
package spike.xqs.lib.client;

import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class Binding {
	
	
	
	private Element element;
	private Element definition;
	private String defaultPrefix;
	private String defaultNamespaceUri;
	private Questionnaire parent;
	
	
	
	public Element getElement() {
		return this.element;
	}

	
	
	/**
	 * @param e
	 * @param defaultNamespaceUri 
	 * @param defaultPrefix 
	 * @param parent 
	 */
	public Binding(Element definition, String defaultPrefix, String defaultNamespaceUri, Questionnaire parent) {
		this.definition = definition;
		this.defaultPrefix = defaultPrefix;
		this.defaultNamespaceUri = defaultNamespaceUri;
		this.parent = parent;

		String name = definition.getAttribute(XQS.ATTR_NAME);
		if (name == null) {
			throw new XQSFormatException("missing name attribute on element binding");
		}
		String prefix = definition.getAttribute(XQS.ATTR_PREFIX);
		String namespaceUri = definition.getAttribute(XQS.ATTR_NAMESPACEURI);
		if (prefix == null) {
			prefix = defaultPrefix;
		}
		if (namespaceUri == null) {
			namespaceUri = defaultNamespaceUri;
		}
		element = XMLNS.createElementNS(name, prefix, namespaceUri);
		
		if (parent != null) {
			parent.getBinding().getElement().appendChild(element);
		}
		
	}



	/**
	 * @param value
	 */
	public void setValue(String value) {
		XML.setSimpleContent(element, value);
	}
	
	

}
