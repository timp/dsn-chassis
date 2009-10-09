/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

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
@PrepareForTest({StudyManagementWidgetModel.class})
public class TestStudyManagementWidgetController {

	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestStudyManagementWidgetController.class);
	}

	private StudyManagementWidgetModel mockModel;
	private StudyManagementWidgetController testController;
	
	@Before
	public void setUp() {
		
		mockModel = PowerMock.createMock(StudyManagementWidgetModel.class);
		
		testController = new StudyManagementWidgetController(mockModel);
		
	}
	
	
	@Test
	public void testDisplayCreateStudyWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, false);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayCreateStudyWidget();

		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayEditStudyWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayEditStudyWidget();

		PowerMock.verify(mockModel);
	}
	
	@Test
	public void testDisplayViewStudyWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayViewStudyWidget();

		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayViewAllStudiesWidget() {
		
		//set expectations
		mockModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, false);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//method under test
		testController.displayViewAllStudiesWidget();

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
