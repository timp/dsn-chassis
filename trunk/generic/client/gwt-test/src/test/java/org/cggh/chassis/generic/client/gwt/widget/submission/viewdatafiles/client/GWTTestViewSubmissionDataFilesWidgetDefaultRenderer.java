/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.client.gwt.configuration.client.TestConfigurationSetUp;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author raok
 *
 */
public class GWTTestViewSubmissionDataFilesWidgetDefaultRenderer extends GWTTestCase {

	private ViewSubmissionDataFilesWidgetModel testModel;
	private ViewSubmissionDataFilesWidgetDefaultRenderer testRenderer;
	private List<AtomEntry> testDataFileEntries;
	private ViewSubmissionDataFilesWidgetController testController;
		
	private MockAtomFactory testFactory = new MockAtomFactory();
	
	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		return "org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.ViewSubmissionDataFilesWidget";
	}

	@Override
	protected void gwtSetUp() {
		
		//setup ConfigurationBean
		TestConfigurationSetUp.createTestConfiguration();
		
		//Create testModel
		testModel = new ViewSubmissionDataFilesWidgetModel();
						
		//create testController
		testController = new ViewSubmissionDataFilesWidgetController(null, null);
		
		// instantiate a renderer
		testRenderer = new ViewSubmissionDataFilesWidgetDefaultRenderer(testController);
		
		//register as listener
		testModel.addListener(testRenderer);
		
		//create test Submission Entries to view
		AtomEntry testFileEntry1 = testFactory.createEntry();
		testFileEntry1.setTitle("file.txt");
		testFileEntry1.setSummary("summary foo");

		AtomEntry testFileEntry2 = testFactory.createEntry();
		testFileEntry2.setTitle("file.txt");
		testFileEntry2.setSummary("summary foo");
		
		//test Submissions for mockFeed to return
		testDataFileEntries = new ArrayList<AtomEntry>();
		testDataFileEntries.add(testFileEntry1);
		testDataFileEntries.add(testFileEntry2);
		
	}
	
	//TODO create loading panel
	public void testInitialState() {
		
		assertNotNull(testRenderer);
		
		assertTrue( (testRenderer.submissionDataFilesListPanel.getParent() == null)
					 || !(testRenderer.submissionDataFilesListPanel.isVisible()) );
		
	}
	
	//TODO handle case where no dataFiles found
	public void testOnSubmissionEntriesChanged() {
			
				
		//call method under test
		testRenderer.onDataFileAtomEntriesChanged(null, testDataFileEntries);
		
		//test outcome
		//TODO test more rigorously?		
		assertTrue(testRenderer.submissionDataFilesListPanel.iterator().hasNext());
		
	}
	
	public void testOnStatusChanged() {
				
		//mock loading state
		testRenderer.onStatusChanged(ViewSubmissionDataFilesWidgetModel.STATUS_INITIAL, ViewSubmissionDataFilesWidgetModel.STATUS_LOADING);
		
		//check loading state
		assertTrue( (testRenderer.loadingPanel.getParent() != null)
		            && (testRenderer.loadingPanel.isVisible()) );
		assertTrue( (testRenderer.submissionDataFilesListPanel.getParent() == null)
		            || !(testRenderer.submissionDataFilesListPanel.isVisible()) );
		
		//mock loaded state
		testRenderer.onStatusChanged(ViewSubmissionDataFilesWidgetModel.STATUS_LOADING, ViewSubmissionDataFilesWidgetModel.STATUS_LOADED);
		
		//check loaded state
		assertTrue( (testRenderer.submissionDataFilesListPanel.getParent() != null)
	                 && (testRenderer.submissionDataFilesListPanel.isVisible()) );
		assertTrue( (testRenderer.loadingPanel.getParent() == null)
	                 || !(testRenderer.loadingPanel.isVisible()) );
		
		
	}
	
}
