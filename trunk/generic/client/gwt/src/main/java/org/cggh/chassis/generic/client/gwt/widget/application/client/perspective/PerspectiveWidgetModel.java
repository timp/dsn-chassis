/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

import java.util.HashSet;
import java.util.Set;

/**
 * @author raok
 *
 */
public class PerspectiveWidgetModel {

	public static final Integer DISPLAYING_NONE = 0;
	public static final Integer DISPLAYING_STUDY_MANAGEMENT_WIDGET = 1;
	public static final Integer DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY = 2;
	public static final Integer DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET = 3;
	public static final Integer DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY = 4;
	
	private Integer displayStatus = DISPLAYING_NONE;
	private Set<PerspectiveWidgetModelListener> listeners = new HashSet<PerspectiveWidgetModelListener>();
	
	public Integer getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(Integer displayStatus) {
		
		setDisplayStatus(displayStatus, false);
		
	}

	public void setDisplayStatus(Integer requestedStatus, Boolean userConfirmed) {
		
		Integer before = this.displayStatus;
		
		if (!userConfirmed
			&& (shouldRequestUserConfirmation(requestedStatus)) ) {
			
			fireUserMightLoseChanges(requestedStatus);
			
		} else {
			
			this.displayStatus = requestedStatus;
			
			fireOnDisplayStatusChanged(before);
		}
		
	}

	private Boolean shouldRequestUserConfirmation(Integer requestedStatus) {
				
		//request if changing from one management widget that is in a data entry state to another widget in any state
		Boolean shouldRequestUserConfirmation = ( (displayStatus == DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY)
												  && !( (requestedStatus == DISPLAYING_STUDY_MANAGEMENT_WIDGET)
														|| (requestedStatus == DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY))  
												|| (displayStatus == DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY)
												  && !( (requestedStatus == DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET) 
														|| (requestedStatus == DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY)) );
		
		return shouldRequestUserConfirmation;
	}

	private void fireUserMightLoseChanges(Integer requestedStatus) {

		for (PerspectiveWidgetModelListener listener : listeners) {
			listener.userMightLoseChanges(requestedStatus);
		}
	}

	private void fireOnDisplayStatusChanged(Integer before) {

		for (PerspectiveWidgetModelListener listener : listeners) {
			listener.onDisplayStatusChanged(before, getDisplayStatus());
		}
	}

	public void addListener(PerspectiveWidgetModelListener listener) {
		listeners.add(listener);
	}
	
	
	
}
