package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author timp
 * @since 12 Jan 2010
 *
 */
public class SelectStudyWidgetModel extends AsyncWidgetModel {

	public static class RetrieveFeedPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveFeedPendingStatus STATUS_RETRIEVE_FEED_PENDING = new RetrieveFeedPendingStatus();
	
	public static class NoEntriesfoundStatus extends ReadyStatus {}
	public static final NoEntriesfoundStatus NO_ENTRIES_FOUND = new NoEntriesfoundStatus();
	
	public static class ReadyForInputStatus extends ReadyStatus {} 
	public static final ReadyForInputStatus STATUS_READY_FOR_INPUT = new ReadyForInputStatus();
	private String selectedStudyId;
	
	/** return nullable id to next widget */
	public String getSelectedStudyId() {
		return selectedStudyId;
	}
	
	
}
