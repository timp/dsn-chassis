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
	public static final Integer DISPLAYING_CREATE_SUBMISSION = 1;
	public static final Integer DISPLAYING_VIEW_SUBMISSION = 2;
	public static final Integer DISPLAYING_EDIT_SUBMISSION = 3;
	public static final Integer DISPLAYING_VIEW_ALL_SUBMISSIONS = 4;
	
	private Integer displayStatus = DISPLAYING_NONE;
	final private Set<SubmissionManagementWidgetModelListener> listeners = new HashSet<SubmissionManagementWidgetModelListener>();
	final private SubmissionManagementWidget owner;
	
	
	public SubmissionManagementWidgetModel(SubmissionManagementWidget owner) {
		this.owner = owner;
	}

	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void addListener(SubmissionManagementWidgetModelListener listener) {
		listeners.add(listener);
	}

	public void setDisplayStatus(Integer requestedDisplay) {
		setDisplayStatus(requestedDisplay, false);
	}

	public void setDisplayStatus(Integer requestedDisplay, Boolean userConfirmed) {
		
		Integer before = this.displayStatus;
		
		if ( !userConfirmed && couldStatusContainUnsavedData(before) ) {
			
			fireUserMightLoseChanges(requestedDisplay);
			
		} else {
		
			this.displayStatus = requestedDisplay;
		
			fireOnDisplayStatusChanged(before, requestedDisplay);
			
			Boolean couldStatusContainUnsavedData = couldStatusContainUnsavedData(displayStatus);
			
			//alert owner
			owner.displayStatusChanged(couldStatusContainUnsavedData);
			
		}
		
	}

	private Boolean couldStatusContainUnsavedData(Integer displayStatus) {
		return (displayStatus == DISPLAYING_CREATE_SUBMISSION) || (displayStatus == DISPLAYING_EDIT_SUBMISSION);
	}

	private void fireOnDisplayStatusChanged(Integer before, Integer after) {
		for (SubmissionManagementWidgetModelListener listener : listeners) {
			listener.onDisplayStatusChanged(before, after);
		}
	}

	private void fireUserMightLoseChanges(Integer requestedDisplay) {

		for (SubmissionManagementWidgetModelListener listener : listeners) {
			listener.userMightLoseChanges(requestedDisplay);			
		}
		
	}

	public void reset() {
		displayStatus = DISPLAYING_NONE;
	}

}
