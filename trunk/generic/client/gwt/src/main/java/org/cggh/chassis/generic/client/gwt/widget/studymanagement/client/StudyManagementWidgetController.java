/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private StudyManagementWidgetModel model;

	public StudyManagementWidgetController(StudyManagementWidgetModel model) {
		this.model = model;
	}

	public void displayCreateStudyWidget() {
		displayCreateStudyWidget(false);
	}

	public void displayCreateStudyWidget(Boolean userConfirmed) {
		log.enter("displayCreateStudyWidget");
		log.debug("userConfirmed: " + userConfirmed);
			
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, userConfirmed);
		
		log.leave();
		
	}

	public void displayEditStudyWidget() {
		log.enter("displayEditStudyWidget");

		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY, true);
		
		log.leave();
	}

	public void displayViewStudyWidget() {
		log.enter("displayViewStudyWidget");

		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY, true);
		
		log.leave();
	}

	public void displayViewAllStudiesWidget() {
		displayViewAllStudiesWidget(false);
	}

	public void displayViewAllStudiesWidget(Boolean userConfirmed) {
		log.enter("displayViewAllStudiesWidget");
		log.debug("userConfirmed: " + userConfirmed);
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, userConfirmed);
					
		log.leave();
	}

	public void reset() {
		log.enter("reset");
		
		model.reset();
		
		log.leave();
	}

	
	
	
	
}
