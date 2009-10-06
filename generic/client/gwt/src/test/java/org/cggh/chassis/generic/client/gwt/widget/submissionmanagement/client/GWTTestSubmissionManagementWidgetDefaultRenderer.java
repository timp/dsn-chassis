/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;


import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
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
	private MockSubmissionFactory mockFactory;
	private MockAtomService mockService;
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
		
		//create testController and inject testModel
		testModel = new SubmissionManagementWidgetModel();
		testController = new SubmissionManagementWidgetController(testModel);
		
		//Create testController, inject mockModel and a mock Service
		mockFactory = new MockSubmissionFactory();
		mockService = new MockAtomService(mockFactory);

		// bootstrap mock service with submission feed
		((MockAtomService)mockService).createFeed(feedURL, "all submissions");

		//Create modules test data
		Map<String, String> testModules = new HashMap<String, String>();
		testModules.put("module1Id", "module1Label");
		testModules.put("module2Id", "module2Label");

		//Set up ConfigurationBean with test values
		ConfigurationBean.useUnitTestConfiguration = true;
		ConfigurationBean.testModules = testModules;
		
		//instantiate a test renderer
		testRenderer = new SubmissionManagementWidgetDefaultRenderer(new SimplePanel(), new SimplePanel(), testController, "");
		
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
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testClickViewAllSubmissionsUI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.displayViewAllSubmissionsUI );
		
		//test outcome
		assertEquals(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testOnDisplayStatusChanged() {
		
		// call method under test
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_CREATE_STUDY);		

		// test outcome 
		assertTrue( (testRenderer.createSubmissionWidgetCanvas.getParent() != null)
					 && (testRenderer.createSubmissionWidgetCanvas.isVisible()) );
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_STUDY);		

		// test outcome 
		assertTrue( (testRenderer.viewSubmissionWidgetCanvas.getParent() != null)
					 && (testRenderer.viewSubmissionWidgetCanvas.isVisible()) );
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);		

		// test outcome 
		assertTrue( (testRenderer.viewAllSubmissionsWidgetCanvas.getParent() != null)
					 && (testRenderer.viewAllSubmissionsWidgetCanvas.isVisible()) );
		
		testModel.setDisplayStatus(SubmissionManagementWidgetModel.DISPLAYING_EDIT_STUDY);		

		// test outcome 
		assertTrue( (testRenderer.editSubmissionWidgetCanvas.getParent() != null)
					 && (testRenderer.editSubmissionWidgetCanvas.isVisible()) );
		
	}
		
	
}
