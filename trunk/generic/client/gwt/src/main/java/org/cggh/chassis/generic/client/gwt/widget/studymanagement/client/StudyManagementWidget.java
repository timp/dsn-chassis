/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetPubSubAPI;
import org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.ViewStudyQuestionnaireWidget;
import org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.EditStudyQuestionnaireWidget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class StudyManagementWidget extends Composite implements StudyManagementWidgetAPI,
											  CreateStudyWidgetPubSubAPI,
											  EditStudyWidgetPubSubAPI,
											  ViewStudyWidgetPubSubAPI,
											  ViewStudiesWidgetPubSubAPI,
											  ViewStudyQuestionnaireWidget.PubSubAPI, 
											  EditStudyQuestionnaireWidget.PubSubAPI {

	
	
	
	
	private StudyManagementWidgetModel model;
	private StudyManagementWidgetController controller;
	private StudyManagementWidgetDefaultRenderer renderer;
	private Panel menuCanvas = new SimplePanel();
	private Set<StudyManagementWidgetPubSubAPI> listeners = new HashSet<StudyManagementWidgetPubSubAPI>();

	
	
	
	public StudyManagementWidget(Panel displayCanvas, String authorEmail) {
		
		model = new StudyManagementWidgetModel(this);
		
		controller = new StudyManagementWidgetController(this, model);
		
		renderer = new StudyManagementWidgetDefaultRenderer(this, menuCanvas, displayCanvas, controller, authorEmail);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.subscribeToChildWidgetEvents();
		
		this.initWidget(renderer.getCanvas());
	}
	
	
	
	
	public StudyManagementWidget() {

		model = new StudyManagementWidgetModel(this);
		
		controller = new StudyManagementWidgetController(this, model);
		
		renderer = new StudyManagementWidgetDefaultRenderer(this, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.subscribeToChildWidgetEvents();
		
		this.initWidget(renderer.getCanvas());
		
	}
	
	
	
	
	private void subscribeToChildWidgetEvents() {

		//register this widget as a listener to child widgets.
		renderer.viewStudyWidget.addViewStudyWidgetListener(this);
		renderer.createStudyWidget.addListener(this);
		renderer.editStudyWidget.addEditStudyWidgetListener(this);
		renderer.viewStudiesWidget.addViewAllStudiesWidgetListener(this);
		renderer.viewStudyQuestionnaireWidget.addListener(this);
		renderer.editStudyQuestionnaireWidget.addListener(this);
		
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
		controller.displayViewStudiesWidget();
	}
	
	
	
	
	public void onUserActionEditStudyCancelled() {
		controller.displayViewStudyWidget();
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI#onUserActionEditStudyQuestionnaire(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onUserActionEditStudyQuestionnaire(StudyEntry studyEntry) {
		
//		renderer.studyQuestionnaireWidget.setEntry(studyEntry, false);
//		controller.displayStudyQuestionnaireWidget();

		renderer.editStudyQuestionnaireWidget.setEntry(studyEntry);
		controller.displayEditStudyQuestionnaireWidget();

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI#onUserActionViewStudyQuestionnaire(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onUserActionViewStudyQuestionnaire(StudyEntry studyEntry) {
		
//		renderer.studyQuestionnaireWidget.setEntry(studyEntry, true);
//		controller.displayStudyQuestionnaireWidget();
		
		renderer.viewStudyQuestionnaireWidget.setEntry(studyEntry);
		controller.displayViewStudyQuestionnaireWidget();

	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidgetAPI#getMenuCanvas()
	 */
	public Panel getMenuCanvas() {
		return menuCanvas;
	}

	
	
	
	protected void fireOnDisplayStatusChanged(Boolean couldStatusContainUnsavedData) {

		for (StudyManagementWidgetPubSubAPI listener : listeners) {
			listener.onStudyManagementDisplayStatusChanged(couldStatusContainUnsavedData);
		}
		
	}

	
	
	
	public void addListener(StudyManagementWidgetPubSubAPI listener) {

		listeners.add(listener);
	}

	
	
	
	public void resetWidget() {
		controller.reset();
	}




	/**
	 * @return
	 */
	public MenuBar getMenu() {
		return this.renderer.getMenu();
	}




	/**
	 * 
	 */
	public void fireOnStudyManagementMenuAction() {

		for (StudyManagementWidgetPubSubAPI listener : listeners) {
			listener.onStudyManagementMenuAction(this);
		}

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.ViewStudyQuestionnaireWidget.PubSubAPI#onUserActionViewStudy(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onUserActionViewStudy(StudyEntry entry) {
		renderer.viewStudyWidget.loadStudyEntry(entry);
		controller.displayViewStudyWidget();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.EditStudyQuestionnaireWidget.PubSubAPI#onUserActionEditStudyQuestionnaireCancelled()
	 */
	public void onUserActionEditStudyQuestionnaireCancelled() {
		controller.displayViewStudyQuestionnaireWidget();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client.EditStudyQuestionnaireWidget.PubSubAPI#onStudyQuestionnaireUpdateSuccess(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onStudyQuestionnaireUpdateSuccess(StudyEntry entry) {
		renderer.viewStudyQuestionnaireWidget.setEntry(entry);
		controller.displayViewStudyQuestionnaireWidget();
	}




	
}
