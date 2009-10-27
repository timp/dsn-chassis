/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author raok
 *
 */
class CreateStudyWidgetDefaultRenderer implements StudyModelListener {

	
	
	
	final private Panel canvas;
	private StudyControllerCreateAPI controller;
	private Boolean isFormComplete = false;
	private Map<String, String> modulesConfig = ConfigurationBean.getModules();
	
	
	
	
	//Expose view elements for testing purposes.
	final TextBoxBase titleUI = new TextBox();
	final TextBoxBase summaryUI = new TextArea();
	final Button createStudyUI = new Button("Create Study", new CreateStudyClickHandler());
	final Button cancelCreateStudyUI = new Button("Cancel", new CancelStudyClickHandler());
	final Map<String, CheckBox> modulesUIHash = new HashMap<String, CheckBox>();
	
	
	

	public CreateStudyWidgetDefaultRenderer(Panel givenCanvas, StudyControllerCreateAPI controller) {
		
		this.canvas = new FlowPanel();
		givenCanvas.add(canvas);
		
		this.controller = controller;
		
		//initialise view
		initCanvas();
	}

	
	
	
	/**
	 * @param controller
	 */
	public CreateStudyWidgetDefaultRenderer(StudyControllerCreateAPI controller) {
		this.canvas = new FlowPanel();
		this.controller = controller;
		
		//initialise view
		initCanvas();
	}

	
	
	
	private void initCanvas() {
		
		this.canvas.addStyleName(CSS.CREATESTUDY_BASE);
		
		this.canvas.add(new HTML("<h2>New Study</h2>"));
		
		this.canvas.add(new HTML("<p>Use this form to create a new record of a study.</p>"));
				
		// prepare form

		FlowPanel createStudyForm = new FlowPanel();

		createStudyForm.add(new HTML("<h3>Study Title and Summary</h3>"));

		// title question
		
		FlowPanel titleQuestion = new FlowPanel();
		InlineLabel titleLabel = new InlineLabel("Please provide a title for the study:");
		titleQuestion.add(titleLabel);
		titleQuestion.add(titleUI);
		titleQuestion.addStyleName(CSS.COMMON_QUESTION);
		
		titleUI.addStyleName(CSS.CREATESTUDY_TITLEINPUT);
		titleUI.addValueChangeHandler(new TitleChangeHandler());

		createStudyForm.add(titleQuestion);
		
		// summary question
		
		FlowPanel summaryQuestion = new FlowPanel();
		
		Label summaryLabel = new Label("Please provide a textual summary of the study...");
		summaryQuestion.add(summaryLabel);
		summaryQuestion.add(summaryUI);
		summaryQuestion.addStyleName(CSS.COMMON_QUESTION);
		
		summaryUI.addValueChangeHandler(new SummaryChangeHandler());

		createStudyForm.add(summaryQuestion);
		
		// modules question
		
		createStudyForm.add(new HTML("<h3>Modules</h3>"));
		
		FlowPanel modulesQuestion = new FlowPanel();
		modulesQuestion.addStyleName(CSS.COMMON_QUESTION);
		modulesQuestion.addStyleName(CSS.CREATESTUDY_MODULES);
		createStudyForm.add(modulesQuestion);
		
		Label modulesLabel = new Label("Please select the modules that this study is relevant to... (at least one must be selected)");
		modulesQuestion.add(modulesLabel);

		FlexTable boxTable = new FlexTable();
		modulesQuestion.add(boxTable);
		int rowNumber = -1;

		//Create as many modules checkboxes as required
		for (String moduleId : modulesConfig.keySet()) {
			
			String UILabel = modulesConfig.get(moduleId);
			CheckBox moduleUICheckBox = new CheckBox();
			
			//add valueChangeHandler
			moduleUICheckBox.addValueChangeHandler(new ModulesUIValueChangeHandler());
			
			//add checkbox reference to HashMap
			modulesUIHash.put(moduleId, moduleUICheckBox);
			
			//add to GUI
			int rn = ++rowNumber;
			boxTable.setWidget(rn, 0, new InlineLabel(UILabel));
			boxTable.setWidget(rn, 1, moduleUICheckBox);
			
		}
		
		FlowPanel buttonsPanel = new FlowPanel();
		buttonsPanel.addStyleName(CSS.CREATESTUDY_ACTIONS);
		buttonsPanel.add(createStudyUI);
		buttonsPanel.add(cancelCreateStudyUI);

		createStudyForm.add(buttonsPanel);
		
		canvas.add(createStudyForm);	
		
	}
	
	
	
	
	class TitleChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateTitle(titleUI.getValue());			
		}
		
	}
	
	
	
	
	class SummaryChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.updateSummary(summaryUI.getValue());			
		}
		
	}

	
	
	
	class ModulesUIValueChangeHandler implements ValueChangeHandler<Boolean> {

		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
			
			//get modules selected
			Set<String> modulesSelected = new HashSet<String>();
			
			for (String moduleId : modulesUIHash.keySet()) {
				
				CheckBox moduleUICheckBox = modulesUIHash.get(moduleId);
				
				if (moduleUICheckBox.getValue()) {
					modulesSelected.add(moduleId);
				}
				
			}
			
			//update model
			controller.updateModules(modulesSelected);
			
		}
		
	}
	
	
	
	
	class CreateStudyClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			if (isFormComplete) {
				controller.saveNewStudyEntry();
			} else {
				//TODO move to management widget?
				DecoratedPopupPanel errorPopUp = new DecoratedPopupPanel(true);
				errorPopUp.add(new Label("Form invalid."));
				errorPopUp.center();
				errorPopUp.show();
			}
		}
		
	}
	
	
	
	
	//TODO handle cancel button (with history?)
	class CancelStudyClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.cancelSaveOrUpdateStudyEntry();
		}
	}

	
	
	
	public void setController(StudyControllerCreateAPI controller) {
		this.controller = controller;
	}

	
	
	
	public void onStatusChanged(Integer before, Integer after) {
		// TODO Auto-generated method stub
	}
	
	
	
	
	public void onStudyEntryChanged(Boolean isValid) {
		this.isFormComplete = isValid;		
	}

	
	
	
	public void onTitleChanged(String before, String after, Boolean isValid) {
		
		//do not fire events, otherwise a probably dangerous feedback loop will be created!
		titleUI.setValue(after, false);
	}

	
	
	
	public void onSummaryChanged(String before, String after, Boolean isValid) {

		summaryUI.setValue(after, false);
	}

	
	
	
	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {
		
		for (String moduleId : modulesConfig.keySet()) {
			
			modulesUIHash.get(moduleId).setValue(after.contains(moduleId), false);
			
		}
		
	}

	
	
	
	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after,
			Boolean isValid) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	/**
	 * @return
	 */
	public Panel getCanvas() {
		return this.canvas;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener#onCreatedChanged(java.lang.String, java.lang.String)
	 */
	public void onCreatedChanged(String before, String created) {
		// not interested
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener#onUpdatedChanged(java.lang.String, java.lang.String)
	 */
	public void onUpdatedChanged(String before, String created) {
		// not interested
	}
	
	
	
	
}
