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

	
	
	private Set<String> values = new HashSet<String>();



	private XQuestion owner;
	
	
	
	/**
	 * @param parentQuestionnaire 
	 * @param e
	 */
	public XQuestionModel(XQuestion owner, Element definition, XQuestionnaire parentQuestionnaire) {
		super(definition, parentQuestionnaire);
		this.owner = owner;
	}

	
	
	@Override
	protected void initElement(Element data) {
		super.initElement(data);
		parseMultiValues();
	}
	
	
	
	public void setValue(String value) {
		log.enter("setValue");
		
		if (this.element != null) {
			
			log.debug("found element, setting simple content: "+value);
			XML.setSimpleContent(element, value);
			parseMultiValues();
			
			XValueChangeEvent e = new XValueChangeEvent();
			owner.getEventBus().fireEvent(e);

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
	
	private void parseMultiValues() {
		log.enter("parseMultiValues");
		
		log.debug("parsing: "+this.getValue());
		if (this.getValue() != null) {
			String[] tokens = this.getValue().split("\\s");
			log.debug("found "+tokens.length+" tokens");
			for (String token : tokens) {
				log.debug("found token: "+token);
				this.values.add(token);
			}
		}
		log.leave();
	}


	
	/**
	 * @return
	 */
	public String getValue() {
		return XML.firstChildNodeValueOrNullIfNoChildren(element);
	}




	public Set<String> getValues() {
		return new HashSet<String>(this.values);
	}









}
