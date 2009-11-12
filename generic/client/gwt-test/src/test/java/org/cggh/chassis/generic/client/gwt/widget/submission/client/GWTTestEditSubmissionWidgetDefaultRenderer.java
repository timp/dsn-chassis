/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.EditSubmissionWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionControllerEditAPI;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestEditSubmissionWidgetDefaultRenderer extends GWTTestCase {
	
	private EditSubmissionWidgetDefaultRenderer testRenderer;
	private MockSubmissionController mockController;
	//test data
	String module1Id = TestConfigurationSetUp.module1Id;
	String module2Id = TestConfigurationSetUp.module2Id;
	String module1Label = TestConfigurationSetUp.module1Label;
	String module2Label = TestConfigurationSetUp.module2Label;

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submission.edit.EditSubmissionWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();

		//create mockController and inject into testRenderer
		mockController = new MockSubmissionController();
		
		testRenderer = new EditSubmissionWidgetDefaultRenderer(null); // TODO fix this
					
	}
	
	public void testInitialState() {
		
//		assertNotNull(testRenderer);
//		
//		assertEquals("", testRenderer.titleUI.getValue());
//		assertEquals("", testRenderer.summaryUI.getValue());
//		
//		//check modules checkboxes initialised, but unchecked
//		assertTrue(testRenderer.modulesUIHash.containsKey(module1Id));
//		assertFalse(testRenderer.modulesUIHash.get(module1Id).getValue());
//		assertTrue(testRenderer.modulesUIHash.containsKey(module2Id));
//		assertFalse(testRenderer.modulesUIHash.get(module2Id).getValue());
		
	}
	
	public void testOnTitleChanged() {
		
//		//test data
//		String title = "title foo";
//		
//		// call method under test
//		testRenderer.onTitleChanged(null, title, true);		
//		
//		
//		//test outcome
//		assertEquals(title, testRenderer.titleUI.getValue());
		
	}
	
	public void testTitleChanged_UI() {
		
//		//test data
//		String title = "title foo";
//		
//		//call method under test
//		testRenderer.titleUI.setValue(title, true);
//		
//		//test outcome
//		assertEquals(title, mockController.updateTitle);
		
	}
	
	public void testOnSummaryChanged() {
		
//		//test data
//		String summary = "summary foo";
//		
//		// call method under test
//		testRenderer.onSummaryChanged(null, summary, true);		
//		
//		
//		//test outcome
//		assertEquals(summary, testRenderer.summaryUI.getValue());
		
	}
	
	public void testSummaryChanged_UI() {
		
//		//test data
//		String summary = "summary foo";
//		
//		//call method under test
//		testRenderer.summaryUI.setValue(summary, true);
//		
//		//test outcome
//		assertEquals(summary, mockController.updateSummary);
		
	}
	
	public void testOnModulesChanged() {

//		//test data
//		Set<String> modulesSet1 = new HashSet<String>();
//		modulesSet1.add(module1Id);		
//
//		// call method under test
//		testRenderer.onModulesChanged(null, modulesSet1, true);		
//		
//		// test outcome
//		assertTrue(testRenderer.modulesUIHash.get(module1Id).getValue());	
//		assertFalse(testRenderer.modulesUIHash.get(module2Id).getValue());	
//		
//		//call method under test
//		testRenderer.onModulesChanged(null, new HashSet<String>(), true);		
//
//		// test outcome
//		assertFalse(testRenderer.modulesUIHash.get(module1Id).getValue());	
//		assertFalse(testRenderer.modulesUIHash.get(module2Id).getValue());	
		
	}

	public void testOnModulesChanged_UI() {
		
//		//test data
//		boolean isChecked = true;
//		
//		//call method under test
//		testRenderer.modulesUIHash.get(module1Id).setValue(isChecked, true);
//		
//		//test outcome
//		assertEquals(isChecked, mockController.updateModules.contains(module1Id));
		
	}
	
	public void testCancelEditSubmissionButton_UI() {
//		//simulate click event
//		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
//								 (HasHandlers)testRenderer.cancelEditSubmissionUI );
//		
//		// test outcome
//		assertTrue(mockController.cancelSaveOrUpdateSubmissionEntry);
	}
	
	
	public void testUpdateSubmissionButton_UI_formIncomplete() {
		
//		//call method under test
//		testRenderer.onSubmissionEntryChanged(false);
//		
//		//simulate click event
//		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
//								 (HasHandlers)testRenderer.updateSubmissionEntryUI );
//		
//
//		// test outcome
//		assertFalse(mockController.updateSubmissionEntryCalled);
				
	}
	
	public void testUpdateSubmissionButton_UI_formComplete() {
		
//		//call method under test
//		testRenderer.onSubmissionEntryChanged(true);
//		
//		//simulate click event
//		DomEvent.fireNativeEvent(Document.get().createClickEvent(0, 0, 0, 0, 0, false, false, false, false),
//								 (HasHandlers)testRenderer.updateSubmissionEntryUI );
//		
//		// test outcome
//		assertTrue(mockController.updateSubmissionEntryCalled);
				
	}
	

	//Define mock controller
	private class MockSubmissionController implements SubmissionControllerEditAPI {

		String addStudyLink;
		boolean cancelSaveOrUpdateSubmissionEntry;
		String removeStudyLink;
		boolean updateSubmissionEntryCalled;
		String updateSummary;
		String updateTitle;
		Set<String> updateModules;
		Set<AtomAuthor> updateAuthors;

		public void addStudyLink(String studyEntryURL) {
			this.addStudyLink = studyEntryURL;
		}

		public void cancelCreateOrUpdateSubmissionEntry() {
			this.cancelSaveOrUpdateSubmissionEntry = true;
		}

		public void removeStudyLink(String studyEntryURL) {
			this.removeStudyLink = studyEntryURL;
		}

		public void updateSubmissionEntry() {
			this.updateSubmissionEntryCalled = true;
		}

		public void updateSummary(String summary) {
			this.updateSummary = summary;
		}

		public void updateTitle(String title) {
			this.updateTitle = title;
		}

		public void updateModules(Set<String> modules) {
			this.updateModules= modules;
		}

		public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad) {
			// not tested here
		}

		public void loadSubmissionEntryByUrl(String SubmissionEntryURL) {
			// not tested here
		}

		public void updateAuthors(Set<AtomAuthor> authors) {
			this.updateAuthors = authors;
		}
		
	}
	
}
