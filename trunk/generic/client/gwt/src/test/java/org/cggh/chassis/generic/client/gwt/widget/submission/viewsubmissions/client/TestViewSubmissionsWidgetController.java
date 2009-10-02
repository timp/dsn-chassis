/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

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

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetController.GetSubmissionFeedCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetController.LoadSubmissionsByEntryURLsCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetController.LoadSubmissionsByEntryURLsErrback;
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
@PrepareForTest({ViewSubmissionsWidget.class, Deferred.class})
public class TestViewSubmissionsWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewSubmissionsWidgetController.class);
	}

	private ViewSubmissionsWidgetController testController;
	private ViewSubmissionsWidgetModel testModel;
	private AtomService mockService;
	private SubmissionFactory mockFactory;
	private ViewSubmissionsWidget mockWidget;
	private List<SubmissionEntry> testSubmissions;
	
	//empty URL because MockEntries already carry the feedURL within their editLink
	String feedURL = "";
		
	@Before
	public void setUp() {
		
		//Create testController, inject mockModel and a mock Service
		testModel = new ViewSubmissionsWidgetModel();
		mockFactory = new MockSubmissionFactory();
		mockService = createMock(AtomService.class);
		
		//create mockWidget
		mockWidget = PowerMock.createPartialMock(ViewSubmissionsWidget.class, "onUserSelectSubmission");
		
		testController = new ViewSubmissionsWidgetController(testModel, mockService, mockWidget, feedURL);

		
		//create test Submission Entries to view
		SubmissionEntry testSubmission1 = mockFactory.createSubmissionEntry();
		testSubmission1.setTitle("submission foo");
		testSubmission1.setSummary("summary foo");
		testSubmission1.addModule("module1");
		testSubmission1.addModule("module2");

		SubmissionEntry testSubmission2 = mockFactory.createSubmissionEntry();
		testSubmission2.setTitle("submission foo2");
		testSubmission2.setSummary("summary foo2");
		testSubmission1.addModule("module1");
		testSubmission1.addModule("module2");
		
		//test submissions for mockFeed to return
		testSubmissions = new ArrayList<SubmissionEntry>();
		testSubmissions.add(testSubmission1);
		testSubmissions.add(testSubmission2);
		
	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
		
	@Test
	public void testLoadSubmissionsByFeedURL_callback() {
						
		//set up expectations
		expect(mockService.getFeed(feedURL)).andReturn(new Deferred<AtomFeed>());
		replay(mockService);

				
		Deferred<AtomFeed> deffered = testController.getSubmissionsByFeedURL();
		
		assertNotNull(deffered);
		
		verify(mockService);		
	}
	
	@Test
	public void testLoadSubmissionsByFeedURL_success() {
		
				
		//create mock Submission Feed Entry to view
		SubmissionFeed mockSubmissionFeed = createMock(SubmissionFeed.class);
		
		//set up expectations
		expect(mockSubmissionFeed.getSubmissionEntries()).andReturn(testSubmissions);
		expectLastCall().atLeastOnce();
		replay(mockSubmissionFeed);

		GetSubmissionFeedCallback callback = testController.new GetSubmissionFeedCallback();
		
		//call method under test
		callback.apply(mockSubmissionFeed);
		
		//test outcome
		assertEquals(testSubmissions, testModel.getSubmissionEntries());
		assertEquals(ViewSubmissionsWidgetModel.STATUS_LOADED, testModel.getStatus());
		
		
		verify(mockSubmissionFeed);
			
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testOnLoadSubmissionsByEntryURLs_callback() {
		
		//test data
		String entryURL1 = "http://example.com/submissions/submission1";
		String entryURL2 = "http://example.com/submissions/submission2";
		Set<String> submissionEntryURLS = new HashSet<String>();
		submissionEntryURLS.add(entryURL1);
		submissionEntryURLS.add(entryURL2);
		
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.getEntry(entryURL1)).andReturn(mockDeffered);
		expect(mockService.getEntry(entryURL2)).andReturn(mockDeffered);
		replay(mockService);
		mockDeffered.addCallbacks(isA(LoadSubmissionsByEntryURLsCallback.class), isA(LoadSubmissionsByEntryURLsErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered).times(2);
		PowerMock.replay(mockDeffered);
		
		
		//call method under test		
		testController.loadSubmissionsByEntryURLs(submissionEntryURLS);
				
		//test outcome
		verify(mockService);	
		PowerMock.verify(mockDeffered);	
		
	}
	

	@Test
	public void testLoadSubmissionEntryURLsCallback_success() {
		
		List<SubmissionEntry> submissionEntries = new ArrayList<SubmissionEntry>();
		
		LoadSubmissionsByEntryURLsCallback callback = testController.new LoadSubmissionsByEntryURLsCallback(submissionEntries, 2);
		
		//call method under test
		callback.apply(testSubmissions.get(0));
		callback.apply(testSubmissions.get(1));
		
		//test outcome
		assertEquals(testSubmissions, testModel.getSubmissionEntries());
		assertEquals(ViewSubmissionsWidgetModel.STATUS_LOADED, testModel.getStatus());
					
	}
	
	@Test
	public void testOnViewSubmissionUIClicked() {
		
		//test data
		SubmissionEntry testSubmission = testSubmissions.get(0);
		
		//set expectations on owner
		mockWidget.onUserSelectSubmission(testSubmission);
		PowerMock.replay(mockWidget, ViewSubmissionsWidget.class);
		
		//call method under test
		testController.onUserSelectSubmission(testSubmission);
		
		PowerMock.verify(mockWidget, ViewSubmissionsWidget.class);
		
		
	}
	
}
