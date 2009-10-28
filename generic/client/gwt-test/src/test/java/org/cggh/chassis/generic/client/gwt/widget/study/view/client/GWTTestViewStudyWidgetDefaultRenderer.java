/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
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
	//test data
	String module1Id = TestConfigurationSetUp.module1Id;
	String module2Id = TestConfigurationSetUp.module2Id;
	String module1Label = TestConfigurationSetUp.module1Label;
	String module2Label = TestConfigurationSetUp.module2Label;
	private StudyEntry testStudyEntry;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.view.ViewStudyWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();		

		//create mockController and inject into testRenderer
		mockController = new MockStudyController();			
		
		testRenderer = new ViewStudyWidgetDefaultRenderer(new SimplePanel(), mockController);
				

		//create testStudyEntry to load
		MockStudyFactory mockFactory = new MockStudyFactory();
		testStudyEntry = mockFactory.createStudyEntry();
		testStudyEntry.setTitle("title");
		testStudyEntry.setSummary("summary");	
		testStudyEntry.addModule(module1Id);
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
	
	public void testOnModulesChanged() {
		
		//test data
		Set<String> modulesSet1 = new HashSet<String>();
		modulesSet1.add("module1");		

		// call method under test
		testRenderer.onModulesChanged(null, modulesSet1, true);		
		
		// test outcome
		assertEquals(modulesSet1.size(), testRenderer.modulesListPanel.getWidgetCount());
		
	}
	
	public void testOnAuthorsChanged() {

		//test data
		StudyFactory testFactory = new MockStudyFactory();
		Set<AtomAuthor> testAuthors = new HashSet<AtomAuthor>();
		AtomAuthor testAtomAuthor = testFactory.createAuthor();
		testAtomAuthor.setEmail("foo@bar.com");
		testAuthors.add(testAtomAuthor);	

		// call method under test
		testRenderer.onAuthorsChanged(null, testAuthors, true);		
		
		// test outcome
		assertEquals(testAuthors.size(), testRenderer.ownersListPanel.getWidgetCount());
		
	}
	
		
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(StudyModel.STATUS_INITIAL, StudyModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.mainPanel.getParent() == null)
		            || !(testRenderer.mainPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(StudyModel.STATUS_LOADING, StudyModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.mainPanel.getParent() != null)
	                 && (testRenderer.mainPanel.isVisible()) );
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

		public void fireOnUserActionEditThisStudy() {
			this.studyEntryToEditFired  = true;
		}

		public void loadStudyEntry(StudyEntry submissionEntryToLoad) {
			// not tested here
		}

		public void loadStudyEntryByURL(String studyEntryURL) {
			// not tested here
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#fireOnUserActionEditStudyQuestionnaire()
		 */
		public void fireOnUserActionEditStudyQuestionnaire() {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#fireOnUserActionViewStudyQuestionnaire()
		 */
		public void fireOnUserActionViewStudyQuestionnaire() {
			// TODO Auto-generated method stub
			
		}
		
	}
		
	
}
