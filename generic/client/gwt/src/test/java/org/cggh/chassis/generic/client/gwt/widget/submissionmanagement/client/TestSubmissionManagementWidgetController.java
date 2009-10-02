/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;


/**
 * @author raok
 *
 */
public class TestSubmissionManagementWidgetController {

	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestSubmissionManagementWidgetController.class);
	}

	private SubmissionManagementWidgetModel testModel;
	private SubmissionManagementWidgetController testController;
	
	@Before
	public void setUp() {
		
		testModel = new SubmissionManagementWidgetModel();
		
		testController = new SubmissionManagementWidgetController(testModel);
		
	}
	
	@Test
	public void testDisplayCreateSubmissionWidget() {
		
		//call method under test
		testController.displayCreateSubmissionWidget();
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testDisplayEditSubmissionWidget() {
		
		//call method under test
		testController.displayEditSubmissionWidget();
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY, testModel.getDisplayStatus());
	}
	
	@Test
	public void testDisplayViewSubmissionWidget() {
		
		//call method under test
		testController.displayViewSubmissionWidget();
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_VIEW_STUDY, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testDisplayViewAllSubmissionsWidget() {
		
		//method under test
		testController.displayViewAllSubmissionsWidget();
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, testModel.getDisplayStatus());
		
	}
	
	
}
