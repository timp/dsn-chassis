/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XQuestionView extends XQSViewBase {

	
	
	
	private static final String STYLENAME = "xquestion";
	static final String STYLENAME_REPEATABLEBUTTONPANEL = "repeatableButtonPanel";
	private Log log = LogFactory.getLog(this.getClass());
	private XFormControl formControl;
	private boolean repeatable = false;
	private XQuestion owner;
	private List<Element> viewElements = new ArrayList<Element>();
	private boolean vertical =true;

	
	
	
	/**
	 * @param repeatable 
	 * @param e
	 */
	public XQuestionView(Element definition, XQuestion owner) {

		this.definition = definition;
		this.owner = owner;
		this.model = owner.getModel();
		this.repeatable  = owner.isRepeatable();
		
		parseDefinition();
		
		constructCanvas();
		
	}
	
	
	
	
	private void parseDefinition() {

		for (Element e : XML.elements(definition.getChildNodes())) {

			if (
				e.getTagName().equals(XQS.ELEMENT_INPUT) ||
				e.getTagName().equals(XQS.ELEMENT_SECRET)
			) {	
				vertical = false;
			}
			else if (
				e.getTagName().equals(XQS.ELEMENT_TEXTAREA) ||
				e.getTagName().equals(XQS.ELEMENT_SELECT) ||
				e.getTagName().equals(XQS.ELEMENT_SELECT1)
			) {
				vertical = true;
			}
			else {
				viewElements.add(e);
			}
			
		}
		
	}




	private void constructCanvas() {
		log.enter("constructCanvas");

		if (vertical) {
			this.canvas = new FlowPanel();			
		}
		else {
			this.canvas = new FlowPanel();
		}
		
		// TODO Consider if this should be in else below
		this.canvas.addStyleName(STYLENAME);
		
		String classAttr = definition.getAttribute(XQS.ATTR_CLASS);
		if (classAttr != null) {
			this.canvas.addStyleName(classAttr);
		} 
		
        log.leave();				
	}




	public void init(boolean readOnly) {
		log.enter("init");
	
		for (Element e : XML.elements(definition.getChildNodes())) {

			if (
					e.getTagName().equals(XQS.ELEMENT_INPUT) ||
					e.getTagName().equals(XQS.ELEMENT_SECRET) ||
					e.getTagName().equals(XQS.ELEMENT_TEXTAREA) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT1)					
			) {
				
				initFormControl(e, readOnly);
			
			}
			
			else {
				
				render(e, readOnly);
				
			}

		}
		
		if (repeatable && !readOnly) {
			initRepeatable();
		}
		
		if (!readOnly && model.hasConditionalRelevance()) {
			owner.getEventBus().addHandler(XValueChangeEvent.TYPE, new XValueChangeHandler() {

				public void onChange(XValueChangeEvent e) {
					log.debug("onChange - refreshing");
					refresh();
				}
				
			});
		}
		
		refresh();
		
		log.leave();

	}

	
	
	
	/**
	 * @param data
	 */
	public void init(Element data, boolean readOnly) {
		log.enter("init[Element]");

		for (Element e : XML.elements(definition.getChildNodes())) {

			if (
					e.getTagName().equals(XQS.ELEMENT_INPUT) ||
					e.getTagName().equals(XQS.ELEMENT_SECRET) ||
					e.getTagName().equals(XQS.ELEMENT_TEXTAREA) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT1)					
			) {
				
				initFormControl(e, readOnly);
				
				String value = owner.getModel().getValue();
				log.debug("setting form control value: "+value);
				this.formControl.setValue(value, false);
			
			}
			
			else {
				
				render(e, readOnly);
				
			}

		}
		
		if (!readOnly && model.hasConditionalRelevance()) {
			owner.getEventBus().addHandler(XValueChangeEvent.TYPE, new XValueChangeHandler() {

				public void onChange(XValueChangeEvent e) {
					refresh();
				}
				
			});
		}
		
		if (repeatable && !readOnly) {
			initRepeatable();
		}
		
		refresh();

	}






	

	/**
	 * 
	 */
	private void initRepeatable() {

		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName(STYLENAME_REPEATABLEBUTTONPANEL);
//		buttonPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
//		buttonPanel.setBorderWidth(4);
		this.widgets.add(buttonPanel);
		
		Button repeatButton = new Button();
		repeatButton.setText("+");
		repeatButton.setTitle("click to add another");
		buttonPanel.add(repeatButton);
		repeatButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				owner.repeat();
			}

		});
		
		List<XQuestion> repeats = owner.getRepeats();
		if (repeats.size() > 1) {

			Button removeButton = new Button();
			removeButton.setText("-");
			removeButton.setTitle("click to remove");
			buttonPanel.add(removeButton);
			removeButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					owner.remove();
				}

			});
			
		}
	}




	/**
	 * @param formControlDefinition
	 * @param readOnly 
	 */
	private void initFormControl(Element formControlDefinition, boolean readOnly) {
		log.enter("initInput");
		
		if (this.formControl != null) {
			throw new XQuestionFormatException("bad view definition, found more than one form control");
		}
		
		this.formControl = XFormControl.create(formControlDefinition, (XQuestionModel) this.model, this.owner, readOnly);
		
		if (this.formControl != null) {
			
			log.debug("adding form control to list of widgets");
			this.widgets.add(formControl);
			
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
