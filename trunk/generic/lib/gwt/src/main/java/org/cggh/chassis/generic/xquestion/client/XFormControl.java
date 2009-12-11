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
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public abstract class XFormControl extends Composite {
	
	
	
	public static final String STYLENAME_ANSWER = "answer";
	protected Element definition;
	protected XQuestionModel model;
	protected boolean readOnly = false;
	protected Panel canvas;
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	

	
	
	protected XFormControl(Element definition, XQuestionModel model, boolean readOnly) {
		this.definition = definition;
		this.model = model;
		this.readOnly = readOnly;
	}
	
	
	
	
	protected void checkDefinitionTagName(String expected) {
		String actual = definition.getTagName();
		if (!expected.equals(actual)) {
			String message = "bad definition element tag name, expected: "+expected+"; found: "+actual;
			throw new XQuestionFormatException(message);
		}
	}
	
	

	
	protected void checkDefinitionAppearance(String expected, boolean defaultAppearance) {
		String actual = definition.getAttribute(XQS.ATTR_APPEARANCE);
		
		if (defaultAppearance && actual == null) {
			return; // can omit appearance attribute if default
		}
		
		if (!expected.equals(actual)) {
			String message = "bad definition appearance attribute, expected: "+expected+"; found: "+actual;
			throw new XQuestionFormatException(message);
		}
	}

	
	
	
	protected void addCustomStyle() {

		log.debug("look for css class");
		String classAttribute = this.definition.getAttribute(XQS.ATTR_CLASS);
		if (classAttribute != null) {
			this.canvas.addStyleName(classAttribute);
		}
		
	}

	
	protected void constructLabel() {

		String labelContent = XML.getElementSimpleContentByTagName(definition, XQS.ELEMENT_LABEL);
		if (labelContent != null) {
			log.debug("found label: "+labelContent);
			this.canvas.add(new InlineLabel(labelContent));
		}
		
	}
	
	
	
	
	protected void constructHint() {

		String hintContent = XML.getElementSimpleContentByTagName(definition, XQS.ELEMENT_HINT);
		if (hintContent != null) {
			log.debug("found hint: "+hintContent);
			this.canvas.setTitle(hintContent);
		}

	}


	
	

	
	
	
	

	public abstract void setValue(String value, boolean fireEvents);
	
	
	
	
	public static XFormControl create(Element definition, XQuestionModel model, XQuestion owner, boolean readOnly) {

		if (definition.getTagName().equals(XQS.ELEMENT_INPUT)) {

			if (model.getElementType() == null || model.getElementType().equals(XQS.TYPE_STRING)) {
				return new XInputString(definition, model, readOnly);				
			}
			else if (model.getElementType().equals(XQS.TYPE_DATE)) {
				return new XInputDate(definition, model, readOnly);
			}
			else if (model.getElementType().equals(XQS.TYPE_GYEAR)) {
				return new XInputString(definition, model, readOnly); // TODO anything special?
			}
			else {
				
				// TODO Review fail hard 
				throw new XQSException("Unrecognised input model element type :" + definition.getTagName());
			}
			
		}

		else if (definition.getTagName().equals(XQS.ELEMENT_SECRET)) {

			return new XSecret(definition, model, readOnly);
			
		}

		else if (definition.getTagName().equals(XQS.ELEMENT_TEXTAREA)) {

			return new XTextArea(definition, model, readOnly);

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
				( definition.getAttribute(XQS.ATTR_APPEARANCE) == null || definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_FULL) ) 
			) {

			return new XSelect1Full(definition, model, owner, readOnly);

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_MINIMAL) 
			) {

			return new XSelect1Minimal(definition, model, readOnly);			

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_COMPACT) 
			) {

			// TODO implement
			throw new XQSException("Unimplemented attribute :" + definition.getAttribute(XQS.ATTR_APPEARANCE));

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT) && 
				( definition.getAttribute(XQS.ATTR_APPEARANCE) == null || definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_FULL) ) 
			) {

			return new XSelectFull(definition, model, readOnly);			

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_MINIMAL) 
			) {

			// TODO implement
			throw new XQSException("Unimplemented attribute :" + definition.getAttribute(XQS.ATTR_APPEARANCE));

		}

		else if ( 
				definition.getTagName().equals(XQS.ELEMENT_SELECT) && 
				definition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_COMPACT) 
			) {

			// TODO implement
			throw new XQSException("Unimplemented attribute :" + definition.getAttribute(XQS.ATTR_APPEARANCE));

		}
		
		else {
			
			// TODO Review fail hard 
			throw new XQSException("Unrecognised model tag name :" + definition.getTagName());
			
		}

	}
	
	
	

	protected class TextInputValueChangeHandler implements ValueChangeHandler<String> {

		private Log log = LogFactory.getLog(this.getClass());
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
		 */
		public void onValueChange(ValueChangeEvent<String> event) {
			log.enter("onValueChange");
			log.debug("event value: "+event.getValue());
			model.setValue(event.getValue());
			log.leave();
		}
		
	}

	
	
	
}
