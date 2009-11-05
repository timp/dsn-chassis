/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

//import static org.easymock.EasyMock.createMock;
//import static org.easymock.EasyMock.createNiceMock;
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.isA;
//import static org.easymock.EasyMock.replay;
//import static org.easymock.EasyMock.verify;
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;

import junit.framework.JUnit4TestAdapter;

//import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
//import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.LoadStudyEntryCallback;
//import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.LoadStudyEntryErrback;
//import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.SaveOrUpdateStudyEntryCallback;
//import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyController.SaveOrUpdateStudyEntryErrback;
//import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
//import org.cggh.chassis.generic.twisted.client.Deferred;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.powermock.api.easymock.PowerMock;
//import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author raok
 *
 */
@RunWith(PowerMockRunner.class)
//@PrepareForTest({StudyController.class, Deferred.class, StudyFactoryImpl.class, AtomServiceImpl.class})
public class TestStudyController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestStudyController.class);
	}

//	private StudyController testController;
//	private StudyModel testModel;
//	private AtomService mockService;
//	private StudyFactory mockFactory;
//	private MockStudyFactory testFactory = new MockStudyFactory();
//	String testStudyFeedURL = TestConfigurationSetUp.testStudyFeedURL;
//	private StudyEntry testStudyEntry;
//	private HashSet<AtomAuthor> testAuthors;
//	private String testAuthorEmail = "foo@bar.com";
		
	@Before
	public void setUp() throws Exception {
		
//		//setup ConfigurationBean
//		TestConfigurationSetUp.createTestConfiguration();
//		
//		//create testModel and mockService to inject
//		testModel = new StudyModel();
//		mockFactory = PowerMock.createMock(StudyFactoryImpl.class);
//		mockService = PowerMock.createMock(AtomServiceImpl.class);
//				
//		//Setup to use mock service and factory
//		PowerMock.expectNew(StudyFactoryImpl.class).andReturn((StudyFactoryImpl) mockFactory);
//		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
//		PowerMock.replay(StudyFactoryImpl.class,AtomServiceImpl.class);
//		
//		//create test controller
//		testController = new StudyController(testModel, null);
//		
//
//		//create test authors
//		testAuthors = new HashSet<AtomAuthor>();
//		AtomAuthor testAtomAuthor = testFactory.createAuthor();
//		testAtomAuthor.setEmail(testAuthorEmail );
//		testAuthors.add(testAtomAuthor);
//		
//		//create test data
//		testStudyEntry = testFactory.createStudyEntry();
//		testStudyEntry.setAuthors(new ArrayList<AtomAuthor>(testAuthors));
//		testStudyEntry.setTitle("existing title");
//		testStudyEntry.setSummary("existing summary");
		
	}
	
	@Test
	public void testSetUpNewStudy() {
		
//		//set up expectations
//		expect(mockFactory.createStudyEntry()).andReturn(testFactory.createStudyEntry());
//		expect(mockFactory.createAuthor()).andReturn(testFactory.createAuthor());
//		PowerMock.replay(mockFactory);
//		
//		//call method under test
//		testController.setUpNewStudy(testAuthorEmail);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_LOADED, testModel.getStatus());
//		assertEquals(testAuthorEmail, testModel.getStudyEntry().getAuthors().iterator().next().getEmail());
//		
//		PowerMock.verify(mockFactory);
	}
	
	@Test
	public void testLoadStudy() {
		
//		//call method under test
//		testController.loadStudyEntry(testStudyEntry);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_LOADED, testModel.getStatus());
//		assertEquals(testStudyEntry, testModel.getStudyEntry());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testLoadStudyByURL() {
		
//		//test data
//		String studyEntryURL = "http://foo.com/studies/study1";
//		
//		//create mock Deffered object
//		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockService.getEntry(studyEntryURL)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		mockDeffered.addCallbacks(isA(LoadStudyEntryCallback.class), isA(LoadStudyEntryErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//		
//		//call method under test
//		testController.loadStudyEntryByURL(studyEntryURL);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_LOADING, testModel.getStatus());
//		
//		PowerMock.verify(mockService);
//		PowerMock.verify(mockDeffered);
	}
	
	@Test
	public void testLoadStudyByURL_success() {
		
//		//object under test
//		LoadStudyEntryCallback callback = testController.new LoadStudyEntryCallback();
//		
//		//call method under test
//		StudyEntry returnedStudyEntry = callback.apply(testStudyEntry);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_LOADED, testModel.getStatus());
//		assertEquals(testStudyEntry, testModel.getStudyEntry());
//		assertEquals(returnedStudyEntry, testStudyEntry);		
		
	}
	
	@Test
	public void testLoadStudyByURL_error() {
		
//		//test data
//		Throwable throwable = new Throwable();
//		
//		//object under test
//		LoadStudyEntryErrback errback = testController.new LoadStudyEntryErrback();
//		
//		//call method under test
//		Throwable returnedThrowable = errback.apply(throwable);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_ERROR, testModel.getStatus());
//		assertEquals(returnedThrowable, throwable);		
		
	}
	
	@Test
	public void testUpdateTitle(){
		
//		//mock loaded state
//		testModel.setStudyEntry(testStudyEntry);
//		
//		//test data
//		String title = "Study foo";
//		
//		//call method under test
//		testController.updateTitle(title);
//		
//		//test outcome
//		assertEquals(title, testModel.getTitle());
	}

	@Test
	public void testUpdateSummary(){
		
//		//mock loaded state
//		testModel.setStudyEntry(testStudyEntry);
//		
//		//test data
//		String summary = "Summary foo";
//		
//		//call method under test
//		testController.updateSummary(summary);
//		
//		//test outcome
//		assertEquals(summary, testModel.getSummary());
	}
	
	@Test
	public void testUpdateModules() {

//		//mock loaded state
//		testModel.setStudyEntry(testStudyEntry);
//		
//		//test data
//		Set<String> modulesSet1 = new HashSet<String>();
//		modulesSet1.add("module1");		
//		
//		//call method under test
//		testController.updateModules(modulesSet1);
//		
//		//test outcome
//		assertEquals(modulesSet1, testModel.getModules());		
		
	}
	
	@Test
	public void testUpdateAuthors() {

//		//mock loaded state
//		testModel.setStudyEntry(testStudyEntry);
//		
//		//test data
//		Set<AtomAuthor> testAuthors = new HashSet<AtomAuthor>();
//		AtomAuthor testAtomAuthor = testFactory.createAuthor();
//		testAtomAuthor.setEmail("foo@bar.com");
//		testAuthors.add(testAtomAuthor);
//		
//		//call method under test
//		testController.updateAuthors(testAuthors);
//		
//		//test outcome
//		assertEquals(testAuthors, testModel.getAuthors());		
		
	}
	
	
	@Test
	public void testCancelCreateStudy() {
	
//		//call method under test
//		testController.cancelSaveOrUpdateStudyEntry();
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_CANCELLED, testModel.getStatus());
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveNewStudyEntry() {
				
//		//test data
//		String feedURL = "http://foo.com/studies";
//		
//		//set up test
//		testSetUpNewStudy();
//				
//		//inject testStudyEntry
//		testModel.setStudyEntry(testStudyEntry);
//		
//		//create mock Deffered object
//		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockService.postEntry(feedURL, testStudyEntry)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		mockDeffered.addCallbacks(isA(SaveOrUpdateStudyEntryCallback.class), isA(SaveOrUpdateStudyEntryErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//		
//		//call method under test
//		testController.saveNewStudyEntry();
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_SAVING, testModel.getStatus());
//		
//		PowerMock.verify(mockService);
//		PowerMock.verify(mockDeffered);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateStudyEntry() {
		
