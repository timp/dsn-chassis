/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerPubSubViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class ViewStudyWidget extends Composite implements ViewStudyWidgetAPI, StudyControllerPubSubViewAPI {

	
	
	
	final private StudyModel model;
	final private StudyControllerViewAPI controller;
	final private ViewStudyWidgetDefaultRenderer renderer;
	private Set<ViewStudyWidgetPubSubAPI> listeners = new HashSet<ViewStudyWidgetPubSubAPI>();
	
	
	
	
	public ViewStudyWidget(Panel canvas) {
		
		model = new StudyModel();
		
		controller = new StudyController(model, this);
		
		renderer = new ViewStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}

	
	
	
	public ViewStudyWidget(AtomService service, ViewStudyWidgetDefaultRenderer customRenderer) {

		model = new StudyModel();
		
		controller = new StudyController(model, this);
		
		renderer = customRenderer;
		
		// register renderer as listener to model
		model.addListener(renderer);

		this.initWidget(renderer.getCanvas());
		
	}
	
	
	
		
	/**
	 * 
	 */
	public ViewStudyWidget() {

		model = new StudyModel();
		
		controller = new StudyController(model, this);
		
		renderer = new ViewStudyWidgetDefaultRenderer(controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI#loadStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void loadStudyEntry(StudyEntry studyEntry) {
		controller.loadStudyEntry(studyEntry);
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI#loadStudyByEntryURL(java.lang.String)
	 */
	public void loadStudyByEntryURL(String entryURL) {
		controller.loadStudyEntryByURL(entryURL);
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI#addViewStudyWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI)
	 */
	public void addViewStudyWidgetListener(ViewStudyWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	public void onUserActionEditStudy(StudyEntry studyEntryToEdit) {
		for (ViewStudyWidgetPubSubAPI listener : listeners ) {
			listener.onUserActionEditStudy(studyEntryToEdit);
		}
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerPubSubViewAPI#onUserActionEditStudyQuestionnaire(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onUserActionEditStudyQuestionnaire(StudyEntry studyEntry) {
		for (ViewStudyWidgetPubSubAPI listener : listeners ) {
			listener.onUserActionEditStudyQuestionnaire(studyEntry);
		}
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerPubSubViewAPI#onUserActionViewStudyQuestionnaire(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void onUserActionViewStudyQuestionnaire(StudyEntry studyEntry) {
		for (ViewStudyWidgetPubSubAPI listener : listeners ) {
			listener.onUserActionViewStudyQuestionnaire(studyEntry);
		}
	}
	
}
