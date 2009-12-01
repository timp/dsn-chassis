/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.submission;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;

/**
 * @author aliman
 *
 */
public class SubmissionQueryService 
	extends AtomQueryService<SubmissionEntry, SubmissionFeed, SubmissionQuery> {


	
	
	/**
	 * @param serviceUrl
	 */
	public SubmissionQueryService(String serviceUrl) {
		super(serviceUrl);
	}

	
	
	
	@Override
	protected AtomService<SubmissionEntry, SubmissionFeed> createPersistenceService() {
		return new SubmissionPersistenceService();
	}

	
	

}
