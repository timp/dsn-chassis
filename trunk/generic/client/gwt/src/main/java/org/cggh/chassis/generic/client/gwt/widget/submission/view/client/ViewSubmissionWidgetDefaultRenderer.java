/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModelListener;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class ViewSubmissionWidgetDefaultRenderer implements SubmissionModelListener {

	//Expose view elements for testing purposes.
	final Label titleLabel = new Label();
	final Label summaryLabel = new Label();
	final Panel loadingPanel = new SimplePanel();
	final Panel submissionDetailsPanel = new HorizontalPanel();
	final Label editThisSubmissionUI = new Label("Edit Submission");
	final VerticalPanel modulesListPanel = new VerticalPanel();
	final VerticalPanel studyLinksListPanel = new VerticalPanel();

	final private Panel canvas;
	final private SubmissionControllerViewAPI controller;
	private Map<String, String> modulesConfig;
	
	//Linked Studies
	final Panel studiesLinkedCanvas = new SimplePanel();
	private ViewStudiesWidget studiesLinkedWidget;
	
	public ViewSubmissionWidgetDefaultRenderer(Panel canvas, SubmissionControllerViewAPI controller) {
		this.canvas = canvas;
		this.controller = controller;
		
		//get modules from config
		this.modulesConfig = ConfigurationBean.getModules();
		
		//Create ViewStudies widget to view linked studies
		studiesLinkedWidget = new ViewStudiesWidget(studiesLinkedCanvas);
		
		initCanvas();
	}

	private void initCanvas() {

		//prepare loading panel
		loadingPanel.add(new Label("Loading..."));
		
		//prepare submission details panel
		VerticalPanel submissionDetailsVPanel = new VerticalPanel();
		
		HorizontalPanel titleHPanel = new HorizontalPanel();
		titleHPanel.add(new Label("Title: "));
		titleHPanel.add(titleLabel);
		submissionDetailsVPanel.add(titleHPanel);
		
		HorizontalPanel summaryHPanel = new HorizontalPanel();
		summaryHPanel.add(new Label("Summary: "));
		summaryHPanel.add(summaryLabel);
		submissionDetailsVPanel.add(summaryHPanel);

		submissionDetailsVPanel.add(modulesListPanel);
		
		submissionDetailsVPanel.add(studiesLinkedCanvas);
		
		submissionDetailsPanel.add(submissionDetailsVPanel);
		
		//TODO add other actions
		//Create actions panel
		VerticalPanel actionsVPanel = new VerticalPanel();
		
		//add clickhandler to editsubmissionUI
		editThisSubmissionUI.addClickHandler(new EditSubmissionClickHandler());
		
		actionsVPanel.add(editThisSubmissionUI);
		
		submissionDetailsPanel.add(actionsVPanel);
				
	}

	class EditSubmissionClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.onUserActionEditThisSubmission();
		}
	}

	public void onModulesChanged(Set<String> before, Set<String> after, Boolean isValid) {

		modulesListPanel.clear();
		
		for (String module : after) {
			modulesListPanel.add(new Label(modulesConfig.get(module)));
		}
	}

	public void onStatusChanged(Integer before, Integer after) {

		if (after == SubmissionModel.STATUS_LOADING) {
			canvas.clear();
			canvas.add(loadingPanel);
		} else if (after == SubmissionModel.STATUS_LOADED) {
			canvas.clear();
			canvas.add(submissionDetailsPanel);
		} else if (after == SubmissionModel.STATUS_ERROR) {
			// TODO handle error case (could use extra panel or pass error to parent)
		}
			
	}

	public void onStudyLinksChanged(Set<String> before, Set<String> after, Boolean isValid) {
		
		studiesLinkedWidget.loadStudies(after);
		
	}

	public void onSubmissionEntryChanged(Boolean isValid) {
		// TODO Auto-generated method stub
		
	}

	public void onSummaryChanged(String before, String after, Boolean isValid) {
		summaryLabel.setText(after);
		
	}

	public void onTitleChanged(String before, String after, Boolean isValid) {
		titleLabel.setText(after);
		
	}

}
