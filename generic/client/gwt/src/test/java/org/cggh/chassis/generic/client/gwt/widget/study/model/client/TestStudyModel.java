/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.model.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModelListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestStudyModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestStudyModel.class);
	}

	private StudyModel testModel;
	private StudyEntry newStudyEntry;
	private MockStudyFactory mockStudyFactory;
	private StudyEntry validStudyEntry;
	
	@Before
	public void setUp() {
		
		//Create object to use in all tests
		testModel = new StudyModel();
		
		//create new study entry
		mockStudyFactory = new MockStudyFactory();
		newStudyEntry = mockStudyFactory.createStudyEntry();
		
		//create a valid study entry
		validStudyEntry = mockStudyFactory.createStudyEntry();
		validStudyEntry.setTitle("title foo");
		validStudyEntry.setSummary("summary foo");
		validStudyEntry.addModule(ApplicationConstants.MODULE_CLINICAL);
		validStudyEntry.addModule(ApplicationConstants.MODULE_PHARMACOLOGY);
			
	}
	
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), StudyModel.STATUS_INITIAL);
		assertEquals(new Integer(1), StudyModel.STATUS_LOADING);
		assertEquals(new Integer(2), StudyModel.STATUS_LOADED);
		assertEquals(new Integer(3), StudyModel.STATUS_SAVING);
		assertEquals(new Integer(4), StudyModel.STATUS_SAVED);
		assertEquals(new Integer(5), StudyModel.STATUS_ERROR);
		assertEquals(new Integer(6), StudyModel.STATUS_CANCELLED);
		
	}
	
	
	@Test
	public void testInitialState() {
				
		// test initial state
		assertNull(testModel.getStudyEntry());
		assertEquals(StudyModel.STATUS_INITIAL, testModel.getStatus());
		
		
	}
		
		
	@Test
	public void testGettersSetters() {
		
		testModel.setStudyEntry(newStudyEntry);
		assertEquals(newStudyEntry, testModel.getStudyEntry());
		
				
		//test data
		String title = "title foo";
		String summary = "summary foo";
		Integer status = StudyModel.STATUS_ERROR;
		
		Boolean acceptClinicalData = true;
		Boolean acceptMolecularData = true;
		Boolean acceptInVitroData = true;
		Boolean acceptPharmacologyData = true;
		
				
		//call methods under test
		testModel.setTitle(title);
		testModel.setSummary(summary);
		testModel.setAcceptClinicalData(acceptClinicalData);
		testModel.setAcceptMolecularData(acceptMolecularData);
		testModel.setAcceptInVitroData(acceptInVitroData);
		testModel.setAcceptPharmacologyData(acceptPharmacologyData);
		testModel.setStatus(status);
		
		
		// test outcome
		assertEquals(title, testModel.getTitle());
		assertEquals(summary, testModel.getSummary());
		assertEquals(acceptClinicalData, testModel.acceptClinicalData());
		assertEquals(acceptMolecularData, testModel.acceptMolecularData());
		assertEquals(acceptInVitroData, testModel.acceptInVitroData());
		assertEquals(acceptPharmacologyData, testModel.acceptPharmacologyData());
		assertEquals(status, testModel.getStatus());
		
	}
	
	
	
	@Test
	public void testOnTitleChanged() {
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = validStudyEntry.getTitle();
		
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		listener.onTitleChanged(null, invalid, false);
		listener.onTitleChanged(invalid, valid, true);	
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setTitle(invalid);
		testModel.setTitle(valid);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnSummaryChanged() {
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = validStudyEntry.getSummary();
		
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		listener.onSummaryChanged(null, invalid, false);
		listener.onSummaryChanged(invalid, valid, true);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setSummary(invalid);
		testModel.setSummary(valid);
		
		verify(listener);
		
	}
	
		
	@Test
	public void testOnAcceptClinicalDataChanged() {
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
				
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		listener.onAcceptClinicalDataChanged(Boolean.FALSE, Boolean.TRUE, true);
		listener.onAcceptClinicalDataChanged(Boolean.TRUE, Boolean.FALSE, true);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAcceptClinicalData(Boolean.TRUE);
		testModel.setAcceptClinicalData(Boolean.FALSE);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnAcceptMolecularDataChanged() {
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
				
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		listener.onAcceptMolecularDataChanged(Boolean.FALSE, Boolean.TRUE, true);
		listener.onAcceptMolecularDataChanged(Boolean.TRUE, Boolean.FALSE, true);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAcceptMolecularData(Boolean.TRUE);
		testModel.setAcceptMolecularData(Boolean.FALSE);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnAcceptInVitroDataChanged() {
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);

		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		listener.onAcceptInVitroDataChanged(Boolean.FALSE, Boolean.TRUE, true);
		listener.onAcceptInVitroDataChanged(Boolean.TRUE, Boolean.FALSE, true);
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAcceptInVitroData(Boolean.TRUE);
		testModel.setAcceptInVitroData(Boolean.FALSE);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnAcceptPharmacologyDataChanged() {
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
				
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		listener.onAcceptPharmacologyDataChanged(Boolean.FALSE, Boolean.TRUE, true);
		listener.onAcceptPharmacologyDataChanged(Boolean.TRUE, Boolean.FALSE, true);
		//allow other events, but do not check
		listener.onTitleChanged(isA(String.class), isA(String.class), isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onSummaryChanged(isA(String.class), isA(String.class), isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onAcceptClinicalDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onAcceptInVitroDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		expectLastCall().anyTimes();
		listener.onAcceptMolecularDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		expectLastCall().anyTimes();
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAcceptPharmacologyData(Boolean.TRUE);
		testModel.setAcceptPharmacologyData(Boolean.FALSE);
		
		verify(listener);
		
	}
	
	@Test
	public void testStudyEntryChanged() {
		
		//test data
		StudyEntry invalid = newStudyEntry;
		StudyEntry valid = validStudyEntry;	
		
		
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		listener.onStudyEntryChanged(Boolean.FALSE);	
		//assert other events called, but do not check validation again here
		listener.onTitleChanged(invalid.getTitle(), invalid.getTitle(), false);
		listener.onSummaryChanged(invalid.getSummary(), invalid.getSummary(), false);
		listener.onAcceptClinicalDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		listener.onAcceptInVitroDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		listener.onAcceptMolecularDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		listener.onAcceptPharmacologyDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		
		listener.onStudyEntryChanged(Boolean.TRUE);	
		//assert other events called, but do not check validation again here
		listener.onTitleChanged(valid.getTitle(), valid.getTitle(), true);
		listener.onSummaryChanged(valid.getSummary(), valid.getSummary(), true);
		listener.onAcceptClinicalDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		listener.onAcceptInVitroDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		listener.onAcceptMolecularDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		listener.onAcceptPharmacologyDataChanged(isA(Boolean.class), isA(Boolean.class), isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setStudyEntry(invalid);
		testModel.setStudyEntry(valid);
		
		verify(listener);		
		
	}
	
	@Test
	public void testOnStatusChanged() {
		
		//test data
		Integer status1 = StudyModel.STATUS_ERROR;
		Integer status2 = StudyModel.STATUS_LOADED;
		
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		listener.onStatusChanged(StudyModel.STATUS_INITIAL, status1);
		listener.onStatusChanged(status1, status2);	
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setStatus(status1);
		testModel.setStatus(status2);
		
		verify(listener);
		
	}
	
}
