/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQuery;
import org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQueryService;
import org.cggh.chassis.generic.atom.datafile.client.protocol.impl.DataFileQueryServiceImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client.ViewSubmissionDataFilesWidgetController.LoadDataFileFeedCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client.ViewSubmissionDataFilesWidgetController.LoadDataFileFeedErrback;
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
@PrepareForTest({ViewSubmissionDataFilesWidgetController.class, ViewSubmissionDataFilesWidget.class, Deferred.class,
	             DataFileQueryService.class, DataFileQuery.class})
public class TestViewSubmissionDataFilesWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewSubmissionDataFilesWidgetController.class);
	}

	private ViewSubmissionDataFilesWidgetController testController;
	private ViewSubmissionDataFilesWidgetModel testModel;
	private DataFileQueryServiceImpl mockDataFileQueryService;
	private ViewSubmissionDataFilesWidget mockWidget;
	private MockAtomFactory testFactory = new MockAtomFactory();
	private List<AtomEntry> testDataFileEntries;

	private String testSubmissionDataFilesQueryServiceURL = TestConfigurationSetUp.testDataFileQueryServiceURL;
		
	@Before
	public void setUp() throws Exception {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
				
		//Create testController, inject mockModel and a mock Service
		testModel = new ViewSubmissionDataFilesWidgetModel();
		mockDataFileQueryService = PowerMock.createMock(DataFileQueryServiceImpl.class);
		
		//Setup to use mock service and factory
		PowerMock.expectNew(DataFileQueryServiceImpl.class, testSubmissionDataFilesQueryServiceURL).andReturn(mockDataFileQueryService);
		PowerMock.replay(DataFileQueryServiceImpl.class);
		
		//create mockWidget
		mockWidget = PowerMock.createMock(ViewSubmissionDataFilesWidget.class);
		
		testController = new ViewSubmissionDataFilesWidgetController(testModel, mockWidget);

		
		//create test Submission Entries to view
		AtomEntry testFileEntry1 = testFactory.createEntry();
		testFileEntry1.setTitle("file.txt");
		testFileEntry1.setSummary("summary foo");

		AtomEntry testFileEntry2 = testFactory.createEntry();
		testFileEntry2.setTitle("file.txt");
		testFileEntry2.setSummary("summary foo");
		
		//test Submissions for mockFeed to return
		testDataFileEntries = new ArrayList<AtomEntry>();
		testDataFileEntries.add(testFileEntry1);
		testDataFileEntries.add(testFileEntry2);
		
	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadDataFilesBySubmissionLink_callback() throws Exception {
		
		//test data
		String submissionLink = "http://foo.com/submission1";
				
		//create mock Deffered object
		Deferred<AtomFeed> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//create mock query
		DataFileQuery mockQuery = PowerMock.createMock(DataFileQuery.class);
		
		//set up expectations
		PowerMock.expectNew(DataFileQuery.class).andReturn(mockQuery);
		mockQuery.setSubmissionUrl(submissionLink);
		PowerMock.expectLastCall().once();
		expect(mockDataFileQueryService.query(mockQuery)).andReturn(mockDeffered);
		PowerMock.replay(mockDataFileQueryService);
		mockDeffered.addCallbacks(isA(LoadDataFileFeedCallback.class), isA(LoadDataFileFeedErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockQuery, DataFileQuery.class);
		PowerMock.replay(mockDeffered);
				
		//call method under test
		testController.loadDataFilesBySubmissionLink(submissionLink);
				
		//test outcome
		PowerMock.verify(mockDataFileQueryService);	
		PowerMock.verify(mockQuery, DataFileQuery.class);	
		PowerMock.verify(mockDeffered);	
		
	}
	
	@Test
	public void testLoadSubmissionFeedCallback() {
				
		//create mock DataFile Feed Entry to view
		AtomFeed mockDataFileFeed = createMock(AtomFeed.class);
		
		//set up expectations
		expect(mockDataFileFeed.getEntries()).andReturn(testDataFileEntries);
		expectLastCall().atLeastOnce();
		replay(mockDataFileFeed);

		LoadDataFileFeedCallback callback = testController.new LoadDataFileFeedCallback();
		
		//call method under test
		callback.apply(mockDataFileFeed);
		
		//test outcome
		assertEquals(testDataFileEntries, testModel.getFileDataAtomEntries());
		assertEquals(ViewSubmissionDataFilesWidgetModel.STATUS_LOADED, testModel.getStatus());
		
		
		verify(mockDataFileFeed);
			
	}
	
	
}
