/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

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

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetController.LoadSubmissionFeedCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetController.LoadSubmissionFeedErrback;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetController.LoadSubmissionsByEntryUrlsCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetController.LoadSubmissionsByEntryUrlsErrback;
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
//@PrepareForTest({ViewSubmissionsWidgetController.class, ViewSubmissionsWidget.class, Deferred.class,
//	             SubmissionFactoryImpl.class, AtomServiceImpl.class, SubmissionQueryServiceImpl.class})
public class TestViewSubmissionsWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewSubmissionsWidgetController.class);
	}
//
//	private ViewSubmissionsWidgetController testController;
//	private ViewSubmissionsWidgetModel testModel;
//	private AtomService mockService;
//	private SubmissionQueryServiceImpl mockSubmissionQueryService;
//	private SubmissionFactory mockFactory;
//	private ViewSubmissionsWidget mockWidget;
//	private MockSubmissionFactory testFactory = new MockSubmissionFactory();
//	private List<SubmissionEntry> testSubmissions;
//	
//	//empty URL because MockEntries already carry the feedURL within their editLink
//	private String testSubmissionCollectionUrl = TestConfigurationSetUp.testSubmissionCollectionUrl;
//	private String testSubmissionQueryServiceURL = TestConfigurationSetUp.testSubmissionQueryServiceURL;
//		
//	@Before
//	public void setUp() throws Exception {
//		
//		//setup ConfigurationBean
//		TestConfigurationSetUp.createTestConfiguration();
//				
//		//Create testController, inject mockModel and a mock Service
//		testModel = new ViewSubmissionsWidgetModel();
//		mockFactory = PowerMock.createMock(SubmissionFactoryImpl.class);
//		mockService = PowerMock.createMock(AtomServiceImpl.class);
//		mockSubmissionQueryService = PowerMock.createMock(SubmissionQueryServiceImpl.class);
//		
//		//Setup to use mock service and factory
//		PowerMock.expectNew(SubmissionFactoryImpl.class).andReturn((SubmissionFactoryImpl) mockFactory);
//		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
//		PowerMock.expectNew(SubmissionQueryServiceImpl.class, testSubmissionQueryServiceURL).andReturn(mockSubmissionQueryService);
//		PowerMock.replay(AtomServiceImpl.class, SubmissionFactoryImpl.class, SubmissionQueryServiceImpl.class);
//		
//		//create mockWidget
//		mockWidget = PowerMock.createMock(ViewSubmissionsWidget.class);
//		
//		testController = new ViewSubmissionsWidgetController(testModel, mockWidget);
//
//		
//		//create test Submission Entries to view
//		SubmissionEntry testSubmission1 = testFactory.createSubmissionEntry();
//		testSubmission1.setTitle("submission foo");
//		testSubmission1.setSummary("summary foo");
//		testSubmission1.addModule("module1");
//		testSubmission1.addModule("module2");
//
//		SubmissionEntry testSubmission2 = testFactory.createSubmissionEntry();
//		testSubmission2.setTitle("submission foo2");
//		testSubmission2.setSummary("summary foo2");
//		testSubmission1.addModule("module1");
//		testSubmission1.addModule("module2");
//		
//		//test submissions for mockFeed to return
//		testSubmissions = new ArrayList<SubmissionEntry>();
//		testSubmissions.add(testSubmission1);
//		testSubmissions.add(testSubmission2);
//		
//	}
//	
//	@Test
//	public void testConstructor() {
//		
//		assertNotNull(testController);
//		
//	}
//		
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testLoadSubmissionsByCollectionUrl_callback() {
//
//		//create mock Deffered object
//		Deferred<AtomFeed> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockService.getFeed(testSubmissionCollectionUrl)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		mockDeffered.addCallbacks(isA(LoadSubmissionFeedCallback.class), isA(LoadSubmissionFeedErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//
//		//call method under test		
//		testController.loadSubmissionsByCollectionUrl();
//				
//		PowerMock.verify(mockService);		
//		PowerMock.verify(mockDeffered);	
//	}
//	
//	@Test
//	public void testLoadSubmissionFeedCallback() {
//		
//				
////		//create mock Submission Feed Entry to view
////		SubmissionFeed mockSubmissionFeed = createMock(SubmissionFeed.class);
////		
////		//set up expectations
////		expect(mockSubmissionFeed.getSubmissionEntries()).andReturn(testSubmissions);
////		expectLastCall().atLeastOnce();
////		replay(mockSubmissionFeed);
////
////		LoadSubmissionFeedCallback callback = testController.new LoadSubmissionFeedCallback();
////		
////		//call method under test
////		callback.apply(mockSubmissionFeed);
////		
////		//test outcome
////		assertEquals(testSubmissions, testModel.getSubmissionEntries());
////		assertEquals(ViewSubmissionsWidgetModel.STATUS_LOADED, testModel.getStatus());
////		
////		
////		verify(mockSubmissionFeed);
//			
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testOnLoadSubmissionsByEntryURLs_callback() {
//		
//		//test data
//		//test handling of relative entryURLs
//		String relEntryURL1 = "/submission1";
//		String relEntryURL2 = "/submission2";
//		Set<String> submissionEntryURLS = new HashSet<String>();
//		submissionEntryURLS.add(relEntryURL1);
//		submissionEntryURLS.add(relEntryURL2);
//		
//		
//		//create mock Deffered object
//		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockService.getEntry(testSubmissionCollectionUrl + relEntryURL1)).andReturn(mockDeffered);
//		expect(mockService.getEntry(testSubmissionCollectionUrl + relEntryURL2)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		mockDeffered.addCallbacks(isA(LoadSubmissionsByEntryURLsCallback.class), isA(LoadSubmissionsByEntryURLsErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered).times(2);
//		PowerMock.replay(mockDeffered);
//		
//		
//		//call method under test		
//		testController.loadSubmissionsByEntryURLs(submissionEntryURLS);
//				
//		//test outcome
//		PowerMock.verify(mockService);	
//		PowerMock.verify(mockDeffered);	
//		
//	}
//	
//
//	@Test
//	public void testLoadSubmissionEntryURLsCallback_success() {
//		
////		List<SubmissionEntry> submissionEntries = new ArrayList<SubmissionEntry>();
////		
////		LoadSubmissionsByEntryURLsCallback callback = testController.new LoadSubmissionsByEntryURLsCallback(submissionEntries, 2);
////		
////		//call method under test
////		callback.apply(testSubmissions.get(0));
////		callback.apply(testSubmissions.get(1));
////		
////		//test outcome
////		assertEquals(testSubmissions, testModel.getSubmissionEntries());
////		assertEquals(ViewSubmissionsWidgetModel.STATUS_LOADED, testModel.getStatus());
//					
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testLoadSubmissionsByAuthorEmail() {
//		
//		//test data
//		String authorEmail = "foo@bar.com";
//				
//		//create mock Deffered object
//		Deferred<SubmissionFeed> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockSubmissionQueryService.getSubmissionsByAuthorEmail(authorEmail)).andReturn(mockDeffered);
//		PowerMock.replay(mockSubmissionQueryService);
//		mockDeffered.addCallbacks(isA(LoadSubmissionFeedCallback.class), isA(LoadSubmissionFeedErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//				
//		//call method under test
//		testController.loadSubmissionsByAuthorEmail(authorEmail);
//				
//		//test outcome
//		PowerMock.verify(mockSubmissionQueryService);	
//		PowerMock.verify(mockDeffered);	
//		
//	}
//	
//	
//	@Test
//	public void testOnViewSubmissionUIClicked() {
//		
////		//test data
////		SubmissionEntry testSubmission = testSubmissions.get(0);
////		
////		//set expectations on owner
////		mockWidget.onUserSelectSubmission(testSubmission);
////		PowerMock.replay(mockWidget, ViewSubmissionsWidget.class);
////		
////		//call method under test
////		testController.onUserSelectSubmission(testSubmission);
////		
////		PowerMock.verify(mockWidget, ViewSubmissionsWidget.class);
//		
//		
//	}
	
}
