package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.xml.client.Document;

/**
 * @author timp
 * @since 12 Jan 2010
 *
 */
public class SelectStudyWidgetModel extends AsyncWidgetModel {
	public static class RetrieveFeedPendingStatus extends AsyncRequestPendingStatus {}
	public static final RetrieveFeedPendingStatus STATUS_RETRIEVE_STUDIES_PENDING = new RetrieveFeedPendingStatus();
	
/*
	public static class NoEntriesfoundStatus extends ReadyStatus {}
	public static final NoEntriesfoundStatus NO_ENTRIES_FOUND = new NoEntriesfoundStatus();
	
	public static class ReadyForInputStatus extends ReadyStatus {} 
	public static final ReadyForInputStatus STATUS_READY_FOR_INPUT = new ReadyForInputStatus();
*/


	public static class StudiesRetrievedStatus extends ReadyStatus {}
	public static final StudiesRetrievedStatus STATUS_STUDIES_RETRIEVED = new StudiesRetrievedStatus();


	
	private String selectedStudyId;

	private Document studyFeed;
	
	/** return nullable id to next widget */
	public String getSelectedStudyId() {
		return selectedStudyId;
	}

	public void setStudyFeed(Document in) {
		this.studyFeed = in;
	}
	
	public Integer getStudyCount() { 
		Integer count = null;
		if (studyFeed == null) 
			return count;
		else 
			return AtomHelper.getEntries(studyFeed.getDocumentElement()).size();
	}
}
