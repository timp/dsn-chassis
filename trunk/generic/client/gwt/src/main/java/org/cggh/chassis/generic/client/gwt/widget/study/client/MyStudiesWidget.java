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
public class MyStudiesWidget extends Composite {

	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	final private MyStudiesWidgetModel model;
	final private MyStudiesWidgetController controller;
	final private MyStudiesWidgetRenderer renderer;
	private Set<MyStudiesWidgetPubSubAPI> listeners = new HashSet<MyStudiesWidgetPubSubAPI>();
	
	
	
	
	public MyStudiesWidget(Panel canvas, String selectStudyLinkText) {
		
		model = new MyStudiesWidgetModel();
		
		controller = new MyStudiesWidgetController(model, this);
		
		renderer = new MyStudiesWidgetDefaultRenderer(canvas, controller,selectStudyLinkText);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}
	
	
	
	
	public MyStudiesWidget(MyStudiesWidgetRenderer customRenderer) {

		model = new MyStudiesWidgetModel();		

		controller = new MyStudiesWidgetController(model, this);
		
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
	public MyStudiesWidget(String selectStudyLinkText) {

		model = new MyStudiesWidgetModel();
		
		controller = new MyStudiesWidgetController(model, this);
		
		renderer = new MyStudiesWidgetDefaultRenderer(controller, selectStudyLinkText);
		
		// register renderer as listener to model
		model.addListener(renderer);
		
		this.initWidget(renderer.getCanvas());
		
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI#loadStudiesByCollectionUrl(java.lang.String)
	 */
	public void loadStudies() {
		controller.loadStudiesByCollectionUrl();
	}

	
	
	
	void onUserSelectStudy(StudyEntry studyEntry) {
		for (MyStudiesWidgetPubSubAPI listener : listeners) {
			listener.onUserActionSelectStudy(studyEntry);
		}
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetAPI#addViewAllStudiesWidgetListener(org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetPubSubAPI)
	 */
	public void addViewAllStudiesWidgetListener(MyStudiesWidgetPubSubAPI listener) {
		listeners.add(listener);
	}

	
	
	
	public void loadStudies(Set<String> studyEntryUrlsToLoad) {
		controller.loadStudiesByEntryUrls(studyEntryUrlsToLoad);
	}

	
	
	
	public void loadStudies(List<String> studyEntryUrlsToLoad) {
		Set<String> urls = new HashSet<String>(studyEntryUrlsToLoad);
		controller.loadStudiesByEntryUrls(urls);
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
