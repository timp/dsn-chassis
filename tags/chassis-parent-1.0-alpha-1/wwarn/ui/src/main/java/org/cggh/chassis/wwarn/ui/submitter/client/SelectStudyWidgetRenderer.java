package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis.Module;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 * @since 13 Jan 2010
 */
public class SelectStudyWidgetRenderer extends ChassisWidgetRenderer<SelectStudyWidgetModel> {

	private Log log = LogFactory.getLog(SelectStudyWidgetRenderer.class);
	
	@UiTemplate("SelectStudyWidget.ui.xml")
	interface SelectStudyWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, SelectStudyWidgetRenderer> {
	}
	private static SelectStudyWidgetRendererUiBinder uiBinder = 
		GWT.create(SelectStudyWidgetRendererUiBinder.class);

	@UiField HTMLPanel bodyPanel;
	@UiField FlowPanel mainActionsPanel;
	@UiField FlowPanel errorPanel;
	@UiField HTMLPanel pendingPanel;
    @UiField HTMLPanel selectExistingStudyPanel;
    @UiField ListBox studySelect;
	@UiField HTMLPanel createStudyInteractionPanel;
	@UiField Button cancelSubmissionButtonOne; //TODO handle cancel
	@UiField Button cancelSubmissionButton;    //TODO Handle cancel
	@UiField Button proceedWithSelectedButton;
	
	@UiField TextBox studyTitle;
	@UiField TextArea studySummary;
	@UiField TextArea otherSubmitters;
	@UiField CheckBox clinical;
	@UiField CheckBox molecular;
	@UiField CheckBox invitro;
	@UiField CheckBox pharmacology;
	
	@UiField Button createAndProceedButton;


	private SelectStudyWidget owner;
	private SelectStudyWidgetController controller;

	public SelectStudyWidgetRenderer(SelectStudyWidget owner) {
		this.owner = owner;
	}

	public void setController(SelectStudyWidgetController controller) {
		this.controller = controller;
	}

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});
		
				
	}

	@Override
	protected void syncUI() {
		super.syncUI();
		errorPanel.setVisible(false);
		proceedWithSelectedButton.setEnabled(false);
	}

	protected void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);
			proceedWithSelectedButton.setEnabled(false);
		} else if (status instanceof SelectStudyWidgetModel.RetrieveFeedPendingStatus) {
		} else if (status instanceof SelectStudyWidgetModel.StudiesRetrievedStatus) {
			syncUiWithFeed();
			pendingPanel.setVisible(false);
			if(model.getStudyCount().equals(new Integer(0))) { 
				selectExistingStudyPanel.setVisible(false);
			}
		} else if (status instanceof SelectStudyWidgetModel.CreateEntryPendingStatus) {
			// Nothing to do
		} else if (status instanceof SelectStudyWidgetModel.StudyCreatedStatus) {
			// Nothing to do
		} else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			error("Error status: " + status);
		} else { 
			error("Unexpected status: " + status);
		}
		log.leave();
	}
	
	/** Clear and re-initialise, setting selected id. */
	void syncUiWithFeed() {
		log.enter("syncUiWithFeed");
		List<Element>  studyEntries = AtomHelper.getEntries(model.getStudyFeed().getDocumentElement());
		studySelect.clear();
		studySelect.addItem("Please select an existing Study", null);
		int index = 1, selectedIndex = 0;
		for (Element element : studyEntries) {
			String id = AtomHelper.getId(element);
			log.debug("id:"+id);
			studySelect.addItem(AtomHelper.getTitle(element), id);
			if(model.getSelectedStudyId() != null && model.getSelectedStudyId().equals(id)) {
				selectedIndex = index;
				log.debug("hit");
			}
			index++;
		}
		log.debug("selectedIndex" + selectedIndex);
		studySelect.setItemSelected(selectedIndex, true);
		proceedWithSelectedButton.setEnabled(this.owner.getModel().isValid());
		log.leave();
	}
	
	@UiHandler("studySelect")
	void handleStudySelection(ClickEvent e) {
		String value = null;
		if (studySelect.getSelectedIndex() != -1 ) {
			value = studySelect.getValue(studySelect.getSelectedIndex());
		}
		this.owner.getModel().setSelectedStudy(value);
		this.owner.getMemory().memorise();
		proceedWithSelectedButton.setEnabled(this.owner.getModel().isValid());
	}

	@UiHandler("proceedWithSelectedButton")
	void handleProceedWithSelectedButtonClick(ClickEvent e) {
		controller.proceed();
	}

	@UiHandler("cancelSubmissionButton")
	void handleCancelSubmissionButtonClick(ClickEvent e) { 
		controller.stepBack();
	}
	@UiHandler("cancelSubmissionButtonOne")
	void handleCancelSubmissionButtonOneClick(ClickEvent e) { 
		controller.stepBack();		
	}
	
	@UiHandler("createAndProceedButton")
	void handleCreateAndProceedButtonClick(ClickEvent e) {
		ArrayList<String> modules = new ArrayList<String>();
		if (clinical.getValue())
			modules.add(Module.CLINICAL.getName());
		if (molecular.getValue())
			modules.add(Module.MOLECULAR.getName());
		if (invitro.getValue())
			modules.add(Module.INVITRO.getName());
		if (pharmacology.getValue())
			modules.add(Module.PHARMACOLOGY.getName());
		controller.createStudyAndProceed(studyTitle.getValue(), 
				studySummary.getValue(), otherSubmitters.getValue(), modules);
	}
	
	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ChangeHandler studySelectedChangeHandler = new ChangeHandler() {

			public void onChange(ChangeEvent event) {
			}

		};
		
		studySelect.addChangeHandler(studySelectedChangeHandler);
		
	}

	public void error(String err) {
		createStudyInteractionPanel.setVisible(false);
		pendingPanel.setVisible(false);
		errorPanel.add(new HTML(err));
		errorPanel.setVisible(true);
	}
	
	
}