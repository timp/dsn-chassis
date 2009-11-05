/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;


import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionManagementWidgetModel;
import org.junit.Test;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;


/**
 * @author raok
 *
 */
public class GWTTestSubmissionManagementWidgetDefaultRenderer extends GWTTestCase {

	private SubmissionManagementWidgetModel testModel;
	private SubmissionManagementWidgetController testController;
	private SubmissionManagementWidgetDefaultRenderer testRenderer;
	private SubmissionFactory mockFactory;
	private SubmissionPersistenceService mockService;
	private String feedURL = "http://www.foo.com/submissions";

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.SubmissionManagementWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//Create testController, inject mockModel and a mock Service
		mockFactory = new SubmissionFactory();
		mockService = new SubmissionPersistenceService();

		//create testController and inject testModel
		testModel = new SubmissionManagementWidgetModel(new SubmissionManagementWidget());
		testController = new SubmissionManagementWidgetController(null, testModel);
		
		//instantiate a test renderer
		testRenderer = new SubmissionManagementWidgetDefaultRenderer(null, testController);
		
		//add as listener
		testModel.addListener(testRenderer);
		
	}
	
	@Test
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		//display menu, but do not display any main widgets initially
		// TODO test?
		
	}
	
	@Test
	public void testClickCreateSubmissionUI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.displayCreateSubmissionUI );
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testClickViewAllSubmissionsUI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.displayViewAllSubmissionsUI );
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testOnDisplayStatusChanged() {
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, SubmissionManagementWidgetModel.DISPLAYING_CREATE_SUBMISSION);

		// test outcome 
		assertTrue( (testRenderer.createSubmissionWidget.getParent() != null)
					 && (testRenderer.createSubmissionWidget.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, SubmissionManagementWidgetModel.DISPLAYING_VIEW_SUBMISSION);

		// test outcome 
		assertTrue( (testRenderer.viewSubmissionWidget.getParent() != null)
					 && (testRenderer.viewSubmissionWidget.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_SUBMISSIONS);	

		// test outcome 
		assertTrue( (testRenderer.viewAllSubmissionsWidget.getParent() != null)
					 && (testRenderer.viewAllSubmissionsWidget.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);

		// test outcome 
		assertTrue( (testRenderer.editSubmissionWidget.getParent() != null)
					 && (testRenderer.editSubmissionWidget.isVisible()) );
		
	}
	
	@Test
	public void testUserMightLoseChanges() {
		
		//call method under test
		testRenderer.userMightLoseChanges(SubmissionManagementWidgetModel.DISPLAYING_EDIT_SUBMISSION);
		

		//test outcome 
		assertTrue( testRenderer.confirmLoseChangesPopup.isShowing() );
		
	}
	
}
