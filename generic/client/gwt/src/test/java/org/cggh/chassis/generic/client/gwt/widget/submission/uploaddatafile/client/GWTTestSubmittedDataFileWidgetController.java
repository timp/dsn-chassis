/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetController.SubmissionDataFileCompleteHandler;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * @author raok
 *
 */
public class GWTTestSubmittedDataFileWidgetController extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.UploadSubmissionDataFileWidget";
	}

	private SubmissionDataFileWidgetController testController;
	private SubmissionDataFileWidgetModel testModel;

	//test data
	private String submissionLink = "http://www.foo.com/submissions/submission1";
	private String authorEmail = "foo@somethings.com";
	
	//FIXME create working mock widget
	private SubmissionDataFileWidget mockOwner ;//= new MockSubmissionDataFileWidget(new SimplePanel(), authorEmail);
		
	@Override
	public void gwtSetUp() throws Exception {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//create testModel and mockService to inject
		testModel = new SubmissionDataFileWidgetModel();
		
		//create test controller
		testController = new SubmissionDataFileWidgetController(testModel, mockOwner);
				
	}
	
	@Test
	public void testSetUpUploadSubmissionDataFile() {
				
		//call method under test
		testController.setUpUploadSubmissionDataFile(submissionLink, authorEmail);
		
		//test outcome
		assertEquals(SubmissionDataFileWidgetModel.STATUS_READY, testModel.getStatus());
		assertEquals(submissionLink, testModel.getSubmissionLink());
		
	}
		
	@Test
	public void testUpdateFileName() {
		
		//mock loaded state
		testController.setUpUploadSubmissionDataFile(submissionLink, authorEmail);
		
		//test data
		String fileName = "C:\\something.txt";
		
		//call method under test
		testController.updateFileName(fileName);
		
		//test outcome
		assertEquals(fileName, testModel.getFileName());
	}

	@Test
	public void testUpdateComment() {
		
		//mock loaded state
		testController.setUpUploadSubmissionDataFile(submissionLink, authorEmail);
		
		//test data
		String comment = "a comment";
		
		//call method under test
		testController.updateComment(comment);
		
		//test outcome
		assertEquals(comment, testModel.getComment());
	}
		
	
	@Test
	public void testCancelUploadSubmissionDataFile() {
	
		//call method under test
		testController.cancelUploadSubmissionDataFile();
		
		//test outcome
		assertEquals(SubmissionDataFileWidgetModel.STATUS_CANCELLED, testModel.getStatus());
		assertTrue(((MockSubmissionDataFileWidget)mockOwner).fireOnUserActionSubmissionDataFileUploadCancelled);
	}

	
	@Test
	public void testGetFormSubmitCompleteHandler() {
	
		//call method under test
		SubmitCompleteHandler handler = testController.getFormSubmitCompleteHandler();
		
		//test outcome
		assertNotNull(handler);	
		
	}

	@Test
	public void testOnSubmitComplete() {
		
		//create test object
		SubmissionDataFileCompleteHandler testHandler = testController.new SubmissionDataFileCompleteHandler();
		
		//call method under test
		testHandler.onSubmitComplete(null);
		
		//test outcome
		assertEquals(SubmissionDataFileWidgetModel.STATUS_UPLOADED, testModel.getStatus());
		assertEquals(testModel.getSubmissionLink(), ((MockSubmissionDataFileWidget)mockOwner).dataFileUploadedForSubmissionLink);
		
	}
	
	@Test
	public void testErrorUploadingSubmissionDataFile() {
	
		//TODO create submission error handler?
		
	}
		
	
	//mock SubmittedDataFileWidget
	private class MockSubmissionDataFileWidget extends SubmissionDataFileWidget {
		
		public MockSubmissionDataFileWidget(Panel givenCanvas, String authorEmail) {
			super(givenCanvas, authorEmail);
			// TODO Auto-generated constructor stub
		}

		boolean fireOnUserActionSubmissionDataFileUploadCancelled = false;
		String dataFileUploadedForSubmissionLink;

		@Override
		void fireOnUserActionSubmissionDataFileUploadCancelled() {
			fireOnUserActionSubmissionDataFileUploadCancelled = true;
		}
		
		@Override
		void fireOnUserActionSubmissionDataFileUploaded(String submissionLink) {
			dataFileUploadedForSubmissionLink = submissionLink;
		}
		
	}
	
}
