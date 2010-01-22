/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;


/**
 * @author aliman
 *
 */
public class StudyFormRenderer extends BaseFormRenderer<StudyEntry, StudyFeed> {


	
	
	
	private Map<String, CheckBox> checkboxes = new HashMap<String, CheckBox>();

	
	
	
	
	/**
	 * @param owner
	 */
	public StudyFormRenderer(StudyForm owner) {
		super(owner);
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer#createResources()
	 */
	@Override
	protected Resources createResources() {
		return new StudyForm.Resources();
	}

	
	
	

	@Override
	public void renderUI() {
		super.renderUI();

		// modules question
		
		this.canvas.add(h3Widget("Modules")); // TODO i18n

		FlowPanel modulesQuestion = new FlowPanel();
		modulesQuestion.addStyleName(CommonStyles.QUESTION);
//		modulesQuestion.addStyleName(CommonStyles.CREATESTUDY_MODULES);
		this.canvas.add(modulesQuestion);
		
		Label modulesLabel = new Label("Please select the modules that this study is relevant to...");
		modulesQuestion.add(modulesLabel);

		FlexTable boxTable = new FlexTable();
		modulesQuestion.add(boxTable);
		int rowNumber = -1;

		Map<String, String> modulesConfig = Configuration.getModules();
		
		// create as many modules checkboxes as required
		for (String moduleId : modulesConfig.keySet()) {
			
			CheckBox cb = new CheckBox();
			
			// add checkbox reference to HashMap
			checkboxes.put(moduleId, cb);

			String label = modulesConfig.get(moduleId);

			// add to UI
			int rn = ++rowNumber;
			boxTable.setWidget(rn, 0, new InlineLabel(label));
			boxTable.setWidget(rn, 1, cb);
			
		}
		
	}
	
	


	/**
	 * 
	 */
	protected void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		for (String module : checkboxes.keySet()) {
			
			CheckBox cb = checkboxes.get(module);

			// add valueChangeHandler
			HandlerRegistration a = cb.addValueChangeHandler(new ModulesUIValueChangeHandler());
			this.childWidgetEventHandlerRegistrations.add(a);
			
		}
		
	}

	
	
	
	
	class ModulesUIValueChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			
			// get modules selected
			
			List<String> modulesSelected = new ArrayList<String>();
			
			for (String moduleId : checkboxes.keySet()) {
				
				CheckBox cb = checkboxes.get(moduleId);
				
				if (cb.getValue()) {
					modulesSelected.add(moduleId);
				}
				
			}
			
			// update model
			model.getStudy().setModules(modulesSelected);
			
		}
		
	}
	
	
	

	
	/**
	 * 
	 */
	public void syncUI() {
		
		super.syncUI();
		
		if (this.model != null) {

			this.syncModules();
			
		}

	}




	/**
	 * 
	 */
	private void syncModules() {
		
		List<String> modules = this.model.getStudy().getModules();
		
		for (String module : checkboxes.keySet()) {
			CheckBox cb = checkboxes.get(module);
			cb.setValue((modules.contains(module)));
		}
		
	}

	
	
	
}
