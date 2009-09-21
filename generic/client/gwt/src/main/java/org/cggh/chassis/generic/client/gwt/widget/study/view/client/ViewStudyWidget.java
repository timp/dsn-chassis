/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewStudyWidget implements ViewStudyWidgetAPI {

	final private ViewStudyWidgetModel model;
	final private ViewStudyWidgetController controller;
	final private ViewStudyWidgetDefaultRenderer renderer;
	private Set<ViewStudyWidgetPubSubAPI> listeners = new HashSet<ViewStudyWidgetPubSubAPI>();
	
	public ViewStudyWidget(Panel canvas, AtomService service) {
		
		model = new ViewStudyWidgetModel();
		
		controller = new ViewStudyWidgetController(model, service, this);
		
		renderer = new ViewStudyWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
	}
	
	public ViewStudyWidget(AtomService service, ViewStudyWidgetDefaultRenderer customRenderer) {
		
		model = new ViewStudyWidgetModel();
		
		controller = new ViewStudyWidgetController(model, service, this);
		
		renderer = customRenderer;
		
		// register renderer as listener to model
		model.addListener(renderer);
		
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
		controller.loadStudyEntryByEntryURL(entryURL);
	}

	void editStudyUIClicked(StudyEntry studyEntryToEdit) {
		for (ViewStudyWidgetPubSubAPI listener : listeners ) {
			listener.onEditStudyUIClicked(studyEntryToEdit);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetAPI#addViewStudyWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetPubSubAPI)
	 */
	public void addViewStudyWidgetListener(ViewStudyWidgetPubSubAPI listener) {
		listeners.add(listener);
	}
	
}
