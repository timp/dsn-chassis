/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetPubSubAPI;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class StudyManagementWidget implements CreateStudyWidgetPubSubAPI,
											  EditStudyWidgetPubSubAPI,
											  ViewStudyWidgetPubSubAPI,
											  ViewStudiesWidgetPubSubAPI {

	
	private StudyManagementWidgetModel model;
	private StudyManagementWidgetController controller;
	private StudyManagementWidgetDefaultRenderer renderer;

	public StudyManagementWidget(Panel menuCanvas, Panel displayCanvas, String authorEmail) {
		
		model = new StudyManagementWidgetModel();
		
		controller = new StudyManagementWidgetController(model);
		
		renderer = new StudyManagementWidgetDefaultRenderer(menuCanvas, displayCanvas, controller, authorEmail);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		//register this widget as a listener to child widgets.
		renderer.viewStudyWidget.addViewStudyWidgetListener(this);
		renderer.createStudyWidget.addCreateStudyWidgetListener(this);
		renderer.editStudyWidget.addEditStudyWidgetListener(this);
		renderer.viewAllStudiesWidget.addViewAllStudiesWidgetListener(this);
	}

	public void onNewStudyCreated(StudyEntry newStudyEntry) {
		renderer.viewStudyWidget.loadStudyEntry(newStudyEntry);
		controller.displayViewStudyWidget();
	}

	public void onStudyUpdateSuccess(StudyEntry updatedStudyEntry) {
		renderer.viewStudyWidget.loadStudyEntry(updatedStudyEntry);
		controller.displayViewStudyWidget();
	}

	public void onUserActionEditStudy(StudyEntry studyEntryToEdit) {
		renderer.editStudyWidget.editStudyEntry(studyEntryToEdit);
		controller.displayEditStudyWidget();
	}

	public void onUserActionSelectStudy(StudyEntry studyEntry) {
		renderer.viewStudyWidget.loadStudyEntry(studyEntry);
		controller.displayViewStudyWidget();
	}

	public void onUserActionCreateStudyCancelled() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
