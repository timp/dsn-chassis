/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;

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
		
	public void testOnStudyEntryChanged() {
		
		//test data
		String title = "title foo";
		String summary = "summary bar";
		
		//create test Study Entry to view
		StudyFactory mockFactory = new MockStudyFactory();
		StudyEntry testStudy = mockFactory.createStudyEntry();
		testStudy.setTitle(title);
		testStudy.setSummary(summary);
		testStudy.addModule(ViewStudyWidgetDefaultRenderer.MODULE_CLINICAL);
		testStudy.addModule(ViewStudyWidgetDefaultRenderer.MODULE_IN_VITRO);
		
		//call method under test
		testRenderer.onStudyEntryChanged(null, testStudy);
		
		//test outcome
		assertEquals(title, testRenderer.titleLabel.getText());
		assertEquals(summary, testRenderer.summaryLabel.getText());
		assertTrue(testRenderer.acceptsClinicalDataIndicator.isVisible());
		assertTrue(testRenderer.acceptsInVitroDataIndicator.isVisible());
		assertFalse(testRenderer.acceptsMolecularDataIndicator.isVisible());
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
