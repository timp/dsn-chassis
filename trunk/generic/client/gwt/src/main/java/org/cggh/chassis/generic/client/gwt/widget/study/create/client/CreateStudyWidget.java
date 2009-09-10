/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;


import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class CreateStudyWidget implements CreateStudyWidgetAPI {

	final private CreateStudyWidgetModel model;
	final private CreateStudyWidgetController controller;
	final private CreateStudyWidgetDefaultRenderer renderer;
	private Set<CreateStudyWidgetPubSubAPI> listeners = new HashSet<CreateStudyWidgetPubSubAPI>();
	
	public CreateStudyWidget(Panel canvas, AtomService service, String feedURL) {
		
		model = new CreateStudyWidgetModel();
		
		controller = new CreateStudyWidgetController(model, service, feedURL, this);
		
		renderer = new CreateStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
	}
	
	public CreateStudyWidget(AtomService service, String feedURL, CreateStudyWidgetDefaultRenderer customRenderer) {
		
		model = new CreateStudyWidgetModel();
		
		controller = new CreateStudyWidgetController(model, service, feedURL, this);
		
		renderer = customRenderer;
		
		// inject controller into renderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetAPI#setUpNewStudy()
	 */
	public void setUpNewStudy() {
		controller.setUpNewStudy();
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetAPI#addCreateStudyWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetPubSubAPI)
	 */
	public void addCreateStudyWidgetListener(CreateStudyWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	void newStudyCreated(StudyEntry studyEntry) {
		for (CreateStudyWidgetPubSubAPI listener : listeners) {
			listener.onNewStudyCreated(studyEntry);
		}
	}
	
}
