/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

public interface StudyQuestionnaireWidgetModelListener {

	/**
	 * @param before
	 * @param status
	 */
	void onStatusChanged(int before, int status);

	/**
	 * @param entry 
	 * @param before 
	 * 
	 */
	void onStudyEntryChanged(StudyEntry before, StudyEntry entry);

}