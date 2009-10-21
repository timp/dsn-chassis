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

import com.google.gwt.user.client.Command;
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
											  ViewStudiesWidgetPubSubAPI {

	
	
	
	
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
	
	
	
	
	public void onUserActionEditStudyCancelled() {
		// TODO Auto-generated method stub
		
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
	
}
