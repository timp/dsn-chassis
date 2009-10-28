/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;

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
@PrepareForTest({ApplicationWidgetModel.class})
public class TestApplicationWidgetController {

	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestApplicationWidgetController.class);
	}

	private ApplicationWidgetModel mockModel;
	private ApplicationWidgetController testController;
	
	@Before
	public void setUp() {
		
		mockModel = PowerMock.createMock(ApplicationWidgetModel.class);
		
		testController = new ApplicationWidgetController(mockModel);
		
	}
	
	@Test
	public void testDisplayCoordinatorPerspective() {
		
		//set up expectations
		mockModel.setStatus(ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayCoordinatorPerspective();
		
		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplaySubmitterPerspective() {
		
		//set up expectations
		mockModel.setStatus(ApplicationWidgetModel.STATUS_SUBMITTER_PERSPECTIVE);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displaySubmitterPerspective();
		
		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayCuratorPerspective() {
		
		//set up expectations
		mockModel.setStatus(ApplicationWidgetModel.STATUS_CURATOR_PERSPECTIVE);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayCuratorPerspective();
		
		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayGatekeeperPerspective() {
		
		//set up expectations
		mockModel.setStatus(ApplicationWidgetModel.STATUS_GATEKEEPER_PERSPECTIVE);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayGatekeeperPerspective();
		
		PowerMock.verify(mockModel);
		
	}
	
	@Test
	public void testDisplayUserPerspective() {
		
		//set up expectations
		mockModel.setStatus(ApplicationWidgetModel.STATUS_USER_PERSPECTIVE);
		PowerMock.expectLastCall();
		PowerMock.replay(mockModel);
		
		//call method under test
		testController.displayUserPerspective();
		
		PowerMock.verify(mockModel);
		
	}
		
	
}
