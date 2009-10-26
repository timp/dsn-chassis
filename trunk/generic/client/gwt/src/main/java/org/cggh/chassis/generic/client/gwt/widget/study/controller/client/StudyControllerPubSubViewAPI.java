/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

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
