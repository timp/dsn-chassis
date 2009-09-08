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

/**
 * @author raok
 *
 */
public class TestViewAllStudiesWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewAllStudiesWidgetController.class);
	}

	private ViewAllStudiesWidgetController testController;
	private ViewAllStudiesWidgetModel testModel;
	private AtomService mockService;
	private StudyFactory mockFactory;
		
	@Before
	public void setUp() {
		
		//Create testController, inject mockModel and a mock Service
		testModel = new ViewAllStudiesWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = createMock(AtomService.class);
				
		testController = new ViewAllStudiesWidgetController(testModel, mockService);

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
		
		//test data
		String title1 = "study foo";
		String summary1 = "summary foo";
		String title2 = "study foo2";
		String summary2 = "summary foo2";

		//create test Study Entries to view
		StudyEntry testStudy1 = mockFactory.createStudyEntry();
		testStudy1.setTitle(title1);
		testStudy1.setSummary(summary1);
		testStudy1.addModule(CreateStudyWidgetModel.MODULE_CLINICAL);
		testStudy1.addModule(CreateStudyWidgetModel.MODULE_IN_VITRO);

		StudyEntry testStudy2 = mockFactory.createStudyEntry();
		testStudy2.setTitle(title2);
		testStudy2.setSummary(summary2);
		testStudy2.addModule(CreateStudyWidgetModel.MODULE_PHARMACOLOGY);
		testStudy2.addModule(CreateStudyWidgetModel.MODULE_MOLECULAR);
		
		//test studies for mockFeed to return
		List<StudyEntry> testStudies = new ArrayList<StudyEntry>();
		testStudies.add(testStudy1);
		testStudies.add(testStudy2);
		
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
	
}
