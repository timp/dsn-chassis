/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import junit.framework.JUnit4TestAdapter;
import static org.easymock.EasyMock.*;

import org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.PerspectiveWidgetModel;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestApplicationWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestApplicationWidgetModel.class);
	}


	private ApplicationWidgetModel testModel;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		//create test object
		testModel = new ApplicationWidgetModel();
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertEquals(PerspectiveWidgetModel.DISPLAYING_NONE, testModel.getStatus());
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), ApplicationWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), ApplicationWidgetModel.STATUS_SUBMITTER_PERSPECTIVE);
		assertEquals(new Integer(2), ApplicationWidgetModel.STATUS_GATEKEEPER_PERSPECTIVE);
		assertEquals(new Integer(3), ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE);
		assertEquals(new Integer(4), ApplicationWidgetModel.STATUS_CURATOR_PERSPECTIVE);
		assertEquals(new Integer(5), ApplicationWidgetModel.STATUS_USER_PERSPECTIVE);
				
	}

	@Test
	public void testStatusChanged() {
		
		ApplicationWidgetModelListener listener = createMock(ApplicationWidgetModelListener.class);
		
		//set up expectations
		listener.onPerspectiveChanged(ApplicationWidgetModel.STATUS_INITIAL, ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE);
		replay(listener);
		
		//register listener with model
		testModel.addListener(listener);
		
		//call method under test
		testModel.setStatus(ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE);
		
		//test outcome
		assertEquals(ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE, testModel.getStatus());

		verify(listener);
	}
	
}
