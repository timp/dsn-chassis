/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidgetListBoxRenderer;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidgetListBoxRenderer.ViewStudyChangeHandler;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewStudiesWidgetListBoxRenderer extends GWTTestCase {

	private ViewStudiesWidgetModel testModel;
	private ViewStudiesWidgetListBoxRenderer testRenderer;
	private MockViewAllStudiesWidgetController mockController;
	private List<StudyEntry> studies;
	
	private class MockViewAllStudiesWidgetController extends ViewStudiesWidgetController {

		public MockViewAllStudiesWidgetController(ViewStudiesWidgetModel model, ViewStudiesWidget owner) {
			super(model, owner);
		}
		
		public StudyEntry onUserSelectStudy;
		
		@Override
		public void onUserSelectStudy(StudyEntry studyEntry) {
			this.onUserSelectStudy = studyEntry;
		}
		
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.ViewAllStudiesWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//Create testModel
		testModel = new ViewStudiesWidgetModel();
						
		//create mockController
		mockController = new MockViewAllStudiesWidgetController(null, null);
		
		// instantiate a renderer
		testRenderer = new ViewStudiesWidgetListBoxRenderer(new SimplePanel(), mockController);
		
		//register as listener
		testModel.addListener(testRenderer);
		
		// use mock factory to create test studies
		StudyFactory mockStudyFactory = new StudyFactory();
		StudyEntry study1 = mockStudyFactory.createEntry();
		study1.setTitle("foo1");
		study1.setSummary("bar 1");
		study1.getStudy().addModule("module foo1");
		StudyEntry study2 = mockStudyFactory.createEntry();
		study2.setTitle("foo2");
		study2.setSummary("bar 2");
		study2.getStudy().addModule("module foo2");
		
		studies = new ArrayList<StudyEntry>();
		studies.add(study1);
		studies.add(study2);
		
	}
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		assertTrue( (testRenderer.studiesListBox.getParent() == null)
					 || !(testRenderer.studiesListBox.isVisible()) );
		
	}
	
	//TODO handle case where no studyEntries found
	public void testOnStudyEntriesChanged() {
			
				
		//call method under test
		testRenderer.onStudyEntriesChanged(null, studies);
		
		//test outcome	
		assertEquals(studies.size(), testRenderer.studiesListBox.getItemCount());
		
	}
	
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(ViewStudiesWidgetModel.STATUS_INITIAL, ViewStudiesWidgetModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.studiesListBox.getParent() == null)
		            || !(testRenderer.studiesListBox.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(ViewStudiesWidgetModel.STATUS_LOADING, ViewStudiesWidgetModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.studiesListBox.getParent() != null)
	                 && (testRenderer.studiesListBox.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
	public void testOnViewStudySelected() {
		
		//test data
		Map<String, StudyEntry> studyEntryIdMap = new HashMap<String, StudyEntry>();
		String testStudyEntryId = "1234";
		StudyEntry testStudyEntry = studies.get(1);
		studyEntryIdMap.put(testStudyEntryId, testStudyEntry);
		
		//mock loaded state
		testRenderer.onStudyEntriesChanged(null, studies);
		
		//hack to set values, since testStudyEntries have no Id
		testRenderer.studiesListBox.setValue(1, testStudyEntryId);
		
		//create click handler
		ViewStudyChangeHandler testClickHandler = testRenderer.new ViewStudyChangeHandler(studyEntryIdMap);
		
		//get selected index of testStudyEntry
		int testIndexToSelect = 0;
		for (; testIndexToSelect < testRenderer.studiesListBox.getItemCount(); ++testIndexToSelect) {
						
			if ( testStudyEntryId.equalsIgnoreCase(testRenderer.studiesListBox.getValue(testIndexToSelect)) ) {
				break;
			}
			
		}	
		
		
		//simulate change event
		testRenderer.studiesListBox.setSelectedIndex(testIndexToSelect);
		testClickHandler.onChange(null);
		
		//test outcome
		assertEquals(testStudyEntry, mockController.onUserSelectStudy);
		
	}
	
	
}
