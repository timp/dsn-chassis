/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewStudyWidgetDefaultRenderer extends GWTTestCase {

	private ViewStudyWidgetDefaultRenderer testRenderer;
	private MockStudyController mockController;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.view.ViewStudyWidget";
	}

	@Override
	protected void gwtSetUp() {
		

		//create mockController and inject into testRenderer
		mockController = new MockStudyController();
				
		testRenderer = new ViewStudyWidgetDefaultRenderer(new SimplePanel(), mockController);
				
	}
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
				
		assertEquals("", testRenderer.titleLabel.getText());
		assertEquals("", testRenderer.summaryLabel.getText());
		
		assertTrue( (testRenderer.acceptsClinicalDataIndicator.getParent() == null)
					 || !(testRenderer.acceptsClinicalDataIndicator.isVisible()) );
		
		assertTrue( (testRenderer.acceptsMolecularDataIndicator.getParent() == null)
					 || !(testRenderer.acceptsMolecularDataIndicator.isVisible()) );
		
		assertTrue( (testRenderer.acceptsInVitroDataIndicator.getParent() == null)
				    || !(testRenderer.acceptsInVitroDataIndicator.isVisible()) );
		
		assertTrue( (testRenderer.acceptsPharmacologyDataIndicator.getParent() == null)
			    	|| !(testRenderer.acceptsPharmacologyDataIndicator.isVisible()) );

		assertTrue( (testRenderer.loadingPanel.getParent() == null)
			        || !(testRenderer.loadingPanel.isVisible()) );
		
		assertTrue( (testRenderer.studyDetailsPanel.getParent() == null)
			        || !(testRenderer.studyDetailsPanel.isVisible()) );
		
	}
	
	public void testOnTitleChanged() {
		
		//test data
		String title = "title foo";
		
		// call method under test
		testRenderer.onTitleChanged(null, title, true);		
		
		
		//test outcome
		assertEquals(title, testRenderer.titleLabel.getText());
		
	}
	
	public void testOnSummaryChanged() {
		
		//test data
		String summary = "summary foo";
		
		// call method under test
		testRenderer.onSummaryChanged(null, summary, true);		
		
		
		//test outcome
		assertEquals(summary, testRenderer.summaryLabel.getText());
		
	}
	
	public void testOnAcceptClinicalDataChanged() {
		
		//test data
		Boolean acceptClinicalData = true;
		
		// call method under test
		testRenderer.onAcceptClinicalDataChanged(null, acceptClinicalData, false);		
		
		
		//test outcome
		assertTrue( (testRenderer.acceptsClinicalDataIndicator.getParent() != null)
					 && (testRenderer.acceptsClinicalDataIndicator.isVisible()) );
	}
	
	public void testOnAcceptMolecularDataChanged() {
		
		//test data
		Boolean acceptMolecularData = true;
		
		// call method under test
		testRenderer.onAcceptMolecularDataChanged(null, acceptMolecularData, false);		
		
		
		//test outcome
		assertTrue( (testRenderer.acceptsMolecularDataIndicator.getParent() != null)
				 && (testRenderer.acceptsMolecularDataIndicator.isVisible()) );
	}
	
	public void testOnAcceptPharmacologyDataChanged() {
		
		//test data
		Boolean acceptPharmacologyData = true;
		
		// call method under test
		testRenderer.onAcceptPharmacologyDataChanged(null, acceptPharmacologyData, false);		
		
		
		//test outcome
		assertTrue( (testRenderer.acceptsPharmacologyDataIndicator.getParent()  != null)
				 && (testRenderer.acceptsPharmacologyDataIndicator.isVisible()) );
	}
	
	public void testOnAcceptInVitroDataChanged() {
		
		//test data
		Boolean acceptInVitroData = true;
		
		// call method under test
		testRenderer.onAcceptInVitroDataChanged(null, acceptInVitroData, false);		
		
		
		//test outcome
		assertTrue( (testRenderer.acceptsInVitroDataIndicator.getParent() != null)
				 && (testRenderer.acceptsInVitroDataIndicator.isVisible()) );
	}
	
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(StudyModel.STATUS_INITIAL, StudyModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.studyDetailsPanel.getParent() == null)
		            || !(testRenderer.studyDetailsPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(StudyModel.STATUS_LOADING, StudyModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.studyDetailsPanel.getParent() != null)
	                 && (testRenderer.studyDetailsPanel.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
	public void testOnEditStudyClicked() {
				
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.editThisStudyUI);
		
		
		assertTrue(mockController.studyEntryToEditFired);
		
		
	}

	//mock MockStudyController
	private class MockStudyController implements StudyControllerViewAPI {
		
		private boolean studyEntryToEditFired = false;

		public void onUserActionEditThisStudy() {
			this.studyEntryToEditFired  = true;
		}

		public void loadStudyEntry(StudyEntry submissionEntryToLoad) {
			// not tested here
		}

		public void loadStudyEntryByURL(String studyEntryURL) {
			// not tested here
		}
		
	}
		
	
}
