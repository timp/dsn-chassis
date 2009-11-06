/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public class MyStudiesWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_ERROR = 3;
	
	private Integer status = STATUS_INITIAL;
	private Set<MyStudiesWidgetModelListener> listeners = new HashSet<MyStudiesWidgetModelListener>();
	private List<StudyEntry> studyEntries; 


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);
		
	}

	private void fireOnStatusChanged(Integer before, Integer after) {
		for (MyStudiesWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}

	public void addListener(MyStudiesWidgetModelListener listener) {
		listeners.add(listener);
	}

	public List<StudyEntry> getStudyEntries() {
		return studyEntries;
	}

	public void setStudyEntries(List<StudyEntry> studyEntries) {
		List<StudyEntry> before = this.studyEntries;
		this.studyEntries = studyEntries;
		fireOnStudyEntriesChanged(before, studyEntries);
	}

	private void fireOnStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after) {
		for (MyStudiesWidgetModelListener listener : listeners) {
			listener.onStudyEntriesChanged(before, after);
		}
	}
}
