/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.protocol;

import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public interface StudyQueryService {

	public Deferred<StudyFeed> getStudiesByAuthorEmail(String email);
	
	public Deferred<StudyFeed> query(StudyQuery query);
	
}
