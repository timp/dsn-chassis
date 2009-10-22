/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetController {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	final private StudyManagementWidgetModel model;
	private StudyManagementWidget owner;

	
	
	
	public StudyManagementWidgetController(StudyManagementWidget owner, StudyManagementWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	public void displayCreateStudyWidget() {
		displayCreateStudyWidget(false);
	}

	
	
	
	public void displayCreateStudyWidget(Boolean userConfirmed) {
		log.enter("displayCreateStudyWidget");
		log.debug("userConfirmed: " + userConfirmed);
			
//		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, userConfirmed);
		
		// disable confirmation for now, because of interaction with other widgets
		log.debug("set display status on model");
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, true);
		
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

	
	
	
	public void displayViewStudiesWidget() {
		displayViewStudiesWidget(false);
	}

	
	
	
	public void displayViewStudiesWidget(Boolean userConfirmed) {
		log.enter("displayViewStudiesWidget");
		log.debug("userConfirmed: " + userConfirmed);
		
//		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, userConfirmed);

		// disable confirmation for now, because of interaction with other widgets
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, true);

		log.leave();
	}

	
	
	
	public void reset() {
		log.enter("reset");
		
		model.reset();
		
		log.leave();
	}




	/**
	 * @param studyEntry
	 * @param readOnly
	 */
	public void displayViewStudyQuestionnaireWidget() {
		log.enter("displayViewStudyQuestionnaireWidget");
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY_QUESTIONNAIRE, true);

		log.leave();
	}

	
	
	
	/**
	 * @param studyEntry
	 * @param readOnly
	 */
	public void displayEditStudyQuestionnaireWidget() {
		log.enter("displayEditStudyQuestionnaireWidget");
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY_QUESTIONNAIRE, true);

		log.leave();
	}

	
	
	
	
}
