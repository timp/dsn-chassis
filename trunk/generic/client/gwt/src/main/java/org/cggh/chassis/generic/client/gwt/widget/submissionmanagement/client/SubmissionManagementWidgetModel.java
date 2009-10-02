/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author raok
 *
 */
public class SubmissionManagementWidgetModel {

	public static final Integer DISPLAYING_NONE = 0;
	public static final Integer DISPLAYING_CREATE_STUDY = 1;
	public static final Integer DISPLAYING_VIEW_STUDY = 2;
	public static final Integer DISPLAYING_EDIT_STUDY = 3;
	public static final Integer DISPLAYING_VIEW_ALL_STUDIES = 4;
	
	private Integer displayStatus = DISPLAYING_NONE;
	private Set<SubmissionManagementWidgetModelListener> listeners = new HashSet<SubmissionManagementWidgetModelListener>();

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void addListener(SubmissionManagementWidgetModelListener listener) {
		listeners.add(listener);
	}

	public void setDisplayStatus(Integer displayStatus) {
		// TODO Handle work flow errors
		
		Integer before = this.displayStatus;
		this.displayStatus = displayStatus;
		fireOnDisplayStatusChanged(before, displayStatus);
	}

	private void fireOnDisplayStatusChanged(Integer before, Integer after) {
		for (SubmissionManagementWidgetModelListener listener : listeners) {
			listener.onDisplayStatusChanged(before, after);
		}
	}

}
