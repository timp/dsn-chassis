/**
 * 
 */
package org.cggh.chassis.generic.atom.rewrite.client.submission;

import org.cggh.chassis.generic.atom.rewrite.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class SubmissionPersistenceService 
	extends AtomServiceImpl<SubmissionEntry, SubmissionFeed> {

	public SubmissionPersistenceService(SubmissionFactory factory) {
		super(factory);
	}
	
	public SubmissionPersistenceService() {
		super(new SubmissionFactory());
	}

}
