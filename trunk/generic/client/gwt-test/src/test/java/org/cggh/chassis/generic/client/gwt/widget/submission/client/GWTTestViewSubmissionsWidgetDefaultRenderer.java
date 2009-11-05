/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.ViewSubmissionsWidget;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.ViewSubmissionsWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.ViewSubmissionsWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.ViewSubmissionsWidgetModel;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewSubmissionsWidgetDefaultRenderer extends GWTTestCase {

	private ViewSubmissionsWidgetModel testModel;
	private ViewSubmissionsWidgetDefaultRenderer testRenderer;
	private MockViewAllSubmissionsWidgetController mockController;
	private List<SubmissionEntry> submissions;
	
	private class MockViewAllSubmissionsWidgetController extends ViewSubmissionsWidgetController {

		public MockViewAllSubmissionsWidgetController(ViewSubmissionsWidgetModel model,	ViewSubmissionsWidget owner) {
			super(model, owner);
		}
		
		public SubmissionEntry submissionEntry;
		
		public void onUserSelectSubmission(SubmissionEntry submissionEntry) {
			this.submissionEntry = submissionEntry;
		}
		
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.ViewSubmissionsWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//Create testModel
		testModel = new ViewSubmissionsWidgetModel();
						
		//create mockController
		mockController = new MockViewAllSubmissionsWidgetController(null, null);
		
		// instantiate a renderer
		testRenderer = new ViewSubmissionsWidgetDefaultRenderer(new SimplePanel(), mockController);
		
		//register as listener
		testModel.addListener(testRenderer);
		
		// use mock factory to create test submissions
		SubmissionFactory mockSubmissionFactory = new SubmissionFactory();
		SubmissionEntry submission1 = mockSubmissionFactory.createEntry();
		submission1.setTitle("foo1");
		submission1.setSummary("bar 1");
		submission1.getSubmission().addModule("module foo1");
		SubmissionEntry submission2 = mockSubmissionFactory.createEntry();
		submission2.setTitle("foo2");
		submission2.setSummary("bar 2");
		submission2.getSubmission().addModule("module foo2");
		
		submissions = new ArrayList<SubmissionEntry>();
		submissions.add(submission1);
		submissions.add(submission2);
		
	}
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		assertTrue( (testRenderer.submissionsListPanel.getParent() == null)
					 || !(testRenderer.submissionsListPanel.isVisible()) );
		
	}
	
	//TODO handle case where no submissionEntries found
	public void testOnSubmissionEntriesChanged() {
			
//				
//		//call method under test
//		testRenderer.onSubmissionEntriesChanged(null, submissions);
//		
//		//test outcome
//		//TODO test more rigorously?		
//		assertTrue(testRenderer.submissionsListPanel.iterator().hasNext());
		
	}
	
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(ViewSubmissionsWidgetModel.STATUS_INITIAL, ViewSubmissionsWidgetModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.submissionsListPanel.getParent() == null)
		            || !(testRenderer.submissionsListPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(ViewSubmissionsWidgetModel.STATUS_LOADING, ViewSubmissionsWidgetModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.submissionsListPanel.getParent() != null)
	                 && (testRenderer.submissionsListPanel.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
	public void testOnViewSubmissionClickHandler() {
		
//		//test data
//		SubmissionEntry testSubmissionEntry = submissions.get(0);
//		
//		//create click handler
//		ViewSubmissionClickHandler testClickHandler = testRenderer.new ViewSubmissionClickHandler(testSubmissionEntry);
//		
//		//simulate click event
//		testClickHandler.onClick(null);
//		
//		//test outcome
//		assertEquals(testSubmissionEntry, mockController.submissionEntry);
		
	}
	
	
}
