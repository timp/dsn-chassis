/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetController.GetStudyFeedEntryCallback;
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
@PrepareForTest({ViewAllStudiesWidget.class})
public class TestViewAllStudiesWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewAllStudiesWidgetController.class);
	}

	private ViewAllStudiesWidgetController testController;
	private ViewAllStudiesWidgetModel testModel;
	private AtomService mockService;
	private StudyFactory mockFactory;
	private ViewAllStudiesWidget mockWidget;
	private List<StudyEntry> testStudies;
		
	@Before
	public void setUp() {
		
		//Create testController, inject mockModel and a mock Service
		testModel = new ViewAllStudiesWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = createMock(AtomService.class);
		
		//create mockWidget
		mockWidget = PowerMock.createPartialMock(ViewAllStudiesWidget.class, "viewStudyUIClicked");
		
		testController = new ViewAllStudiesWidgetController(testModel, mockService, mockWidget);

		
		//create test Study Entries to view
		StudyEntry testStudy1 = mockFactory.createStudyEntry();
		testStudy1.setTitle("study foo");
		testStudy1.setSummary("summary foo");
		testStudy1.addModule(CreateStudyWidgetModel.MODULE_CLINICAL);
		testStudy1.addModule(CreateStudyWidgetModel.MODULE_IN_VITRO);

		StudyEntry testStudy2 = mockFactory.createStudyEntry();
		testStudy2.setTitle("study foo2");
		testStudy2.setSummary("summary foo2");
		testStudy2.addModule(CreateStudyWidgetModel.MODULE_PHARMACOLOGY);
		testStudy2.addModule(CreateStudyWidgetModel.MODULE_MOLECULAR);
		
		//test studies for mockFeed to return
		testStudies = new ArrayList<StudyEntry>();
		testStudies.add(testStudy1);
		testStudies.add(testStudy2);
		
	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
		
	@Test
	public void testLoadStudies_callback() {
		
		//test data
		String feedURL = "http://example.com/studies";
				
		//set up expectations
		expect(mockService.getFeed(feedURL)).andReturn(new Deferred<AtomFeed>());
		replay(mockService);

				
		Deferred<AtomFeed> deffered = testController.getStudiesByFeedURL(feedURL);
		
		assertNotNull(deffered);
		
		verify(mockService);		
	}
	
	@Test
	public void testLoadStudies_success() {
		
				
		//create mock Study Feed Entry to view
		StudyFeed mockStudyFeed = createMock(StudyFeed.class);
		
		//set up expectations
		expect(mockStudyFeed.getStudyEntries()).andReturn(testStudies);
		replay(mockStudyFeed);

		GetStudyFeedEntryCallback callback = testController.new GetStudyFeedEntryCallback();
		
		//call method under test
		callback.apply(mockStudyFeed);
		
		//test outcome
		assertEquals(testStudies, testModel.getStudyEntries());
		assertEquals(ViewAllStudiesWidgetModel.STATUS_LOADED, testModel.getStatus());
		
		
		verify(mockStudyFeed);
			
	}
	
	@Test
	public void testOnViewStudyUIClicked() {
		
		//test data
		StudyEntry testStudy = testStudies.get(0);
		
		//set expectations on owner
		mockWidget.onViewStudyUIClicked(testStudy);
		PowerMock.replay(mockWidget, ViewAllStudiesWidget.class);
		
		//call method under test
		testController.onViewStudyUIClicked(testStudy);
		
		PowerMock.verify(mockWidget, ViewAllStudiesWidget.class);
		
		
	}
	
}
