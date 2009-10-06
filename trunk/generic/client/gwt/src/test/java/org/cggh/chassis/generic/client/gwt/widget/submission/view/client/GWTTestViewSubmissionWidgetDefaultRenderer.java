/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewSubmissionWidgetDefaultRenderer extends GWTTestCase {

	private ViewSubmissionWidgetDefaultRenderer testRenderer;
	private MockSubmissionController mockController;
	
	//test data
	String module1Id = "module1Id";
	String module2Id = "module2Id";
	String module1Label = "module1Label";
	String module2Label = "module2Label";
	private SubmissionEntry testSubmissionEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submission.view.ViewSubmissionWidget";
	}

	@Override
	protected void gwtSetUp() {
		

		//create mockController and inject into testRenderer
		mockController = new MockSubmissionController();				

		//Create modules test data
		Map<String, String> testModules = new HashMap<String, String>();
		testModules.put(module1Id, module1Label);
		testModules.put(module2Id, module2Label);

		//Set up ConfigurationBean with test values
		ConfigurationBean.useUnitTestConfiguration = true;
		ConfigurationBean.testModules = testModules;
		
		testRenderer = new ViewSubmissionWidgetDefaultRenderer(new SimplePanel(), mockController);
				

		//create testSubmissionEntry to load
		MockSubmissionFactory mockFactory = new MockSubmissionFactory();
		testSubmissionEntry = mockFactory.createSubmissionEntry();
		testSubmissionEntry.setTitle("title");
		testSubmissionEntry.setSummary("summary");	
		testSubmissionEntry.addModule(module1Id);
		testSubmissionEntry.addStudyLink("http://foo.com/studies/study1");
	}
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
				
		assertEquals("", testRenderer.titleLabel.getText());
		assertEquals("", testRenderer.summaryLabel.getText());
		
		assertTrue(testRenderer.modulesListPanel.getWidgetCount() == 0);

		assertTrue( (testRenderer.loadingPanel.getParent() == null)
			        || !(testRenderer.loadingPanel.isVisible()) );
		
		assertTrue( (testRenderer.submissionDetailsPanel.getParent() == null)
			        || !(testRenderer.submissionDetailsPanel.isVisible()) );
		
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
	
	public void testOnStudyLinksChanged() {
		
		//no testing required here.
		
	}
	
	public void testOnModulesChanged() {
		
		//test data
		Set<String> modulesSet1 = new HashSet<String>();
		modulesSet1.add("module1");		

		// call method under test
		testRenderer.onModulesChanged(null, modulesSet1, true);		
		
		// test outcome
		assertEquals(modulesSet1.size(), testRenderer.modulesListPanel.getWidgetCount());
		
	}
	
		
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(SubmissionModel.STATUS_INITIAL, SubmissionModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.submissionDetailsPanel.getParent() == null)
		            || !(testRenderer.submissionDetailsPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(SubmissionModel.STATUS_LOADING, SubmissionModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.submissionDetailsPanel.getParent() != null)
	                 && (testRenderer.submissionDetailsPanel.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
	public void testOnEditSubmissionClicked() {
				
		//simulate click event
		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
								 (HasHandlers)testRenderer.editThisSubmissionUI);
		
		
		assertTrue(mockController.onUserActionEditThisSubmission);
		
		
	}

	//mock MockSubmissionController
	private class MockSubmissionController implements SubmissionControllerViewAPI {
		
		private boolean onUserActionEditThisSubmission = false;

		public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad) {
			// not tested here
		}

		public void loadSubmissionEntryByURL(String SubmissionEntryURL) {
			// not tested here
		}

		public void onUserActionEditThisSubmission() {
			this.onUserActionEditThisSubmission = true;
		}
		
	}
		
	
}
