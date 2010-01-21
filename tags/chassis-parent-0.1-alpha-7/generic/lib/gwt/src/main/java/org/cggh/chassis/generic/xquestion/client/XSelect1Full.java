/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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
		
		log.debug("check guard conditions");
		checkDefinition();

		log.debug("construct canvas");
		constructCanvas();
		
		log.debug("add custom style");
		addCustomStyle();
		
		log.debug("look for label");
		constructLabel();
		
		log.debug("map values to items");
		constructItemMap();
		
		deferredItems.addCallback(new Function<List<Element>, List<Element>>() {

			public List<Element> apply(List<Element> in) {
				addItems(in);
				constructRadioButtonGrid();
				return in;
			}
			
		});
		
		
		log.debug("construct button group name");
		constructButtonGroupName();
		
		log.debug("look for hint");
		constructHint();

		log.debug("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	
	private void checkDefinition() {
		checkDefinitionTagName(XQS.ELEMENT_SELECT1);
		checkDefinitionAppearance(XQS.APPEARANCE_FULL, true);
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
			
			log.debug("using button group name: "+buttonGroupName);	

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
			
			radioButtonGrid = new Grid(values.size(), 2);
			this.canvas.add(radioButtonGrid);
			
			Iterator<String> it = values.iterator();
			
			for (int index=0; it.hasNext(); index++) {
				String value = it.next();
				String label = labels.get(value);
				constructRadioButton(value, label, index);
			}
		}

	}
	
	
	
	
	private void constructRadioButton(String itemValue, String itemLabel, int index) {

		log.debug("adding radio button for item label: "+itemLabel+"; value: "+itemValue);

		if (itemLabel != null) {
			log.debug("label != null, adding label widget");
			radioButtonGrid.setWidget(index, 0, new Label(itemLabel));
		}
		
		if (itemValue == null) {
			throw new XQuestionFormatException("bad select1 definition, found no value for item ["+index+"]");
		}
		
		final RadioButton button = new RadioButton(buttonGroupName);
		buttons.add(button);
		
		button.setFormValue(itemValue);
		log.debug("checking button form value, expect: "+itemValue+"; found: "+button.getFormValue());
		
		log.debug("adding button to grid");
		radioButtonGrid.setWidget(index, 1, button);
		
		button.addValueChangeHandler(new RadioButtonValueChangeHandler(button));

	}

	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(final String value, final boolean fireEvents) {
		
		Function setValue = new Function() {

			public Object apply(Object in) {

				if (readOnly) {
					readOnlyLabel.setText(labels.get(value));
				}
				else {
					for (RadioButton b : buttons) {
						if (b.getFormValue().equals(value)) {
							b.setValue(true, fireEvents);
						}
					}
				}
				
				return in;
			}
			
		};
		
		deferredItems.addCallback(setValue); // make sure this gets done after any pending async requests

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
			log.debug("event value: "+event.getValue());

			if (event.getValue()) {
				String value = this.button.getFormValue();
				model.setValue(value);	
				log.debug("setting model with value: "+value);
			}

			log.leave();
		}
		
	}

	
	
	


}
