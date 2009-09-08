/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetController.GetStudyEntryCallback;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestViewStudyWidgetController {

	
	public static junit.framework.Test suite() {
	   return new JUnit4TestAdapter(TestViewStudyWidgetController.class);
	}

	private ViewStudyWidgetController testController;
	private ViewStudyWidgetModel mockModel;
	private AtomService mockService;
	private StudyFactory mockFactory;
		
	@Before
	public void setUp() {
		
		//Create testController, inject mockModel and a mock Service
		mockModel = new ViewStudyWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = createMock(AtomService.class);
				
		testController = new ViewStudyWidgetController(mockModel, mockService);

	}
	
	@Test
	public void testConstructor() {
		
		assertNotNull(testController);
		
	}
		
	@Test
	public void testLoadStudy_callback() {
		
		//test data
		String entryURL = "http://example.com/studies/study_1";
				
		//set up expectations
		expect(mockService.getEntry(entryURL)).andReturn(new Deferred<AtomEntry>());
		replay(mockService);

				
		Deferred<AtomEntry> deffered =  testController.getStudyEntry(entryURL);
		
		assertNotNull(deffered);
		
		verify(mockService);		
	}
	
	@Test
	public void testLoadStudy_success() {
		
		//test data
		String title = "study foo";
		String summary = "summary foo";
		
		
		//create test Study Entry to view
		StudyEntry testStudy = mockFactory.createStudyEntry();
		testStudy.setTitle(title);
		testStudy.setSummary(summary);
		testStudy.addModule(CreateStudyWidgetModel.MODULE_CLINICAL);
		testStudy.addModule(CreateStudyWidgetModel.MODULE_IN_VITRO);

		GetStudyEntryCallback callback = testController.new GetStudyEntryCallback();
		
		//call method under test
		callback.apply(testStudy);
		
		//test outcome
		assertEquals(ViewStudyWidgetModel.STATUS_LOADED, mockModel.getStatus());
		assertEquals(title, mockModel.getTitle());
		assertEquals(summary, mockModel.getSummary());
		assertEquals(Boolean.TRUE, mockModel.acceptClinicalData());
		assertEquals(Boolean.TRUE, mockModel.acceptInVitroData());
		assertEquals(Boolean.FALSE, mockModel.acceptMolecularData());
		assertEquals(Boolean.FALSE, mockModel.acceptPharmacologyData());
		
		
	}
	
}
