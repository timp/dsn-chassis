/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

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
