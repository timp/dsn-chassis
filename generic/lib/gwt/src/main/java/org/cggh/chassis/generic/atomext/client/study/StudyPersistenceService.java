/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.study;

import org.cggh.chassis.generic.atom.rewrite.client.AtomServiceImpl;

/**
 * @author aliman
 *
 */
public class StudyPersistenceService extends
		AtomServiceImpl<StudyEntry, StudyFeed> {

	/**
	 * @param factory
	 */
	public StudyPersistenceService(StudyFactory factory) {
		super(factory);
	}


	public StudyPersistenceService() {
		super(new StudyFactory());
	}

}
