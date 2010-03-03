/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;
import org.cggh.chassis.generic.xml.client.XMLNS;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public abstract class XQSModelBase {

	
	
	
	private Log log = LogFactory.getLog(XQSModelBase.class);

	
	
	protected Element definition;
	protected Element element;
	protected List<Element> relevant;
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
		
		this.relevant = XML.getElementsByTagName(definition, XQS.ELEMENT_RELEVANT);
		
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


	/**
	 * @return
	 */
	public boolean isRelevant() {
		log.enter("isRelevant");
		
		if (this.relevant.size() == 0) {
			log.debug("no relevant clauses found, relevant by default");
			return true; // relevant by default
		}
		
		for (Element relevant : this.relevant) {
			log.debug("relevant clauses found, testing relevance");
			if (isRelevant(relevant)) return true; // return true if any tests are true
		}
		
		log.leave();
		return false;
	}



	/**
	 * @param relevant
	 * @return
	 */
	private boolean isRelevant(Element relevant) {
		log.enter("isRelevant");
		
		String path = relevant.getAttribute(XQS.ATTR_PATH);
		String comparator = relevant.getAttribute(XQS.ATTR_COMPARATOR);
		String value = relevant.getAttribute(XQS.ATTR_VALUE);
		
		log.debug("path: "+path);
		log.debug("comparator: "+comparator);
		log.debug("value: "+value);
		
		Set<Element> elements = XML.getElementsBySimplePath(this.element, path);
		log.debug("found "+elements.size()+" elements for path");
		
		if (comparator.equals(XQS.COMPARATOR_EQUALS)) {
			
			log.debug("comparing for equality");
			for (Element e : elements) {
				String content = XML.firstChildNodeValueOrNullIfNoChildren(e);
				log.debug("comparing content ["+content+"] with value ["+value+"]");
				if (content != null && content.equals(value)) {
					log.debug("found match");
					log.leave();
					return true;
				}
				else {
					log.debug("no match");
				}
			}
		}
		else if (comparator.equals(XQS.COMPARATOR_CONTAINS)) {
			
			log.debug("comparing for containment");
			for (Element e : elements) {
				String content = XML.firstChildNodeValueOrNullIfNoChildren(e);
				log.debug("comparing content ["+content+"] with value ["+value+"]");
				
				if (content != null) {
					
					// TODO: Centralise the space delimiter for values. Where is it used to join content values? May be XQuestionModel serialiseMultiValues().
					String[] contentValues = content.split(" ");
					
					for (int i = 0; i < contentValues.length; i++) {
						
						if (contentValues[i].equals(value)) {
							log.debug("found containment");
							log.leave();
							return true;
						
						}
					}
					
					log.debug("no match");
				}
				else {
					log.debug("no match");
				}
			}
			
		}
		
		log.leave();
		return false;
	}



	public boolean hasConditionalRelevance() {
		return relevant.size() > 0;
	}

	
	
}
