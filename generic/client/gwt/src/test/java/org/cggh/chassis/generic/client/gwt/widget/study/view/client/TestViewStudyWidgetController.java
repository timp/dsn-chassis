/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetController.GetStudyEntryCallback;
import org.cggh.chassis.generic.twisted.client.Deferred;
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
@PrepareForTest({ViewStudyWidget.class})
public class TestViewStudyWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewStudyWidgetController.class);
	}

	private ViewStudyWidgetController testController;
	private ViewStudyWidgetModel mockModel;
	private AtomService mockService;
	private StudyFactory mockFactory;
	private ViewStudyWidget mockWidget;
	private StudyEntry testStudy;
		
	@Before
	public void setUp() {
		
		//Create testController, inject mockModel and a mock Service
		mockModel = new ViewStudyWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = createMock(AtomService.class);

		//create mock widget
		mockWidget = PowerMock.createPartialMock(ViewStudyWidget.class, "editStudyUIClicked");
				
		testController = new ViewStudyWidgetController(mockModel, mockService, mockWidget);
		

		//create test Study Entry to view
		testStudy = mockFactory.createStudyEntry();
		testStudy.setTitle("foo");
		testStudy.setSummary("bar");
		testStudy.addModule(ApplicationConstants.MODULE_CLINICAL);
		testStudy.addModule(ApplicationConstants.MODULE_IN_VITRO);
	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
		
	@Test
	public void testLoadStudyByEntryURL_callback() {
		
		//test data
		String entryURL = "http://example.com/studies/study_1";
				
		//set up expectations
		expect(mockService.getEntry(entryURL)).andReturn(new Deferred<AtomEntry>());
		replay(mockService);

				
		Deferred<AtomEntry> deffered =  testController.getStudyEntry(entryURL);
		
		assertNotNull(deffered);
		
		verify(mockService);		
	}
	
	@Test
	public void testLoadStudyByEntryURL_success() {
		
		
		GetStudyEntryCallback callback = testController.new GetStudyEntryCallback();
		
		//call method under test
		callback.apply(testStudy);
		
		//test outcome
		assertEquals(ViewStudyWidgetModel.STATUS_LOADED, mockModel.getStatus());
		assertEquals(testStudy, mockModel.getStudyEntry());
		
		
	}
	
	@Test
	public void testLoadStudyEntry() {
		
				
		//call method under test
		testController.loadStudyEntry(testStudy);
		
		//test outcome
		assertEquals(ViewStudyWidgetModel.STATUS_LOADED, mockModel.getStatus());
		assertEquals(testStudy, mockModel.getStudyEntry());
		
		
	}
	
	@Test
	public void testOnEditStudyUIClicked() {
		

		mockModel.setStudyEntry(testStudy);
		
		//set expectations on owner
		mockWidget.editStudyUIClicked(isA(StudyEntry.class));
		PowerMock.replay(mockWidget, ViewStudyWidget.class);
		
		//call method under test
		testController.onEditStudyUIClicked();
		
		PowerMock.verify(mockWidget, ViewStudyWidget.class);
		
	}
	
}
