/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetController.SaveUpdateFailFunctionErrback;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetController.SaveUpdateSuccessFunctionCallback;
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
@PrepareForTest({EditStudyWidget.class})
public class TestEditStudyWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestEditStudyWidgetController.class);
	}

	private EditStudyWidgetController testController;
	final private EditStudyWidgetModel testModel = new EditStudyWidgetModel();
	private AtomService mockService;
	private MockStudyFactory testFactory;
	private EditStudyWidget mockWidget;
	private StudyEntry testStudyEntry;
		
	@Before
	public void setUp() {
		
		//Create testController, inject testModel and a mock Service
		testFactory = new MockStudyFactory();
		mockService = createMock(AtomService.class);
		
		//create mock widget
		mockWidget = PowerMock.createPartialMock(EditStudyWidget.class, "studyUpdateSuccess");
		
		testController = new EditStudyWidgetController(testModel, mockService, mockWidget);

		//Replace default factory with MockFactory for testing
		testController.setStudyFactory(testFactory);
		
		//create testStudyEntry to load
		MockStudyFactory mockFactory = new MockStudyFactory();
		testStudyEntry = mockFactory.createStudyEntry();
		testStudyEntry.setTitle("");
		testStudyEntry.setSummary("");		
	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
	
	@Test
	public void testTestFactorySetter() {
		
		//Check setter works
		assertEquals(testFactory, testController.getStudyFactory());
	}
	
	@Test
	public void testLoadStudyToEdit() {
		
		//call method under test
		testController.loadStudyEntry(testStudyEntry);
		
		//test outcome
		assertEquals(EditStudyWidgetModel.STATUS_LOADED, testModel.getStatus());
		assertEquals(testStudyEntry, testModel.getStudyEntry());
	}
			
	@Test
	public void testUpdateTitle(){
		
		//test data
		String title = "Study foo";
		
		//set loaded studyEntry
		testModel.setStudyEntry(testStudyEntry);
		
		//call method under test
		testController.updateTitle(title);
		
		//test outcome
		assertEquals(title, testModel.getTitle());
	}
	
	@Test
	public void testUpdateSummary(){
		
		//test data
		String summary = "Summary foo";
		
		//set loaded studyEntry
		testModel.setStudyEntry(testStudyEntry);
		
		//call method under test
		testController.updateSummary(summary);
		
		//test outcome
		assertEquals(summary, testModel.getSummary());
	}
	
	@Test
	public void testUpdateAcceptClinicalData(){
		
		//set loaded studyEntry
		testModel.setStudyEntry(testStudyEntry);
				
		//call method under test
		testController.updateAcceptClinicalData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptClinicalData());
	}
	
	@Test
	public void testUpdateAcceptMolecularData(){
		
		//set loaded studyEntry
		testModel.setStudyEntry(testStudyEntry);
				
		//call method under test
		testController.updateAcceptMolecularData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptMolecularData());
	}
	
	@Test
	public void testUpdateAcceptInVitroData(){
		
		//set loaded studyEntry
		testModel.setStudyEntry(testStudyEntry);
				
		//call method under test
		testController.updateAcceptInVitroData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptInVitroData());
	}
	
	@Test
	public void testUpdateAcceptPharmacologyData(){
		
		//set loaded studyEntry
		testModel.setStudyEntry(testStudyEntry);
				
		//call method under test
		testController.updateAcceptPharmacologyData(Boolean.TRUE);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptPharmacologyData());
	}
	
	@Test
	public void testCancelEditStudy() {

		//call method under test
		testController.cancelEditStudy();
		
		//test outcome
		assertEquals(EditStudyWidgetModel.STATUS_CANCELLED, testModel.getStatus());
		
	}
	

	@Test
	public void testHandleUpdateSuccess() {
				
		
		testModel.setStudyEntry(testStudyEntry);
		
		//set expectations on owner
		mockWidget.studyUpdateSuccess(isA(StudyEntry.class));
		PowerMock.replay(mockWidget, EditStudyWidget.class);
		
		SaveUpdateSuccessFunctionCallback callback = testController.new SaveUpdateSuccessFunctionCallback();
		
		//call method under test
		callback.apply(testStudyEntry);
		
		//test outcome
		assertEquals(EditStudyWidgetModel.STATUS_SAVED, testModel.getStatus());
		
		//verify mocks
		PowerMock.verify(mockWidget, EditStudyWidget.class);
		
	}
	
	@Test
	public void testHandleUpdateFail() {
		
		testModel.setStudyEntry(testStudyEntry);
				
		SaveUpdateFailFunctionErrback callback = testController.new SaveUpdateFailFunctionErrback();
		
		//call method under test
		callback.apply(new Throwable());
		
		assertEquals(EditStudyWidgetModel.STATUS_SAVE_ERROR, testModel.getStatus());
				
	}
	
}
