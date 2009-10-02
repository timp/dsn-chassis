/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewAllStudiesWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewAllStudiesWidgetModelListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestViewAllStudiesWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestViewAllStudiesWidgetModel.class);
	}

	private ViewAllStudiesWidgetModel testModel;
	private List<StudyEntry> testStudies;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new ViewAllStudiesWidgetModel();
		
		//create list of test studyEntries
		testStudies = new ArrayList<StudyEntry>();
		
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
		
		testStudies.add(study1);
		testStudies.add(study2);
		
	}
	

	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), ViewAllStudiesWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), ViewAllStudiesWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), ViewAllStudiesWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(3), ViewAllStudiesWidgetModel.STATUS_ERROR);
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getStudyEntries());
		assertEquals(ViewAllStudiesWidgetModel.STATUS_INITIAL, testModel.getStatus());
	}
	
	@Test
	public void testGettersSetters() {
		
		//test data
		Integer status = ViewAllStudiesWidgetModel.STATUS_LOADED;	
		
		
		//call methods under test
		testModel.setStatus(status);
		testModel.setStudyEntries(testStudies);
		
		//test outcome
		assertEquals(status, testModel.getStatus());
		assertEquals(testStudies, testModel.getStudyEntries());
		
	}
	
	
	
	@Test
	public void testOnStatusChanged() {
		
		//create listener mock
		ViewAllStudiesWidgetModelListener listener = createMock(ViewAllStudiesWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStatusChanged(ViewAllStudiesWidgetModel.STATUS_INITIAL, ViewAllStudiesWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(ViewAllStudiesWidgetModel.STATUS_LOADING, ViewAllStudiesWidgetModel.STATUS_ERROR);
		replay(listener);
		
		//call methods under test
		testModel.setStatus(ViewAllStudiesWidgetModel.STATUS_LOADING);
		testModel.setStatus(ViewAllStudiesWidgetModel.STATUS_ERROR);
		

		//test outcome
		verify(listener);
		
	}
	
	@Test
	public void testOnStudyEntriesChanged() {
		
		//create listener mock
		ViewAllStudiesWidgetModelListener listener = createMock(ViewAllStudiesWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStudyEntriesChanged(null, testStudies);
		replay(listener);
		
		//call methods under test
		testModel.setStudyEntries(testStudies);
		
		verify(listener);
		
	}
	
}
