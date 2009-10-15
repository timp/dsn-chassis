/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionView extends XQSViewBase {

	
	
	
	private static final String STYLENAME = "xquestion";
	private Log log = LogFactory.getLog(this.getClass());
	private Widget formControl;
	private XQuestionModel model;
	private boolean repeatable = false;
	private XQuestion owner;;

	
	
	
	/**
	 * @param repeatable 
	 * @param e
	 */
	public XQuestionView(Element definition, XQuestion owner) {

		this.definition = definition;
		this.owner = owner;
		this.model = owner.getModel();
		this.repeatable  = owner.isRepeatable();
		this.canvas = new VerticalPanel();
		this.canvas.addStyleName(STYLENAME);

		for (Element e : XML.elements(definition.getChildNodes())) {

			if (
					e.getTagName().equals(XQS.ELEMENT_INPUT) ||
					e.getTagName().equals(XQS.ELEMENT_SECRET) ||
					e.getTagName().equals(XQS.ELEMENT_TEXTAREA) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT1)					
			) {
				
				initFormControl(e);
			
			}
			
			else {
				
				render(e);
				
			}

		}
		
		if (repeatable) {
			initRepeatable();
		}
		
		refresh();
		
	}
	
	
	

	/**
	 * 
	 */
	private void initRepeatable() {
		Button repeatButton = new Button();
		repeatButton.setText("+");
//		this.canvas.add(repeatButton);
		this.widgets.add(repeatButton);
		repeatButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				owner.repeat();
			}

		});
	}




	// TODO refactor this hideous beast!!!
	
	/**
	 * @param e
	 */
	private void initFormControl(Element e) {
		log.enter("initInput");
		
		if (this.formControl != null) {
			throw new XQuestionFormatException("bad view definition, found more than one form control");
		}

		Panel container = null;
		
		if (e.getTagName().equals(XQS.ELEMENT_INPUT)) {

			log.trace("found input");
			container = new HorizontalPanel();
			
			log.trace("look for label");
			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
			if (labelContent != null) {
				log.trace("found label: "+labelContent);
				container.add(new Label(labelContent));
			}
			
			log.trace("instantiate control");
			TextBox control = new TextBox();
			container.add(control);
			
			log.trace("add value change handler");
			control.addValueChangeHandler(new ValueChangeHandler<String>() {
				
				public void onValueChange(ValueChangeEvent<String> event) {
					log.enter("[anonymous ValueChangeHandler] :: onValueChange");
					log.trace("received value change: "+event.getValue());
					model.setValue(event.getValue());
					log.leave();
				}

			});
			
			log.trace("look for hint");
			String hintContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_HINT);
			if (hintContent != null) {
				log.trace("found hint: "+hintContent);
				control.setTitle(hintContent);
			}
			
			this.formControl = control;
			
		}

		else if (e.getTagName().equals(XQS.ELEMENT_SECRET)) {

			log.trace("found secret");
			
			container = new HorizontalPanel();
			
			log.trace("look for label");
			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
			if (labelContent != null) {
				log.trace("found label: "+labelContent);
				container.add(new Label(labelContent));
			}
			
			log.trace("instantiate control");
			PasswordTextBox control = new PasswordTextBox();
			container.add(control);
			
			log.trace("add value change handler");
			control.addValueChangeHandler(new ValueChangeHandler<String>() {
				
				public void onValueChange(ValueChangeEvent<String> event) {
					log.enter("[anonymous ValueChangeHandler] :: onValueChange");
					log.trace("received value change: "+event.getValue());
					model.setValue(event.getValue());
					log.leave();
				}

			});
			
			log.trace("look for hint");
			String hintContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_HINT);
			if (hintContent != null) {
				log.trace("found hint: "+hintContent);
				control.setTitle(hintContent);
			}
			
			this.formControl = control;

		}

		else if (e.getTagName().equals(XQS.ELEMENT_TEXTAREA)) {

			log.trace("found textarea");
			
			container = new VerticalPanel();
			
			log.trace("look for label");
			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
			if (labelContent != null) {
				log.trace("found label: "+labelContent);
				container.add(new Label(labelContent));
			}
			
			log.trace("instantiate control");
			TextArea control = new TextArea();
			container.add(control);
			
			log.trace("add value change handler");
			control.addValueChangeHandler(new ValueChangeHandler<String>() {
				
				public void onValueChange(ValueChangeEvent<String> event) {
					log.enter("[anonymous ValueChangeHandler] :: onValueChange");
					log.trace("received value change: "+event.getValue());
					model.setValue(event.getValue());
					log.leave();
				}

			});
			
			log.trace("look for hint");
			String hintContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_HINT);
			if (hintContent != null) {
				log.trace("found hint: "+hintContent);
				control.setTitle(hintContent);
			}
			
			this.formControl = control;

		}

		if (container != null && this.formControl != null) {
			this.widgets.add(container);
			
			log.trace("look for css class");
			String styleName = e.getAttribute(XQS.ATTR_CLASS);
			if (styleName != null) {
				this.formControl.addStyleName(styleName);
			}
			
		}

		

		log.leave();
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
	public Widget getFormControl() {
		return this.formControl;
	}






}
