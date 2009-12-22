/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.user.UserFeed;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class SelectCuratorWidgetModel extends AsyncWidgetModel {

	private SubmissionEntry submissionEntry;
	private UserFeed curators;

	/**
	 * @param entry
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		this.submissionEntry = entry;
		// N.B. we won't fire a model change event, because no-one is interested
	}
	
	public SubmissionEntry getSubmissionEntry() {
		return this.submissionEntry;
	}
	
	public void setCurators(UserFeed curators) {
		this.curators = curators;
		// TODO fire a model change event
	}


	
	
	public static class RetrieveCuratorsPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_RETRIEVE_CURATORS_PENDING = new RetrieveCuratorsPendingStatus();
	
	public static class UpdateSubmissionPendingStatus extends AsyncRequestPendingStatus {}
	public static final Status STATUS_UPDATE_SUBMISSION_PENDING = new UpdateSubmissionPendingStatus();

	public static class CuratorsRetrievedStatus extends ReadyStatus {}
	public static final Status STATUS_CURATORS_RETRIEVED = new CuratorsRetrievedStatus();
	
	public static class UpdateSubmissionSuccessStatus extends ReadyStatus {}
	public static final Status STATUS_SUBMISSION_UPDATE_SUCCESS = new UpdateSubmissionSuccessStatus();

}
