/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.client.gwt.widget.study.create.client.CreateStudyWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetModel;
import org.cggh.chassis.generic.client.gwt.widget.study.view.client.ViewStudyWidgetModelListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestViewStudyWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestViewStudyWidgetModel.class);
	}

	private ViewStudyWidgetModel testModel;
	
	@Before
	public void setUp() {
		
		//create test object
		testModel = new ViewStudyWidgetModel();
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getStudyEntry());
		assertEquals(CreateStudyWidgetModel.STATUS_INITIAL, testModel.getStatus());
	}
	
	@Test
	public void testGettersSetters() {
		
		//test data
		Integer status = ViewStudyWidgetModel.STATUS_LOADED;
		
		// use mock factory to create test studies
		MockStudyFactory mockStudyFactory = new MockStudyFactory();
		StudyEntry study = mockStudyFactory.createStudyEntry();
		study.setTitle("foo1");
		study.setSummary("bar 1");
		study.addModule("module foo1");
		
		//call methods under test
		testModel.setStudyEntry(study);
		testModel.setStatus(status);
		
		// test outcome
		assertEquals(study, testModel.getStudyEntry());
		assertEquals(status, testModel.getStatus());
		
	}
	
		
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), ViewStudyWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), ViewStudyWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), ViewStudyWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(3), ViewStudyWidgetModel.STATUS_ERROR);
		
	}
	
	@Test
	public void testOnStudyEntryChanged() {
		
		// use mock factory to create test studies
		MockStudyFactory mockStudyFactory = new MockStudyFactory();
		StudyEntry study = mockStudyFactory.createStudyEntry();
		study.setTitle("foo1");
		study.setSummary("bar 1");
		study.addModule("module foo1");
		
		ViewStudyWidgetModelListener listener = createMock(ViewStudyWidgetModelListener.class);
		
		//set up expectations
		listener.onStudyEntryChanged(null, study);
		replay(listener);
		
		//register with model
		testModel.addListener(listener);
		
		//call methods under test
		testModel.setStudyEntry(study);

		verify(listener);
		
	}
	
	@Test
	public void testOnStatusChanged() {
		
		ViewStudyWidgetModelListener listener = createMock(ViewStudyWidgetModelListener.class);
		
		//set up expectations
		listener.onStatusChanged(ViewStudyWidgetModel.STATUS_INITIAL, ViewStudyWidgetModel.STATUS_LOADING);
		listener.onStatusChanged(ViewStudyWidgetModel.STATUS_LOADING, ViewStudyWidgetModel.STATUS_ERROR);
		replay(listener);
		
		//register with model
		testModel.addListener(listener);
		
		//call methods under test
		testModel.setStatus(ViewStudyWidgetModel.STATUS_LOADING);
		testModel.setStatus(ViewStudyWidgetModel.STATUS_ERROR);
		
		verify(listener);
		
	}
	
}
