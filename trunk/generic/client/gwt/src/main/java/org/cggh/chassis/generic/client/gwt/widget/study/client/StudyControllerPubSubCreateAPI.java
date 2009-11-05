/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface StudyControllerPubSubCreateAPI extends AbstractStudyControllerPubSubAPI {

	public void fireOnUserActionCreateStudyEntryCancelled();

	public void fireOnNewStudySaved(StudyEntry studyEntry);

}
