/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client.perspective;


import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;


/**
 * @author raok
 *
 */
public class GWTTestPerspectiveWidgetDefaultRenderer extends GWTTestCase {

	private PerspectiveWidgetModel testModel;
	private PerspectiveWidgetController testController;
	private PerspectiveWidgetDefaultRenderer testRenderer;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.application.Application";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//create testController and inject testModel
		testModel = new PerspectiveWidgetModel();
		testController = new PerspectiveWidgetController(testModel);
		
		//instantiate a test renderer
		testRenderer = new PerspectiveWidgetDefaultRenderer(new SimplePanel(), testController, "");
		
		//add as listener
		testModel.addListener(testRenderer);
		
	}
	
	@Test
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		//check no widgets on display
		//TODO change to home page once created
		assertFalse(testRenderer.activeChildWidgetDisplayCanvas.iterator().hasNext());
				
	}
	
	@Test
	public void testOnDisplayStatusChanged() {
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, PerspectiveWidgetModel.DISPLAYING_STUDY_MANAGEMENT_WIDGET);

		// test outcome 
		assertTrue( (testRenderer.studyManagmentWidgetDisplayCanvas.getParent() != null)
					 && (testRenderer.studyManagmentWidgetDisplayCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, PerspectiveWidgetModel.DISPLAYING_SUBMISSION_MANAGEMENT_WIDGET_DATA_ENTRY);

		// test outcome 
		assertTrue( (testRenderer.submissionManagmentWidgetDisplayCanvas.getParent() != null)
					 && (testRenderer.submissionManagmentWidgetDisplayCanvas.isVisible()) );
		
	}		
	
}
