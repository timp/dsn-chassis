/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;


import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;

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
	
	
	
	
	/**
	 * Construct a create study widget, passing in the panel to use as the
	 * widget's canvas.
	 * 
	 * @param canvas
	 */
	public CreateStudyWidget(Panel canvas) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, this);
				
		renderer = new CreateStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
	
	
	
	/**
	 * Construct a create study widget with a custom renderer.
	 * 
	 * @param customRenderer
	 */
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
	 * Construct a create study widget, letting the widget create its own
	 * canvas.
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
	public void addListener(CreateStudyWidgetPubSubAPI listener) {
		listeners.add(listener);
	}
	
	
	

	public void fireOnNewStudySaved(StudyEntry studyEntry) {
		for (CreateStudyWidgetPubSubAPI listener : listeners) {
			listener.onNewStudyCreated(studyEntry);
		}
	}

	
	
	
	public void fireOnUserActionCreateStudyEntryCancelled() {
		for (CreateStudyWidgetPubSubAPI listener : listeners) {
			listener.onUserActionCreateStudyCancelled();
		}
	}

	
	
	
	public void setUpNewStudy(String authorEmail) {
		controller.setUpNewStudy(authorEmail);
	}
	
	
	
	
}
