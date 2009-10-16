/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xml.client.XML;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSelect1Full extends XFormControl {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private XQuestion owner;
	private Grid radioButtonGrid;
	private String buttonGroupName;
	private List<RadioButton> buttons = new ArrayList<RadioButton>();
	
	
	
	public static final String STYLENAME = "xselect1-full";

	
	
	
	/**
	 * @param definition
	 * @param model
	 */
	XSelect1Full(Element definition, XQuestionModel model, XQuestion owner) {
		super(definition, model);
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
		this.canvas = new VerticalPanel();
		this.canvas.addStyleName(STYLENAME);
	}
	
	

	
	private void constructButtonGroupName() {

		buttonGroupName = this.owner.getId();
		if (this.owner.isRepeatable()) {
			XQuestionnaire parent = this.owner.getParentQuestionnaire();
			XQuestionnaireView parentView = parent.getView();
			List<XQuestion> siblings = parentView.getQuestions();
			buttonGroupName += siblings.indexOf(this.owner);
		}
		
		log.trace("using button group name: "+buttonGroupName);	

		// TODO what if repeatable and can remove as well as add, need to rename button groups?
		
	}
	
	
	
	
	private void constructRadioButtonGrid() {

		List<Element> itemElements = XML.getElementsByTagName(definition, XQS.ELEMENT_ITEM);
		radioButtonGrid = new Grid(itemElements.size(), 2);
		this.canvas.add(radioButtonGrid);
		
		for (int index=0; index < itemElements.size() ; index++) {
			
			Element itemElement = itemElements.get(index);
			constructRadioButton(itemElement, index);
			
		}

	}
	
	
	
	
	private void constructRadioButton(Element itemElement, int index) {

		String itemLabel = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_LABEL);
		String itemValue = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_VALUE);
		
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
		
		for (RadioButton b : buttons) {
			if (b.getFormValue().equals(value)) {
				b.setValue(true, fireEvents);
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
