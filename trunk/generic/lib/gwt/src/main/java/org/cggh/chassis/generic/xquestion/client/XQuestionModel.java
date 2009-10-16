/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionModel extends XQSModelBase {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private XQuestionnaire parentQuestionnaire;

	
	
	private Set<String> values = new HashSet<String>();
	
	
	
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
		
		construct();
		
	}

	
	
	
	public void setValue(String value) {
		log.enter("setValue");
		
		if (this.element != null) {
			log.trace("found element, setting simple content: "+value);
			XML.setSimpleContent(element, value);
		}
	
		log.leave();
	}
	
	
	
	
	public void addValue(String value) {
		values.add(value);
		setValue(this.serialiseMultiValues());
	}
	
	public void removeValue(String value) {
		values.remove(value);
		setValue(this.serialiseMultiValues());
	}
	
	private String serialiseMultiValues() {
		String content = "";
		Iterator<String> it = values.iterator();
		while (it.hasNext()) {
			content += it.next();
			if (it.hasNext()) content += " ";
		}
		return content;
	}




	/**
	 * @return
	 */
	public String getValue() {
		return XML.firstChildNodeValueOrNullIfNoChildren(element);
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
