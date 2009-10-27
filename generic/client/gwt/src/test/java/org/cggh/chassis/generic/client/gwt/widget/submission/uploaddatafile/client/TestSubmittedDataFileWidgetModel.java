/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.uploaddatafile.client.SubmissionDataFileWidgetModelListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestSubmittedDataFileWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestSubmittedDataFileWidgetModel.class);
	}

	private SubmissionDataFileWidgetModel testModel;
	
	@Before
	public void setUp() {
		
		//Create object to use in all tests
		testModel = new SubmissionDataFileWidgetModel();		
			
	}
	
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), SubmissionDataFileWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), SubmissionDataFileWidgetModel.STATUS_READY);
		assertEquals(new Integer(2), SubmissionDataFileWidgetModel.STATUS_UPLOADING);
		assertEquals(new Integer(3), SubmissionDataFileWidgetModel.STATUS_UPLOADED);
		assertEquals(new Integer(4), SubmissionDataFileWidgetModel.STATUS_ERROR);
		assertEquals(new Integer(5), SubmissionDataFileWidgetModel.STATUS_CANCELLED);
		
	}
	
	
	@Test
	public void testInitialState() {
				
		// test initial state
		assertNull(testModel.getFileName());
		assertNull(testModel.getComment());
		assertNull(testModel.getSubmissionLink());
		assertEquals(SubmissionDataFileWidgetModel.STATUS_INITIAL, testModel.getStatus());
		
		
	}
		
		
	@Test
	public void testGettersSetters() {
								
		//test data
		String comment = "comment foo";
		String fileName = "C:\\something.txt";
		String submissionLink = "http://www.foo.com/submissions/submission1";
		String author = "bob@foo.com";
		Integer status = SubmissionDataFileWidgetModel.STATUS_UPLOADED;
				
				
		//call methods under test
		testModel.setComment(comment);
		testModel.setFileName(fileName);
		testModel.setSubmissionLink(submissionLink);
		testModel.setAuthor(author);
		testModel.setStatus(status);
		
		
		// test outcome
		assertEquals(comment, testModel.getComment());
		assertEquals(fileName, testModel.getFileName());
		assertEquals(submissionLink, testModel.getSubmissionLink());
		assertEquals(author, testModel.getAuthor());
		assertEquals(status, testModel.getStatus());
		
	}
	
	
	
	@Test
	public void testOnCommentChanged() {
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = "A valid comment.";
		
		SubmissionDataFileWidgetModelListener listener = createMock(SubmissionDataFileWidgetModelListener.class);
		
		//set up expectations
		listener.onCommentChanged(null, invalid, false);
		listener.onSubmittedDataFileFormChanged(isA(Boolean.class));
		listener.onCommentChanged(invalid, valid, true);
		listener.onSubmittedDataFileFormChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setComment(invalid);
		testModel.setComment(valid);
		
		verify(listener);
		
	}
	
	
	
	@Test
	public void testOnFileNameChanged() {
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = "C:\\something.txt";
		
		SubmissionDataFileWidgetModelListener listener = createMock(SubmissionDataFileWidgetModelListener.class);
		
		//set up expectations
		listener.onFileNameChanged(null, invalid, false);
		listener.onSubmittedDataFileFormChanged(isA(Boolean.class));
		listener.onFileNameChanged(invalid, valid, true);
		listener.onSubmittedDataFileFormChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setFileName(invalid);
		testModel.setFileName(valid);
		
		verify(listener);
		
	}
	
	
	
	@Test
	public void testOnSubmissionLinkChanged() {
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = "http://www.foo.com/submissions/submission1";
		
		SubmissionDataFileWidgetModelListener listener = createMock(SubmissionDataFileWidgetModelListener.class);
		
		//set up expectations
		listener.onSubmissionLinkChanged(null, invalid);
		listener.onSubmissionLinkChanged(invalid, valid);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setSubmissionLink(invalid);
		testModel.setSubmissionLink(valid);
		
		verify(listener);
		
	}
	
	
	
	@Test
	public void testOnAuthorChanged() {
		
		//test data
		String invalid = "";
		String valid = "bob@example.com";
		
		SubmissionDataFileWidgetModelListener listener = createMock(SubmissionDataFileWidgetModelListener.class);
		
		//set up expectations
		listener.onAuthorChanged(null, invalid);
		listener.onAuthorChanged(invalid, valid);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAuthor(invalid);
		testModel.setAuthor(valid);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnSubmittedDataFileFormChanged() {
		
		//test data
		String validComment = "a comment";
		String validFileName = "C:\\something.txt";
		
		//create Nice mock to ignore events tested elsewhere
		SubmissionDataFileWidgetModelListener listener = createNiceMock(SubmissionDataFileWidgetModelListener.class);
		
		//set up expectations
		listener.onSubmittedDataFileFormChanged(false);
		listener.onSubmittedDataFileFormChanged(true);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setFileName(validFileName);
		testModel.setComment(validComment);
		
		verify(listener);
		
	}
		
	@Test
	public void testOnStatusChanged() {
		
		//test data
		Integer status1 = SubmissionDataFileWidgetModel.STATUS_ERROR;
		Integer status2 = SubmissionDataFileWidgetModel.STATUS_UPLOADED;
		
		SubmissionDataFileWidgetModelListener listener = createMock(SubmissionDataFileWidgetModelListener.class);
		
		//set up expectations
		listener.onStatusChanged(SubmissionDataFileWidgetModel.STATUS_INITIAL, status1);
		listener.onStatusChanged(status1, status2);	
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setStatus(status1);
		testModel.setStatus(status2);
		
		verify(listener);
		
	}
	
}
