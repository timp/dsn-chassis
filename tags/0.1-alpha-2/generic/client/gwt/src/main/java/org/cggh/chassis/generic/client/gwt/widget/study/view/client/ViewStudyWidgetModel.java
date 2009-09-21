/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetModel {
	
	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_ERROR = 3;
	
	private StudyEntry studyEntry;
	private Integer status;
	private Set<ViewStudyWidgetModelListener> listeners = new HashSet<ViewStudyWidgetModelListener>();


	public ViewStudyWidgetModel() {
		status = STATUS_INITIAL;
	}
	

	public Integer getStatus() {
		return this.status;
	}

	
	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);		
	}
	
	private void fireOnStatusChanged(Integer before, Integer after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}

	public StudyEntry getStudyEntry() {
		return studyEntry;
	}


	public void setStudyEntry(StudyEntry studyEntry) {
		StudyEntry before = this.studyEntry;
		this.studyEntry = studyEntry;
		fireOnStudyEntryChanged(before, studyEntry);
	}


	private void fireOnStudyEntryChanged(StudyEntry before, StudyEntry after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onStudyEntryChanged(before, after);
		}
	}


	public void addListener(ViewStudyWidgetModelListener listener) {
		this.listeners.add(listener);
	}
	
	
}
