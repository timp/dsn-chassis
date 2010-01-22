package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

public class ReviewSubmissionWidgetModel 
    extends AsyncWidgetModel {
	
	
	private SubmissionEntry submissionEntry;
	private String submissionEntryId;

	
	
	
    public void setSubmissionEntry(SubmissionEntry submissionEntry) {
    	SubmissionEntryChangeEvent e = new SubmissionEntryChangeEvent(this.submissionEntry, submissionEntry);
    	this.submissionEntry = submissionEntry;
    	this.fireChangeEvent(e);
    }

    
    
	public SubmissionEntry getSubmissionEntry() {
		return submissionEntry;
	}

	
	
	public void setSubmissionEntryId(String id) {
		this.submissionEntryId = id;
		// N.B. we do not fire events, because not used in UI, only used for memory
	}
	
	
	
	public static class RetrieveSubmissionPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_RETRIEVE_SUBMISSION_PENDING = new RetrieveSubmissionPendingStatus();
	
	public static class CreateReviewPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_CREATE_REVIEW_PENDING = new CreateReviewPendingStatus();
	
	

	public static class ReviewCreatedStatus extends ReadyStatus {}
	public static final Status STATUS_REVIEW_CREATED = new ReviewCreatedStatus();
	
	public static class SubmissionRetrievedStatus extends ReadyStatus {}
	public static final Status STATUS_SUBMISSION_RETRIEVED = new SubmissionRetrievedStatus();




	public HandlerRegistration addSubmissionEntryChangeHandler(SubmissionEntryChangeHandler h) {
		return this.addChangeHandler(h, SubmissionEntryChangeEvent.TYPE);
	}



	/**
	 * @return
	 */
	public String getSubmissionEntryId() {
		return this.submissionEntryId;
	}


	

}
