/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestViewSubmissionDataFilesWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestViewSubmissionDataFilesWidgetModel.class);
	}

	private ViewSubmissionDataFilesWidgetModel testModel;
	private List<AtomEntry> testDataFilesAtomEntries;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new ViewSubmissionDataFilesWidgetModel();
		
		//create list of test submissionEntries
		testDataFilesAtomEntries = new ArrayList<AtomEntry>();
		
		// use mock factory to create test dataFileAtomEntries
		MockAtomFactory mockAtomFactory = new MockAtomFactory();
		AtomEntry file1 = mockAtomFactory.createEntry();
		file1.setTitle("foo1");
		file1.setSummary("bar 1");

		AtomEntry file2 = mockAtomFactory.createEntry();
		file2.setTitle("foo1");
		file2.setSummary("bar 1");
		
		testDataFilesAtomEntries.add(file1);
		testDataFilesAtomEntries.add(file2);
				
	}
	

	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), ViewSubmissionDataFilesWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), ViewSubmissionDataFilesWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), ViewSubmissionDataFilesWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(3), ViewSubmissionDataFilesWidgetModel.STATUS_ERROR);
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getFileDataAtomEntries());
		assertEquals(ViewSubmissionDataFilesWidgetModel.STATUS_INITIAL, testModel.getStatus());
	}
	
	@Test
	public void testGettersSetters() {
		
		//test data
		Integer status = ViewSubmissionDataFilesWidgetModel.STATUS_LOADED;	
		
		
		//call methods under test
		testModel.setStatus(status);
		testModel.setFileDataAtomEntries(testDataFilesAtomEntries);
		
		//test outcome
		assertEquals(status, testModel.getStatus());
		assertEquals(testDataFilesAtomEntries, testModel.getFileDataAtomEntries());
		
	}
	
	
	
	@Test
	public void testOnStatusChanged() {
		
		//create listener mock
		ViewSubmissionDataFilesWidgetModelListener listener = createMock(ViewSubmissionDataFilesWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStatusChanged(ViewSubmissionDataFilesWidgetModel.STATUS_INITIAL, ViewSubmissionDataFilesWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(ViewSubmissionDataFilesWidgetModel.STATUS_LOADING, ViewSubmissionDataFilesWidgetModel.STATUS_ERROR);
		replay(listener);
		
		//call methods under test
		testModel.setStatus(ViewSubmissionDataFilesWidgetModel.STATUS_LOADING);
		testModel.setStatus(ViewSubmissionDataFilesWidgetModel.STATUS_ERROR);
		

		//test outcome
		verify(listener);
		
	}
	
	@Test
	public void testOnSubmissionEntriesChanged() {
		
		//create listener mock
		ViewSubmissionDataFilesWidgetModelListener listener = createMock(ViewSubmissionDataFilesWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onDataFileAtomEntriesChanged(null, testDataFilesAtomEntries);
		replay(listener);
		
		//call methods under test
		testModel.setFileDataAtomEntries(testDataFilesAtomEntries);
		
		verify(listener);
		
	}
	
}
