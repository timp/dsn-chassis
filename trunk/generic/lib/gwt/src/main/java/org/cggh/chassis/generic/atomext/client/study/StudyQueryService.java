/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;

/**
 * @author aliman
 *
 */
public class StudyQueryService 
	extends AtomQueryService<StudyEntry, StudyFeed, StudyQuery>
{

	
	
	/**
	 * @param serviceUrl
	 */
	public StudyQueryService(String serviceUrl) {
		super(serviceUrl);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.AtomQueryService#createPersistenceService()
	 */
	@Override
	protected AtomService<StudyEntry, StudyFeed> createPersistenceService() {
		return new StudyPersistenceService();
	}

	
	

}
