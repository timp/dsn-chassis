/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.review;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionLink;

/**
 * @author aliman
 *
 */
public interface ReviewEntry extends AtomEntry {
	
	public void setSubmissionLink(String submissionEntryUrl);

	public SubmissionLink getSubmissionLink();
	
	public Review getReview();
	
	public AtomAuthor getAuthor(); 

}
