/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionView extends XQSViewBase {

	
	
	

	private TextBox input;
	private XQuestionModel model;

	
	
	
	/**
	 * @param e
	 */
	public XQuestionView(Element definition, XQuestionModel model) {

		this.definition = definition;
		this.model = model;
		this.canvas = new VerticalPanel();

		for (Element e : XML.elements(definition.getChildNodes())) {

			if (e.getTagName().equals(XQS.ELEMENT_INPUT)) {
				
				initInput(e);
			
			}
			
			else {
				
				render(e);
				
			}

		}
		
	}
	
	
	

	/**
	 * @param e
	 */
	private void initInput(Element e) {
		
		if (this.input != null) {
			throw new XQuestionFormatException("bad view definition, found more than one input");
		}

		String type = e.getAttribute(XQS.ATTR_TYPE);
		
		if (type.equals(XQS.INPUTTYPE_TEXT)) {

			this.input = new TextBox();
			
			this.input.addValueChangeHandler(new ValueChangeHandler<String>() {
				
				public void onValueChange(ValueChangeEvent<String> event) {
					model.setValue(event.getValue());
				}

			});
			
			this.canvas.add(this.input);
			
		}

	}




	/**
	 * @return
	 */
	public Widget getCanvas() {
		return this.canvas;
	}




	/**
	 * @return
	 */
	public Widget getInput() {
		return this.input;
	}






}
