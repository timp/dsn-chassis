/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
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
	String module1Id = "module1Id";
	String module2Id = "module2Id";
	String module1Label = "module1Label";
	String module2Label = "module2Label";
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
		

		//create mockController and inject into testRenderer
		mockController = new MockStudyController();				

		//Create modules test data
		Map<String, String> testModules = new HashMap<String, String>();
		testModules.put(module1Id, module1Label);
		testModules.put(module2Id, module2Label);
		
		testRenderer = new ViewStudyWidgetDefaultRenderer(new SimplePanel(), mockController, testModules);
				

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
