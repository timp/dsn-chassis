/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ListSubmissionsWidgetModel extends AsyncWidgetModel {

	
	
	
	private SubmissionFeed feed;




	private Boolean filterByReviewExistance;
	
	
	
	
	@Override
	public void init() {
		super.init();
		this.setFeed(null);
	}




	/**
	 * @param feed the feed to set
	 */
	public void setFeed(SubmissionFeed feed) {
		SubmissionFeedChangeEvent e = new SubmissionFeedChangeEvent(this.feed, feed);
		this.feed = feed;
		this.fireChangeEvent(e);
	}




	/**
	 * @return the feed
	 */
	public SubmissionFeed getFeed() {
		return feed;
	}
	
	
	
	
	public HandlerRegistration addSubmissionFeedChangeHandler(SubmissionFeedChangeHandler h) {
		return this.addChangeHandler(h, SubmissionFeedChangeEvent.TYPE);
	}




	public void setFilterByReviewExistance(Boolean exists) {
		filterByReviewExistance = exists;
	}
	public Boolean getFilterByReviewExistance() {
		return filterByReviewExistance;
	}
	
	
}
