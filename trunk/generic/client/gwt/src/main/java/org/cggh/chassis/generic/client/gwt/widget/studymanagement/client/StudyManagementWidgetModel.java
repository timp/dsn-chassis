/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetModel {

	public static final Integer DISPLAYING_NONE = 0;
	public static final Integer DISPLAYING_CREATE_STUDY = 1;
	public static final Integer DISPLAYING_VIEW_STUDY = 2;
	public static final Integer DISPLAYING_EDIT_STUDY = 3;
	public static final Integer DISPLAYING_VIEW_ALL_STUDIES = 4;
	
	private Integer displayStatus = DISPLAYING_NONE;
	private Set<StudyManagementWidgetModelListener> listeners = new HashSet<StudyManagementWidgetModelListener>();

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void addListener(StudyManagementWidgetModelListener listener) {
		listeners.add(listener);
	}

	public void setDisplayStatus(Integer displayStatus) {
		// TODO Handle work flow errors
		
		Integer before = this.displayStatus;
		this.displayStatus = displayStatus;
		fireOnDisplayStatusChanged(before, displayStatus);
	}

	private void fireOnDisplayStatusChanged(Integer before, Integer after) {
		for (StudyManagementWidgetModelListener listener : listeners) {
			listener.onDisplayStatusChanged(before, after);
		}
	}

}
