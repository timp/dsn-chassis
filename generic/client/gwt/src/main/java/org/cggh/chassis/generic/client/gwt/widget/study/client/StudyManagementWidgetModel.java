/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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
	public static final Integer DISPLAYING_VIEW_STUDY_QUESTIONNAIRE = 5;
	public static final Integer DISPLAYING_EDIT_STUDY_QUESTIONNAIRE = 6;
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
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
		log.enter("setDisplayStatus");
	
		// TODO review this method, contains business logic - model should be
		// dumb bean
		
		Integer before = this.displayStatus;
		
		if ( !userConfirmed && couldStatusContainUnsavedData(before) ) {
			log.debug("not user confirmed and could contain unsaved data ... firing user might lose changes");
			
			fireUserMightLoseChanges(requestedDisplay);
			
		} else {

			log.debug("user confirmed or safe transition");
			
			this.displayStatus = requestedDisplay;
		
			log.debug("firing display status changed");
			fireOnDisplayStatusChanged(before, requestedDisplay);
			
			Boolean couldStatusContainUnsavedData = couldStatusContainUnsavedData(displayStatus);
			
			// alert owner TODO why?
			
			log.debug("firing event on owner");
			owner.fireOnDisplayStatusChanged(couldStatusContainUnsavedData);

		}
		
		log.leave();
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
