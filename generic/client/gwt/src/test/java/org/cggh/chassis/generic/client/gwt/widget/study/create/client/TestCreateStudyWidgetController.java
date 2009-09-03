/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.twisted.client.*;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author raok
 *
 */
public class TestCreateStudyWidgetController extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.create.CreateStudyWidget";
	}
	
//	public static junit.framework.Test suite() {
//	   return new JUnit4TestAdapter(TestCreateStudyWidgetController.class);
//	}

	private CreateStudyWidgetController testController;
	final private CreateStudyWidgetModel mockModel = new CreateStudyWidgetModel();;
	private AtomService mockService;
	private MockStudyFactory mockFactory;
	private String feedURL = "http://www.foo.com/studies";
		
	@Before
	public void gwtSetUp() {
		
		//Create testController, inject testModel and a mock Service
		mockFactory = new MockStudyFactory();
		mockService = new MockAtomService(mockFactory);
		
		// bootstrap mock service with study feed
		((MockAtomService)mockService).createFeed(feedURL, "all studies");
		
		testController = new CreateStudyWidgetController(mockModel, mockService, feedURL);

		//Replace default factory with MockFactory for testing
		testController.setStudyFactory(mockFactory);
	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
	
	@Test
	public void testTestFactorySetter() {
		
		//Check setter works
		assertEquals(mockFactory, testController.getStudyFactory());
	}
	
	@Test
	public void testSetUpNewStudy() {
		
		// What a new study should be setup as
		String newString = "";
		Boolean newBoolean = false;
		
		//call method under test
		testController.setUpNewStudy();
		
		assertEquals(CreateStudyWidgetModel.STATUS_READY, mockModel.getStatus());
		assertEquals(newString, mockModel.getTitle());
		assertEquals(newString, mockModel.getSummary());
		assertEquals(newBoolean, mockModel.acceptClinicalData());
		assertEquals(newBoolean, mockModel.acceptInVitroData());
		assertEquals(newBoolean, mockModel.acceptMolecularData());
		assertEquals(newBoolean, mockModel.acceptPharmacologyData());
		
	}
		
	@Test
	public void testSaveNewStudy_callback() {
		
		//test data
		final String title = "study Foo";
		final String summary = "summary blah";
		final Boolean acceptClinicalData = true;
		final Boolean acceptMolecularData = false;
		final Boolean acceptInVitroData = true;
		final Boolean acceptPharmacologyData = false;
				
		// Set model with test data, and mock status.
		mockModel.setTitle(title);
		mockModel.setSummary(summary);
		mockModel.setAcceptClinicalData(acceptClinicalData);
		mockModel.setAcceptMolecularData(acceptMolecularData);
		mockModel.setAcceptInVitroData(acceptInVitroData);
		mockModel.setAcceptPharmacologyData(acceptPharmacologyData);
		mockModel.setStatus(CreateStudyWidgetModel.STATUS_READY);
		
		
		// Test failed if timer expires. Check AtomService logs for details of failure.
		delayTestFinish(5000);
		
		// call method under test
		Deferred<AtomEntry> deferredEntry = testController.saveNewStudy();
		
		//test outcome
		deferredEntry.addCallback(new Function<StudyEntry,StudyEntry>() {

			public StudyEntry apply(StudyEntry persistedStudy) {
				
				//Check study is equal
				assertEquals(title, persistedStudy.getTitle());
				assertEquals(summary, persistedStudy.getSummary());
				assertTrue(acceptClinicalData == persistedStudy.getModules().contains(CreateStudyWidgetModel.MODULE_CLINICAL));
				assertTrue(acceptMolecularData == persistedStudy.getModules().contains(CreateStudyWidgetModel.MODULE_MOLECULAR));
				assertTrue(acceptInVitroData == persistedStudy.getModules().contains(CreateStudyWidgetModel.MODULE_IN_VITRO));
				assertTrue(acceptPharmacologyData == persistedStudy.getModules().contains(CreateStudyWidgetModel.MODULE_PHARMACOLOGY));

				assertEquals(CreateStudyWidgetModel.STATUS_SAVED, mockModel.getStatus());
				
				// tell the test system the test is now done
			    finishTest();
				
				return null;
			}
			
		});
	}
	
}
