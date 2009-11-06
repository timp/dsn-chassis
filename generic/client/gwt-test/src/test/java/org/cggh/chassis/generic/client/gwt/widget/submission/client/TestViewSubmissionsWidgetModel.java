/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.MySubmissionsWidgetModelListener;
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

	private MySubmissionsWidgetModel testModel;
	private List<SubmissionEntry> testSubmissions;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new MySubmissionsWidgetModel();
		
		//create list of test submissionEntries
		testSubmissions = new ArrayList<SubmissionEntry>();
		
		// use mock factory to create test submissions
		SubmissionFactory mockSubmissionFactory = new SubmissionFactory();
		SubmissionEntry submission1 = mockSubmissionFactory.createEntry();
		submission1.setTitle("foo1");
		submission1.setSummary("bar 1");
		submission1.getSubmission().addModule("module foo1");
		SubmissionEntry submission2 = mockSubmissionFactory.createEntry();
		submission2.setTitle("foo2");
		submission2.setSummary("bar 2");
		submission2.getSubmission().addModule("module foo2");
		
		testSubmissions.add(submission1);
		testSubmissions.add(submission2);
		
	}
	

	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), MySubmissionsWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), MySubmissionsWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), MySubmissionsWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(3), MySubmissionsWidgetModel.STATUS_ERROR);
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getSubmissionEntries());
		assertEquals(MySubmissionsWidgetModel.STATUS_INITIAL, testModel.getStatus());
	}
	
	@Test
	public void testGettersSetters() {
		
//		//test data
//		Integer status = ViewSubmissionsWidgetModel.STATUS_LOADED;	
//		
//		
//		//call methods under test
//		testModel.setStatus(status);
//		testModel.setSubmissionEntries(testSubmissions);
//		
//		//test outcome
//		assertEquals(status, testModel.getStatus());
//		assertEquals(testSubmissions, testModel.getSubmissionEntries());
		
	}
	
	
	
	@Test
	public void testOnStatusChanged() {
		
		//create listener mock
		MySubmissionsWidgetModelListener listener = createMock(MySubmissionsWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStatusChanged(MySubmissionsWidgetModel.STATUS_INITIAL, MySubmissionsWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(MySubmissionsWidgetModel.STATUS_LOADING, MySubmissionsWidgetModel.STATUS_ERROR);
		replay(listener);
		
		//call methods under test
		testModel.setStatus(MySubmissionsWidgetModel.STATUS_LOADING);
		testModel.setStatus(MySubmissionsWidgetModel.STATUS_ERROR);
		

		//test outcome
		verify(listener);
		
	}
	
	@Test
	public void testOnSubmissionEntriesChanged() {
		
//		//create listener mock
//		ViewSubmissionsWidgetModelListener listener = createMock(ViewSubmissionsWidgetModelListener.class);
//		testModel.addListener(listener);
//		
//		//set up expectations
//		listener.onSubmissionEntriesChanged(null, testSubmissions);
//		replay(listener);
//		
//		//call methods under test
//		testModel.setSubmissionEntries(testSubmissions);
//		
//		verify(listener);
		
	}
	
}
