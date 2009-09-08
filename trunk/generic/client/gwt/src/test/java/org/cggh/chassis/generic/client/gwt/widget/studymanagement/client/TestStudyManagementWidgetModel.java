/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestStudyManagementWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestStudyManagementWidgetModel.class);
	}

	private StudyManagementWidgetModel testModel;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new StudyManagementWidgetModel();
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertEquals(StudyManagementWidgetModel.DISPLAYING_NONE, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), StudyManagementWidgetModel.DISPLAYING_NONE);
		assertEquals(new Integer(1), StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		assertEquals(new Integer(2), StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY);
		assertEquals(new Integer(3), StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		assertEquals(new Integer(4), StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);
				
	}
		
	@Test
	public void testStatusChanged() {
	
		StudyManagementWidgetModelListener listener = createMock(StudyManagementWidgetModelListener.class);
		
		// set up expectations
		listener.onDisplayStatusChanged(StudyManagementWidgetModel.DISPLAYING_NONE, StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		listener.onDisplayStatusChanged(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		
		verify(listener);
	}
}
