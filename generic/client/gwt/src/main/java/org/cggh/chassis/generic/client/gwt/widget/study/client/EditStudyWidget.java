/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.Set;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class EditStudyWidget extends Composite implements EditStudyWidgetAPI, StudyControllerPubSubEditAPI {

	private StudyModel model;
	private StudyControllerEditAPI controller;
	private EditStudyWidgetDefaultRenderer renderer;
	
	private Set<EditStudyWidgetPubSubAPI> listeners = new HashSet<EditStudyWidgetPubSubAPI>();

	public EditStudyWidget(Panel canvas) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, this);
		
		renderer = new EditStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);	
		
		this.initWidget(this.renderer.getCanvas());
		
	}
	
	public EditStudyWidget(AtomService service, EditStudyWidgetDefaultRenderer customRenderer) {
		
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
	public EditStudyWidget() {
		
		model = new StudyModel();
		
		controller = new StudyController(model, this);
		
		renderer = new EditStudyWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);	
		
		this.initWidget(this.renderer.getCanvas());
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetAPI#addEditStudyWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetPubSubAPI)
	 */
	public void addListener(EditStudyWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetAPI#editStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void editStudyEntry(StudyEntry studyEntryToEdit) {
		controller.loadStudyEntry(studyEntryToEdit);
	}

	public void onStudyUpdated(StudyEntry studyEntry) {
		for (EditStudyWidgetPubSubAPI listener : listeners) {
			listener.onStudyUpdateSuccess(studyEntry);
		}
	}

	public void onUserActionEditStudyEntryCancelled() {
		for (EditStudyWidgetPubSubAPI listener : listeners) {
			listener.onUserActionEditStudyCancelled();
		}
	}
	
}
