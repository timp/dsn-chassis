/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestSubmissionManagementWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestSubmissionManagementWidgetModel.class);
	}

	private SubmissionManagementWidgetModel testModel;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new SubmissionManagementWidgetModel();
		
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
		assertEquals(new Integer(1), SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		assertEquals(new Integer(2), SubmissionManagementWidgetModel.DISPLAYING_VIEW_STUDY);
		assertEquals(new Integer(3), SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		assertEquals(new Integer(4), SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);
				
	}
		
	@Test
	public void testStatusChanged() {
	
		SubmissionManagementWidgetModelListener listener = createMock(SubmissionManagementWidgetModelListener.class);
		
		// set up expectations
		listener.onDisplayStatusChanged(SubmissionManagementWidgetModel.DISPLAYING_NONE, SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		listener.onDisplayStatusChanged(SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY, SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		
		verify(listener);
	}
}
