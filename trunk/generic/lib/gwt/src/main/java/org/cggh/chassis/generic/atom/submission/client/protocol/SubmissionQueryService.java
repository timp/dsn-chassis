/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.protocol;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface SubmissionQueryService {

	public Deferred<SubmissionFeed> getSubmissionsByAuthorEmail(String email);
	
}
