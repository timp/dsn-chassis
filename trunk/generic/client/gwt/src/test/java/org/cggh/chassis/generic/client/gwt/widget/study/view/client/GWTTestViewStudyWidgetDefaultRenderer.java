/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewStudyWidgetDefaultRenderer extends GWTTestCase {

	private ViewStudyWidgetModel testModel;
	private ViewStudyWidgetDefaultRenderer testRenderer;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.view.ViewStudyWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//Create testController, inject testModel and a mock Service
		testModel = new ViewStudyWidgetModel();
						
		// instantiate a renderer
		testRenderer = new ViewStudyWidgetDefaultRenderer(new SimplePanel());
		
		//register as listener
		testModel.addListener(testRenderer);
		
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
		String title = "study foo";
		
		//call method under test
		testRenderer.onTitleChanged(null, title);
		
		//test outcome
		assertEquals(title, testRenderer.titleLabel.getText());
	}
	
	public void testOnSummaryChanged() {
		
		//test data
		String summary = "summary foo";
		
		//call method under test
		testRenderer.onSummaryChanged(null, summary);
		
		//test outcome
		assertEquals(summary, testRenderer.summaryLabel.getText());
		
	}
	
	public void testOnAcceptClinicalDataChanged() {
		
		//call method under test
		testRenderer.onAcceptClinicalDataChanged(null, true);
		
		assertTrue(testRenderer.acceptsClinicalDataIndicator.isVisible());
		
		//call method under test
		testRenderer.onAcceptClinicalDataChanged(true, false);
		
		assertFalse(testRenderer.acceptsClinicalDataIndicator.isVisible());
		
	}
	
	public void testOnAcceptInVitroDataChanged() {
		
		//call method under test
		testRenderer.onAcceptInVitroDataChanged(null, true);
		
		assertTrue(testRenderer.acceptsInVitroDataIndicator.isVisible());
		
		//call method under test
		testRenderer.onAcceptInVitroDataChanged(true, false);
		
		assertFalse(testRenderer.acceptsInVitroDataIndicator.isVisible());
		
	}
	
	public void testOnAcceptMolecularDataChanged() {
		
		//call method under test
		testRenderer.onAcceptMolecularDataChanged(null, true);
		
		assertTrue(testRenderer.acceptsMolecularDataIndicator.isVisible());
		
		//call method under test
		testRenderer.onAcceptMolecularDataChanged(true, false);
		
		assertFalse(testRenderer.acceptsMolecularDataIndicator.isVisible());
		
	}
	
	public void testOnAcceptPharmacologyDataChanged() {
		
		//call method under test
		testRenderer.onAcceptPharmacologyDataChanged(null, true);
		
		assertTrue(testRenderer.acceptsPharmacologyDataIndicator.isVisible());
		
		//call method under test
		testRenderer.onAcceptPharmacologyDataChanged(true, false);
		
		assertFalse(testRenderer.acceptsPharmacologyDataIndicator.isVisible());
		
	}
	
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(ViewStudyWidgetModel.STATUS_INITIAL, ViewStudyWidgetModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.studyDetailsPanel.getParent() == null)
		            || !(testRenderer.studyDetailsPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(ViewStudyWidgetModel.STATUS_LOADING, ViewStudyWidgetModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.studyDetailsPanel.getParent() != null)
	                 && (testRenderer.studyDetailsPanel.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
	
}
