/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSecret extends XInput {

	
	
	
	/**
	 * @param definition
	 * @param model
	 */
	public XSecret(Element definition, XQuestionModel model) {
		super(definition, model);
	}

	

	
	@Override
	protected void checkDefinitionTagName() {
		checkDefinitionTagName(XQS.ELEMENT_SECRET);
	}
	

	
	
	@Override
	protected void constructTextBox() {

		textBox = new PasswordTextBox();
		this.canvas.add(textBox);		

	}






}
