/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.edit.client;

import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.form.submission.client.SubmissionForm;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class EditSubmissionWidgetDefaultRenderer implements EditSubmissionWidgetModel.Listener {

	
	
	

	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas = new FlowPanel();
	private EditSubmissionWidgetController controller;
	private SubmissionForm form;
	private Button cancelEditSubmissionUI = new Button("Cancel", new CancelEditSubmissionUIClickHandler());
	private Button saveNewSubmissionEntryUI = new Button("Save Changes", new SaveNewSubmissionUIClickHandler());
	private FlowPanel buttonsPanel;
	private FlowPanel savingPanel;
	private FlowPanel mainPanel;
	
	
	
	/**
	 * @param owner
	 * @param controller
	 */
	public EditSubmissionWidgetDefaultRenderer(EditSubmissionWidgetController controller) {
		
		this.controller = controller;
		this.canvas.addStyleName(CSS.EDITSUBMISSION_BASE);

	}
	
	
	
	public SubmissionForm getForm() {
		return this.form;
	}

	
	
	
	public void render() {
		log.enter("render");
		
		this.canvas.clear();

		this.savingPanel = new FlowPanel();
		this.savingPanel.setVisible(false);
		this.savingPanel.add(new InlineLabel("saving..."));
		this.canvas.add(this.savingPanel);
		
		this.mainPanel = new FlowPanel();
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);
		
		this.mainPanel.add(new HTML("<h2>Edit Data Submission</h2>"));

		this.mainPanel.add(new HTML("<p>Use the form below to edit your data submission.</p>"));
		
		this.form = new SubmissionForm();
		this.form.render();
		this.mainPanel.add(this.form);
		
		this.initButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);
		
		log.leave();
	}



	
	/**
	 * @return
	 */
	public Widget getCanvas() {
		return this.canvas;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.EditSubmissionWidgetModel.Listener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {

		if (before == EditSubmissionWidgetModel.STATUS_INITIAL && after == EditSubmissionWidgetModel.STATUS_READY) {
			
			this.render();
			this.mainPanel.setVisible(true);
			
		}
		else if (before == EditSubmissionWidgetModel.STATUS_READY && after == EditSubmissionWidgetModel.STATUS_SAVING) {

			this.mainPanel.setVisible(false);
			this.savingPanel.setVisible(true);

		}
		else if (before == EditSubmissionWidgetModel.STATUS_SAVING && after == EditSubmissionWidgetModel.STATUS_SAVED) {

			// TODO anything?
			
		}
		else if (after == EditSubmissionWidgetModel.STATUS_ERROR) {

			// TODO anything?
			
		}
		else if (after == EditSubmissionWidgetModel.STATUS_CANCELLED) {

			// TODO anything?
			
		}
		else {
			throw new Error("unexpected state transition");
		}
		
	}



	



	private void initButtonsPanel() {

		buttonsPanel = new FlowPanel();
		buttonsPanel.add(saveNewSubmissionEntryUI);
		buttonsPanel.add(cancelEditSubmissionUI);
		buttonsPanel.addStyleName(CSS.EDITSUBMISSION_ACTIONS);

	}

	
	
	
	private class CancelEditSubmissionUIClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelEditSubmissionEntry();
		}
		
	}
	
	private class SaveNewSubmissionUIClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.saveNewSubmissionEntry(form.getModel());
		}
		
	}
		
	
