/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public abstract class XFormControl extends Composite {
	
	
	
	protected Element definition;
	protected XQuestionModel model;
	protected Panel canvas;
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	

	
	
	protected XFormControl(Element definition, XQuestionModel model) {
		this.definition = definition;
		this.model = model;
	}
	
	
	
	
	protected void checkDefinitionTagName(String expected) {
		String actual = definition.getTagName();
		if (!expected.equals(actual)) {
			String message = "bad definition element tag name, expected: "+expected+"; found: "+actual;
			throw new XQuestionFormatException(message);
		}
	}
	
	

	
	protected void checkDefinitionAppearance(String expected) {
		String actual = definition.getAttribute(XQS.ATTR_APPEARANCE);
		if (!expected.equals(actual)) {
			String message = "bad definition appearance attribute, expected: "+expected+"; found: "+actual;
			throw new XQuestionFormatException(message);
		}
	}

	
	
	
	protected void addCustomStyle() {

		log.trace("look for css class");
		String classAttribute = this.definition.getAttribute(XQS.ATTR_CLASS);
		if (classAttribute != null) {
			this.canvas.addStyleName(classAttribute);
		}
		
	}

	
	protected void constructLabel() {

		String labelContent = XML.getElementSimpleContentByTagName(definition, XQS.ELEMENT_LABEL);
		if (labelContent != null) {
			log.trace("found label: "+labelContent);
			this.canvas.add(new Label(labelContent));
		}
		
	}
	
	
	
	
	/**
	 * 
	 */
	protected void constructHint() {

		String hintContent = XML.getElementSimpleContentByTagName(definition, XQS.ELEMENT_HINT);
		if (hintContent != null) {
			log.trace("found hint: "+hintContent);
			this.canvas.setTitle(hintContent);
		}

	}


	
	

	
	
	
	

	public abstract void setValue(String value, boolean fireEvents);
	
	
	
	
	public static XFormControl create(Element definition, XQuestionModel model, XQuestion owner) {

		if (definition.getTagName().equals(XQS.ELEMENT_INPUT)) {

			return new XInput(definition, model);
			
		}

		else if (definition.getTagName().equals(XQS.ELEMENT_SECRET)) {

			return new XSecret(definition, model);
			
		}

		else if (definition.getTagName().equals(XQS.ELEMENT_TEXTAREA)) {

			return new XTextArea(definition, model);

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
				( definition.getAttribute(XQS.ATTR_APPEARANCE) == null || definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_FULL) ) 
			) {

			return new XSelect1Full(definition, model, owner);

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_MINIMAL) 
			) {

			return new XSelect1Minimal(definition, model);			

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_COMPACT) 
			) {

			// TODO
			return null;			

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT) && 
				( definition.getAttribute(XQS.ATTR_APPEARANCE) == null || definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_FULL) ) 
			) {

			return new XSelectFull(definition, model);			

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_MINIMAL) 
			) {

			// TODO
			return null;			

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_COMPACT) 
			) {

			// TODO
			return null;			

		}
		
		else {
			
			return null;
			
		}

	}
	
	
	

	protected class TextInputValueChangeHandler implements ValueChangeHandler<String> {

		private Log log = LogFactory.getLog(this.getClass());
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
		 */
		public void onValueChange(ValueChangeEvent<String> event) {
			log.enter("onValueChange");
			log.trace("event value: "+event.getValue());
			model.setValue(event.getValue());
			log.leave();
		}
		
	}

	
	
	
}
