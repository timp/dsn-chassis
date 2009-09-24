/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewAllStudiesWidget implements ViewAllStudiesWidgetAPI {

	final private ViewAllStudiesWidgetModel model;
	final private ViewAllStudiesWidgetController controller;
	final private ViewAllStudiesWidgetDefaultRenderer renderer;
	private Set<ViewAllStudiesWidgetPubSubAPI> listeners = new HashSet<ViewAllStudiesWidgetPubSubAPI>();
	
	public ViewAllStudiesWidget(Panel canvas, AtomService service, String feedURL) {
		
		model = new ViewAllStudiesWidgetModel();
		
		controller = new ViewAllStudiesWidgetController(model, service, this, feedURL);
		
		renderer = new ViewAllStudiesWidgetDefaultRenderer(canvas, controller);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI#loadStudiesByFeedURL(java.lang.String)
	 */
	public void loadStudies() {
		controller.loadStudies();
	}

	void onViewStudyUIClicked(StudyEntry studyEntry) {
		for (ViewAllStudiesWidgetPubSubAPI listener : listeners) {
			listener.onUserActionViewStudy(studyEntry);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI#addViewAllStudiesWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetPubSubAPI)
	 */
	public void addViewAllStudiesWidgetListener(ViewAllStudiesWidgetPubSubAPI listener) {
		listeners.add(listener);
	}
	
	
}
