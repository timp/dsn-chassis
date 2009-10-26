/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/**
 * @author raok
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SubmissionManagementWidget.class})
public class TestSubmissionManagementWidgetController {

	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestSubmissionManagementWidgetController.class);
	}

	private SubmissionManagementWidgetModel mockModel;
	private SubmissionManagementWidgetController testController;
	
	@Before
	public void setUp() {
		
		mockModel = PowerMock.createMock(SubmissionManagementWidgetModel.class);
		
		testController = new SubmissionManagementWidgetController(null, mockModel);
		
	}
	
	@Test
	public void testDisplayCreateSubmissionWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION, false);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayCreateSubmissionWidget();

		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayEditSubmissionWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION, true);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayEditSubmissionWidget();

		PowerMock.verify(mockModel);
	}
	
	@Test
	public void testDisplayViewSubmissionWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_SUBMISSION, true);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayViewSubmissionWidget();

		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayViewAllSubmissionsWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS, false);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//method under test
		testController.displayViewAllSubmissionsWidget();

		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testReset() {
		
		//set expectations
		mockModel.reset();
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//method under test
		testController.reset();

		PowerMock.verify(mockModel);
		
	}
	
	
}
