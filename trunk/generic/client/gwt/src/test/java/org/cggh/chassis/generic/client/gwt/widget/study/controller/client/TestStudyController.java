/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.LoadStudyEntryCallback;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.LoadStudyEntryErrback;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.SaveOrUpdateStudyEntryCallback;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.SaveOrUpdateStudyEntryErrback;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
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
public class TestStudyController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestStudyController.class);
	}

	private StudyController testController;
	private MockStudyFactory testFactory = new MockStudyFactory();
	private StudyModel testModel;
	private AtomService mockService;
	private StudyFactory mockFactory;
	String feedURL = "http://foo.com/studies";
	private StudyEntry testStudyEntry;
		
	@Before
	public void setUp() {
		
		//create testModel and mockService to inject
		testModel = new StudyModel();
		mockService = createMock(AtomService.class);
		mockFactory = createMock(StudyFactory.class);
		
		//create test controller
		testController = new StudyController(testModel, mockService, null);
		
		//Replace default factory with MockFactory for testing
		testController.setStudyFactory(mockFactory);
		
		//create test data
		testStudyEntry = testFactory.createStudyEntry();
		testStudyEntry.setTitle("existing title");
		testStudyEntry.setSummary("existing summary");
		
	}
	
	@Test
	public void testTestFactorySetter() {
		
		//Check setter works
		assertEquals(mockFactory, testController.getStudyFactory());
	}
	
	@Test
	public void testSetUpNewStudy() {
				
		//set up expectations
		expect(mockFactory.createStudyEntry()).andReturn(testStudyEntry);
		replay(mockFactory);
		
		//call method under test
		testController.setUpNewStudy();
		
		//test outcome
		assertEquals(StudyModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testStudyEntry, testModel.getStudyEntry());
		
		verify(mockFactory);
	}
	
	@Test
	public void testLoadStudy() {
		
		//call method under test
		testController.loadStudyEntry(testStudyEntry);
		
		//test outcome
		assertEquals(StudyModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testStudyEntry, testModel.getStudyEntry());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadStudyByURL() {
		
		//test data
		String studyEntryURL = "http://foo.com/studies/study1";
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.getEntry(studyEntryURL)).andReturn(mockDeffered);
		replay(mockService);
		mockDeffered.addCallbacks(isA(LoadStudyEntryCallback.class), isA(LoadStudyEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		
		//call method under test
		testController.loadStudyEntryByURL(studyEntryURL);
		
		//test outcome
		assertEquals(StudyModel.STATUS_LOADING, testModel.getStatus());
		
		verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@Test
	public void testLoadStudyByURL_success() {
		
		//object under test
		LoadStudyEntryCallback callback = testController.new LoadStudyEntryCallback();
		
		//call method under test
		StudyEntry returnedStudyEntry = callback.apply(testStudyEntry);
		
		//test outcome
		assertEquals(StudyModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testStudyEntry, testModel.getStudyEntry());
		assertEquals(returnedStudyEntry, testStudyEntry);		
		
	}
	
	@Test
	public void testLoadStudyByURL_error() {
		
		//test data
		Throwable throwable = new Throwable();
		
		//object under test
		LoadStudyEntryErrback errback = testController.new LoadStudyEntryErrback();
		
		//call method under test
		Throwable returnedThrowable = errback.apply(throwable);
		
		//test outcome
		assertEquals(StudyModel.STATUS_ERROR, testModel.getStatus());
		assertEquals(returnedThrowable, throwable);		
		
	}
	
	@Test
	public void testUpdateTitle(){
		
		//mock loaded state
		testModel.setStudyEntry(testStudyEntry);
		
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
		testModel.setStudyEntry(testStudyEntry);
		
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
		testModel.setStudyEntry(testStudyEntry);
		
		//call method under test
		testController.updateAcceptClinicalData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptClinicalData());
	}

	@Test
	public void testUpdateAcceptMolecularData(){
	
		//mock loaded state
		testModel.setStudyEntry(testStudyEntry);
		
		//call method under test
		testController.updateAcceptMolecularData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptMolecularData());
	}

	@Test
	public void testUpdateAcceptInVitroData(){
	
		//mock loaded state
		testModel.setStudyEntry(testStudyEntry);
		
		//call method under test
		testController.updateAcceptInVitroData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptInVitroData());
	}

	@Test
	public void testUpdateAcceptPharmacologyData(){
	
		//mock loaded state
		testModel.setStudyEntry(testStudyEntry);
		
		//call method under test
		testController.updateAcceptPharmacologyData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptPharmacologyData());
	}

	@Test
	public void testCancelCreateStudy() {
	
		//call method under test
		testController.cancelSaveOrUpdateStudyEntry();
		
		//test outcome
		assertEquals(StudyModel.STATUS_CANCELLED, testModel.getStatus());
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveNewStudyEntry() {
				
		//test data
		String feedURL = "http://foo.com/studies";
		
		//set up test
		testSetUpNewStudy();
				
		//inject testStudyEntry
		testModel.setStudyEntry(testStudyEntry);
		
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockService.postEntry(feedURL, testStudyEntry)).andReturn(mockDeffered);
		replay(mockService);
		mockDeffered.addCallbacks(isA(SaveOrUpdateStudyEntryCallback.class), isA(SaveOrUpdateStudyEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		
		//call method under test
		testController.saveNewStudyEntry(feedURL);
		
		//test outcome
		assertEquals(StudyModel.STATUS_SAVING, testModel.getStatus());
		
		verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateStudyEntry() {
		
		//test data
		String entryURL = "http://foo.com/studies/study1";
		
		//create mockStudyEntry and AtomLink for this test
		StudyEntry mockStudyEntry = createNiceMock(StudyEntry.class);
		AtomLink mockAtomLink = createMock(AtomLink.class);
						
		//create mock Deffered object
		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
		
		//set up expectations
		expect(mockStudyEntry.getEditLink()).andReturn(mockAtomLink);
		expect(mockStudyEntry.getModules()).andReturn(new ArrayList<String>());
		expectLastCall().anyTimes();
		replay(mockStudyEntry);
		
		expect(mockAtomLink.getHref()).andReturn(entryURL);
		replay(mockAtomLink);
		
		expect(mockService.putEntry(entryURL, mockStudyEntry)).andReturn(mockDeffered);
		replay(mockService);
		
		mockDeffered.addCallbacks(isA(SaveOrUpdateStudyEntryCallback.class), isA(SaveOrUpdateStudyEntryErrback.class));
		PowerMock.expectLastCall().andReturn(mockDeffered);
		PowerMock.replay(mockDeffered);
		

		//mock loaded state
		testModel.setStudyEntry(mockStudyEntry);
		
		//call method under test
		// note mockService uses full URL, so do not need to pass it the feedURL
		testController.updateStudyEntry("");
		
		//test outcome
		assertEquals(StudyModel.STATUS_SAVING, testModel.getStatus());

		verify(mockStudyEntry);
		verify(mockAtomLink);
		verify(mockService);
		PowerMock.verify(mockDeffered);
	}
	
	@Test
	public void testSaveOrUpdate_success() {
		
		//object under test
		SaveOrUpdateStudyEntryCallback callback = testController.new SaveOrUpdateStudyEntryCallback();
		
		//call method under test
		StudyEntry returnedStudyEntry = callback.apply(testStudyEntry);
		
		//test outcome
		assertEquals(StudyModel.STATUS_SAVED, testModel.getStatus());
		assertEquals(testStudyEntry, testModel.getStudyEntry());
		assertEquals(returnedStudyEntry, testStudyEntry);		
		
	}
	
	@Test
	public void testSaveOrUpdate_error() {
		
		//test data
		Throwable throwable = new Throwable();
		
		//object under test
		SaveOrUpdateStudyEntryErrback errback = testController.new SaveOrUpdateStudyEntryErrback();
		
		//call method under test
		Throwable returnedThrowable = errback.apply(throwable);
		
		//test outcome
		assertEquals(StudyModel.STATUS_ERROR, testModel.getStatus());
		assertEquals(returnedThrowable, throwable);		
		
	}
	
	@Test
	public void testPubSubCreateAPI() {

		//create mock Widget and inject into new testController using Create API
		StudyControllerPubSubCreateAPI mockListener = createMock(StudyControllerPubSubCreateAPI.class);
		StudyControllerCreateAPI testCreateController = new StudyController(testModel, mockService, mockListener);
						
		//set up expectations
		mockListener.onNewStudySaved(testStudyEntry);
		mockListener.onUserActionCreateStudyEntryCancelled();
		replay(mockListener);
		
		//set up test
		SaveOrUpdateStudyEntryCallback callback = ((StudyController)testCreateController).new SaveOrUpdateStudyEntryCallback();
				
		//call methods under test
		callback.apply(testStudyEntry);
		testCreateController.cancelSaveOrUpdateStudyEntry();
		
		
		verify(mockListener);		
		
	}
	
	@Test
	public void testPubSubEditAPI() {

		//create mock Widget and inject into new testController using Create API
		StudyControllerPubSubEditAPI mockListener = createMock(StudyControllerPubSubEditAPI.class);
		StudyControllerEditAPI testEditController = new StudyController(testModel, mockService, mockListener);
						
		//set up expectations
		mockListener.onStudyUpdated(testStudyEntry);
		mockListener.onUserActionEditStudyEntryCancelled();
		replay(mockListener);
		
		//set up test
		SaveOrUpdateStudyEntryCallback callback = ((StudyController)testEditController).new SaveOrUpdateStudyEntryCallback();
				
		//call methods under test
		callback.apply(testStudyEntry);
		testEditController.cancelSaveOrUpdateStudyEntry();
		
		
		verify(mockListener);		
		
	}
	
	@Test
	public void testPubSubViewAPI() {

		//create mock Widget and inject into new testController using Create API
		StudyControllerPubSubViewAPI mockListener = createMock(StudyControllerPubSubViewAPI.class);
		StudyControllerViewAPI testViewController = new StudyController(testModel, mockService, mockListener);
						
		//set up expectations
		mockListener.onUserActionEditStudy(testStudyEntry);
		replay(mockListener);
		
		//set up test
		testModel.setStudyEntry(testStudyEntry);
		
		testViewController.onUserActionEditThisStudy();
		
		verify(mockListener);		
		
	}
	
}
