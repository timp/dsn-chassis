/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class EditStudyWidget implements EditStudyWidgetAPI {

	private EditStudyWidgetModel model;
	private EditStudyWidgetController controller;
	private EditStudyWidgetDefaultRenderer renderer;
	
	private Set<EditStudyWidgetPubSubAPI> listeners = new HashSet<EditStudyWidgetPubSubAPI>();

	public EditStudyWidget(Panel canvas, AtomService service) {
		
		model = new EditStudyWidgetModel();
		
		controller = new EditStudyWidgetController(model, service, this);
		
		renderer = new EditStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);			
	}
	
	public EditStudyWidget(AtomService service, EditStudyWidgetDefaultRenderer customRenderer) {
		
		model = new EditStudyWidgetModel();
		
		controller = new EditStudyWidgetController(model, service, this);
		
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);
		
	}

	void studyUpdateSuccess(StudyEntry updatedStudyEntry) {
		for (EditStudyWidgetPubSubAPI listener : listeners) {
			listener.onStudyUpdateSuccess(updatedStudyEntry);
		}
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
	
}
