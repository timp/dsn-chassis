/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidgetModel;
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
	public void testDisplaySubmissionDataFileUploadWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_SUBMISSION_DATA_FILE_UPLOAD_WIDGET, true);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//method under test
		testController.displaySubmissionDataFileUploadWidget();

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
