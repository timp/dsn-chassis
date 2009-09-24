/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI;

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
	
	private MockStudyController mockController;
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
		
		//create mockController and inject into testRenderer
		mockController = new MockStudyController();
		
		testRenderer = new CreateStudyWidgetDefaultRenderer(new SimplePanel(), mockController, null);
		
	}
	
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
				
		//check no values set
		assertEquals("", testRenderer.titleUI.getValue());
		assertEquals("", testRenderer.summaryUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptClinicalDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptMolecularDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptInVitroDataUI.getValue());
		assertEquals(Boolean.FALSE, testRenderer.acceptPharmacologyDataUI.getValue());
			
		
	}
	
	public void testOnTitleChanged() {
		
		//test data
		String title = "title foo";
		
		// call method under test
		testRenderer.onTitleChanged(null, title, true);		
		
		
		//test outcome
		assertEquals(title, testRenderer.titleUI.getValue());
		
	}
	
	public void testTitleChanged_UI() {
		
		//test data
		String title = "title foo";
		
		//call method under test
		testRenderer.titleUI.setValue(title, true);
		
		//test outcome
		assertEquals(title, mockController.updateTitle);
		
	}
	
	public void testOnSummaryChanged() {
		
		//test data
		String summary = "summary foo";
		
		// call method under test
		testRenderer.onSummaryChanged(null, summary, true);		
		
		
		//test outcome
		assertEquals(summary, testRenderer.summaryUI.getValue());
		
	}
	
	public void testSummaryChanged_UI() {
		
		//test data
		String summary = "summary foo";
		
		//call method under test
		testRenderer.summaryUI.setValue(summary, true);
		
		//test outcome
		assertEquals(summary, mockController.updateSummary);
		
	}
	
	public void testOnAcceptClinicalDataChanged() {
		
		//test data
		Boolean acceptClinicalData = true;
		
		// call method under test
		testRenderer.onAcceptClinicalDataChanged(null, acceptClinicalData, false);		
		
		
		//test outcome
		assertEquals(acceptClinicalData, testRenderer.acceptClinicalDataUI.getValue());
		
	}
	
	public void testAcceptClinicalDataChanged_UI() {
		
		//test data
		Boolean acceptClinicalData = true;
		
		//call method under test
		testRenderer.acceptClinicalDataUI.setValue(acceptClinicalData, true);
		
		//test outcome
		assertEquals(acceptClinicalData, mockController.updateAcceptClinicalData);
		
	}
	
	public void testOnAcceptInVitroDataChanged() {
		
		//test data
		Boolean acceptInVitroData = true;
		
		// call method under test
		testRenderer.onAcceptInVitroDataChanged(null, acceptInVitroData, false);		
		
		
		//test outcome
		assertEquals(acceptInVitroData, testRenderer.acceptInVitroDataUI.getValue());
		
	}
	
	public void testAcceptInVitroDataChanged_UI() {
		
		//test data
		Boolean acceptInVitroData = true;
		
		//call method under test
		testRenderer.acceptInVitroDataUI.setValue(acceptInVitroData, true);
		
		//test outcome
		assertEquals(acceptInVitroData, mockController.updateAcceptInVitroData);
		
	}
	
	public void testOnAcceptMolecularDataChanged() {
		
		//test data
		Boolean acceptMolecularData = true;
		
		// call method under test
		testRenderer.onAcceptMolecularDataChanged(null, acceptMolecularData, false);		
		
		
		//test outcome
		assertEquals(acceptMolecularData, testRenderer.acceptMolecularDataUI.getValue());
		
	}
	
	public void testAcceptMolecularDataChanged_UI() {
		
		//test data
		Boolean acceptMolecularData = true;
		
		//call method under test
		testRenderer.acceptMolecularDataUI.setValue(acceptMolecularData, true);
		
		//test outcome
		assertEquals(acceptMolecularData, mockController.updateAcceptMolecularData);
		
	}
	
	public void testOnAcceptPharmacologyDataChanged() {
		
		//test data
		Boolean acceptPharmacologyData = true;
		
		// call method under test
		testRenderer.onAcceptPharmacologyDataChanged(null, acceptPharmacologyData, false);		
		
		
		//test outcome
		assertEquals(acceptPharmacologyData, testRenderer.acceptPharmacologyDataUI.getValue());
		
	}
	
	public void testAcceptPharmacologyDataChanged_UI() {
		
		//test data
		Boolean acceptPharmacologyData = true;
		
		//call method under test
		testRenderer.acceptPharmacologyDataUI.setValue(acceptPharmacologyData, true);
		
		//test outcome
		assertEquals(acceptPharmacologyData, mockController.updateAcceptPharmacologyData);
		
	}
		
	public void testCancelCreateStudyButton_UI() {
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.cancelCreateStudyUI );
		
		// test outcome
		assertTrue(mockController.cancelSaveOrUpdateStudyEntry);
		
	}
	
	
	public void testSaveNewStudyEntryButtonUI_onStudyEntryChanged_valid() {
		
		//call method under test
		testRenderer.onStudyEntryChanged(true);
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.createStudyUI );
		
		// test outcome
		assertTrue(mockController.saveNewStudyEntryCalled);
		
	}
	
	public void testSaveNewStudyEntryButtonUI_onStudyEntryChanged_invalid() {
		
		//call method under test
		testRenderer.onStudyEntryChanged(false);
		
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.createStudyUI );
		
		// test outcome
		assertFalse(mockController.saveNewStudyEntryCalled);
		
	}
	
	
	//Define mock controller
	private class MockStudyController implements StudyControllerCreateAPI {

		boolean cancelSaveOrUpdateStudyEntry;
		boolean saveNewStudyEntryCalled;
		Boolean updateAcceptClinicalData;
		Boolean updateAcceptInVitroData;
		Boolean updateAcceptMolecularData;
		Boolean updateAcceptPharmacologyData;
		String updateSummary;
		String updateTitle;

		public void cancelSaveOrUpdateStudyEntry() {
			this.cancelSaveOrUpdateStudyEntry = true;
		}

		public void saveNewStudyEntry(String feedURL) {
			this.saveNewStudyEntryCalled = true;
		}

		public void setUpNewStudy() {
			// not tested here
		}

		public void updateAcceptClinicalData(Boolean acceptClinicalData) {
			this.updateAcceptClinicalData = acceptClinicalData;
		}

		public void updateAcceptInVitroData(Boolean acceptInVitroData) {
			this.updateAcceptInVitroData = acceptInVitroData;
		}

		public void updateAcceptMolecularData(Boolean acceptMolecularData) {
			this.updateAcceptMolecularData = acceptMolecularData;
		}

		public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData) {
			this.updateAcceptPharmacologyData = acceptPharmacologyData;
		}

		public void updateSummary(String summary) {
			this.updateSummary = summary;
		}

		public void updateTitle(String title) {
			this.updateTitle = title;
		}
		
	}
	
}
