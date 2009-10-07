/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
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
@PrepareForTest({StudyManagementWidget.class})
public class TestStudyManagementWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestStudyManagementWidgetModel.class);
	}

	private StudyManagementWidgetModel testModel;
	private StudyManagementWidget mockOwner;
	
	@Before
	public void setUp() {

		//create mockOwner
		mockOwner = PowerMock.createMock(StudyManagementWidget.class);
				
		//create test object
		testModel = new StudyManagementWidgetModel(mockOwner);
		
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
		mockOwner.displayStatusChanged(true);
		replay(listener);
		PowerMock.replay(mockOwner);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		
		verify(listener);
		PowerMock.verify(mockOwner);
	}
		
	@Test
	public void testStatusChanged_UserMightLoseChanges() {
	
		//set up test
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		
		StudyManagementWidgetModelListener listener = createMock(StudyManagementWidgetModelListener.class);
		
		// set up expectations
		listener.onUserMightLoseChanges(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		
		verify(listener);
	}
		
	@Test
	public void testStatusChanged_UserMightLoseChanges_confirmed() {
	
		//set up test
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		
		StudyManagementWidgetModelListener listener = createMock(StudyManagementWidgetModelListener.class);
		
		// set up expectations
		listener.onDisplayStatusChanged(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY, StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, true);
		
		verify(listener);
	}
	
}
