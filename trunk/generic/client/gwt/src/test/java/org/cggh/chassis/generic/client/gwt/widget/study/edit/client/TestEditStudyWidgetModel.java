/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestEditStudyWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestEditStudyWidgetModel.class);
	}

	private EditStudyWidgetModel testModel;
	private StudyEntry studyEntry;
	
	@Before
	public void setUp() {
		
		//Edit object to use in all tests
		testModel = new EditStudyWidgetModel();
		
		//create mock studyEntry to load
		MockStudyFactory mockFactory = new MockStudyFactory();
		studyEntry = mockFactory.createStudyEntry();
		studyEntry.setTitle("");
		studyEntry.setSummary("");
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertNull(testModel.getStudyEntry());
		assertEquals(EditStudyWidgetModel.STATUS_INITIAL, testModel.getStatus());
		
		
	}
	
	@Test
	public void testGettersSetters() {
		
		//test data
		String title = "new title";
		String summary = "summary";
		Boolean acceptClinicalData = true;
		Boolean acceptMolecularData = true;
		Boolean acceptInVitroData = true;
		Boolean acceptPharmacologyData = true;
		
		//mock loaded study entry
		testModel.setStudyEntry(studyEntry);
		
		// call methods under test
		testModel.setTitle(title);
		testModel.setSummary(summary);
		testModel.setAcceptClinicalData(acceptClinicalData);
		testModel.setAcceptMolecularData(acceptMolecularData);
		testModel.setAcceptInVitroData(acceptInVitroData);
		testModel.setAcceptPharmacologyData(acceptPharmacologyData);
		
		// test outcome
		assertEquals(title, testModel.getTitle());
		assertEquals(summary, testModel.getSummary());
		assertEquals(acceptClinicalData, testModel.acceptClinicalData());
		assertEquals(acceptMolecularData, testModel.acceptMolecularData());
		assertEquals(acceptInVitroData, testModel.acceptInVitroData());
		assertEquals(acceptPharmacologyData, testModel.acceptPharmacologyData());
		
	}
	
	@Test
	public void testStudyEntryChanged() {
		
		//create mock
		EditStudyWidgetModelListener listener = createMock(EditStudyWidgetModelListener.class);
		
		//set up expectations
		listener.onStudyEntryChanged(null, studyEntry);
		replay(listener);
		
		testModel.addListener(listener);
		
		//call method under test
		testModel.setStudyEntry(studyEntry);
		
		verify(listener);
		
	}
	
	@Test
	public void testTitleChanged() {
		
		// test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = "bar";
				
		EditStudyWidgetModelListener listener = createMock(EditStudyWidgetModelListener.class);
		
		// set up expectations
		// allow calls on other methods
		listener.onStudyEntryChanged(null, studyEntry);
		expectLastCall().anyTimes();
		listener.onSummaryChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onFormCompleted(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onModulesChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		
		//strict expectations on below
		listener.onTitleChanged(Boolean.FALSE);
		listener.onTitleChanged(Boolean.TRUE);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		//mock loaded study entry
		testModel.setStudyEntry(studyEntry);
		
		// call method under test
		testModel.setTitle(invalid);
		testModel.setTitle(valid);
		
		verify(listener);
		
	}
	
	@Test
	public void testSummaryChanged() {
		
		// test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = "bar";
		
		EditStudyWidgetModelListener listener = createMock(EditStudyWidgetModelListener.class);
		
		// set up expectations
		// allow calls on other methods
		listener.onStudyEntryChanged(null, studyEntry);
		expectLastCall().anyTimes();
		listener.onTitleChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onFormCompleted(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onModulesChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		
		//strict expectations on below
		listener.onSummaryChanged(Boolean.FALSE);
		listener.onSummaryChanged(Boolean.TRUE);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		//mock loaded study entry
		testModel.setStudyEntry(studyEntry);
		
		testModel.setSummary(invalid);
		testModel.setSummary(valid);
		
		verify(listener);
		
	}
	
		
	@Test
	public void testModulesChanged() {
			
		
		EditStudyWidgetModelListener listener = createMock(EditStudyWidgetModelListener.class);
		
		// set up expectations
		// allow calls on other methods
		listener.onStudyEntryChanged(null, studyEntry);
		expectLastCall().anyTimes();
		listener.onTitleChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onFormCompleted(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onSummaryChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		
		//strict expectations on below
		listener.onModulesChanged(Boolean.FALSE);
		listener.onModulesChanged(Boolean.TRUE);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		//mock loaded study entry
		testModel.setStudyEntry(studyEntry);
		
		testModel.setAcceptPharmacologyData(false);
		testModel.setAcceptPharmacologyData(true);
		
		verify(listener);
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), EditStudyWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), EditStudyWidgetModel.STATUS_LOADING);
		assertEquals(new Integer(2), EditStudyWidgetModel.STATUS_LOAD_ERROR);
		assertEquals(new Integer(3), EditStudyWidgetModel.STATUS_LOADED);
		assertEquals(new Integer(4), EditStudyWidgetModel.STATUS_SAVING);
		assertEquals(new Integer(5), EditStudyWidgetModel.STATUS_SAVED);
		assertEquals(new Integer(6), EditStudyWidgetModel.STATUS_SAVE_ERROR);
		assertEquals(new Integer(7), EditStudyWidgetModel.STATUS_CANCELLED);
		
	}
	
	@Test
	public void testStatusChanged() {
				
		EditStudyWidgetModelListener listener = createMock(EditStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onStatusChanged(EditStudyWidgetModel.STATUS_INITIAL, EditStudyWidgetModel.STATUS_LOADED);
		listener.onStatusChanged(EditStudyWidgetModel.STATUS_LOADED, EditStudyWidgetModel.STATUS_SAVE_ERROR);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setStatus(EditStudyWidgetModel.STATUS_LOADED);
		testModel.setStatus(EditStudyWidgetModel.STATUS_SAVE_ERROR);
		
		verify(listener);
	}
	
	@Test
	public void testFormComplete() {
		
		EditStudyWidgetModelListener listener = createMock(EditStudyWidgetModelListener.class);
		
		//set up expectations
		listener.onStudyEntryChanged(null, studyEntry);
		expectLastCall().anyTimes();
		listener.onTitleChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onSummaryChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onModulesChanged(isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onFormCompleted(Boolean.FALSE);
		listener.onFormCompleted(Boolean.FALSE);
		listener.onFormCompleted(Boolean.TRUE);
		replay(listener);
		

		// register with model
		testModel.addListener(listener);
		
		//mock loaded study entry
		testModel.setStudyEntry(studyEntry);
		
		//call method under test
		//TODO test more rigorously
		testModel.setTitle("valid title");
		testModel.setSummary("valid summary");
		testModel.setAcceptClinicalData(true);
		
		verify(listener);
		
	}
	
}
