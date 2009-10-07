/**
 * 
 */
package org.cggh.chassis.generic.atom.datafile.client.protocol;

/**
 * @author aliman
 *
 */
public class DataFileQuery {

	private String authorEmail, submissionUrl;

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

	/**
	 * @param authorEmail the authorEmail to set
	 */
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	/**
	 * @return the authorEmail
	 */
	public String getAuthorEmail() {
		return authorEmail;
	}
	
}
