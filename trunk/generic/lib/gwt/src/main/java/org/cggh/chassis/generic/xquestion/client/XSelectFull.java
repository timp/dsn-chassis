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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSelectFull extends XFormControl {

	private Log log = LogFactory.getLog(this.getClass());
	private Grid checkBoxGrid;
	private List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
	
	
	
	public static final String STYLENAME = "xselect-full";

	
	
	
	/**
	 * @param definition
	 * @param model
	 */
	XSelectFull(Element definition, XQuestionModel model) {
		super(definition, model);
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
		
		log.trace("instantiate radio buttons");
		constructCheckBoxGrid();
		
		log.trace("look for hint");
		constructHint();

		log.trace("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	
	private void checkDefinition() {
		checkDefinitionTagName(XQS.ELEMENT_SELECT);
		checkDefinitionAppearance(XQS.APPEARANCE_FULL);
	}
	
	
	
	
	private void constructCanvas() {
		this.canvas = new VerticalPanel();
		this.canvas.addStyleName(STYLENAME);
	}
	
	

	
	private void constructCheckBoxGrid() {

		List<Element> itemElements = XML.getElementsByTagName(definition, XQS.ELEMENT_ITEM);
		checkBoxGrid = new Grid(itemElements.size(), 2);
		this.canvas.add(checkBoxGrid);
		
		for (int index=0; index < itemElements.size() ; index++) {
			
			Element itemElement = itemElements.get(index);
			constructCheckBox(itemElement, index);
			
		}

	}
	
	
	
	
	private void constructCheckBox(Element itemElement, int index) {

		String itemLabel = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_LABEL);
		String itemValue = XML.getElementSimpleContentByTagName(itemElement, XQS.ELEMENT_VALUE);
		
		log.trace("adding radio button for item label: "+itemLabel+"; value: "+itemValue);

		if (itemLabel != null) {
			log.trace("label != null, adding label widget");
			checkBoxGrid.setWidget(index, 0, new Label(itemLabel));
		}
		
		if (itemValue == null) {
			throw new XQuestionFormatException("bad select1 definition, found no value for item ["+index+"]");
		}
		
		CheckBox box = new CheckBox();
		checkBoxes.add(box);
		
		box.setFormValue(itemValue);
		log.trace("checking box form value, expect: "+itemValue+"; found: "+box.getFormValue());
		
		log.trace("adding box to grid");
		checkBoxGrid.setWidget(index, 1, box);
		
		box.addValueChangeHandler(new CheckBoxValueChangeHandler(box));

	}

	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean fireEvents) {
		
		for (CheckBox b : checkBoxes) {
			if (b.getFormValue().equals(value)) {
				b.setValue(true, fireEvents);
			}
		}

	}
	
	
	

	protected class CheckBoxValueChangeHandler implements ValueChangeHandler<Boolean> {

		private Log log = LogFactory.getLog(this.getClass());
		private CheckBox box;

		protected CheckBoxValueChangeHandler(CheckBox box) {
			this.box = box;
		}
		
		/* (non-Javadoc)
		 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
		 */
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			log.enter("onValueChange");
			log.trace("event value: "+event.getValue());

			if (event.getValue()) {
				String value = this.box.getFormValue();
				model.addValue(value);	
				log.trace("adding value: "+value);
			}

			else {
				String value = this.box.getFormValue();
				model.removeValue(value);	
				log.trace("adding value: "+value);
			}

			log.leave();
		}
		
	}

	
	
	



}
