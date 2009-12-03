/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.client.AtomQuery;

/**
 * @author aliman
 *
 */
public class SubmissionQuery extends AtomQuery {

	
	
	
	public static final String ACCEPTANCEREVIEWSTATUS_PENDING = "pending";

	
	
	public static final String PARAM_ACCEPTANCEREVIEW = "acceptancereview";
	
	
	
	/**
	 * @param acceptancereviewstatusPending
	 */
	public void setAcceptanceReviewStatus(String status) {
		this.set(PARAM_ACCEPTANCEREVIEW, status);
	}

	
	
}
