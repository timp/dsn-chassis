/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class StudyManagementWidget extends Composite implements StudyManagementWidgetAPI,
											  NewStudyWidgetPubSubAPI,
											  EditStudyWidgetPubSubAPI,
											  ViewStudyWidgetPubSubAPI,
											  MyStudiesWidgetPubSubAPI,
											  ViewStudyQuestionnaireWidget.PubSubAPI, 
											  EditStudyQuestionnaireWidget.PubSubAPI {

	
	
	
	
	private StudyManagementWidgetModel model;
	private StudyManagementWidgetController controller;
	private StudyManagementWidgetDefaultRenderer renderer;
	private Panel menuCanvas = new SimplePanel();
	private Set<StudyManagementWidgetPubSubAPI> listeners = new HashSet<StudyManagementWidgetPubSubAPI>();

	
	
	
	public StudyManagementWidget(Panel displayCanvas) {
		
		model = new StudyManagementWidgetModel(this);
		
		controller = new StudyManagementWidgetController(model);
		
		renderer = new StudyManagementWidgetDefaultRenderer(this, menuCanvas, displayCanvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.subscribeToChildWidgetEvents();
		
		this.initWidget(renderer.getCanvas());
	}
	
	
	
	
	public StudyManagementWidget() {

		model = new StudyManagementWidgetModel(this);
		
		controller = new StudyManagementWidgetController(model);
		
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
		controller.displayViewStudyWidget();
		renderer.viewStudyWidget.loadStudyEntry(newStudyEntry);
	}

	
	
	
	public void onStudyUpdateSuccess(StudyEntry updatedStudyEntry) {
		controller.displayViewStudyWidget();
		renderer.viewStudyWidget.loadStudyEntry(updatedStudyEntry);
	}

	
	
	
	public void onUserActionEditStudy(StudyEntry studyEntryToEdit) {
		controller.displayEditStudyWidget();
		renderer.editStudyWidget.editStudyEntry(studyEntryToEdit);
	}
	
	
	

	public void onUserActionSelectStudy(StudyEntry studyEntry) {
		controller.displayViewStudyWidget();
		renderer.viewStudyWidget.loadStudyEntry(studyEntry);
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

		controller.displayEditStudyQuestionnaireWidget();
		renderer.editStudyQuestionnaireWidget.setEntry(studyEntry);

	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI#onUserActionViewStudyQuestionnaire(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onUserActionViewStudyQuestionnaire(StudyEntry studyEntry) {
		
//		renderer.studyQuestionnaireWidget.setEntry(studyEntry, true);
//		controller.displayStudyQuestionnaireWidget();
		
		controller.displayViewStudyQuestionnaireWidget();
		renderer.viewStudyQuestionnaireWidget.setEntry(studyEntry);

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
		controller.displayViewStudyWidget();
		renderer.viewStudyWidget.loadStudyEntry(entry);
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
		controller.displayViewStudyQuestionnaireWidget();
		renderer.viewStudyQuestionnaireWidget.setEntry(entry);
	}




	
}
