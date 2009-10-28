/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetModel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestUploadSubmittedDataFileWidgetDefaultRenderer extends GWTTestCase {
	
	private SubmissionDataFileWidgetController mockController;
	private SubmissionDataFileWidgetDefaultRenderer testRenderer;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.UploadSubmissionDataFileWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//create mockController and inject into testRenderer
		mockController = new MockSubmissionDataFileWidgetController(null);
		
		testRenderer = new SubmissionDataFileWidgetDefaultRenderer(new SimplePanel(), mockController);
		
	}
	
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
				
		//check no values set
		assertEquals("", testRenderer.fileUI.getFilename());
		assertEquals("", testRenderer.commentUI.getValue());
		assertEquals("", testRenderer.submissionLinkHiddenField.getValue());
		assertEquals("", testRenderer.authorHiddenField.getValue());
		
		//check htmlFormPanel correctly setup
		assertEquals(TestConfigurationSetUp.testDataFileFeedURL, testRenderer.htmlFormPanel.getAction());
		assertEquals(FormPanel.ENCODING_MULTIPART, testRenderer.htmlFormPanel.getEncoding());
		assertEquals(FormPanel.METHOD_POST, testRenderer.htmlFormPanel.getMethod());
		
	}
	
	public void testOnCommentChanged() {
		
		//test data
		String comment = "a comment";
		
		// call method under test
		testRenderer.onCommentChanged(null, comment, true);		
		
		
		//test outcome
		assertEquals(comment, testRenderer.commentUI.getValue());
		
	}
	
	public void testCommentChanged_UI() {
		
		//test data
		String comment = "a comment";
		
		//call method under test
		testRenderer.commentUI.setValue(comment, true);
		
		//test outcome
		assertEquals(comment, ((MockSubmissionDataFileWidgetController)mockController).updateComment);
		
	}
	
	public void testOnFileNameChanged() {
		
		// functionality not required
		
	}
	
	public void testFileNameChanged_UI() {
		
		// unable to implement validation on fileName with creating a fileUpload widget that fires events. 		
		
	}
	
	public void testOnSubmissionLinkChanged() {
		
		//test data
		String link = "http://foo.com/submissions/submission1";
		
		// call method under test
		testRenderer.onSubmissionLinkChanged(null, link);		
		
		
		//test outcome
		assertEquals(link, testRenderer.submissionLinkHiddenField.getValue());
		
	}
	
	public void testOnAuthorChanged() {
		
		//test data
		String author = "bob@foo.com";
		
		// call method under test
		testRenderer.onAuthorChanged(null, author);		
		
		
		//test outcome
		assertEquals(author, testRenderer.authorHiddenField.getValue());
		
	}
	
	
	public void testCancelUploadSubmissionDataFileButton_UI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.cancelUploadSubmissionDataFileUI );
		
		// test outcome
		assertTrue(((MockSubmissionDataFileWidgetController)mockController).cancelUploadSubmissionDataFile);
		
	}
	
	
	public void testSaveNewSubmissionEntryButtonUI_onSubmissionEntryChanged_valid() {
				
		//call method under test
		testRenderer.onSubmittedDataFileFormChanged(true);
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.saveUploadSubmissionDataFileUI );
		
		// test outcome
		//TODO test
		
	}
	
	public void testSaveNewSubmissionEntryButtonUI_onSubmissionEntryChanged_invalid() {
		
		//call method under test
		testRenderer.onSubmittedDataFileFormChanged(false);
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.saveUploadSubmissionDataFileUI );
		
		// test outcome
		//TODO test
		
	}
	

	//Define mock controller
	private class MockSubmissionDataFileWidgetController extends SubmissionDataFileWidgetController {

		String updateComment;
		boolean cancelUploadSubmissionDataFile;
		String updateFileName;

		public MockSubmissionDataFileWidgetController(SubmissionDataFileWidgetModel model) {
			super(model, null);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void updateComment(String comment) {
			this.updateComment = comment;
		}
		
		@Override
		public void updateFileName(String fileName) {
			this.updateFileName = fileName;
		}
		
		
		@Override
		public void cancelUploadSubmissionDataFile() {
			this.cancelUploadSubmissionDataFile = true;
		}
		
		
	}
	
}
