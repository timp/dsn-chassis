/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XInputString extends XFormControl {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	protected TextBox textBox;
	protected InlineLabel readOnlyLabel;
	
	
	
	
	public static final String STYLENAME = "xinput-string";
	
	
	
	public XInputString(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);		
		construct();
	}
	
	
	
	
	protected void construct() {
		log.enter("construct");
		
		log.debug("check guard conditions");
		checkDefinitionTagName();

		log.debug("construct canvas");
		constructCanvas();
		
		log.debug("add custom style");
		addCustomStyle();
		
		log.debug("look for readOnlyLabel");
		constructLabel();
		
		log.debug("instantiate text box");
		constructTextBox();
		
		log.debug("add value change handler");
		addValueChangeHandler();
		
		log.debug("look for hint");
		constructHint();

		log.debug("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	

	protected void checkDefinitionTagName() {
		checkDefinitionTagName(XQS.ELEMENT_INPUT);
	}
	
	

	
	protected void constructCanvas() {

		this.canvas = new FlowPanel();
		this.canvas.addStyleName(STYLENAME);
		
	}
	
	
	
	
	protected void constructTextBox() {

		if (readOnly) {
			readOnlyLabel = new InlineLabel();
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
