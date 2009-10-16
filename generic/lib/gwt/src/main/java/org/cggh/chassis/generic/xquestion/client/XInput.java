/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XInput extends XFormControl {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	protected TextBox textBox;
	
	
	
	
	public static final String STYLENAME = "xinput";
	
	
	
	public XInput(Element definition, XQuestionModel model) {
		super(definition, model);		
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
		
		log.trace("look for label");
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

		textBox = new TextBox();
		this.canvas.add(textBox);		

	}




	/**
	 * 
	 */
	protected void addValueChangeHandler() {

		textBox.addValueChangeHandler(new TextInputValueChangeHandler());

	}




	
	public void setValue(String value, boolean fireEvents) {
		this.textBox.setValue(value, fireEvents);
	}


}
