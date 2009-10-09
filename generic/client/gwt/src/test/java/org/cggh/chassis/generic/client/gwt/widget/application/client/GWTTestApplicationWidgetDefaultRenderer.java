/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;


import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;


/**
 * @author raok
 *
 */
public class GWTTestApplicationWidgetDefaultRenderer extends GWTTestCase {

	private ApplicationWidgetModel testModel;
	private ApplicationWidgetController testController;
	private ApplicationWidgetDefaultRenderer testRenderer;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.application.Application";
	}

	@Override
	protected void gwtSetUp() {
		
		//create testController and inject testModel
		testModel = new ApplicationWidgetModel();
		testController = new ApplicationWidgetController(testModel);
		
		//instantiate a test renderer
		testRenderer = new ApplicationWidgetDefaultRenderer(new SimplePanel(), null);
		
		//add as listener
		testModel.addListener(testRenderer);
		
	}
	
	@Test
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		//check user details widget displayed, but not perspective widget
		assertTrue(testRenderer.userDetailsWidgetCanvas.iterator().hasNext());
		assertFalse(testRenderer.activePerspectiveWidgetPanel.iterator().hasNext());
				
	}
	
	@Test
	public void testOnUserRefreshed() {
		
		//test data
		String userId = "alice@example.com";
		
		//call method under test
		testRenderer.onUserRefreshed(userId);
		
		//TODO testable?
		
		
	}
	
	@Test
	public void testOnPerspectiveChanged() {
		
		// call method under test
		testRenderer.onPerspectiveChanged(null, ApplicationWidgetModel.STATUS_SUBMITTER_PERSPECTIVE);

		// test outcome 
		assertTrue( (testRenderer.submitterPerspectiveWidgetCanvas.getParent() != null)
					 && (testRenderer.submitterPerspectiveWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onPerspectiveChanged(null, ApplicationWidgetModel.STATUS_CURATOR_PERSPECTIVE);

		// test outcome 
		assertTrue( (testRenderer.curatorPerspectiveWidgetCanvas.getParent() != null)
					 && (testRenderer.curatorPerspectiveWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onPerspectiveChanged(null, ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE);

		// test outcome 
		assertTrue( (testRenderer.coordinatorPerspectiveWidgetCanvas.getParent() != null)
					 && (testRenderer.coordinatorPerspectiveWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onPerspectiveChanged(null, ApplicationWidgetModel.STATUS_GATEKEEPER_PERSPECTIVE);

		// test outcome 
		assertTrue( (testRenderer.gatekeeperPerspectiveWidgetCanvas.getParent() != null)
					 && (testRenderer.gatekeeperPerspectiveWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onPerspectiveChanged(null, ApplicationWidgetModel.STATUS_USER_PERSPECTIVE);

		// test outcome 
		assertTrue( (testRenderer.userPerspectiveWidgetCanvas.getParent() != null)
					 && (testRenderer.userPerspectiveWidgetCanvas.isVisible()) );
		
	}		
	
}
