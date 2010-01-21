/**
 * 
 */
package org.cggh.chassis.generic.xquestion.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class XSelectFull extends XSelectBase {

	private Log log = LogFactory.getLog(this.getClass());
	private Grid checkBoxGrid;
	private List<CheckBox> checkBoxes = new ArrayList<CheckBox>();
	private Label readOnlyLabel;
	
	
	
	public static final String STYLENAME = "xselect-full";

	
	
	
	/**
	 * @param definition
	 * @param model
	 * @param readOnly 
	 */
	XSelectFull(Element definition, XQuestionModel model, boolean readOnly) {
		super(definition, model, readOnly);
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
				constructCheckBoxGrid();
				return in;
			}
			
		});
		
		log.debug("look for hint");
		constructHint();
		
		log.debug("initialise value from model");
		initValues();

		log.debug("complete construction");
		initWidget(this.canvas);
		
		log.leave();
	}
	
	
	
	
	private void checkDefinition() {
		checkDefinitionTagName(XQS.ELEMENT_SELECT);
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
	
	

	
	private void constructCheckBoxGrid() {

		if (readOnly) {

			readOnlyLabel = new Label();
			readOnlyLabel.addStyleName(XFormControl.STYLENAME_ANSWER);
			this.canvas.add(readOnlyLabel);

		}
		else {

			checkBoxGrid = new Grid(values.size(), 2);
			Iterator<String> it = values.iterator();
			
			for (int index=0; it.hasNext(); index++) {
				
				String itemValue = it.next();
				String itemLabel = labels.get(itemValue);
				
				log.debug("adding list item for item label: "+itemLabel+"; value: "+itemValue);
				constructCheckBox(itemValue, itemLabel, index);
								
			}
			
			this.canvas.add(checkBoxGrid);

		}
	}
	
	
	
	
	private void constructCheckBox(String itemValue, String itemLabel, int index) {

		log.debug("adding radio button for item label: "+itemLabel+"; value: "+itemValue);

		if (itemLabel != null) {
			log.debug("label != null, adding label widget");
			checkBoxGrid.setWidget(index, 0, new Label(itemLabel));
		}
		
		if (itemValue == null) {
			throw new XQuestionFormatException("bad select1 definition, found no value for item ["+index+"]");
		}
		
		CheckBox box = new CheckBox();
		checkBoxes.add(box);
		
		box.setFormValue(itemValue);
		log.debug("checking box form value, expect: "+itemValue+"; found: "+box.getFormValue());
		
		log.debug("adding box to grid");
		checkBoxGrid.setWidget(index, 1, box);
		
		box.addValueChangeHandler(new CheckBoxValueChangeHandler(box));

	}

	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.xquestion.client.XFormControl#setValue(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(final String value, final boolean fireEvents) {
		log.enter("setValue");
		
		Function setValue = new Function() {

			public Object apply(Object in) {

				for (CheckBox b : checkBoxes) {
					String formValue = b.getFormValue();
					log.debug("compare form value ["+formValue+"] with value to set ["+value+"]");
					if (formValue.equals(value)) {
						log.debug("found match, setting value");
						b.setValue(true, fireEvents);
					}
				}
								
				return in;
			}
			
		};
		
		deferredItems.addCallback(setValue); // make sure this gets done after any pending async requests

		log.leave();
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private void initValues() {
		log.enter("initValues");
		
		Function initValues = new Function() {

			public Object apply(Object in) {
				if (model.getValue() != null) {
					Set<String> values = model.getValues();
					
					if (readOnly) {
						String content = "";
						Iterator<String> it = values.iterator();
						for (int index=0; it.hasNext(); index++) {
							content += labels.get(it.next());
							if (it.hasNext()) {
								content += ", ";
							}
						}
						readOnlyLabel.setText(content);
					}
					else {
						for (CheckBox b : checkBoxes) {
							String formValue = b.getFormValue();
							if (values.contains(formValue)) {
								log.debug("found match, setting value: "+formValue);
								b.setValue(true, false);
							}
						}
					}
					
				}
				return in;
			}
			
		};

		deferredItems.addCallback(initValues); // make sure this gets done after any pending async requests

		log.leave();
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
			log.debug("event value: "+event.getValue());

			if (event.getValue()) {
				String value = this.box.getFormValue();
				model.addValue(value);	
				log.debug("adding value: "+value);
			}

			else {
				String value = this.box.getFormValue();
				model.removeValue(value);	
				log.debug("adding value: "+value);
			}

			log.leave();
		}
		
	}

	
	
	



}
