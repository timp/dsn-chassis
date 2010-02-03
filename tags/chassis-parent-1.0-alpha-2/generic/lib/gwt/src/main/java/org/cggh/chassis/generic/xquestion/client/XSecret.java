/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSecret extends XInputString {

	
	
	
	/**
	 * @param definition
	 * @param model
	 * @param readOnly 
	 */
	public XSecret(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
	}

	

	
	@Override
	protected void checkDefinitionTagName() {
		checkDefinitionTagName(XQS.ELEMENT_SECRET);
	}
	

	
	
	@Override
	protected void constructTextBox() {

		if (readOnly) {
			
			readOnlyLabel = new InlineLabel();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);
			
		}
		else {

			textBox = new PasswordTextBox();
			this.canvas.add(textBox);		

		}

	}


	@Override
	public void setValue(String value, boolean fireEvents) {
		
		if (readOnly) {
			this.readOnlyLabel.setText("****");
		}
		else {
			this.textBox.setValue(value, fireEvents);
		}
		
	}




}
