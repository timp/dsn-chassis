/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomService;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestCreateStudyWidgetDefaultRenderer extends GWTTestCase {

	private CreateStudyWidgetModel testModel;
	private MockStudyFactory mockFactory;
	private MockAtomService mockService;
	private CreateStudyWidgetController testController;
	private String feedURL = "http://www.foo.com/studies";
	private CreateStudyWidgetDefaultRenderer testRenderer;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.create.CreateStudyWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//Create testController, inject testModel and a mock Service
		testModel = new CreateStudyWidgetModel();
		mockFactory = new MockStudyFactory();
		mockService = new MockAtomService(mockFactory);
		
		// bootstrap mock service with study feed
		((MockAtomService)mockService).createFeed(feedURL, "all studies");
		
		testController = new CreateStudyWidgetController(testModel, mockService, feedURL);

		//Replace default factory with MockFactory for testing
		testController.setStudyFactory(mockFactory);
		
		// instantiate a renderer
		testRenderer = new CreateStudyWidgetDefaultRenderer(new SimplePanel(), testController);
		
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
	
	public void testCreateStudyButton_UI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.createStudyUI );
		
		//test outcome
		assertTrue( CreateStudyWidgetModel.STATUS_SAVING == testModel.getStatus()
				    || CreateStudyWidgetModel.STATUS_SAVED == testModel.getStatus() );
	}
	
	public void testCancelCreateStudyButton_UI() {
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.cancelCreateStudyUI );
		
		// test outcome
		assertEquals(CreateStudyWidgetModel.STATUS_CANCELLED, testModel.getStatus());
	}
	
}
