/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerPubSubEditAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class EditStudyWidget implements EditStudyWidgetAPI, StudyControllerPubSubEditAPI {

	private StudyModel model;
	private StudyControllerEditAPI controller;
	private EditStudyWidgetDefaultRenderer renderer;
	
	private Set<EditStudyWidgetPubSubAPI> listeners = new HashSet<EditStudyWidgetPubSubAPI>();

	public EditStudyWidget(Panel canvas, AtomService service, String feedURL, Map<String, String> modulesMap) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, service, this);
		
		renderer = new EditStudyWidgetDefaultRenderer(canvas, controller, feedURL, modulesMap);
		
		// register renderer as listener to model
		model.addListener(renderer);			
	}
	
	public EditStudyWidget(AtomService service, EditStudyWidgetDefaultRenderer customRenderer) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, service, this);
		
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);
		
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetAPI#addEditStudyWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetPubSubAPI)
	 */
	public void addEditStudyWidgetListener(EditStudyWidgetPubSubAPI listener) {
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
			listener.onUserActionCreateStudyCancelled();
		}
	}
	
}
