/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.mockimpl;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQuery;
import org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class MockSubmissionQueryService implements SubmissionQueryService {

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQueryService#query(org.cggh.chassis.generic.atom.submission.client.protocol.SubmissionQuery)
	 */
	public Deferred<SubmissionFeed> query(SubmissionQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
