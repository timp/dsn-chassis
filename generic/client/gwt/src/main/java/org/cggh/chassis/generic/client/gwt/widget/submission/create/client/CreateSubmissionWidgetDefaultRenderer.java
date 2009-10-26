/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.form.submission.client.SubmissionForm;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetModel;
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
public class CreateSubmissionWidgetDefaultRenderer /* implements SubmissionModelListener, ViewStudiesWidgetPubSubAPI */ implements CreateSubmissionWidgetModel.Listener {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas = new FlowPanel();
	private CreateSubmissionWidgetController controller;
	private SubmissionForm form;
	private Button cancelCreateSubmissionUI = new Button("Cancel", new CancelCreateSubmissionUIClickHandler());
	private Button saveNewSubmissionEntryUI = new Button("Create Submission", new SaveNewSubmissionUIClickHandler());
	private FlowPanel buttonsPanel;
	private FlowPanel savingPanel;
	private FlowPanel mainPanel;
	
	
	
	/**
	 * @param owner
	 * @param controller
	 */
	public CreateSubmissionWidgetDefaultRenderer(CreateSubmissionWidgetController controller) {
		
		this.controller = controller;
		this.canvas.addStyleName(CSS.CREATESUBMISSION_BASE);

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
		
		this.mainPanel.add(new HTML("<h2>New Data Submission</h2>"));

		this.mainPanel.add(new HTML("<p>Use the form below to create a new data submission.</p>"));
		
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
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetModel.Listener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {

		if (before == CreateSubmissionWidgetModel.STATUS_INITIAL && after == CreateSubmissionWidgetModel.STATUS_READY) {
			
			this.render();
			this.mainPanel.setVisible(true);
			
		}
		else if (before == CreateSubmissionWidgetModel.STATUS_READY && after == CreateSubmissionWidgetModel.STATUS_SAVING) {

			this.mainPanel.setVisible(false);
			this.savingPanel.setVisible(true);

		}
		else if (before == CreateSubmissionWidgetModel.STATUS_SAVING && after == CreateSubmissionWidgetModel.STATUS_SAVED) {

			// TODO anything?
			
		}
		else if (after == CreateSubmissionWidgetModel.STATUS_ERROR) {

			// TODO anything?
			
		}
		else if (after == CreateSubmissionWidgetModel.STATUS_CANCELLED) {

			// TODO anything?
			
		}
		else {
			throw new Error("unexpected state transition");
		}
		
	}



	
	

	
	
	
//	private SubmissionControllerCreateAPI controller;
	
	
	
//	private Boolean isFormComplete = false;
//	private Map<String, String> modulesConfig = ConfigurationBean.getModules();
	
	
	
	
	//Expose view elements for testing purposes.
//	final Panel createSubmissionFormPanel = new FlowPanel();
//	final TextBoxBase titleUI = new TextBox();
//	final TextBoxBase summaryUI = new TextArea();
//	final FlowPanel noStudiesAddedPanel = new FlowPanel();
//	final Button showAddStudyLinkUI = new Button("Add a study");
//	String studyLinkToAdd;
	
	
	
	
	//Add studyLink UI
//	final DecoratedPopupPanel studyLinkChooserPopup = new DecoratedPopupPanel(false);
//	private ViewStudiesWidget viewStudiesWidgetListBox;
//	final Map<String, CheckBox> modulesUIHash = new HashMap<String, CheckBox>();
	
	
	
	
	//show linked Studies
//	ViewStudiesWidget studiesLinkedWidget;
	
	
	
	

	
	
	
//	/**
//	 * Construct a renderer, passing in the panel to use as the renderer's canvas.
//	 * 
//	 * @param canvas
//	 * @param controller
//	 * @param authorEmail
//	 */
//	public CreateSubmissionWidgetDefaultRenderer(Panel canvas, SubmissionControllerCreateAPI controller) {
//		
//		this.canvas = canvas;
//		this.controller = controller;
//		
//		//initialise view
//		initCanvas();
//	}

	
	
	
	
//	/**
//	 * Construct a renderer, allowing the renderer to create its own canvas.
//	 * 
//	 * @param controller
//	 */
//	public CreateSubmissionWidgetDefaultRenderer(SubmissionControllerCreateAPI controller) {
//		log.enter("<constructor>");
//		
//		this.canvas = new FlowPanel();
//		this.controller = controller;
//		
//		log.debug("initialise canvas");
//		initCanvas();
//
//		log.leave();
//	}
	
	
	
	
//	private void initCanvas() {
//		log.enter("initCanvas");
//
////		this.createStudiesLinkedWidget();
//		
//		this.canvas.addStyleName(CSS.CREATESUBMISSION_BASE);
//		
//		this.canvas.add(new HTML("<h2>New Data Submission</h2>"));
//
//		this.canvas.add(new HTML("<p>Use this form to create a new data submission.</p>"));
//		
//		// prepare form
//
//		FlowPanel createSubmissionForm = new FlowPanel();
//		
////		this.initSubmissionTitleAndSummaryQuestions(createSubmissionForm);
////		
////		this.initModulesQuestion(createSubmissionForm);
////		
////		this.initStudiesQuestion(createSubmissionForm);
//
//		this.initButtonsPanel(createSubmissionForm);
//		
//		this.canvas.add(createSubmissionForm);
//	
//		log.leave();
//	}

	
	
	






//	/**
//	 * @param createSubmissionForm
//	 */
//	private void initSubmissionTitleAndSummaryQuestions(FlowPanel createSubmissionForm) {
//		log.enter("initSubmissionTitleAndSummaryQuestions");
//
//		createSubmissionForm.add(new HTML("<h3>Submission Title and Summary</h3>"));
//
//		// title question
//		
//		FlowPanel titleQuestion = new FlowPanel();
//		InlineLabel titleLabel = new InlineLabel("Please provide a title for the submission:");
//		titleQuestion.add(titleLabel);
//		titleQuestion.add(titleUI);
//		titleQuestion.addStyleName(CSS.COMMON_QUESTION);
//		
//		titleUI.addStyleName(CSS.CREATESUBMISSION_TITLEINPUT);
//		titleUI.addValueChangeHandler(new TitleChangeHandler());
//
//		createSubmissionForm.add(titleQuestion);
//		
//		// summary question
//		
//		FlowPanel summaryQuestion = new FlowPanel();
//		
//		Label summaryLabel = new Label("Please provide a textual summary of the submission...");
//		summaryQuestion.add(summaryLabel);
//		summaryQuestion.add(summaryUI);
//		summaryQuestion.addStyleName(CSS.COMMON_QUESTION);
//		
//		summaryUI.addValueChangeHandler(new SummaryChangeHandler());
//
//		createSubmissionForm.add(summaryQuestion);	
//		
//		log.leave();
//	}




//	/**
//	 * @param createSubmissionForm
//	 */
//	private void initModulesQuestion(FlowPanel createSubmissionForm) {
//		log.enter("initModulesQuestion");
//
//		// modules question
//		
//		createSubmissionForm.add(new HTML("<h3>Modules</h3>"));
//		
//		FlowPanel modulesQuestion = new FlowPanel();
//		modulesQuestion.addStyleName(CSS.COMMON_QUESTION);
//		modulesQuestion.addStyleName(CSS.CREATESUBMISSION_MODULES);
//		createSubmissionForm.add(modulesQuestion);
//		
//		Label modulesLabel = new Label("Please select the modules that this submission is relevant to... (at least one must be selected)");
//		modulesQuestion.add(modulesLabel);
//
//		FlexTable boxTable = new FlexTable();
//		modulesQuestion.add(boxTable);
//		int rowNumber = -1;
//
//		//Create as many modules checkboxes as required
//		for (String moduleId : modulesConfig.keySet()) {
//			
//			String UILabel = modulesConfig.get(moduleId);
//			CheckBox moduleUICheckBox = new CheckBox();
//			
//			//add valueChangeHandler
//			moduleUICheckBox.addValueChangeHandler(new ModulesUIValueChangeHandler());
//			
//			//add checkbox reference to HashMap
//			modulesUIHash.put(moduleId, moduleUICheckBox);
//			
//			//add to GUI
//			int rn = ++rowNumber;
//			boxTable.setWidget(rn, 0, new InlineLabel(UILabel));
//			boxTable.setWidget(rn, 1, moduleUICheckBox);
//			
//		}
//
//		log.leave();
//	}



	

//	/**
//	 * @param createSubmissionForm
//	 */
//	private void initStudiesQuestion(FlowPanel createSubmissionForm) {
//		log.enter("initStudiesQuestion");
//		
//		createSubmissionForm.add(new HTML("<h3>Studies</h3>"));
//
//		createSubmissionForm.add(new HTML("<p>Your new data submission must be linked to at least one study. Use the controls below to link this submission to a study.</p>"));
//		
//		FlowPanel studiesPanel = new FlowPanel();
//		
//		studiesPanel.add(viewStudiesWidgetListBox);
//		
//		log.debug("loading studies into list box...");
//		viewStudiesWidgetListBox.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
//		
////		
////		
////		//prepare panel to show list of studies added
////		VerticalPanel studiesLinkedDisplayPanel = new VerticalPanel();
////		
////		studiesLinkedDisplayPanel.add(new Label("Studies Linked:"));
////		studiesLinkedDisplayPanel.add(noStudiesAddedPanel);
////		studiesLinkedDisplayPanel.add(studiesLinkedWidget);
////		studiesLinkedDisplayPanel.add(showAddStudyLinkUI);		
////		
////		
////		//prepare the studyLink chooser panel
////		VerticalPanel studyLinkChooserVP = new VerticalPanel();
////		studyLinkChooserPopup.add(studyLinkChooserVP);
////		studyLinkChooserVP.add(new Label("Choose a study for the drop down box and then click 'Add study'."));
////		studyLinkChooserVP.add(viewStudiesWidgetListBox);
////		
////		//Create addStudy Button
////		Button addStudyLinkUI = new Button("Add study");
////		addStudyLinkUI.addClickHandler(new ClickHandler() {
////		
////			public void onClick(ClickEvent arg0) {
////				
////				if (studyLinkToAdd != null) {
////					//add study link
////					controller.addStudyLink(studyLinkToAdd);
////					
////					//close popup
////					studyLinkChooserPopup.hide();
////				}
////			}
////			
////		});
////		
////		//Create cancel addStudy Button
////		Button cancelAddStudyLinkUI = new Button("Cancel");
////		cancelAddStudyLinkUI.addClickHandler(new ClickHandler() {
////		
////			public void onClick(ClickEvent arg0) {
////				//close popup
////				studyLinkChooserPopup.hide();
////			}
////			
////		});
////		
////		HorizontalPanel addStudyButtonsPanel = new HorizontalPanel();
////		addStudyButtonsPanel.add(addStudyLinkUI);
////		addStudyButtonsPanel.add(cancelAddStudyLinkUI);
////		
////		
////		studyLinkChooserVP.add(addStudyButtonsPanel);
////		
////		
////		
////		//add clickHandler to display studyLinkChooserPopup
////		showAddStudyLinkUI.addClickHandler(new ClickHandler() {
////			
////			public void onClick(ClickEvent arg0) {
////
////				viewStudiesWidgetListBox.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
////				
////				studyLinkChooserPopup.center();
////				studyLinkChooserPopup.show();
////			}
////		});
////		
////		createSubmissionForm.add(studiesLinkedDisplayPanel);
//
//		createSubmissionForm.add(studiesPanel);
//		
//		log.leave();
//	}







	private void initButtonsPanel() {

		buttonsPanel = new FlowPanel();
		buttonsPanel.add(saveNewSubmissionEntryUI);
		buttonsPanel.add(cancelCreateSubmissionUI);
		buttonsPanel.addStyleName(CSS.CREATESUBMISSION_ACTIONS);

	}

	
	
	
//	/**
//	 * 
//	 */
//	private void createStudiesLinkedWidget() {
//
//		//Create ViewStudies widget to view linked studies
//		studiesLinkedWidget = new ViewStudiesWidget("remove study link");
//
//		//Create ViewStudiesWidget with ListBox renderer
//		ViewStudiesWidgetModelListener customRenderer = new ViewStudiesWidgetListBoxRenderer();
//		viewStudiesWidgetListBox = new ViewStudiesWidget(customRenderer);
//		
//		//add this as listener
//		viewStudiesWidgetListBox.addViewAllStudiesWidgetListener(this);
//		studiesLinkedWidget.addViewAllStudiesWidgetListener(this);
//
//	}





//	private class TitleChangeHandler implements ValueChangeHandler<String> {
//
//		public void onValueChange(ValueChangeEvent<String> arg0) {
//			controller.updateTitle(titleUI.getValue());
//		}
//		
//	}
	
//	private class SummaryChangeHandler implements ValueChangeHandler<String> {
//
//		public void onValueChange(ValueChangeEvent<String> arg0) {
//			controller.updateSummary(summaryUI.getValue());
//		}
//		
//	}
	

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
		
	private class CancelCreateSubmissionUIClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelCreateSubmissionEntry();
		}
		
	}
	
	private class SaveNewSubmissionUIClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.saveNewSubmissionEntry(form.getModel());
		}
		
	}
		

