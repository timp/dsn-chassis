/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


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