/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestPerspectiveWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestPerspectiveWidgetModel.class);
	}

	private PerspectiveWidgetModel testModel;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new PerspectiveWidgetModel();
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertEquals(PerspectiveWidgetModel.DISPLAYING_NONE, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), PerspectiveWidgetModel.DISPLAYING_NONE);
		assertEquals(new Integer(1), PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET);
		assertEquals(new Integer(2), PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY);
		assertEquals(new Integer(3), PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET);
		assertEquals(new Integer(4), PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY);
				
	}
		
	@Test
	public void testStatusChanged() {
	
		PerspectiveWidgetModelListener listener = createMock(PerspectiveWidgetModelListener.class);
		
		// set up expectations
		listener.onDisplayStatusChanged(PerspectiveWidgetModel.DISPLAYING_NONE, PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET);
		listener.onDisplayStatusChanged(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET, PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		testModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET);
		testModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET);
		
		verify(listener);
	}
	
	@Test
	public void testStatusChanged_UserMightLoseChanges() {
		
		//set up test
		testModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY);
		
		//create mock
		PerspectiveWidgetModelListener mockListener = createMock(PerspectiveWidgetModelListener.class);
		
		//set up expectations
		mockListener.userMightLoseChanges(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET);
		replay(mockListener);
		
		//register listener with model
		testModel.addListener(mockListener);
		
		//call method under test
		testModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET);
		
		verify(mockListener);
		
		
	}
		
	@Test
	public void testStatusChanged_UserMightLoseChanges_confirmed() {
		
		//set up test
		testModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY);
		
		//create mock
		PerspectiveWidgetModelListener mockListener = createMock(PerspectiveWidgetModelListener.class);
		
		//set up expectations
		mockListener.onDisplayStatusChanged(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET_DATA_ENTRY, PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET);
		replay(mockListener);
		
		//register listener with model
		testModel.addListener(mockListener);
		
		//call method under test
		testModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET, true);
		
		verify(mockListener);
		
		
		
		
	}
	
	
	
}