//	public void onTitleChanged(String before, String after, Boolean isValid) {
//		
//		//do not fire events, otherwise a probably dangerous feedback loop will be created!
//		titleUI.setValue(after, false);
//	}

//	public void onSummaryChanged(String before, String after, Boolean isValid) {
//
//		summaryUI.setValue(after, false);
//	}

	
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

//	public void onSubmissionEntryChanged(Boolean isValid) {
//		log.enter("onSubmissionEntryChanged");
//		
//		this.isFormComplete = isValid;
//		log.debug("isFormComplete: " + isFormComplete);
//		
//		log.leave();
//	}

//	public void onStatusChanged(Integer before, Integer after) {
//		
//
//	}

//	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {
//
//		for (String moduleId : modulesConfig.keySet()) {
//			
//			modulesUIHash.get(moduleId).setValue(after.contains(moduleId), false);
//			
//		}
//	}

	
	
	
//	public void onUserActionSelectStudy(StudyEntry studyEntry) {
//		
//		//Distinguish between studies list panel and add study popup panel
//		if (studyLinkChooserPopup.isShowing()) {
//			studyLinkToAdd = studyEntry.getEditLink().getHref();
//		} else {
//			controller.removeStudyLink(studyEntry.getEditLink().getHref());
//		}
//	}

	
	
	
//	public void onAuthorsChanged(Set<AtomAuthor> before, Set<AtomAuthor> after, Boolean isValid) {
//		// TODO Auto-generated method stub
//		
//	}

	
	
	
}
