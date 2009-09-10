/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface EditStudyWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onTitleChanged(Boolean isValid);

	void onSummaryChanged(Boolean isValid);

	void onModulesChanged(Boolean isValid);

	void onFormCompleted(Boolean isFormComplete);

	void onStudyEntryChanged(StudyEntry before, StudyEntry after);
}
