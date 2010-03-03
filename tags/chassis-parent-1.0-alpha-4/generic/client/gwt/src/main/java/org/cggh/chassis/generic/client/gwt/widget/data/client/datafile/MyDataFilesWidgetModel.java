/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;


import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class MyDataFilesWidgetModel extends AsyncWidgetModel {

	
	
	
	private Log log;

	
	
	
	private DataFileFeed feed;
	
	
	
	
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		super.init();
		
		this.feed = null;
		
		log.leave();
	}
	
	
	
	
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MyDataFilesWidgetModel.class);
	}




	public void setFeed(DataFileFeed feed) {
		DataFileFeedChangeEvent e = new DataFileFeedChangeEvent(this.feed, feed);
		this.feed = feed;
		this.fireChangeEvent(e);
	}




	public DataFileFeed getFeed() {
		return feed;
	}

	
	
	
	public HandlerRegistration addChangeHandler(DataFileFeedChangeHandler h) {
		return this.addChangeHandler(h, DataFileFeedChangeEvent.TYPE);
	}



}
