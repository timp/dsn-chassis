/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.client.AtomServiceImpl;

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

	public SubmissionPersistenceService(String baseUrl) {
		super(new SubmissionFactory(), baseUrl);
	}

}
