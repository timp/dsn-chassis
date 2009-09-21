/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestCreateStudyWidgetModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestCreateStudyWidgetModel.class);
	}

	private CreateStudyWidgetModel testModel;
	
	@Before
	public void setUp() {
		
		//Create object to use in all tests
		testModel = new CreateStudyWidgetModel();
		
	}
	
	@Test
	public void testInitialState() {
		
		// test constructor
		assertNotNull(testModel);
		
		// test initial state
		assertEquals(0, testModel.getTitle().length());
		assertEquals(0, testModel.getSummary().length());
		assertFalse(testModel.acceptClinicalData());
		assertFalse(testModel.acceptMolecularData());
		assertFalse(testModel.acceptInVitroData());
		assertFalse(testModel.acceptPharmacologyData());
		assertEquals(CreateStudyWidgetModel.STATUS_INITIAL, testModel.getStatus());
		
		
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
	public void testTitleChanged() {
		
		// test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = "bar";
				
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		// allow calls on other methods
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
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		// allow calls on other methods
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
		
		testModel.setSummary(invalid);
		testModel.setSummary(valid);
		
		verify(listener);
		
	}
	
		
	@Test
	public void testModulesChanged() {
			
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		// allow calls on other methods
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
		
		testModel.setAcceptPharmacologyData(false);
		testModel.setAcceptPharmacologyData(true);
		
		verify(listener);
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), CreateStudyWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), CreateStudyWidgetModel.STATUS_READY);
		assertEquals(new Integer(2), CreateStudyWidgetModel.STATUS_SAVING);
		assertEquals(new Integer(3), CreateStudyWidgetModel.STATUS_SAVED);
		assertEquals(new Integer(4), CreateStudyWidgetModel.STATUS_SAVE_ERROR);
		assertEquals(new Integer(5), CreateStudyWidgetModel.STATUS_CANCELLED);
		
	}
	
	@Test
	public void testStatusChanged() {
				
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onStatusChanged(CreateStudyWidgetModel.STATUS_INITIAL, CreateStudyWidgetModel.STATUS_READY);
		listener.onStatusChanged(CreateStudyWidgetModel.STATUS_READY, CreateStudyWidgetModel.STATUS_SAVE_ERROR);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setStatus(CreateStudyWidgetModel.STATUS_READY);
		testModel.setStatus(CreateStudyWidgetModel.STATUS_SAVE_ERROR);
		
		verify(listener);
	}
	
	@Test
	public void testFormComplete() {
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		//set up expectations
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
		
		//call method under test
		//TODO test more rigorously
		testModel.setTitle("valid title");
		testModel.setSummary("valid summary");
		testModel.setAcceptClinicalData(true);
		
		verify(listener);
		
	}
	
}
