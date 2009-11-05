/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface StudyControllerPubSubViewAPI extends AbstractStudyControllerPubSubAPI {

	public void fireOnUserActionEditStudy(StudyEntry studyEntryToEdit);

	/**
	 * @param studyEntry
	 */
	public void fireOnUserActionEditStudyQuestionnaire(StudyEntry studyEntry);

	/**
	 * @param studyEntry
	 */
	public void fireOnUserActionViewStudyQuestionnaire(StudyEntry studyEntry);

}
