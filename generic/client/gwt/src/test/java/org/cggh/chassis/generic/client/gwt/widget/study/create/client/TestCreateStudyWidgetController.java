/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestCreateStudyWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestCreateStudyWidgetController.class);
	}

	private CreateStudyWidgetController testController;
	final private CreateStudyWidgetModel mockModel = new CreateStudyWidgetModel();;
	private AtomService mockService;
	private MockStudyFactory mockFactory;
	private String feedURL = "http://www.foo.com/studies";
		
	@Before
	public void setUp() {
		
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
	public void testUpdateTitle(){
		
		//test data
		String title = "Study foo";
		
		//call method under test
		testController.updateTitle(title);
		
		//test outcome
		assertEquals(title, mockModel.getTitle());
	}
	
	@Test
	public void testUpdateSummary(){
		
		//test data
		String summary = "Summary foo";
		
		//call method under test
		testController.updateSummary(summary);
		
		//test outcome
		assertEquals(summary, mockModel.getSummary());
	}
	
	@Test
	public void testUpdateAcceptClinicalData(){
				
		//call method under test
		testController.updateAcceptClinicalData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, mockModel.acceptClinicalData());
	}
	
	@Test
	public void testUpdateAcceptMolecularData(){
				
		//call method under test
		testController.updateAcceptMolecularData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, mockModel.acceptMolecularData());
	}
	
	@Test
	public void testUpdateAcceptInVitroData(){
				
		//call method under test
		testController.updateAcceptInVitroData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, mockModel.acceptInVitroData());
	}
	
	@Test
	public void testUpdateAcceptPharmacologyData(){
				
		//call method under test
		testController.updateAcceptPharmacologyData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, mockModel.acceptPharmacologyData());
	}
	
	@Test
	public void testCancelCreateStudy() {

		//call method under test
		testController.cancelCreateStudy();
		
		//test outcome
		assertEquals(CreateStudyWidgetModel.STATUS_CANCELLED, mockModel.getStatus());
		
	}
	
}
