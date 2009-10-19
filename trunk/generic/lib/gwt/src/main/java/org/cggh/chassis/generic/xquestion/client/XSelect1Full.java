/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSelect1Full extends XSelectBase {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Grid radioButtonGrid;
	private String buttonGroupName;
	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	protected XQuestion owner;
	
	
	
	public static final String STYLENAME = "xselect1-full";

	
	
	
	/**
	 * @param definition
	 * @param model
	 * @param readOnly 
	 */
	XSelect1Full(Element definition, XQuestionModel model, XQuestion owner, boolean readOnly) {
		super(definition, model, readOnly);
		this.owner = owner;
		construct();
	}

	
	
	
	private void construct() {
		log.enter("construct");
		
		log.trace("check guard conditions");
		checkDefinition();

		log.trace("construct canvas");
		constructCanvas();
		
		log.trace("add custom style");
		addCustomStyle();
		
		log.trace("look for label");
		constructLabel();
		
		log.trace("map values to items");
		constructItemMap();
		
		log.trace("construct button group name");
		constructButtonGroupName();
		
		log.trace("instantiate radio buttons");
		constructRadioButtonGrid();
		
		log.trace("look for hint");
		constructHint();

		log.trace("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	
	private void checkDefinition() {
		checkDefinitionTagName(XQS.ELEMENT_SELECT1);
		checkDefinitionAppearance(XQS.APPEARANCE_FULL);
	}
	
	
	
	
	private void constructCanvas() {
		if (readOnly) {
			this.canvas = new HorizontalPanel();
		}
		else {
			this.canvas = new VerticalPanel();
		}
		this.canvas.addStyleName(STYLENAME);
	}
	
	

	
	private void constructButtonGroupName() {

		if (!readOnly) {
			
			buttonGroupName = this.owner.getId();
			if (this.owner.isRepeatable()) {
				XQuestionnaire parent = this.owner.getParentQuestionnaire();
				XQuestionnaireView parentView = parent.getView();
				List<XQuestion> siblings = parentView.getQuestions();
				buttonGroupName += siblings.indexOf(this.owner);
			}
			
			log.trace("using button group name: "+buttonGroupName);	

		}

		// TODO what if repeatable and can remove as well as add, need to rename button groups?
		
	}
	
	
	
	
	private void constructRadioButtonGrid() {

		if (readOnly) {

			readOnlyLabel = new Label();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);

		}
		else {
			
//			List<Element> itemElements = XML.getElementsByTagName(definition, XQS.ELEMENT_ITEM);

			radioButtonGrid = new Grid(items.keySet().size(), 2);
			this.canvas.add(radioButtonGrid);
			
//			for (int index=0; index < itemElements.size() ; index++) {
//				
//				Element itemElement = itemElements.get(index);
//				constructRadioButton(itemElement, index);
//				
//			}

			Iterator<String> it = items.keySet().iterator();
			
			for (int index=0; it.hasNext(); index++) {
				String value = it.next();
				String label = items.get(value);
				constructRadioButton(value, label, index);
			}
		}

	}
	
	
	
	
	private void constructRadioButton(String itemValue, String itemLabel, int index) {

//		String itemLabel = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_LABEL);
//		String itemValue = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_VALUE);
		
		log.trace("adding radio button for item label: "+itemLabel+"; value: "+itemValue);

		if (itemLabel != null) {
			log.trace("label != null, adding label widget");
			radioButtonGrid.setWidget(index, 0, new Label(itemLabel));
		}
		
		if (itemValue == null) {
			throw new XQuestionFormatException("bad select1 definition, found no value for item ["+index+"]");
		}
		
		final RadioButton button = new RadioButton(buttonGroupName);
		buttons.add(button);
		
		button.setFormValue(itemValue);
		log.trace("checking button form value, expect: "+itemValue+"; found: "+button.getFormValue());
		
		log.trace("adding button to grid");
		radioButtonGrid.setWidget(index, 1, button);
		
		button.addValueChangeHandler(new RadioButtonValueChangeHandler(button));

	}

	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean fireEvents) {
		
		if (readOnly) {
			this.readOnlyLabel.setText(items.get(value));
		}
		else {
			for (RadioButton b : buttons) {
				if (b.getFormValue().equals(value)) {
					b.setValue(true, fireEvents);
				}
			}
		}

	}
	
	
	
	
	protected class RadioButtonValueChangeHandler implements ValueChangeHandler<Boolean> {

		private Log log = LogFactory.getLog(this.getClass());
		private RadioButton button;

		protected RadioButtonValueChangeHandler(RadioButton button) {
			this.button = button;
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
		 */
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			log.enter("onValueChange");
			log.trace("event value: "+event.getValue());

			if (event.getValue()) {
				String value = this.button.getFormValue();
				model.setValue(value);	
				log.trace("setting model with value: "+value);
			}

			log.leave();
		}
		
	}

	
	
	


}
