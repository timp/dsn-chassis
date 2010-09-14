/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class MyDatasetsWidgetModel 
	extends AsyncWidgetModel {
	
	
	
	// TODO consider refactoring with mydatafileswidgetmodel
	
	
	
	
	private Log log = LogFactory.getLog(MyDatasetsWidgetModel.class);
	
	
	
	
	private DatasetFeed feed;

	
	
	
	
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init();
		
		this.feed = null;
		
		log.leave();
	}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MyDatasetsWidgetModel.class);
	}


	


	public void setFeed(DatasetFeed feed) {
		DatasetFeedChangeEvent e = new DatasetFeedChangeEvent(this.feed, feed);
		this.feed = feed;
		this.fireChangeEvent(e);
	}




	public DatasetFeed getFeed() {
		return feed;
	}

	
	
	
	public HandlerRegistration addChangeHandler(DatasetFeedChangeHandler h) {
		return this.addChangeHandler(h, DatasetFeedChangeEvent.TYPE);
	}

}
