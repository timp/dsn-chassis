/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;


import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
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
		
		//create testController and inject testModel
		testModel = new StudyManagementWidgetModel();
		testController = new StudyManagementWidgetController(testModel);
		
		//Create testController, inject mockModel and a mock Service
		mockFactory = new MockStudyFactory();
		mockService = new MockAtomService(mockFactory);

		// bootstrap mock service with study feed
		((MockAtomService)mockService).createFeed(feedURL, "all studies");
		
		//instantiate a test renderer
		testRenderer = new StudyManagementWidgetDefaultRenderer(new SimplePanel(), new SimplePanel(), testController, mockService);
		
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
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_CREATE_STUDY);		

		// test outcome 
		assertTrue( (testRenderer.createStudyWidgetCanvas.getParent() != null)
					 && (testRenderer.createStudyWidgetCanvas.isVisible()) );
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY);		

		// test outcome 
		assertTrue( (testRenderer.viewStudyWidgetCanvas.getParent() != null)
					 && (testRenderer.viewStudyWidgetCanvas.isVisible()) );
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_VIEW_ALL_STUDIES);		

		// test outcome 
		assertTrue( (testRenderer.viewAllStudiesWidgetCanvas.getParent() != null)
					 && (testRenderer.viewAllStudiesWidgetCanvas.isVisible()) );
		
		testModel.setDisplayStatus(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY);		

		// test outcome 
		assertTrue( (testRenderer.editStudyWidgetCanvas.getParent() != null)
					 && (testRenderer.editStudyWidgetCanvas.isVisible()) );
		
	}
	
	@Test
	public void testOnNewStudySaved_StudyUpdateSuccess_editStudyUIClicked_viewStudyUIClicked() {
		
		//test data
		String title = "study foo";
		String summary = "summary foo";
		
		//create test Study Entry to view
		mockFactory = new MockStudyFactory();
		StudyEntry testStudy = mockFactory.createStudyEntry();
		testStudy.setTitle(title);
		testStudy.setSummary(summary);
		testStudy.addModule(ApplicationConstants.MODULE_CLINICAL);
		testStudy.addModule(ApplicationConstants.MODULE_IN_VITRO);
		
		// make sure test study is saved, entryURL is received, and test completes
		delayTestFinish(5000);
		
		//create test study
		Deferred<AtomEntry> deferredEntry = mockService.postEntry(feedURL, testStudy);
		
		//get entryURL and then perform test
		deferredEntry.addCallback(new Function<StudyEntry,StudyEntry>() {

			public StudyEntry apply(StudyEntry in) {
				
				testRenderer.onNewStudyCreated(in);
				assertEquals(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY, testModel.getDisplayStatus());
				
				testRenderer.onEditStudyUIClicked(in);
				assertEquals(StudyManagementWidgetModel.DISPLAYING_EDIT_STUDY, testModel.getDisplayStatus());
								
				testRenderer.onStudyUpdateSuccess(in);
				assertEquals(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY, testModel.getDisplayStatus());
				
				testRenderer.viewStudyUIClicked(in);
				assertEquals(StudyManagementWidgetModel.DISPLAYING_VIEW_STUDY, testModel.getDisplayStatus());
							
				
				//complete test
			    finishTest();				
				
				return in;
			}
			
		});
		
	}
	
	
}
