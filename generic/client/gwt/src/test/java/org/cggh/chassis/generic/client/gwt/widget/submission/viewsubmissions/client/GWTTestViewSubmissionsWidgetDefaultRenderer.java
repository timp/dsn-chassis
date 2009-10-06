/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.ViewSubmissionsWidgetDefaultRenderer.ViewSubmissionClickHandler;

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
		
		@Override
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
		
		//Set up ConfigurationBean with test values for controller
		ConfigurationBean.useUnitTestConfiguration = true;
		ConfigurationBean.testSubmissionFeedURL = "http://foo.com/submissions";
		ConfigurationBean.testSubmissionQueryServiceURL = "http://foo.com/submissions_query";
		
		//Create testModel
		testModel = new ViewSubmissionsWidgetModel();
						
		//create mockController
		mockController = new MockViewAllSubmissionsWidgetController(null, null);
		
		// instantiate a renderer
		testRenderer = new ViewSubmissionsWidgetDefaultRenderer(new SimplePanel(), mockController);
		
		//register as listener
		testModel.addListener(testRenderer);
		
		// use mock factory to create test submissions
		MockSubmissionFactory mockSubmissionFactory = new MockSubmissionFactory();
		SubmissionEntry submission1 = mockSubmissionFactory.createSubmissionEntry();
		submission1.setTitle("foo1");
		submission1.setSummary("bar 1");
		submission1.addModule("module foo1");
		SubmissionEntry submission2 = mockSubmissionFactory.createSubmissionEntry();
		submission2.setTitle("foo2");
		submission2.setSummary("bar 2");
		submission2.addModule("module foo2");
		
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
			
				
		//call method under test
		testRenderer.onSubmissionEntriesChanged(null, submissions);
		
		//test outcome
		//TODO test more rigorously?		
		assertTrue(testRenderer.submissionsListPanel.iterator().hasNext());
		
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
		
		//test data
		SubmissionEntry testSubmissionEntry = submissions.get(0);
		
		//create click handler
		ViewSubmissionClickHandler testClickHandler = testRenderer.new ViewSubmissionClickHandler(testSubmissionEntry);
		
		//simulate click event
		testClickHandler.onClick(null);
		
		//test outcome
		assertEquals(testSubmissionEntry, mockController.submissionEntry);
		
	}
	
	
}
