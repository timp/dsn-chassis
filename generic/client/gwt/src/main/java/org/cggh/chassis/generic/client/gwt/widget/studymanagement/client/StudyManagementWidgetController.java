/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

/**
 * @author raok
 *
 */
public class StudyManagementWidgetController {

	final private StudyManagementWidgetModel model;

	public StudyManagementWidgetController(StudyManagementWidgetModel model) {
		this.model = model;
	}

	public void displayCreateStudyWidget() {
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		
	}

	public void displayEditStudyWidget(String entryURL) {

		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
	}

	public void displayViewStudyWidget() {

		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY);
	}

	public void displayViewAllStudiesWidget() {
		
		model.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);
		
	}

	
	
	
	
}
