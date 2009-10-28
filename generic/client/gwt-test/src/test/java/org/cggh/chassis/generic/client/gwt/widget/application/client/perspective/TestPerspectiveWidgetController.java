/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;

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
@PrepareForTest({PerspectiveWidgetModel.class})
public class TestPerspectiveWidgetController {

	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestPerspectiveWidgetController.class);
	}

	private PerspectiveWidgetModel mockModel;
	private PerspectiveWidgetController testController;
	
	@Before
	public void setUp() {
		
		mockModel = PowerMock.createMock(PerspectiveWidgetModel.class);
		
		testController = new PerspectiveWidgetController(mockModel);
		
	}
	
	@Test
	public void testDisplayStudyManagementWidget() {
		
		//set up expectations
		mockModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET, true);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayStudyManagementWidget(false, true);
		
		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplaySubmissionManagementWidget() {
		
		//set up expectations
		mockModel.setDisplayStatus(PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY, true);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displaySubmissionManagementWidget(true, true);
		
		PowerMock.verify(mockModel);
	}
		
	
}
