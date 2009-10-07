/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;


import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
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
public class GWTTestStudyManagementWidgetDefaultRenderer extends GWTTestCase {

	private StudyManagementWidgetModel testModel;
	private StudyManagementWidgetController testController;
	private StudyManagementWidgetDefaultRenderer testRenderer;
	private MockStudyFactory mockFactory;
	private MockAtomService mockService;
	private String feedURL = "http://www.foo.com/studies";

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.studymanagement.StudyManagementWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//Create testController, inject mockModel and a mock Service
		mockFactory = new MockStudyFactory();
		mockService = new MockAtomService(mockFactory);

		// bootstrap mock service with study feed
		((MockAtomService)mockService).createFeed(feedURL, "all studies");

		//Create modules test data
		Map<String, String> testModules = new HashMap<String, String>();
		testModules.put("module1Id", "module1Label");
		testModules.put("module2Id", "module2Label");
		
		//Set up ConfigurationBean with test values
		ConfigurationBean.useUnitTestConfiguration = true;
		ConfigurationBean.testModules = testModules;
		
		//create testController and inject testModel
		testModel = new StudyManagementWidgetModel(new StudyManagementWidget(new SimplePanel(), ""));
		testController = new StudyManagementWidgetController(testModel);
		
		//instantiate a test renderer
		testRenderer = new StudyManagementWidgetDefaultRenderer(new SimplePanel(), new SimplePanel(), testController, "");
		
		//add as listener
		testModel.addListener(testRenderer);
		
	}
	
	@Test
	public void testInitialState() {
		
		assertNotNull(testRenderer);
				
	}
	
	@Test
	public void testClickCreateStudyUI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.displayCreateStudyUI );
		
		//test outcome
		assertEquals(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testClickViewAllStudiesUI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.displayViewAllStudiesUI );
		
		//test outcome
		assertEquals(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES, testModel.getDisplayStatus());
		
	}
	
	@Test
	public void testOnDisplayStatusChanged() {
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);

		// test outcome 
		assertTrue( (testRenderer.createStudyWidgetCanvas.getParent() != null)
					 && (testRenderer.createStudyWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY);

		// test outcome 
		assertTrue( (testRenderer.viewStudyWidgetCanvas.getParent() != null)
					 && (testRenderer.viewStudyWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);

		// test outcome 
		assertTrue( (testRenderer.viewStudiesWidgetCanvas.getParent() != null)
					 && (testRenderer.viewStudiesWidgetCanvas.isVisible()) );
		
		// call method under test
		testRenderer.onDisplayStatusChanged(null, StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);

		// test outcome 
		assertTrue( (testRenderer.editStudyWidgetCanvas.getParent() != null)
					 && (testRenderer.editStudyWidgetCanvas.isVisible()) );
		
	}
	
	@Test
	public void testUserMightLoseChanges() {
		
		//call method under test
		testRenderer.onUserMightLoseChanges(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);
		
		//test outcome 
		assertTrue( testRenderer.confirmLoseChangesPopup.isShowing() );
			
		
	}
	
	
}
