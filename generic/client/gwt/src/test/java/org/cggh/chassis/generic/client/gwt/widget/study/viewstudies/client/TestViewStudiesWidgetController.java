/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetController.GetStudyFeedCallback;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetController.LoadStudiesByEntryURLsCallback;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetController.LoadStudiesByEntryURLsErrback;
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
@PrepareForTest({ViewStudiesWidget.class, Deferred.class})
public class TestViewStudiesWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewStudiesWidgetController.class);
	}

	private ViewStudiesWidgetController testController;
	private ViewStudiesWidgetModel testModel;
	private AtomService mockService;
	private StudyFactory mockFactory;
	private ViewStudiesWidget mockWidget;
	private List<StudyEntry> testStudies;
	
	//empty URL because MockEntries already carry the feedURL within their editLink
	String feedURL = "";
		
	@Before
	public void setUp() {
		
		//Create testController, inject mockModel and a mock Service
		testModel = new ViewStudiesWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = createMock(AtomService.class);
		
		//create mockWidget
		mockWidget = PowerMock.createPartialMock(ViewStudiesWidget.class, "onUserSelectStudy");
		
		testController = new ViewStudiesWidgetController(testModel, mockService, mockWidget, feedURL);

		
		//create test Study Entries to view
		StudyEntry testStudy1 = mockFactory.createStudyEntry();
		testStudy1.setTitle("study foo");
		testStudy1.setSummary("summary foo");
		testStudy1.addModule("module1");
		testStudy1.addModule("module2");

		StudyEntry testStudy2 = mockFactory.createStudyEntry();
		testStudy2.setTitle("study foo2");
		testStudy2.setSummary("summary foo2");
		testStudy1.addModule("module1");
		testStudy1.addModule("module2");
		
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
	public void testLoadStudiesByFeedURL_callback() {
						
		//set up expectations
		expect(mockService.getFeed(feedURL)).andReturn(new Deferred<AtomFeed>());
		replay(mockService);

				
		Deferred<AtomFeed> deffered = testController.getStudiesByFeedURL();
		
		assertNotNull(deffered);
		
		verify(mockService);		
	}
	
	@Test
	public void testLoadStudiesByFeedURL_success() {
		
				
		//create mock Study Feed Entry to view
		StudyFeed mockStudyFeed = createMock(StudyFeed.class);
		
		//set up expectations
		expect(mockStudyFeed.getStudyEntries()).andReturn(testStudies);
		expectLastCall().atLeastOnce();
		replay(mockStudyFeed);

		GetStudyFeedCallback callback = testController.new GetStudyFeedCallback();
		
		//call method under test
		callback.apply(mockStudyFeed);
		
		//test outcome
		assertEquals(testStudies, testModel.getStudyEntries());
		assertEquals(ViewStudiesWidgetModel.STATUS_LOADED, testModel.getStatus());
		
		
		verify(mockStudyFeed);
			
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testOnLoadStudiesByEntryURLs_callback() {
		
		//test data
		String entryURL1 = "http://example.com/studies/study1";
		String entryURL2 = "http://example.com/studies/study2";
		Set<String> studyEntryURLS = new HashSet<String>();
		studyEntryURLS.add(entryURL1);
		studyEntryURLS.add(entryURL2);
		
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.getEntry(entryURL1)).andReturn(mockDeffered);
		expect(mockService.getEntry(entryURL2)).andReturn(mockDeffered);
		replay(mockService);
		mockDeffered.addCallbacks(isA(LoadStudiesByEntryURLsCallback.class), isA(LoadStudiesByEntryURLsErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered).times(2);
		PowerMock.replay(mockDeffered);
		
		
		//call method under test		
		testController.loadStudiesByEntryURLs(studyEntryURLS);
				
		//test outcome
		verify(mockService);	
		PowerMock.verify(mockDeffered);	
		
	}
	

	@Test
	public void testLoadStudyEntryURLCallback_success() {
		
		List<StudyEntry> studyEntries = new ArrayList<StudyEntry>();
		
		LoadStudiesByEntryURLsCallback callback = testController.new LoadStudiesByEntryURLsCallback(studyEntries, 2);
		
		//call method under test
		callback.apply(testStudies.get(0));
		callback.apply(testStudies.get(1));
		
		//test outcome
		assertEquals(testStudies, testModel.getStudyEntries());
		assertEquals(ViewStudiesWidgetModel.STATUS_LOADED, testModel.getStatus());
					
	}
	
	@Test
	public void testOnViewStudyUIClicked() {
		
		//test data
		StudyEntry testStudy = testStudies.get(0);
		
		//set expectations on owner
		mockWidget.onUserSelectStudy(testStudy);
		PowerMock.replay(mockWidget, ViewStudiesWidget.class);
		
		//call method under test
		testController.onUserSelectStudy(testStudy);
		
		PowerMock.verify(mockWidget, ViewStudiesWidget.class);
		
		
	}
	
}
