/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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
	private XFormControl formControl;
	private XQuestionModel model;
	private boolean repeatable = false;
	private XQuestion owner;

	
	
	
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
				
	}
	
	
	
	
	public void init() {
	
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
	 * @param data
	 */
	public void init(Element data) {

		for (Element e : XML.elements(definition.getChildNodes())) {

			if (
					e.getTagName().equals(XQS.ELEMENT_INPUT) ||
					e.getTagName().equals(XQS.ELEMENT_SECRET) ||
					e.getTagName().equals(XQS.ELEMENT_TEXTAREA) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT) ||
					e.getTagName().equals(XQS.ELEMENT_SELECT1)					
			) {
				
				initFormControl(e);
				this.formControl.setValue(owner.getModel().getValue(), false);
			
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




	/**
	 * @param formControlDefinition
	 */
	private void initFormControl(Element formControlDefinition) {
		log.enter("initInput");
		
		if (this.formControl != null) {
			throw new XQuestionFormatException("bad view definition, found more than one form control");
		}
		
		this.formControl = XFormControl.create(formControlDefinition, this.model, this.owner);

//		if (formControlDefinition.getTagName().equals(XQS.ELEMENT_INPUT)) {
//
//			this.formControl = new XInput(formControlDefinition, this.model);
//
////			Panel container = null;
////			
////			log.trace("found input");
////			container = new HorizontalPanel();
////			
////			log.trace("look for label");
////			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
////			if (labelContent != null) {
////				log.trace("found label: "+labelContent);
////				container.add(new Label(labelContent));
////			}
////			
////			log.trace("instantiate control");
////			TextBox control = new TextBox();
////			container.add(control);
////			
////			log.trace("add value change handler");
////			control.addValueChangeHandler(new ValueChangeHandler<String>() {
////				
////				public void onValueChange(ValueChangeEvent<String> event) {
////					log.enter("[anonymous ValueChangeHandler] :: onValueChange");
////					log.trace("received value change: "+event.getValue());
////					model.setValue(event.getValue());
////					log.leave();
////				}
////
////			});
////			
////			log.trace("look for hint");
////			String hintContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_HINT);
////			if (hintContent != null) {
////				log.trace("found hint: "+hintContent);
////				control.setTitle(hintContent);
////			}
////			
////			this.formControl = container;
//			
//		}
//
//		else if (formControlDefinition.getTagName().equals(XQS.ELEMENT_SECRET)) {
//
//			log.trace("found secret");
//
//			this.formControl = new XSecret(formControlDefinition, this.model);
//			
////			Panel container = null;
////			
////			container = new HorizontalPanel();
////			
////			log.trace("look for label");
////			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
////			if (labelContent != null) {
////				log.trace("found label: "+labelContent);
////				container.add(new Label(labelContent));
////			}
////			
////			log.trace("instantiate control");
////			PasswordTextBox control = new PasswordTextBox();
////			container.add(control);
////			
////			log.trace("add value change handler");
////			control.addValueChangeHandler(new ValueChangeHandler<String>() {
////				
////				public void onValueChange(ValueChangeEvent<String> event) {
////					log.enter("[anonymous ValueChangeHandler] :: onValueChange");
////					log.trace("received value change: "+event.getValue());
////					model.setValue(event.getValue());
////					log.leave();
////				}
////
////			});
////			
////			log.trace("look for hint");
////			String hintContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_HINT);
////			if (hintContent != null) {
////				log.trace("found hint: "+hintContent);
////				control.setTitle(hintContent);
////			}
////			
////			this.formControl = container;
//
//		}
//
//		else if (formControlDefinition.getTagName().equals(XQS.ELEMENT_TEXTAREA)) {
//
//			log.trace("found textarea");
//
//			this.formControl = new XTextArea(formControlDefinition, model);
//
////			Panel container = null;
////			
////			container = new VerticalPanel();
////			
////			log.trace("look for label");
////			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
////			if (labelContent != null) {
////				log.trace("found label: "+labelContent);
////				container.add(new Label(labelContent));
////			}
////			
////			log.trace("instantiate control");
////			TextArea control = new TextArea();
////			container.add(control);
////			
////			log.trace("add value change handler");
////			control.addValueChangeHandler(new ValueChangeHandler<String>() {
////				
////				public void onValueChange(ValueChangeEvent<String> event) {
////					log.enter("[anonymous ValueChangeHandler] :: onValueChange");
////					log.trace("received value change: "+event.getValue());
////					model.setValue(event.getValue());
////					log.leave();
////				}
////
////			});
////			
////			log.trace("look for hint");
////			String hintContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_HINT);
////			if (hintContent != null) {
////				log.trace("found hint: "+hintContent);
////				control.setTitle(hintContent);
////			}
////			
////			this.formControl = container;
//
//		}
//
//		else if ( 
//				formControlDefinition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
//				( formControlDefinition.getAttribute(XQS.ATTR_APPEARANCE) == null || formControlDefinition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_FULL) ) 
//			) {
//
//			log.trace("found select1, appearance full");
//			
//			this.formControl = new XSelect1Full(formControlDefinition, model, this.owner);
//
////			Panel container = null;
////			
////			container = new VerticalPanel();
////			
////			log.trace("look for label");
////			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
////			if (labelContent != null) {
////				log.trace("found label: "+labelContent);
////				container.add(new Label(labelContent));
////			}
////			
////			log.trace("instantiate controls");
////			List<Element> itemElements = XML.getElementsByTagName(e, XQS.ELEMENT_ITEM);
////			Grid g = new Grid(itemElements.size(), 2);
////			container.add(g);
////			
////			for (int i=0; i < itemElements.size() ; i++) {
////				
////				Element ie = itemElements.get(i);
////				String itemLabel = XML.getElementSimpleContentByTagName(ie, XQS.ELEMENT_LABEL);
////				String itemValue = XML.getElementSimpleContentByTagName(ie, XQS.ELEMENT_VALUE);
////				
////				log.trace("adding radio button for item label: "+itemLabel+"; value: "+itemValue);
////
////				if (itemLabel != null) {
////					log.trace("label != null, adding label widget");
////					g.setWidget(i, 0, new Label(itemLabel));
////				}
////				
////				if (itemValue == null) {
////					throw new XQuestionFormatException("bad select1 definition, found no value for item ["+i+"]");
////				}
////				
////				String buttonGroupName = this.owner.getId();
////				if (this.repeatable) {
////					XQuestionnaire parent = this.owner.getParentQuestionnaire();
////					XQuestionnaireView parentView = parent.getView();
////					List<XQuestion> siblings = parentView.getQuestions();
////					buttonGroupName += siblings.indexOf(this.owner);
////				}
////				
////				log.trace("using button group name: "+buttonGroupName);	
////				
////				// TODO what if repeatable and can remove as well as add, need to rename button groups?
////				
////				final RadioButton button = new RadioButton(buttonGroupName);
////				button.setFormValue(itemValue);
////				log.trace("checking button form value, expect: "+itemValue+"; found: "+button.getFormValue());
////				
////				log.trace("adding button to grid");
////				g.setWidget(i, 1, button);
////				
////				// TODO button group?
////				
////				button.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
////
////					public void onValueChange(ValueChangeEvent<Boolean> event) {
////						log.enter("[anonymous ValueChangeHandler] :: onValueChange");
////						log.trace("received value change: "+event.getValue());
////						if (event.getValue()) {
////							model.setValue(button.getFormValue());	
////							log.trace("setting model with value: "+button.getFormValue());
////						}
////						log.leave();
////					}
////				});
////			}
////
////						
////			log.trace("TODO look for hint");
////			
////			this.formControl = container;
//
//		}
//
//		else if ( 
//				formControlDefinition.getTagName().equals(XQS.ELEMENT_SELECT1) && 
//				( formControlDefinition.getAttribute(XQS.ATTR_APPEARANCE) != null || formControlDefinition.getAttribute(XQS.ATTR_APPEARANCE).equals(XQS.APPEARANCE_MINIMAL) ) 
//			) {
//
//			log.trace("found select1, appearance minimal");
//
//			this.formControl = new XSelect1Minimal(formControlDefinition, this.model);
//			
////			Panel container = null;
////			
////			container = new HorizontalPanel();
////			
////			log.trace("look for label");
////			String labelContent = XML.getElementSimpleContentByTagName(e, XQS.ELEMENT_LABEL);
////			if (labelContent != null) {
////				log.trace("found label: "+labelContent);
////				container.add(new Label(labelContent));
////			}
////			
////			log.trace("instantiate controls");
////			final ListBox box = new ListBox();
////			container.add(box);
////			
////			List<Element> itemElements = XML.getElementsByTagName(e, XQS.ELEMENT_ITEM);
////			
////			for (int i=0; i < itemElements.size() ; i++) {
////				
////				Element ie = itemElements.get(i);
////				String itemLabel = XML.getElementSimpleContentByTagName(ie, XQS.ELEMENT_LABEL);
////				String itemValue = XML.getElementSimpleContentByTagName(ie, XQS.ELEMENT_VALUE);
////				
////				if (itemLabel == null) {
////					throw new XQuestionFormatException("bad select1 definition, found no label for item ["+i+"]");
////				}
////				
////				if (itemValue == null) {
////					throw new XQuestionFormatException("bad select1 definition, found no value for item ["+i+"]");
////				}
////				
////				log.trace("adding list item for item label: "+itemLabel+"; value: "+itemValue);
////				
////				box.addItem(itemLabel, itemValue);
////				
////				if (i == 0) {
////					model.setValue(itemValue); // set initial value
////				}
////								
////			}
////
////			box.addClickHandler(new ClickHandler() {
////				
////				public void onClick(ClickEvent event) {
////					log.enter("[anonymous ClickHandler] :: onClick");
////					int index = box.getSelectedIndex();
////					String value = box.getValue(index);
////					if (model.getValue() == null || !model.getValue().equals(value)) {
////						model.setValue(value);	
////						log.trace("setting model with value: "+value);
////					}
////					else {
////						log.trace("no change to selection");
////					}
////					log.leave();
////				}
////				
////			});
////									
////			log.trace("TODO look for hint");
////			
////			this.formControl = container; 
//
//		}

		if (this.formControl != null) {
			
			log.trace("adding form control to list of widgets");
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
