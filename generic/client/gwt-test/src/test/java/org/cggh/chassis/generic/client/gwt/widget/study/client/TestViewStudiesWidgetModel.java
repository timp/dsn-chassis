/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudiesWidgetModelListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestViewStudiesWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestViewStudiesWidgetModel.class);
	}

	private ViewStudiesWidgetModel testModel;
	private List<StudyEntry> testStudies;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new ViewStudiesWidgetModel();
		
		//create list of test studyEntries
		testStudies = new ArrayList<StudyEntry>();
		
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
		
		testStudies.add(study1);
		testStudies.add(study2);
		
	}
	

	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), ViewStudiesWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), ViewStudiesWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), ViewStudiesWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(3), ViewStudiesWidgetModel.STATUS_ERROR);
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getStudyEntries());
		assertEquals(ViewStudiesWidgetModel.STATUS_INITIAL, testModel.getStatus());
	}
	
	@Test
	public void testGettersSetters() {
		
		//test data
		Integer status = ViewStudiesWidgetModel.STATUS_LOADED;	
		
		
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
		ViewStudiesWidgetModelListener listener = createMock(ViewStudiesWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStatusChanged(ViewStudiesWidgetModel.STATUS_INITIAL, ViewStudiesWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(ViewStudiesWidgetModel.STATUS_LOADING, ViewStudiesWidgetModel.STATUS_ERROR);
		replay(listener);
		
		//call methods under test
		testModel.setStatus(ViewStudiesWidgetModel.STATUS_LOADING);
		testModel.setStatus(ViewStudiesWidgetModel.STATUS_ERROR);
		

		//test outcome
		verify(listener);
		
	}
	
	@Test
	public void testOnStudyEntriesChanged() {
		
		//create listener mock
		ViewStudiesWidgetModelListener listener = createMock(ViewStudiesWidgetModelListener.class);
		testModel.addListener(listener);
		
		//set up expectations
		listener.onStudyEntriesChanged(null, testStudies);
		replay(listener);
		
		//call methods under test
		testModel.setStudyEntries(testStudies);
		
		verify(listener);
		
	}
	
}
