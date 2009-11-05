/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface ViewStudyWidgetPubSubAPI {

	void onUserActionEditStudy(StudyEntry studyEntryToEdit);

	/**
	 * @param studyEntry
	 */
	void onUserActionEditStudyQuestionnaire(StudyEntry studyEntry);

	/**
	 * @param studyEntry
	 */
	void onUserActionViewStudyQuestionnaire(StudyEntry studyEntry);

}
