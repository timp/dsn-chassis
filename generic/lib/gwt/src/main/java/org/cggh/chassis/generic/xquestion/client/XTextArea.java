/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XTextArea extends XFormControl {

	
	
	
	
	public static final String STYLENAME = "xtextarea";
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private TextArea textArea;
	private Label readOnlyLabel;

	
	
	
	public XTextArea(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
		construct();
	}
	
	
	
	
	private void construct() {
		log.enter("construct");
		
		log.debug("check guard conditions");
		checkDefinitionTagName(XQS.ELEMENT_TEXTAREA);

		log.debug("construct canvas");
		constructCanvas();
		
		log.debug("add custom style");
		addCustomStyle();
		
		log.debug("look for label");
		constructLabel();
		
		log.debug("instantiate text area");
		constructTextArea();
		
		log.debug("add value change handler");
		addValueChangeHandler();
		
		log.debug("look for hint");
		constructHint();

		log.debug("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	

	
	private void constructCanvas() {

		this.canvas = new VerticalPanel();
		this.canvas.addStyleName(STYLENAME);
		
	}
	
	
	
	
	private void constructTextArea() {

		if (readOnly) {

			readOnlyLabel = new Label();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);

		}
		else {
			textArea = new TextArea();
			this.canvas.add(textArea);					
		}

	}




	/**
	 * 
	 */
	private void addValueChangeHandler() {

		if (!readOnly) {
			textArea.addValueChangeHandler(new TextInputValueChangeHandler());
		}

	}


	

	@Override
	public void setValue(String value, boolean fireEvents) {
		if (readOnly) {
			this.readOnlyLabel.setText(value);
		}
		else {
			this.textArea.setValue(value, fireEvents);
		}
	}




}
