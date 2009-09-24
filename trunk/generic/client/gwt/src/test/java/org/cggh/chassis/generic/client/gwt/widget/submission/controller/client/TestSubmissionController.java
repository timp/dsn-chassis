/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
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
@PrepareForTest({Deferred.class})
public class TestSubmissionController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestSubmissionController.class);
	}

	private SubmissionController testController;
	private MockSubmissionFactory testFactory = new MockSubmissionFactory();
	private SubmissionModel testModel;
	private AtomService mockService;
	private SubmissionFactory mockFactory;
	String feedURL = "http://foo.com/submissions";
	private SubmissionEntry testSubmissionEntry;
		
	@Before
	public void setUp() {
		
		//create testModel and mockService to inject
		testModel = new SubmissionModel();
		mockService = createMock(AtomService.class);
		mockFactory = createMock(SubmissionFactory.class);
		
		//create test controller
		testController = new SubmissionController(testModel, mockService, null);
		
		//Replace default factory with MockFactory for testing
		testController.setSubmissionFactory(mockFactory);
		
		//create test data
		testSubmissionEntry = testFactory.createSubmissionEntry();
		testSubmissionEntry.setTitle("existing title");
		testSubmissionEntry.setSummary("existing summary");
		testSubmissionEntry.addStudyLink("http://foo.com/studies/study1");
		
	}
	
	@Test
	public void testTestFactorySetter() {
		
		//Check setter works
		assertEquals(mockFactory, testController.getSubmissionFactory());
	}
	
	@Test
	public void testSetUpNewSubmission() {
				
		//set up expectations
		expect(mockFactory.createSubmissionEntry()).andReturn(testSubmissionEntry);
		replay(mockFactory);
		
		//call method under test
		testController.setUpNewSubmission(feedURL);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testSubmissionEntry, testModel.getSubmissionEntry());
		
		verify(mockFactory);
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
		replay(mockService);
		mockDeffered.addCallbacks(isA(LoadSubmissionEntryCallback.class), isA(LoadSubmissionEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		
		//call method under test
		testController.loadSubmissionEntryByURL(submissionEntryURL);
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_LOADING, testModel.getStatus());
		
		verify(mockService);
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
	public void testUpdateTitle(){
		
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//test data
		String title = "Study foo";
		
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
	public void testUpdateAcceptClinicalData(){
	
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//call method under test
		testController.updateAcceptClinicalData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptClinicalData());
	}

	@Test
	public void testUpdateAcceptMolecularData(){
	
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//call method under test
		testController.updateAcceptMolecularData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptMolecularData());
	}

	@Test
	public void testUpdateAcceptInVitroData(){
	
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//call method under test
		testController.updateAcceptInVitroData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptInVitroData());
	}

	@Test
	public void testUpdateAcceptPharmacologyData(){
	
		//mock loaded state
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//call method under test
		testController.updateAcceptPharmacologyData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptPharmacologyData());
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
	public void testCancelCreateStudy() {
	
		//call method under test
		testController.cancelCreateOrUpdateSubmissionEntry();
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_CANCELLED, testModel.getStatus());
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveNewSubmissionEntry() {
				
		//test data
		String feedURL = "http://foo.com/submissions";
		
		//set up test
		testSetUpNewSubmission();
				
		//inject testSubmissionEntry
		testModel.setSubmissionEntry(testSubmissionEntry);
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.postEntry(feedURL, testSubmissionEntry)).andReturn(mockDeffered);
		replay(mockService);
		mockDeffered.addCallbacks(isA(SaveOrUpdateSubmissionEntryCallback.class), isA(SaveOrUpdateSubmissionEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		
		//call method under test
		testController.saveNewSubmissionEntry();
		
		//test outcome
		assertEquals(SubmissionModel.STATUS_SAVING, testModel.getStatus());
		
		verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateSubmissionEntry() {
		
		//test data
		String entryURL = "http://foo.com/submissions/submission1";
		
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
		expectLastCall().anyTimes();
		replay(mockSubmissionEntry);
		
		expect(mockAtomLink.getHref()).andReturn(entryURL);
		replay(mockAtomLink);
		
		expect(mockService.putEntry(entryURL, mockSubmissionEntry)).andReturn(mockDeffered);
		replay(mockService);
		
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
		verify(mockService);
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
	public void testPubSubCreateAPI() {

		//create mock Widget and inject into new testController using Create API
		SubmissionControllerPubSubCreateAPI mockListener = createMock(SubmissionControllerPubSubCreateAPI.class);
		SubmissionControllerCreateAPI testCreateController = new SubmissionController(testModel, mockService, mockListener);
						
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
	
}
