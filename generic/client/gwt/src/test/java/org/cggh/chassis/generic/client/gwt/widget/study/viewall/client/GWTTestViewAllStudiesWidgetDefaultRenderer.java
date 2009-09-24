/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.ViewAllStudiesWidgetDefaultRenderer.ViewStudyClickHandler;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewAllStudiesWidgetDefaultRenderer extends GWTTestCase {

	private ViewAllStudiesWidgetModel testModel;
	private ViewAllStudiesWidgetDefaultRenderer testRenderer;
	private MockViewAllStudiesWidgetController mockController;
	private List<StudyEntry> studies;
	
	private class MockViewAllStudiesWidgetController extends ViewAllStudiesWidgetController {

		public MockViewAllStudiesWidgetController(
				ViewAllStudiesWidgetModel model, AtomService service,
				ViewAllStudiesWidget owner) {
			super(model, service, owner, null);
		}
		
		public StudyEntry studyEntry;
		
		@Override
		public void onViewStudyUIClicked(StudyEntry studyEntry) {
			this.studyEntry = studyEntry;
		}
		
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.study.viewall.ViewAllStudiesWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//Create testModel
		testModel = new ViewAllStudiesWidgetModel();
						
		//create mockController
		mockController = new MockViewAllStudiesWidgetController(null, null, null);
		
		// instantiate a renderer
		testRenderer = new ViewAllStudiesWidgetDefaultRenderer(new SimplePanel(), mockController);
		
		//register as listener
		testModel.addListener(testRenderer);
		
		// use mock factory to create test studies
		MockStudyFactory mockStudyFactory = new MockStudyFactory();
		StudyEntry study1 = mockStudyFactory.createStudyEntry();
		study1.setTitle("foo1");
		study1.setSummary("bar 1");
		study1.addModule("module foo1");
		StudyEntry study2 = mockStudyFactory.createStudyEntry();
		study2.setTitle("foo2");
		study2.setSummary("bar 2");
		study2.addModule("module foo2");
		
		studies = new ArrayList<StudyEntry>();
		studies.add(study1);
		studies.add(study2);
		
	}
	
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		assertTrue( (testRenderer.studiesListPanel.getParent() == null)
					 || !(testRenderer.studiesListPanel.isVisible()) );
		
	}
	
	//TODO handle case where no studyEntries found
	public void testOnStudyEntriesChanged() {
			
				
		//call method under test
		testRenderer.onStudyEntriesChanged(null, studies);
		
		//test outcome
		//TODO test more rigorously?		
		assertTrue(testRenderer.studiesListPanel.iterator().hasNext());
		
	}
	
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(ViewAllStudiesWidgetModel.STATUS_INITIAL, ViewAllStudiesWidgetModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.studiesListPanel.getParent() == null)
		            || !(testRenderer.studiesListPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(ViewAllStudiesWidgetModel.STATUS_LOADING, ViewAllStudiesWidgetModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.studiesListPanel.getParent() != null)
	                 && (testRenderer.studiesListPanel.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
	public void testOnViewStudyClickHandler() {
		
		//test data
		StudyEntry testStudyEntry = studies.get(0);
		
		//create click handler
		ViewStudyClickHandler testClickHandler = testRenderer.new ViewStudyClickHandler(testStudyEntry);
		
		//simulate click event
		testClickHandler.onClick(null);
		
		//test outcome
		assertEquals(testStudyEntry, mockController.studyEntry);
		
	}
	
	
}
