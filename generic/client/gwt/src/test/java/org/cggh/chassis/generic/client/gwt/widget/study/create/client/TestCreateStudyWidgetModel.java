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
		assertNull(testModel.getTitle());
		assertNull(testModel.getSummary());
		assertNull(testModel.acceptClinicalData());
		assertNull(testModel.acceptMolecularData());
		assertNull(testModel.acceptInVitroData());
		assertNull(testModel.acceptPharmacologyData());
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
		String foo = "foo";
		String bar = "bar";
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onTitleChanged(null, foo);
		listener.onTitleChanged(foo, bar);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setTitle(foo);
		testModel.setTitle(bar);
		
		verify(listener);
		
	}
	
	@Test
	public void testSummaryChanged() {
		
		// test data
		String foo = "foo";
		String bar = "bar";
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onSummaryChanged(null, foo);
		listener.onSummaryChanged(foo, bar);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setSummary(foo);
		testModel.setSummary(bar);
		
		verify(listener);
		
	}
	
	@Test
	public void testAcceptClinicalDataChanged() {
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onAcceptClinicalDataChanged(null, true);
		listener.onAcceptClinicalDataChanged(true, false);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setAcceptClinicalData(true);
		testModel.setAcceptClinicalData(false);
		
		verify(listener);
		
	}
	
	@Test
	public void testAcceptMolecularDataChanged() {
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onAcceptMolecularDataChanged(null, true);
		listener.onAcceptMolecularDataChanged(true, false);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setAcceptMolecularData(true);
		testModel.setAcceptMolecularData(false);
		
		verify(listener);
		
	}
	
	@Test
	public void testAcceptInVitroDataChanged() {
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onAcceptInVitroDataChanged(null, true);
		listener.onAcceptInVitroDataChanged(true, false);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setAcceptInVitroData(true);
		testModel.setAcceptInVitroData(false);
		
		verify(listener);
		
	}
	
	@Test
	public void testAcceptPharmacologyDataChanged() {
		
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onAcceptPharmacologyDataChanged(null, true);
		listener.onAcceptPharmacologyDataChanged(true, false);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setAcceptPharmacologyData(true);
		testModel.setAcceptPharmacologyData(false);
		
		verify(listener);
		
	}
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), CreateStudyWidgetModel.STATUS_INITIAL);
		assertEquals(new Integer(1), CreateStudyWidgetModel.STATUS_READY);
		assertEquals(new Integer(2), CreateStudyWidgetModel.STATUS_SAVING);
		assertEquals(new Integer(3), CreateStudyWidgetModel.STATUS_SAVED);
		assertEquals(new Integer(4), CreateStudyWidgetModel.STATUS_ERROR);
		assertEquals(new Integer(5), CreateStudyWidgetModel.STATUS_CANCELLED);
		
	}
	
	@Test
	public void testStatusChanged() {
				
		CreateStudyWidgetModelListener listener = createMock(CreateStudyWidgetModelListener.class);
		
		// set up expectations
		listener.onStatusChanged(CreateStudyWidgetModel.STATUS_INITIAL, CreateStudyWidgetModel.STATUS_READY);
		listener.onStatusChanged(CreateStudyWidgetModel.STATUS_READY, CreateStudyWidgetModel.STATUS_ERROR);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		testModel.setStatus(CreateStudyWidgetModel.STATUS_READY);
		testModel.setStatus(CreateStudyWidgetModel.STATUS_ERROR);
		
		verify(listener);
	}
	
}
