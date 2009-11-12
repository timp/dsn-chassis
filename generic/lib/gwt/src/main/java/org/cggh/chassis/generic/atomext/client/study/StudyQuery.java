/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atomext.client.shared.AtomQuery;

/**
 * @author aliman
 *
 */
public class StudyQuery 
	extends AtomQuery {

	private String submissionUrl;

	/**
	 * @param submissionUrl the submissionUrl to set
	 */
	public void setSubmissionUrl(String submissionUrl) {
		this.submissionUrl = submissionUrl;
	}

	/**
	 * @return the submissionUrl
	 */
	public String getSubmissionUrl() {
		return submissionUrl;
	}

}