//		//test data
//		//Note: assumes editLink is stored as a relative link.
//		String relEntryURL = "/study1"; 
//		String entryURL = testStudyFeedURL + relEntryURL;
//		
//		//create mockStudyEntry and AtomLink for this test
//		StudyEntry mockStudyEntry = createNiceMock(StudyEntry.class);
//		AtomLink mockAtomLink = createMock(AtomLink.class);
//						
//		//create mock Deffered object
//		Deferred<AtomEntry> mockDeffered = PowerMock.createMock(Deferred.class);
//		
//		//set up expectations
//		expect(mockStudyEntry.getEditLink()).andReturn(mockAtomLink);
//		expect(mockStudyEntry.getModules()).andReturn(new ArrayList<String>());
//		expect(mockStudyEntry.getAuthors()).andReturn(new ArrayList<AtomAuthor>());
//		replay(mockStudyEntry);
//		
//		expect(mockAtomLink.getHref()).andReturn(relEntryURL);
//		replay(mockAtomLink);
//		
//		expect(mockService.putEntry(entryURL, mockStudyEntry)).andReturn(mockDeffered);
//		PowerMock.replay(mockService);
//		
//		mockDeffered.addCallbacks(isA(SaveOrUpdateStudyEntryCallback.class), isA(SaveOrUpdateStudyEntryErrback.class));
//		PowerMock.expectLastCall().andReturn(mockDeffered);
//		PowerMock.replay(mockDeffered);
//		
//
//		//mock loaded state
//		testModel.setStudyEntry(mockStudyEntry);
//		
//		//call method under test
//		testController.updateStudyEntry();
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_SAVING, testModel.getStatus());
//
//		verify(mockStudyEntry);
//		verify(mockAtomLink);
//		PowerMock.verify(mockService);
//		PowerMock.verify(mockDeffered);
	}
	
	@Test
	public void testSaveOrUpdate_success() {
		
//		//object under test
//		SaveOrUpdateStudyEntryCallback callback = testController.new SaveOrUpdateStudyEntryCallback();
//		
//		//call method under test
//		StudyEntry returnedStudyEntry = callback.apply(testStudyEntry);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_SAVED, testModel.getStatus());
//		assertEquals(testStudyEntry, testModel.getStudyEntry());
//		assertEquals(returnedStudyEntry, testStudyEntry);		
		
	}
	
	@Test
	public void testSaveOrUpdate_error() {
		
//		//test data
//		Throwable throwable = new Throwable();
//		
//		//object under test
//		SaveOrUpdateStudyEntryErrback errback = testController.new SaveOrUpdateStudyEntryErrback();
//		
//		//call method under test
//		Throwable returnedThrowable = errback.apply(throwable);
//		
//		//test outcome
//		assertEquals(StudyModel.STATUS_ERROR, testModel.getStatus());
//		assertEquals(returnedThrowable, throwable);		
		
	}
	
	@Test
	public void testPubSubCreateAPI() throws Exception {
		
//		//Setup to use mock service and factory
//		PowerMock.resetAll();
//		PowerMock.expectNew(StudyFactoryImpl.class).andReturn((StudyFactoryImpl) mockFactory);
//		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
//		PowerMock.replay(StudyFactoryImpl.class,AtomServiceImpl.class);
//		
//		//create mock Widget and inject into new testController using Create API
//		StudyControllerPubSubCreateAPI mockListener = createMock(StudyControllerPubSubCreateAPI.class);
//		StudyControllerCreateAPI testCreateController = new StudyController(testModel, mockListener);
//						
//		//set up expectations
//		mockListener.fireOnNewStudySaved(testStudyEntry);
//		mockListener.fireOnUserActionCreateStudyEntryCancelled();
//		replay(mockListener);
//		
//		//set up test
//		SaveOrUpdateStudyEntryCallback callback = ((StudyController)testCreateController).new SaveOrUpdateStudyEntryCallback();
//				
//		//call methods under test
//		callback.apply(testStudyEntry);
//		testCreateController.cancelSaveOrUpdateStudyEntry();
//		
//		
//		verify(mockListener);		
		
	}
	
	@Test
	public void testPubSubEditAPI() throws Exception {
		
//		//Setup to use mock service and factory
//		PowerMock.resetAll();
//		PowerMock.expectNew(StudyFactoryImpl.class).andReturn((StudyFactoryImpl) mockFactory);
//		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
//		PowerMock.replay(StudyFactoryImpl.class,AtomServiceImpl.class);
//
//		//create mock Widget and inject into new testController using Create API
//		StudyControllerPubSubEditAPI mockListener = createMock(StudyControllerPubSubEditAPI.class);
//		StudyControllerEditAPI testEditController = new StudyController(testModel, mockListener);
//						
//		//set up expectations
//		mockListener.onStudyUpdated(testStudyEntry);
//		mockListener.onUserActionEditStudyEntryCancelled();
//		replay(mockListener);
//		
//		//set up test
//		SaveOrUpdateStudyEntryCallback callback = ((StudyController)testEditController).new SaveOrUpdateStudyEntryCallback();
//				
//		//call methods under test
//		callback.apply(testStudyEntry);
//		testEditController.cancelSaveOrUpdateStudyEntry();
//		
//		
//		verify(mockListener);		
//		
	}
	
	@Test
	public void testPubSubViewAPI() throws Exception {
		
//		//Setup to use mock service and factory
//		PowerMock.resetAll();
//		PowerMock.expectNew(StudyFactoryImpl.class).andReturn((StudyFactoryImpl) mockFactory);
//		PowerMock.expectNew(AtomServiceImpl.class, mockFactory).andReturn((AtomServiceImpl) mockService);
//		PowerMock.replay(StudyFactoryImpl.class,AtomServiceImpl.class);
//
//		//create mock Widget and inject into new testController using Create API
//		StudyControllerPubSubViewAPI mockListener = createMock(StudyControllerPubSubViewAPI.class);
//		StudyControllerViewAPI testViewController = new StudyController(testModel, mockListener);
//						
//		//set up expectations
//		mockListener.fireOnUserActionEditStudy(testStudyEntry);
//		replay(mockListener);
//		
//		//set up test
//		testModel.setStudyEntry(testStudyEntry);
//		
//		testViewController.fireOnUserActionEditThisStudy();
//		
//		verify(mockListener);		
		
	}
	
}
