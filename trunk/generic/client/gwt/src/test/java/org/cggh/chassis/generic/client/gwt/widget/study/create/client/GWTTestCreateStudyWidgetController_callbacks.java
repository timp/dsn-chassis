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

import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author raok
 *
 */
public class GWTTestCreateStudyWidgetController_callbacks extends GWTTestCase {
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.create.CreateStudyWidget";
	}

	private CreateStudyWidgetController testController;
	private CreateStudyWidgetModel testModel;
	private AtomService mockService;
	private MockStudyFactory mockFactory;
	private String feedURL = "http://www.foo.com/studies";
		

	@Override
	public void gwtSetUp() {
		
		//Create testController, inject testModel and a mock Service
		testModel = new CreateStudyWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = new MockAtomService(mockFactory);
		
		// bootstrap mock service with study feed
		((MockAtomService)mockService).createFeed(feedURL, "all studies");
		
		testController = new CreateStudyWidgetController(testModel, mockService, feedURL, null);

		//Replace default factory with MockFactory for testing
		testController.setStudyFactory(mockFactory);

		//prepare to create new study
		testController.setUpNewStudy();
	}
	
	
	public void testPostStudyEntry() {
		
		//test data
		final String title = "study Foo";
		final String summary = "summary blah";
		final Boolean acceptClinicalData = true;
		final Boolean acceptMolecularData = false;
		final Boolean acceptInVitroData = true;
		final Boolean acceptPharmacologyData = false;
				
		// Set model with test data, and mock status.
		testModel.setTitle(title);
		testModel.setSummary(summary);
		testModel.setAcceptClinicalData(acceptClinicalData);
		testModel.setAcceptMolecularData(acceptMolecularData);
		testModel.setAcceptInVitroData(acceptInVitroData);
		testModel.setAcceptPharmacologyData(acceptPharmacologyData);
		testModel.setStatus(CreateStudyWidgetModel.STATUS_READY);
		
		
		// Test failed if timer expires. Check AtomService logs for details of failure.
		delayTestFinish(5000);
		
		// call method under test
		Deferred<AtomEntry> deferredEntry = testController.postStudyEntry();
		
		// Check status 
		assertEquals(CreateStudyWidgetModel.STATUS_SAVING, testModel.getStatus());
		
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
				
				// tell the test system the test is now done
			    finishTest();
				
				return null;
			}
			
		});
	}
	
	
}
