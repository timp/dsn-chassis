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

	final private StudyManagementWidgetModel model;
	private Log log = LogFactory.getLog(this.getClass());

	public StudyManagementWidgetController(StudyManagementWidgetModel model) {
		this.model = model;
	}

	public void displayCreateStudyWidget() {
		log.enter("displayCreateStudyWidget");
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		
		log.leave();
	}

	public void displayEditStudyWidget() {
		log.enter("displayEditStudyWidget");

		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		
		log.leave();
	}

	public void displayViewStudyWidget() {
		log.enter("displayViewStudyWidget");

		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY);
		
		log.leave();
	}

	public void displayViewAllStudiesWidget() {
		log.enter("displayViewAllStudiesWidget");
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);
		
		log.leave();		
	}

	
	
	
	
}
