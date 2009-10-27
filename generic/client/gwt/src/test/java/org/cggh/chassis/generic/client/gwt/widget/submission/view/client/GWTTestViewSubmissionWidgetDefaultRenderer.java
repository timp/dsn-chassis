/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.view.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
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
	String module1Id = TestConfigurationSetUp.module1Id;
	String module2Id = TestConfigurationSetUp.module2Id;
	String module1Label = TestConfigurationSetUp.module1Label;
	String module2Label = TestConfigurationSetUp.module2Label;
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
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();		

		//create mockController and inject into testRenderer
		mockController = new MockSubmissionController();	
		
		testRenderer = new ViewSubmissionWidgetDefaultRenderer(null); // TODO fix this
				

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
		
		assertTrue( (testRenderer.mainPanel.getParent() == null)
			        || !(testRenderer.mainPanel.isVisible()) );
		
	}
	
	public void testOnTitleChanged() {
		
		//test data
		String title = "title foo";
		
		// call method under test
		testRenderer.renderTitle(title);		
		
		
		//test outcome
		assertEquals(title, testRenderer.titleLabel.getText());
		
	}
	
	public void testOnSummaryChanged() {
		
		//test data
		String summary = "summary foo";
		
		// call method under test
//		testRenderer.onSummaryChanged(null, summary, true);		
		testRenderer.renderSummary(summary);		
		
		
		//test outcome
		assertEquals(summary, testRenderer.summaryLabel.getText());
		
	}
	
	public void testOnStudyLinksChanged() {
		
		//no testing required here.
		
	}
	
	public void testOnModulesChanged() {
		
		//test data
		List<String> modulesSet1 = new ArrayList<String>();
		modulesSet1.add("module1");		

		// call method under test
//		testRenderer.onModulesChanged(null, modulesSet1, true);	
		testRenderer.renderModules(modulesSet1);	
		
		
		// test outcome
		assertEquals(modulesSet1.size(), testRenderer.modulesListPanel.getWidgetCount());
		
	}
	
	public void testOnAuthorsChanged() {

		//test data
		StudyFactory testFactory = new MockStudyFactory();
		List<AtomAuthor> testAuthors = new ArrayList<AtomAuthor>();
		AtomAuthor testAtomAuthor = testFactory.createAuthor();
		testAtomAuthor.setEmail("foo@bar.com");
		testAuthors.add(testAtomAuthor);	

		// call method under test
//		testRenderer.onAuthorsChanged(null, testAuthors, true);		
		testRenderer.renderOwners(testAuthors);	
		
		// test outcome
		assertEquals(testAuthors.size(), testRenderer.ownersListPanel.getWidgetCount());
		
	}
	
		
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(SubmissionModel.STATUS_INITIAL, SubmissionModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.mainPanel.getParent() == null)
		            || !(testRenderer.mainPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(SubmissionModel.STATUS_LOADING, SubmissionModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.mainPanel.getParent() != null)
	                 && (testRenderer.mainPanel.isVisible()) );
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
