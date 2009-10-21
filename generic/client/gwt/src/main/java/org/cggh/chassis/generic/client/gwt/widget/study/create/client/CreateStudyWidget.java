/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;


import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerPubSubCreateAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class CreateStudyWidget extends Composite implements CreateStudyWidgetAPI, StudyControllerPubSubCreateAPI {

	
	
	
	final private StudyModel model;
	final private StudyControllerCreateAPI controller;
	final private CreateStudyWidgetDefaultRenderer renderer;
	private Set<CreateStudyWidgetPubSubAPI> listeners = new HashSet<CreateStudyWidgetPubSubAPI>();
	
	
	
	
	public CreateStudyWidget(Panel canvas) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, this);
				
		renderer = new CreateStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	
	
	public CreateStudyWidget(CreateStudyWidgetDefaultRenderer customRenderer) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, this);
		
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	
	
	/**
	 * 
	 */
	public CreateStudyWidget() {

		model = new StudyModel();
		
		controller = new StudyController(model, this);
				
		renderer = new CreateStudyWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetAPI#addCreateStudyWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetPubSubAPI)
	 */
	public void addCreateStudyWidgetListener(CreateStudyWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	public void onNewStudySaved(StudyEntry studyEntry) {
		for (CreateStudyWidgetPubSubAPI listener : listeners) {
			listener.onNewStudyCreated(studyEntry);
		}
	}

	public void onUserActionCreateStudyEntryCancelled() {
		for (CreateStudyWidgetPubSubAPI listener : listeners) {
			listener.onUserActionCreateStudyCancelled();
		}
	}

	public void setUpNewStudy(String authorEmail) {
		controller.setUpNewStudy(authorEmail);
	}
	
}
