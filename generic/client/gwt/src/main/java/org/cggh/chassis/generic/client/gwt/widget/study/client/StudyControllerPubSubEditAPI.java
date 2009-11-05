/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface StudyControllerPubSubEditAPI extends AbstractStudyControllerPubSubAPI {

	public void onStudyUpdated(StudyEntry studyEntry);

	public void onUserActionEditStudyEntryCancelled();

}
