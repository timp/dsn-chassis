/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XInputString extends XFormControl {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	protected TextBox textBox;
	protected Label readOnlyLabel;
	
	
	
	
	public static final String STYLENAME = "xinput-string";
	
	
	
	public XInputString(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);		
		construct();
	}
	
	
	
	
	protected void construct() {
		log.enter("construct");
		
		log.trace("check guard conditions");
		checkDefinitionTagName();

		log.trace("construct canvas");
		constructCanvas();
		
		log.trace("add custom style");
		addCustomStyle();
		
		log.trace("look for readOnlyLabel");
		constructLabel();
		
		log.trace("instantiate text box");
		constructTextBox();
		
		log.trace("add value change handler");
		addValueChangeHandler();
		
		log.trace("look for hint");
		constructHint();

		log.trace("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	

	protected void checkDefinitionTagName() {
		checkDefinitionTagName(XQS.ELEMENT_INPUT);
	}
	
	

	
	protected void constructCanvas() {

		this.canvas = new HorizontalPanel();
		this.canvas.addStyleName(STYLENAME);
		
	}
	
	
	
	
	protected void constructTextBox() {

		if (readOnly) {
			readOnlyLabel = new Label();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);
		}
		else {
			textBox = new TextBox();
			this.canvas.add(textBox);		
		}

	}




	/**
	 * 
	 */
	protected void addValueChangeHandler() {

		if (!readOnly) {
			textBox.addValueChangeHandler(new TextInputValueChangeHandler());
		}

	}




	
	public void setValue(String value, boolean fireEvents) {
		
		if (readOnly) {
			this.readOnlyLabel.setText(value);
		}
		else {
			this.textBox.setValue(value, fireEvents);
		}
		
	}


}
