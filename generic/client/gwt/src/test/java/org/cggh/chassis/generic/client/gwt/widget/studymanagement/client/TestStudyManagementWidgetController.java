/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;


/**
 * @author raok
 *
 */
public class TestStudyManagementWidgetController {

	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestStudyManagementWidgetController.class);
	}

	private StudyManagementWidgetModel testModel;
	private StudyManagementWidgetController testController;
	
	@Before
	public void setUp() {
		
		testModel = new StudyManagementWidgetModel();
		
		testController = new StudyManagementWidgetController(testModel);
		
	}
	
	@Test
	public void testDisplayCreateStudyWidget() {
		
		//call method under test
		testController.displayCreateStudyWidget();
		
		//test outcome
		assertEquals(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testDisplayEditStudyWidget() {
		
		//test data
		String entryURL = "http://example.com/studies/study1";
		
		//call method under test
		testController.displayEditStudyWidget(entryURL);
		
		//test outcome
		assertEquals(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY, testModel.getDisplayStatus());
	}
	
	@Test
	public void testDisplayViewStudyWidget() {
		
		//call method under test
		testController.displayViewStudyWidget();
		
		//test outcome
		assertEquals(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testDisplayViewAllStudiesWidget() {
		
		//method under test
		testController.displayViewAllStudiesWidget();
		
		//test outcome
		assertEquals(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, testModel.getDisplayStatus());
		
	}
	
	
}
