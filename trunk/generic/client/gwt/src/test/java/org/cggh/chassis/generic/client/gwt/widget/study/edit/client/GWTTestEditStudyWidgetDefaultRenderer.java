/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestEditStudyWidgetDefaultRenderer extends GWTTestCase {
	
	private EditStudyWidgetDefaultRenderer testRenderer;
	private EditStudyWidgetModel testModel;
	private MockStudyFactory mockFactory;
	private MockAtomService mockService;
	private String feedURL = "http://www.foo.com/studies";
	private MockViewStudyWidgetController testController;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.edit.EditStudyWidget";
	}

	@Override
	protected void gwtSetUp() {

		//Create testController, inject testModel and a mock Service
		testModel = new EditStudyWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = new MockAtomService(mockFactory);

		// bootstrap mock service with study feed
		((MockAtomService)mockService).createFeed(feedURL, "all studies");
		
		testController = new MockViewStudyWidgetController(testModel, mockService, null);
			
		//create testStudyEntry to load
		MockStudyFactory mockFactory = new MockStudyFactory();
		StudyEntry testStudyEntry = mockFactory.createStudyEntry();
		testStudyEntry.setTitle("title");
		testStudyEntry.setSummary("summary");	
		testStudyEntry.addModule(EditStudyWidgetModel.MODULE_CLINICAL);
		
		//call method under test
		testController.loadStudyEntry(testStudyEntry);

		// instantiate a renderer
		testRenderer = new EditStudyWidgetDefaultRenderer(new SimplePanel(), testController);
		
		//register as listener
		testModel.addListener(testRenderer);
		
	}
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		assertEquals("", testRenderer.titleUI.getValue());
		assertEquals("", testRenderer.summaryUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptClinicalDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptMolecularDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptInVitroDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptPharmacologyDataUI.getValue());
		
	}
	
	public void testOnStudyEntryChanged() {
		
		//test data
		String title = "foo";
		String summary = "summary";
		
		//create testStudyEntry to load
		MockStudyFactory mockFactory = new MockStudyFactory();
		StudyEntry testStudyEntry = mockFactory.createStudyEntry();
		testStudyEntry.setTitle(title);
		testStudyEntry.setSummary(summary);	
		testStudyEntry.addModule(EditStudyWidgetModel.MODULE_CLINICAL);
		
				
		//call method under test
		testController.loadStudyEntry(testStudyEntry);
		
		//test outcome
		assertEquals(title, testRenderer.titleUI.getValue());
		assertEquals(summary, testRenderer.summaryUI.getValue());
		assertEquals(Boolean.TRUE, testRenderer.acceptClinicalDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptMolecularDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptInVitroDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptPharmacologyDataUI.getValue());
		
	}
	
	public void testOnTitleChanged_UI() {
		
		//test data
		String study = "Study Foo";
		
		//Simulate text entry and fire change event  
		testRenderer.titleUI.setValue(study, true);
		
		// test outcome
		assertEquals(study, testModel.getTitle());
	}
	
	public void testOnSummaryChanged_UI() {
		
		//test data
		String summary = "Summary Foo";
		
		//Simulate text entry and fire change event  
		testRenderer.summaryUI.setValue(summary, true);
		
		// test outcome
		assertEquals(summary, testModel.getSummary());
	}
	
	public void testOnAcceptClinicalDataChanged_UI() {
		
		//Simulate click 
		testRenderer.acceptClinicalDataUI.setValue(true, true);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptClinicalData());
		
	}
	
	public void testOnAcceptMolecularDataChanged_UI() {
		
		//Simulate click 
		testRenderer.acceptMolecularDataUI.setValue(true, true);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptMolecularData());
		
	}
	
	public void testOnAcceptInVitroDataChanged_UI() {
		
		//Simulate click 
		testRenderer.acceptInVitroDataUI.setValue(true, true);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptInVitroData());
		
	}
	
	public void testOnAcceptPharmacologyDataChanged_UI() {
		
		//Simulate click 
		testRenderer.acceptPharmacologyDataUI.setValue(true, true);
		
		//test outcome
		assertEquals(Boolean.TRUE, testModel.acceptPharmacologyData());
		
	}
	
	public void testCancelCreateStudyButton_UI() {
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.cancelEditStudyUI );
		
		//test outcome
		assertEquals(EditStudyWidgetModel.STATUS_CANCELLED, testModel.getStatus());
	}
	
	
	public void testUpdateStudyButton_UI_formIncomplete() {
		
		//call method under test
		testRenderer.onFormCompleted(false);
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.updateStudyUI );
		
		// test outcome
		assertEquals(EditStudyWidgetModel.STATUS_LOADED, testModel.getStatus());
				
	}
	
	public void testUpdateStudyButton_UI_formComplete() {
		
		//call method under test
		testRenderer.onFormCompleted(true);
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.updateStudyUI );
		
		// test outcome
		assertTrue(testController.putUpdatedStudyFired);
				
	}
	
	
	//mock MockViewStudyWidget
	private class MockViewStudyWidgetController extends EditStudyWidgetController {

				
		public MockViewStudyWidgetController(EditStudyWidgetModel model,
				AtomService service, EditStudyWidget owner) {
			super(model, service, owner);
		}

		//check to see if onEditStudyUIClicked called
		public boolean putUpdatedStudyFired = false;
		
		@Override
		public void putUpdatedStudy() {
			putUpdatedStudyFired = true;
		}
		
	}
	
	
}
