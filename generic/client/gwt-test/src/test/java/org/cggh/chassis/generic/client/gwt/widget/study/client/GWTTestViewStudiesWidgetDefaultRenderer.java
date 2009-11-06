/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetController;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidgetDefaultRenderer.SelectStudyClickHandler;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author raok
 *
 */
public class GWTTestViewStudiesWidgetDefaultRenderer extends GWTTestCase {

	private MyStudiesWidgetModel testModel;
	private MyStudiesWidgetDefaultRenderer testRenderer;
	private MockViewAllStudiesWidgetController mockController;
	private List<StudyEntry> studies;
	
	private class MockViewAllStudiesWidgetController extends MyStudiesWidgetController {

		public MockViewAllStudiesWidgetController(MyStudiesWidgetModel model, MyStudiesWidget owner) {
			super(model, owner);
		}
		
		public StudyEntry studyEntry;
		
		@Override
		public void onUserSelectStudy(StudyEntry studyEntry) {
			this.studyEntry = studyEntry;
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
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//Create testModel
		testModel = new MyStudiesWidgetModel();
						
		//create mockController
		mockController = new MockViewAllStudiesWidgetController(null, null);
		
		// instantiate a renderer
		testRenderer = new MyStudiesWidgetDefaultRenderer(new SimplePanel(), mockController, "");
		
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
		testRenderer.onStatusChanged(MyStudiesWidgetModel.STATUS_INITIAL, MyStudiesWidgetModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.studiesListPanel.getParent() == null)
		            || !(testRenderer.studiesListPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(MyStudiesWidgetModel.STATUS_LOADING, MyStudiesWidgetModel.STATUS_LOADED);
		
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
		SelectStudyClickHandler testClickHandler = testRenderer.new SelectStudyClickHandler(testStudyEntry);
		
		//simulate click event
		testClickHandler.onClick(null);
		
		//test outcome
		assertEquals(testStudyEntry, mockController.studyEntry);
		
	}
	
	
}
