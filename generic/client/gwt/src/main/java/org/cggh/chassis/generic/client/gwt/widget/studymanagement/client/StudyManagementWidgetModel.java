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
	final private StudyManagementWidget owner;

	
	
	
	StudyManagementWidgetModel(StudyManagementWidget owner) {
		this.owner = owner;
	}

	
	
	
	public void addListener(StudyManagementWidgetModelListener listener) {
		listeners.add(listener);
	}

	
	
	
	Integer getDisplayStatus() {
		return displayStatus;
	}

	
	
	
	void setDisplayStatus(Integer requestedDisplay) {
		setDisplayStatus(requestedDisplay, false);
	}

	
	
	
	void setDisplayStatus(Integer requestedDisplay, Boolean userConfirmed) {
	
		// TODO review this method, contains business logic - model should be
		// dumb bean
		
		Integer before = this.displayStatus;
		
		if ( !userConfirmed && couldStatusContainUnsavedData(before) ) {
			
			fireUserMightLoseChanges(requestedDisplay);
			
		} else {
		
			this.displayStatus = requestedDisplay;
		
			fireOnDisplayStatusChanged(before, requestedDisplay);
			
			Boolean couldStatusContainUnsavedData = couldStatusContainUnsavedData(displayStatus);
			
			//alert owner
			owner.fireOnDisplayStatusChanged(couldStatusContainUnsavedData);
		}
		
	}

	
	
	
	private Boolean couldStatusContainUnsavedData(Integer displayStatus) {
		return (displayStatus == DISPLAYING_CREATE_STUDY) || (displayStatus == DISPLAYING_EDIT_STUDY);
	}

	
	
	
	private void fireOnDisplayStatusChanged(Integer before, Integer after) {
		for (StudyManagementWidgetModelListener listener : listeners) {
			listener.onDisplayStatusChanged(before, after);
		}
	}

	
	
	
	private void fireUserMightLoseChanges(Integer requestedDisplay) {

		for (StudyManagementWidgetModelListener listener : listeners) {
			listener.onUserMightLoseChanges(requestedDisplay);			
		}
		
	}

	
	
	
	void reset() {
		displayStatus = DISPLAYING_NONE;
	}

	
	
	
}
