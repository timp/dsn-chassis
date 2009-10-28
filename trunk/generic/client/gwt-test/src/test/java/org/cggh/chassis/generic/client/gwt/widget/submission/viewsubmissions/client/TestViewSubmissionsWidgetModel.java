/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestViewSubmissionsWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestViewSubmissionsWidgetModel.class);
	}

	private ViewSubmissionsWidgetModel testModel;
	private List<SubmissionEntry> testSubmissions;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new ViewSubmissionsWidgetModel();
		
		//create list of test submissionEntries
		testSubmissions = new ArrayList<SubmissionEntry>();
		
		// use mock factory to create test submissions
		MockSubmissionFactory mockSubmissionFactory = new MockSubmissionFactory();
		SubmissionEntry submission1 = mockSubmissionFactory.createSubmissionEntry();
		submission1.setTitle("foo1");
		submission1.setSummary("bar 1");
		submission1.addModule("module foo1");
		SubmissionEntry submission2 = mockSubmissionFactory.createSubmissionEntry();
		submission2.setTitle("foo2");
		submission2.setSummary("bar 2");
		submission2.addModule("module foo2");
		
		testSubmissions.add(submission1);
		testSubmissions.add(submission2);
		
	}
	

	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), ViewSubmissionsWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), ViewSubmissionsWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), ViewSubmissionsWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(3), ViewSubmissionsWidgetModel.STATUS_ERROR);
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getSubmissionEntries());
		assertEquals(ViewSubmissionsWidgetModel.STATUS_INITIAL, testModel.getStatus());
	}
	
	@Test
	public void testGettersSetters() {
		
		//test data
		Integer status = ViewSubmissionsWidgetModel.STATUS_LOADED;	
		
		
		//call methods under test
		testModel.setStatus(status);
		testModel.setSubmissionEntries(testSubmissions);
		
		//test outcome
		assertEquals(status, testModel.getStatus());
		assertEquals(testSubmissions, testModel.getSubmissionEntries());
		
	}
	
	
	
	@Test
	public void testOnStatusChanged() {
		
		//create listener mock
		ViewSubmissionsWidgetModelListener listener = createMock(ViewSubmissionsWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStatusChanged(ViewSubmissionsWidgetModel.STATUS_INITIAL, ViewSubmissionsWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(ViewSubmissionsWidgetModel.STATUS_LOADING, ViewSubmissionsWidgetModel.STATUS_ERROR);
		replay(listener);
		
		//call methods under test
		testModel.setStatus(ViewSubmissionsWidgetModel.STATUS_LOADING);
		testModel.setStatus(ViewSubmissionsWidgetModel.STATUS_ERROR);
		

		//test outcome
		verify(listener);
		
	}
	
	@Test
	public void testOnSubmissionEntriesChanged() {
		
		//create listener mock
		ViewSubmissionsWidgetModelListener listener = createMock(ViewSubmissionsWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onSubmissionEntriesChanged(null, testSubmissions);
		replay(listener);
		
		//call methods under test
		testModel.setSubmissionEntries(testSubmissions);
		
		verify(listener);
		
	}
	
}
