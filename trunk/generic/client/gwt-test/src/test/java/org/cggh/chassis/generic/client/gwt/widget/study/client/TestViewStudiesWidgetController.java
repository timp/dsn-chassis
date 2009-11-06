/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

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
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetController.LoadStudiesByEntryURLsCallback;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetController.LoadStudiesByEntryURLsErrback;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetController.LoadStudyFeedCallback;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetController.LoadStudyFeedErrback;
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
//@PrepareForTest({ViewStudiesWidgetController.class, AtomServiceImpl.class, StudyFactoryImpl.class, 
//				 StudyQueryServiceImpl.class, ViewStudiesWidget.class, Deferred.class})
public class TestViewStudiesWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewStudiesWidgetController.class);
	}

//	private ViewStudiesWidgetController testController;
//	private ViewStudiesWidgetModel testModel;
//	private AtomService mockService;
//	private StudyQueryService mockStudyQueryService;
//	private StudyFactory mockFactory;
//	private ViewStudiesWidget mockWidget;
//	private MockStudyFactory testFactory = new MockStudyFactory();
//	private List<StudyEntry> testStudies;
	
	private String testStudyFeedURL = TestConfigurationSetUp.testStudyFeedURL;
	private String testStudyQueryServiceURL = TestConfigurationSetUp.testStudyQueryServiceURL;
		
	@Before
	public void setUp() throws Exception {
		
//		//setup ConfigurationBean
//		TestConfigurationSetUp.createTestConfiguration();
//		
//		//Create testController, inject mockModel and a mock Services
//		testModel = new ViewStudiesWidgetModel();
//		mockFactory = PowerMock.createMock(StudyFactoryImpl.class);
//		mockService = PowerMock.createMock(AtomServiceImpl.class);
//		mockStudyQueryService = PowerMock.createMock(StudyQueryServiceImpl.class);
//		
//		//Setup to use mock service and factory
//		PowerMock.expectNew(StudyFactoryImpl.class).andReturn((StudyFactoryImpl) mockFactory);
//		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
//		PowerMock.expectNew(StudyQueryServiceImpl.class, testStudyQueryServiceURL).andReturn((StudyQueryServiceImpl) mockStudyQueryService);
//		PowerMock.replay(StudyFactoryImpl.class, AtomServiceImpl.class, StudyQueryServiceImpl.class);
//		
//		//create mockWidget
//		mockWidget = PowerMock.createMock(ViewStudiesWidget.class);
//		
//		//instantiate test Object
//		testController = new ViewStudiesWidgetController(testModel, mockWidget);
//
//		
//		//create test Study Entries to view
//		StudyEntry testStudy1 = testFactory.createStudyEntry();
//		testStudy1.setTitle("study foo");
//		testStudy1.setSummary("summary foo");
//		testStudy1.addModule("module1");
//		testStudy1.addModule("module2");
//
//		StudyEntry testStudy2 = testFactory.createStudyEntry();
//		testStudy2.setTitle("study foo2");
//		testStudy2.setSummary("summary foo2");
//		testStudy1.addModule("module1");
//		testStudy1.addModule("module2");
//		
//		//test studies for mockFeed to return
//		testStudies = new ArrayList<StudyEntry>();
//		testStudies.add(testStudy1);
//		testStudies.add(testStudy2);
		
	}
	
	@Test
	public void testConstructor() {
		
//		assertNotNull(testController);
		
	}
		
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadStudiesByFeedURL_callback() {

//		//create mock Deffered object
//		Deferred<AtomFeed> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockService.getFeed(testStudyFeedURL)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		mockDeffered.addCallbacks(isA(LoadStudyFeedCallback.class), isA(LoadStudyFeedErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//
//		//call method under test		
//		testController.loadStudiesByFeedURL();
//		
//		PowerMock.verify(mockService);		
//		PowerMock.verify(mockDeffered);		
	}
	
	@Test
	public void testLoadStudyFeedCallback() {
		
				
//		//create mock Study Feed Entry to view
//		StudyFeed mockStudyFeed = createMock(StudyFeed.class);
//		
//		//set up expectations
//		expect(mockStudyFeed.getStudyEntries()).andReturn(testStudies);
//		expectLastCall().atLeastOnce();
//		replay(mockStudyFeed);
//
//		LoadStudyFeedCallback callback = testController.new LoadStudyFeedCallback();
//		
//		//call method under test
//		callback.apply(mockStudyFeed);
//		
//		//test outcome
//		assertEquals(testStudies, testModel.getStudyEntries());
//		assertEquals(ViewStudiesWidgetModel.STATUS_LOADED, testModel.getStatus());
//		
//		
//		verify(mockStudyFeed);
			
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testOnLoadStudiesByEntryURLs_callback() {
		
//		//test data
//		//test handling of relative entryURLs
//		String relEntryURL1 = "/study1";
//		String relEntryURL2 = "/study2";
//		Set<String> studyEntryURLS = new HashSet<String>();
//		studyEntryURLS.add(relEntryURL1);
//		studyEntryURLS.add(relEntryURL2);
//		
//		
//		//create mock Deffered object
//		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockService.getEntry(testStudyFeedURL + relEntryURL1)).andReturn(mockDeffered);
//		expect(mockService.getEntry(testStudyFeedURL + relEntryURL2)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		mockDeffered.addCallbacks(isA(LoadStudiesByEntryURLsCallback.class), isA(LoadStudiesByEntryURLsErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered).times(2);
//		PowerMock.replay(mockDeffered);
//		
//		
//		//call method under test		
//		testController.loadStudiesByEntryURLs(studyEntryURLS);
//				
//		//test outcome
//		PowerMock.verify(mockService);	
//		PowerMock.verify(mockDeffered);	
		
	}
	

	@Test
	public void testLoadStudyEntryURLCallback_success() {
		
//		List<StudyEntry> studyEntries = new ArrayList<StudyEntry>();
//		
//		LoadStudiesByEntryURLsCallback callback = testController.new LoadStudiesByEntryURLsCallback(studyEntries, 2);
//		
//		//call method under test
//		callback.apply(testStudies.get(0));
//		callback.apply(testStudies.get(1));
//		
//		//test outcome
//		assertEquals(testStudies, testModel.getStudyEntries());
//		assertEquals(ViewStudiesWidgetModel.STATUS_LOADED, testModel.getStatus());
					
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadStudiesByAuthorEmail_callback() {
		
//		//test data
//		String authorEmail = "foo@bar.com";
//
//		//create mock Deffered object
//		Deferred<StudyFeed> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockStudyQueryService.getStudiesByAuthorEmail(authorEmail)).andReturn(mockDeffered);
//		PowerMock.replay(mockStudyQueryService);
//		mockDeffered.addCallbacks(isA(LoadStudyFeedCallback.class), isA(LoadStudyFeedErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//		
//		//call method under test
//		testController.loadStudiesByAuthorEmail(authorEmail);
//
//		//test outcome
//		PowerMock.verify(mockStudyQueryService);	
//		PowerMock.verify(mockDeffered);	
	}

	
	@Test
	public void testOnViewStudyUIClicked() {
		
//		//test data
//		StudyEntry testStudy = testStudies.get(0);
//		
//		//set expectations on owner
//		mockWidget.onUserSelectStudy(testStudy);
//		PowerMock.replay(mockWidget, ViewStudiesWidget.class);
//		
//		//call method under test
//		testController.onUserSelectStudy(testStudy);
//		
//		PowerMock.verify(mockWidget, ViewStudiesWidget.class);
		
		
	}
	
}
