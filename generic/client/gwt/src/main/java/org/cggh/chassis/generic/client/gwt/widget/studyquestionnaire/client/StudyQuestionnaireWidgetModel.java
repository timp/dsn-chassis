/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studyquestionnaire.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
public class StudyQuestionnaireWidgetModel {

	static final int STATUS_INITIAL = 0;
	static final int STATUS_LOADINGQUESTIONNAIRE = 1;
	static final int STATUS_READY = 2;
	static final int STATUS_INITIALISINGQUESTIONNAIRE = 3;
	static final int STATUS_ERROR = 4;
	static final int STATUS_SAVING = 5;
	
	private int status = STATUS_INITIAL;
	private StudyEntry entry = null;
	
	private Set<StudyQuestionnaireWidgetModelListener> listeners = new HashSet<StudyQuestionnaireWidgetModelListener>();
	
	void addListener(StudyQuestionnaireWidgetModelListener l) {
		this.listeners.add(l);
	}
	
	void setStatus(int status) {
		int before = this.status;
		this.status = status;
		for (StudyQuestionnaireWidgetModelListener l : listeners) {
			l.onStatusChanged(before, status);
		}
	}
	
	void setEntry(StudyEntry entry) {
		StudyEntry before = this.entry;
		this.entry = entry;
		for (StudyQuestionnaireWidgetModelListener l : listeners) {
			l.onStudyEntryChanged(before, entry);
		}
	}

	/**
	 * @return
	 */
	int getStatus() {
		return this.status;
	}

	/**
	 * @return
	 */
	StudyEntry getEntry() {
		return this.entry;
	}
	
}
