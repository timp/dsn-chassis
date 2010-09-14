/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.user.UserFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class SelectCuratorWidgetModel extends AsyncWidgetModel {

	private Log log = LogFactory.getLog(SelectCuratorWidgetModel.class);
	
	
	
	private String submissionEntryUrl;
	private SubmissionEntry submissionEntry;
	private UserFeed curators;

    public void setSubmissionEntry(SubmissionEntry submissionEntry) {
    	SubmissionEntryChangeEvent e = new SubmissionEntryChangeEvent(this.submissionEntry, submissionEntry);
    	this.submissionEntry = submissionEntry;
    	this.fireChangeEvent(e);
    }

	public SubmissionEntry getSubmissionEntry() {
		return this.submissionEntry;
	}
	
	public void setCurators(UserFeed curators) {
		log.enter("setCurators");
		
		CuratorsChangeEvent e = new CuratorsChangeEvent(this.curators, curators);
		this.curators = curators;
		this.fireChangeEvent(e);
		
		log.leave();
	}

	public UserFeed getCurators() {
		return this.curators;
	}



	
	public static class RetrieveSubmissionEntryPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_RETRIEVE_SUBMISSION_PENDING = new RetrieveSubmissionEntryPendingStatus();
	
	public static class RetrieveCuratorsPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_RETRIEVE_CURATORS_PENDING = new RetrieveCuratorsPendingStatus();
	
	public static class UpdateSubmissionPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_UPDATE_SUBMISSION_PENDING = new UpdateSubmissionPendingStatus();

	public static class CuratorsRetrievedStatus extends ReadyStatus {}
	public static final Status STATUS_CURATORS_RETRIEVED = new CuratorsRetrievedStatus();
	
	public static class UpdateSubmissionSuccessStatus extends ReadyStatus {}
	public static final Status STATUS_UPDATE_SUBMISSION_SUCCESS = new UpdateSubmissionSuccessStatus();


	public HandlerRegistration addCuratorsChangeHandler(CuratorsChangeHandler h) {
		return this.addChangeHandler(h, CuratorsChangeEvent.TYPE);
	}

	public HandlerRegistration addSubmissionEntryChangeHandler(SubmissionEntryChangeHandler h) {
		return this.addChangeHandler(h, SubmissionEntryChangeEvent.TYPE);
	}

	/**
	 * @param submissionEntryUrl the submissionEntryUrl to set
	 */
	public void setSubmissionEntryUrl(String submissionEntryUrl) {
		this.submissionEntryUrl = submissionEntryUrl;
		// do not fire event, only needed for memory
	}

	/**
	 * @return the submissionEntryUrl
	 */
	public String getSubmissionEntryUrl() {
		return submissionEntryUrl;
	}

	


}
