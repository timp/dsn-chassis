/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController.LoadSubmissionEntryCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController.LoadSubmissionEntryErrback;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController.SaveOrUpdateSubmissionEntryCallback;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionController.SaveOrUpdateSubmissionEntryErrback;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
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
@PrepareForTest({SubmissionController.class, Deferred.class, SubmissionFactoryImpl.class, AtomServiceImpl.class})
public class TestSubmissionController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestSubmissionController.class);
	}

	private SubmissionController testController;
	private MockSubmissionFactory testFactory = new MockSubmissionFactory();
	private SubmissionModel testModel;
	private AtomService mockService;
	private SubmissionFactory mockFactory;
	private String submissionFeedURL = TestConfigurationSetUp.testSubmissionFeedURL;
	private SubmissionEntry testSubmissionEntry;
	private String testAuthorEmail = "foo@bar.com";
		
	@Before
	public void setUp() throws Exception {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//create testModel and mockService to inject
		testModel = new SubmissionModel();
		mockService = PowerMock.createMock(AtomServiceImpl.class);
		mockFactory = PowerMock.createMock(SubmissionFactoryImpl.class);

		//Setup to use mock service and factory
		PowerMock.expectNew(SubmissionFactoryImpl.class).andReturn((SubmissionFactoryImpl) mockFactory);
		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
		PowerMock.replay(AtomServiceImpl.class, SubmissionFactoryImpl.class);
				
		//create test controller
		testController = new SubmissionController(testModel, null);
				
		//create test data
		testSubmissionEntry = testFactory.createSubmissionEntry();
		testSubmissionEntry.setTitle("existing title");
		testSubmissionEntry.setSummary("existing summary");
		testSubmissionEntry.addStudyLink("http://foo.com/studies/study1");
		
	}
		
	@Test
	public void testSetUpNewSubmission() {
		
		//set up expectations
		expect(mockFactory.createSubmissionEntry()).andReturn(testFactory.createSubmissionEntry());
		expect(mockFactory.createAuthor()).andReturn(testFactory.createAuthor());
		PowerMock.replay(mockFactory);
		
		//call method under test
		testController.setUpNewSubmission(testAuthorEmail);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testAuthorEmail, testModel.getSubmissionEntry().getAuthors().iterator().next().getEmail());
		
		PowerMock.verify(mockFactory);
	}
	
	@Test
	public void testLoadSubmission() {
		
		//call method under test
		testController.loadSubmissionEntry(testSubmissionEntry);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testSubmissionEntry, testModel.getSubmissionEntry());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadSubmissionByURL() {
		
		//test data
		String submissionEntryURL = "http://foo.com/submissions/submission1";
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.getEntry(submissionEntryURL)).andReturn(mockDeffered);
		PowerMock.replay(mockService);
		mockDeffered.addCallbacks(isA(LoadSubmissionEntryCallback.class), isA(LoadSubmissionEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		
		//call method under test
		testController.loadSubmissionEntryByURL(submissionEntryURL);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_LOADING, testModel.getStatus());
		
		PowerMock.verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@Test
	public void testLoadSubmissionByURL_success() {
		
		//object under test
		LoadSubmissionEntryCallback callback = testController.new LoadSubmissionEntryCallback();
		
		//call method under test
		SubmissionEntry returnedSubmissionEntry = callback.apply(testSubmissionEntry);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testSubmissionEntry, testModel.getSubmissionEntry());
		assertEquals(returnedSubmissionEntry, testSubmissionEntry);		
		
	}
	
	@Test
	public void testLoadSubmissionByURL_error() {
		
		//test data
		Throwable throwable = new Throwable();
		
		//object under test
		LoadSubmissionEntryErrback errback = testController.new LoadSubmissionEntryErrback();
		
		//call method under test
		Throwable returnedThrowable = errback.apply(throwable);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_ERROR, testModel.getStatus());
		assertEquals(returnedThrowable, throwable);		
		
	}
	
	@Test
	public void testUpdateAuthors() {

		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//test data
		Set<AtomAuthor> testAuthors = new HashSet<AtomAuthor>();
		AtomAuthor testAtomAuthor = testFactory.createAuthor();
		testAtomAuthor.setEmail("foo@bar.com");
		testAuthors.add(testAtomAuthor);
		
		//call method under test
		testController.updateAuthors(testAuthors);
		
		//test outcome
		assertEquals(testAuthors, testModel.getAuthors());		
		
	}
	
	@Test
	public void testUpdateTitle(){
		
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//test data
		String title = "Submission foo";
		
		//call method under test
		testController.updateTitle(title);
		
		//test outcome
		assertEquals(title, testModel.getTitle());
	}

	@Test
	public void testUpdateSummary(){
		
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//test data
		String summary = "Summary foo";
		
		//call method under test
		testController.updateSummary(summary);
		
		//test outcome
		assertEquals(summary, testModel.getSummary());
	}

	
	@Test
	public void testAddStudyLink_and_testRemoveStudyLink() {
	
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//test data
		String studyEntryURL = "http://foo.com/studies/study1";
		String studyEntryURL2 = "http://foo.com/studies/study2";
		
		//call method under test
		testController.addStudyLink(studyEntryURL);
		testController.addStudyLink(studyEntryURL2);
		
		//test outcome
		assertTrue(testModel.getStudyLinks().contains(studyEntryURL));
		assertTrue(testModel.getStudyLinks().contains(studyEntryURL2));
		
		//call other method under test
		testController.removeStudyLink(studyEntryURL2);
	
		//test outcome
		assertTrue(testModel.getStudyLinks().contains(studyEntryURL));
		assertFalse(testModel.getStudyLinks().contains(studyEntryURL2));
		
	}
	
	@Test
	public void testUpdateModules() {

		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//test data
		Set<String> modulesSet1 = new HashSet<String>();
		modulesSet1.add("module1");		
		
		//call method under test
		testController.updateModules(modulesSet1);
		
		//test outcome
		assertEquals(modulesSet1, testModel.getModules());		
		
	}

	@Test
	public void testCancelCreateStudy() {
	
		//call method under test
		testController.cancelCreateOrUpdateSubmissionEntry();
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_CANCELLED, testModel.getStatus());
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveNewSubmissionEntry() {
						
		//set up test
		testSetUpNewSubmission();
				
		//inject testSubmissionEntry
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.postEntry(submissionFeedURL, testSubmissionEntry)).andReturn(mockDeffered);
		PowerMock.replay(mockService);
		mockDeffered.addCallbacks(isA(SaveOrUpdateSubmissionEntryCallback.class), isA(SaveOrUpdateSubmissionEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		
		//call method under test
		testController.saveNewSubmissionEntry();
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_SAVING, testModel.getStatus());
		
		PowerMock.verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateSubmissionEntry() {
		
		//test data
		//test handling of relative entryURLs
		String relEntryURL = "/submission1";
		
		//create mockSubmissionEntry and AtomLink for this test
		SubmissionEntry mockSubmissionEntry = createNiceMock(SubmissionEntry.class);
		AtomLink mockAtomLink = createMock(AtomLink.class);
						
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockSubmissionEntry.getEditLink()).andReturn(mockAtomLink);
		//return something nice for call to studyLinks, and modules
		expect(mockSubmissionEntry.getStudyLinks()).andReturn(new ArrayList<AtomLink>());
		expect(mockSubmissionEntry.getModules()).andReturn(new ArrayList<String>());
		expect(mockSubmissionEntry.getAuthors()).andReturn(new ArrayList<AtomAuthor>());
		replay(mockSubmissionEntry);
		
		expect(mockAtomLink.getHref()).andReturn(relEntryURL);
		replay(mockAtomLink);
		
		expect(mockService.putEntry(relEntryURL, mockSubmissionEntry)).andReturn(mockDeffered);
		PowerMock.replay(mockService);
		
		mockDeffered.addCallbacks(isA(SaveOrUpdateSubmissionEntryCallback.class), isA(SaveOrUpdateSubmissionEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		

		//mock loaded state
		testModel.setSubmissionEntry(mockSubmissionEntry);
		
		//call method under test
		testController.updateSubmissionEntry();
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_SAVING, testModel.getStatus());

		verify(mockSubmissionEntry);
		verify(mockAtomLink);
		PowerMock.verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@Test
	public void testSaveOrUpdate_success() {
		
		//object under test
		SaveOrUpdateSubmissionEntryCallback callback = testController.new SaveOrUpdateSubmissionEntryCallback();
		
		//call method under test
		SubmissionEntry returnedSubmissionEntry = callback.apply(testSubmissionEntry);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_SAVED, testModel.getStatus());
		assertEquals(testSubmissionEntry, testModel.getSubmissionEntry());
		assertEquals(returnedSubmissionEntry, testSubmissionEntry);		
		
	}
	
	@Test
	public void testSaveOrUpdate_error() {
		
		//test data
		Throwable throwable = new Throwable();
		
		//object under test
		SaveOrUpdateSubmissionEntryErrback errback = testController.new SaveOrUpdateSubmissionEntryErrback();
		
		//call method under test
		Throwable returnedThrowable = errback.apply(throwable);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_ERROR, testModel.getStatus());
		assertEquals(returnedThrowable, throwable);		
		
	}
	
	@Test
	public void testPubSubCreateAPI() throws Exception {

		//Setup to use mock service and factory
		PowerMock.resetAll();
		PowerMock.expectNew(SubmissionFactoryImpl.class).andReturn((SubmissionFactoryImpl) mockFactory);
		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
		PowerMock.replay(AtomServiceImpl.class, SubmissionFactoryImpl.class);
		
		//create mock Widget and inject into new testController using Create API
		SubmissionControllerPubSubCreateAPI mockListener = createMock(SubmissionControllerPubSubCreateAPI.class);
		SubmissionControllerCreateAPI testCreateController = new SubmissionController(testModel, mockListener);
						
		//set up expectations
		mockListener.newSubmissionSaved(testSubmissionEntry);
		mockListener.cancelCreateNewSubmissionEntry();
		replay(mockListener);
		
		//set up test
		SaveOrUpdateSubmissionEntryCallback callback = ((SubmissionController)testCreateController).new SaveOrUpdateSubmissionEntryCallback();
				
		//call methods under test
		callback.apply(testSubmissionEntry);
		testCreateController.cancelCreateOrUpdateSubmissionEntry();
		
		
		verify(mockListener);		
		
	}
	

	@Test
	public void testPubSubEditAPI() throws Exception {
		
		//Setup to use mock service and factory
		PowerMock.resetAll();
		PowerMock.expectNew(SubmissionFactoryImpl.class).andReturn((SubmissionFactoryImpl) mockFactory);
		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
		PowerMock.replay(SubmissionFactoryImpl.class,AtomServiceImpl.class);

		//create mock Widget and inject into new testController using Create API
		SubmissionControllerPubSubEditAPI mockListener = createMock(SubmissionControllerPubSubEditAPI.class);
		SubmissionControllerEditAPI testEditController = new SubmissionController(testModel, mockListener);
						
		//set up expectations
		mockListener.onSubmissionEntryUpdated(testSubmissionEntry);
		mockListener.onUserActionEditSubmissionEntryCancelled();
		replay(mockListener);
		
		//set up test
		SaveOrUpdateSubmissionEntryCallback callback = ((SubmissionController)testEditController).new SaveOrUpdateSubmissionEntryCallback();
				
		//call methods under test
		callback.apply(testSubmissionEntry);
		testEditController.cancelCreateOrUpdateSubmissionEntry();
		
		
		verify(mockListener);		
		
	}
	

	@Test
	public void testPubSubViewAPI() throws Exception {
		
		//Setup to use mock service and factory
		PowerMock.resetAll();
		PowerMock.expectNew(SubmissionFactoryImpl.class).andReturn((SubmissionFactoryImpl) mockFactory);
		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
		PowerMock.replay(SubmissionFactoryImpl.class,AtomServiceImpl.class);

		//create mock Widget and inject into new testController using Create API
		SubmissionControllerPubSubViewAPI mockListener = createMock(SubmissionControllerPubSubViewAPI.class);
		SubmissionControllerViewAPI testViewController = new SubmissionController(testModel, mockListener);
						
		//set up expectations
		mockListener.onUserActionEditSubmission(testSubmissionEntry);
		replay(mockListener);
		
		//set up test
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		testViewController.onUserActionEditThisSubmission();
		
		verify(mockListener);		
		
	}
	
}
