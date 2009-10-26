/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class TestSubmissionManagementWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestSubmissionManagementWidgetModel.class);
	}

	private SubmissionManagementWidgetModel testModel;
	private SubmissionManagementWidget mockOwner;
	
	@Before
	public void setUp() {
		
		//create mockOwner
		mockOwner = PowerMock.createMock(SubmissionManagementWidget.class);
		
		//create test object
		testModel = new SubmissionManagementWidgetModel(mockOwner);
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_NONE, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), SubmissionManagementWidgetModel.DISPLAYING_NONE);
		assertEquals(new Integer(1), SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION);
		assertEquals(new Integer(2), SubmissionManagementWidgetModel.DISPLAYING_VIEW_SUBMISSION);
		assertEquals(new Integer(3), SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);
		assertEquals(new Integer(4), SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS);
				
	}
		
	@Test
	public void testStatusChanged() {
	
		SubmissionManagementWidgetModelListener listener = createMock(SubmissionManagementWidgetModelListener.class);
		
		// set up expectations
		listener.onDisplayStatusChanged(SubmissionManagementWidgetModel.DISPLAYING_NONE, SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION);
		mockOwner.fireOnDisplayStatusChanged(true);
		replay(listener);
		PowerMock.replay(mockOwner);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION);
		
		verify(listener);
		PowerMock.verify(mockOwner);
	}
		
	@Test
	public void testStatusChanged_UserMightLoseChanges() {
	
		//set up test
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION);
		
		SubmissionManagementWidgetModelListener listener = createMock(SubmissionManagementWidgetModelListener.class);
		
		// set up expectations
		listener.userMightLoseChanges(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);
		
		verify(listener);
	}
		
	@Test
	public void testStatusChanged_UserMightLoseChanges_confirmed() {
	
		//set up test
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);
		
		SubmissionManagementWidgetModelListener listener = createMock(SubmissionManagementWidgetModelListener.class);
		
		// set up expectations
		listener.onDisplayStatusChanged(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION, SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS, true);
		
		verify(listener);
	}
	
	@Test
	public void testReset() {
		
		SubmissionManagementWidgetModelListener listener = createMock(SubmissionManagementWidgetModelListener.class);
		
		// set up expectations, reset silently
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.reset();
		
		verify(listener);
	}
}