//	
//	private Panel canvas;
//	private SubmissionControllerEditAPI controller;
//	private Boolean isFormComplete = false;
//	private Map<String, String> modulesConfig;
//	
//	
//	
//	
//	//Expose view elements for testing purposes.
//	final Panel editSubmissionFormPanel = new SimplePanel();
//	final TextBoxBase titleUI = new TextBox();
//	final TextBoxBase summaryUI = new TextArea();
//	final SimplePanel noStudiesAddedPanel = new SimplePanel();
//	final Button showAddStudyLinkUI = new Button("Add a study");
//	String studyLinkToAdd;
//	
//	
//	
//	
//	//Add studyLink UI
//	final DecoratedPopupPanel studyLinkChooserPopup = new DecoratedPopupPanel(false);
//	private ViewStudiesWidget viewStudiesWidgetListBox;
//	final Map<String, CheckBox> modulesUIHash = new HashMap<String, CheckBox>();
//	
//	
//	
//	
//	//show linked Studies
//	private ViewStudiesWidget studiesLinkedWidget;
//	
//	
//	final Button cancelEditSubmissionUI = new Button("Cancel", new CancelCreateSubmissionUIClickHandler());
//	final Button updateSubmissionEntryUI = new Button("Update Submission", new SaveNewSubmissionUIClickHandler());
//
//	
//	
//	
//	/**
//	 * 
//	 * @param canvas
//	 * @param controller
//	 * @param authorEmail
//	 */
//	public EditSubmissionWidgetDefaultRenderer(Panel canvas, SubmissionControllerEditAPI controller) {
//		this.canvas = canvas;
//		this.controller = controller;
//		
//		//get modules from config
//		this.modulesConfig = ConfigurationBean.getModules();
//		
//		this.constructStudiesLinkedWidget();
//		
//		//initialise view
//		initCanvas();
//	}
//
//	
//	
//	
//	/**
//	 * @param controller
//	 */
//	public EditSubmissionWidgetDefaultRenderer(SubmissionControllerEditAPI controller) {
//
//		this.canvas = new FlowPanel();
//		this.controller = controller;
//		
//		//get modules from config
//		this.modulesConfig = ConfigurationBean.getModules();
//		
//		this.constructStudiesLinkedWidget();
//		
//		//initialise view
//		initCanvas();
//	}
//
//	
//	
//	
//	/**
//	 * 
//	 */
//	private void constructStudiesLinkedWidget() {
//
//		//Create ViewStudies widget to view linked studies
//		studiesLinkedWidget = new ViewStudiesWidget("remove study link");
//
//		//Create ViewStudiesWidget with ListBox renderer
//		ViewStudiesWidgetModelListener customRenderer = new ViewStudiesWidgetListBoxRenderer(null);
//		viewStudiesWidgetListBox = new ViewStudiesWidget(customRenderer);
//		
//		//add this as listener
//		viewStudiesWidgetListBox.addViewAllStudiesWidgetListener(this);
//		studiesLinkedWidget.addViewAllStudiesWidgetListener(this);
//				
//	}
//
//
//
//
//	
//	private void initCanvas() {
//				
//		//prepare form
//		FlexTable editSubmissionForm = new FlexTable();
//		int rowNumber = -1;
//		
//		Label titleLabel = new Label("Enter title:");
//		editSubmissionForm.setWidget(++rowNumber, 0, titleLabel);
//		titleUI.addValueChangeHandler(new TitleChangeHandler());
//		editSubmissionForm.setWidget(++rowNumber, 0, titleUI);
//		
//		Label summaryLabel = new Label("Enter summary:"); 
//		editSubmissionForm.setWidget(++rowNumber, 0, summaryLabel);
//		summaryUI.addValueChangeHandler(new SummaryChangeHandler());
//		editSubmissionForm.setWidget(++rowNumber, 0, summaryUI);
//
//		Label modulesLabel = new Label("Select modules (at least one must be selected):");
//		editSubmissionForm.setWidget(++rowNumber, 0, modulesLabel);
//
//		//Create as many modules checkboxes as required
//		for (String moduleId : modulesConfig.keySet()) {
//			
//			String UILabel = modulesConfig.get(moduleId);
//			CheckBox moduleUICheckBox = new CheckBox(UILabel);
//			
//			//add valueChangeHandler
//			moduleUICheckBox.addValueChangeHandler(new ModulesUIValueChangeHandler());
//			
//			//add checkbox reference to HashMap
//			modulesUIHash.put(moduleId, moduleUICheckBox);
//			
//			//add to GUI
//			editSubmissionForm.setWidget(++rowNumber, 0, moduleUICheckBox);
//			
//		}
//		
//		
//		//prepare panel to show list of studies added
//		VerticalPanel studiesLinkedDisplayPanel = new VerticalPanel();
//		
//		studiesLinkedDisplayPanel.add(new Label("Studies Linked:"));
//		studiesLinkedDisplayPanel.add(noStudiesAddedPanel);
//		studiesLinkedDisplayPanel.add(this.studiesLinkedWidget);
//		studiesLinkedDisplayPanel.add(showAddStudyLinkUI);		
//		
//		
//		//prepare the studyLink chooser panel
//		VerticalPanel studyLinkChooserVP = new VerticalPanel();
//		studyLinkChooserPopup.add(studyLinkChooserVP);
//		studyLinkChooserVP.add(new Label("Choose a study for the drop down box and then click 'Add study'."));
//		studyLinkChooserVP.add(this.viewStudiesWidgetListBox);
//		
//		//Create addStudy Button
//		Button addStudyLinkUI = new Button("Add study");
//		addStudyLinkUI.addClickHandler(new ClickHandler() {
//		
//			public void onClick(ClickEvent arg0) {
//				
//				if (studyLinkToAdd != null) {
//					//add study link
//					controller.addStudyLink(studyLinkToAdd);
//					
//					//close popup
//					studyLinkChooserPopup.hide();
//				}
//			}
//			
//		});
//		
//		//Create cancel addStudy Button
//		Button cancelAddStudyLinkUI = new Button("Cancel");
//		cancelAddStudyLinkUI.addClickHandler(new ClickHandler() {
//		
//			public void onClick(ClickEvent arg0) {
//				//close popup
//				studyLinkChooserPopup.hide();
//			}
//			
//		});
//		
//		HorizontalPanel addStudyButtonsPanel = new HorizontalPanel();
//		addStudyButtonsPanel.add(addStudyLinkUI);
//		addStudyButtonsPanel.add(cancelAddStudyLinkUI);
//		
//		
//		studyLinkChooserVP.add(addStudyButtonsPanel);
//		
//		
//		
//		//add clickHandler to display studyLinkChooserPopup
//		showAddStudyLinkUI.addClickHandler(new ClickHandler() {
//			
//			public void onClick(ClickEvent arg0) {
//
//				viewStudiesWidgetListBox.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
//				
//				studyLinkChooserPopup.center();
//				studyLinkChooserPopup.show();
//			}
//		});
//		
//		//buttons panel
//		HorizontalPanel buttonsPanel = new HorizontalPanel();
//		buttonsPanel.add(updateSubmissionEntryUI);
//		buttonsPanel.add(cancelEditSubmissionUI);
//		
//		//Place above into a holder panel, then add to canvas
//		VerticalPanel mainVP = new VerticalPanel();
//		mainVP.add(editSubmissionForm);
//		mainVP.add(studiesLinkedDisplayPanel);
//		mainVP.add(buttonsPanel);
//		
//		canvas.add(mainVP);
//		
//	}
//	
//	private class TitleChangeHandler implements ValueChangeHandler<String> {
//
//		public void onValueChange(ValueChangeEvent<String> arg0) {
//			controller.updateTitle(titleUI.getValue());
//		}
//		
//	}
//	
//	private class SummaryChangeHandler implements ValueChangeHandler<String> {
//
//		public void onValueChange(ValueChangeEvent<String> arg0) {
//			controller.updateSummary(summaryUI.getValue());
//		}
//		
//	}
//	
//
//	class ModulesUIValueChangeHandler implements ValueChangeHandler<Boolean> {
//
//		public void onValueChange(ValueChangeEvent<Boolean> arg0) {
//			
//			//get modules selected
//			Set<String> modulesSelected = new HashSet<String>();
//			
//			for (String moduleId : modulesUIHash.keySet()) {
//				
//				CheckBox moduleUICheckBox = modulesUIHash.get(moduleId);
//				
//				if (moduleUICheckBox.getValue()) {
//					modulesSelected.add(moduleId);
//				}
//				
//			}
//			
//			//update model
//			controller.updateModules(modulesSelected);
//			
//		}
//		
//	}
//		
//	private class CancelCreateSubmissionUIClickHandler implements ClickHandler {
//
//		public void onClick(ClickEvent arg0) {
//			controller.cancelCreateOrUpdateSubmissionEntry();
//		}
//		
//	}
//	
//	private class SaveNewSubmissionUIClickHandler implements ClickHandler {
//		
//		public void onClick(ClickEvent arg0) {
//			
//			if (isFormComplete){
//				controller.updateSubmissionEntry();
//			} else {
//				//TODO move to management widget?
//				DecoratedPopupPanel errorPopUp = new DecoratedPopupPanel(true);
//				errorPopUp.add(new Label("Form invalid."));
//				errorPopUp.center();
//				errorPopUp.show();
//			}
//			
//		}
//		
//	}
//		
//
//	public void onTitleChanged(String before, String after, Boolean isValid) {
//		
//		//do not fire events, otherwise a probably dangerous feedback loop will be created!
//		titleUI.setValue(after, false);
//	}
//
//	public void onSummaryChanged(String before, String after, Boolean isValid) {
//
//		summaryUI.setValue(after, false);
//	}
//
//	
//	public void onStudyLinksChanged(Set<String> before, Set<String> after, Boolean isValid) {
//
//		//show hide panel depending on if studies added
//		boolean noStudiesLinked = (after.size() == 0);
//		noStudiesAddedPanel.setVisible(noStudiesLinked);
//		studiesLinkedWidget.setVisible(!noStudiesLinked);		
//				
//		//load linked studies
//		studiesLinkedWidget.loadStudies(after);		
//
//	}
//
//	public void onSubmissionEntryChanged(Boolean isValid) {
//		this.isFormComplete = isValid;
//	}
//
//	public void onStatusChanged(Integer before, Integer after) {
//		
//
//	}
//
//	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {
//
//		for (String moduleId : modulesConfig.keySet()) {
//			
//			modulesUIHash.get(moduleId).setValue(after.contains(moduleId), false);
//			
//		}
//	}
//
//	public void onUserActionSelectStudy(StudyEntry studyEntry) {
//		
//		//Distinguish between studies list panel and add study popup panel
//		if (studyLinkChooserPopup.isShowing()) {
//			studyLinkToAdd = studyEntry.getEditLink().getHref();
//		} else {
//			controller.removeStudyLink(studyEntry.getEditLink().getHref());
//		}
//	}
//
//	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after, Boolean isValid) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	/**
//	 * @return
//	 */
//	public Panel getCanvas() {
//		return this.canvas;
//	}
//
//
//
//
//	/* (non-Javadoc)
//	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModelListener#onCreatedChanged(java.lang.String, java.lang.String)
//	 */
//	public void onCreatedChanged(String before, String created) {
//		// TODO Auto-generated method stub
//		
//	}
//
//
//
//
//	/* (non-Javadoc)
//	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModelListener#onUpdatedChanged(java.lang.String, java.lang.String)
//	 */
//	public void onUpdatedChanged(String before, String created) {
//		// TODO Auto-generated method stub
//		
//	}

}
