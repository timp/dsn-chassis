/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public class ViewStudiesWidget extends Composite {

	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	final private ViewStudiesWidgetModel model;
	final private ViewStudiesWidgetController controller;
	final private ViewStudiesWidgetRenderer renderer;
	private Set<ViewStudiesWidgetPubSubAPI> listeners = new HashSet<ViewStudiesWidgetPubSubAPI>();
	
	
	
	
	public ViewStudiesWidget(Panel canvas, String selectStudyLinkText) {
		
		model = new ViewStudiesWidgetModel();
		
		controller = new ViewStudiesWidgetController(model, this);
		
		renderer = new ViewStudiesWidgetDefaultRenderer(canvas, controller,selectStudyLinkText);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}
	
	
	
	
	public ViewStudiesWidget(ViewStudiesWidgetRenderer customRenderer) {

		model = new ViewStudiesWidgetModel();		

		controller = new ViewStudiesWidgetController(model, this);
		
		renderer = customRenderer;
		
		//inject controller into customRenderer
		renderer.setController(controller);

		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}
	
	
	
	
	/**
	 * @param string
	 */
	public ViewStudiesWidget(String selectStudyLinkText) {

		model = new ViewStudiesWidgetModel();
		
		controller = new ViewStudiesWidgetController(model, this);
		
		renderer = new ViewStudiesWidgetDefaultRenderer(controller, selectStudyLinkText);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI#loadStudiesByFeedURL(java.lang.String)
	 */
	public void loadStudies() {
		controller.loadStudiesByFeedURL();
	}

	
	
	
	void onUserSelectStudy(StudyEntry studyEntry) {
		for (ViewStudiesWidgetPubSubAPI listener : listeners) {
			listener.onUserActionSelectStudy(studyEntry);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI#addViewAllStudiesWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetPubSubAPI)
	 */
	public void addViewAllStudiesWidgetListener(ViewStudiesWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	
	
	
	public void loadStudies(Set<String> studyEntryURLsToLoad) {
		controller.loadStudiesByEntryURLs(studyEntryURLsToLoad);
	}

	
	
	
	public void loadStudies(List<String> studyEntryURLsToLoad) {
		Set<String> urls = new HashSet<String>(studyEntryURLsToLoad);
		controller.loadStudiesByEntryURLs(urls);
	}

	
	
	
	public void loadStudiesByAuthorEmail(String authorEmail) {
		log.enter("loadStudiesByAuthorEmail( "+authorEmail+" )");
		controller.loadStudiesByAuthorEmail(authorEmail);
		log.leave();
	}




	/**
	 * @param query
	 */
	public void loadStudies(StudyQuery query) {
		controller.loadStudies(query);
	}
	
	
	
}
