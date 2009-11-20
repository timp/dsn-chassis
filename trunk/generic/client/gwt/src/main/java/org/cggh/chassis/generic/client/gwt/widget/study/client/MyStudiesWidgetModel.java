/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;


/**
 * @author raok
 *
 */
public class MyStudiesWidgetModel 
	extends AsyncWidgetModel {
	
	
	
	// TODO consider refactoring with mydatafileswidgetmodel
	
	
	
	
	private Log log = LogFactory.getLog(MyStudiesWidgetModel.class);
	
	
	
	
	private StudyFeed feed;

	
	
	
	
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init();
		
		this.feed = null;
		
		log.leave();
	}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MyStudiesWidgetModel.class);
	}


	


	public void setFeed(StudyFeed feed) {
		StudyFeedChangeEvent e = new StudyFeedChangeEvent(this.feed, feed);
		this.feed = feed;
		this.fireChangeEvent(e);
	}




	public StudyFeed getFeed() {
		return feed;
	}

	
	
	
	public HandlerRegistration addChangeHandler(StudyFeedChangeHandler h) {
		return this.addChangeHandler(h, StudyFeedChangeEvent.TYPE);
	}

}
