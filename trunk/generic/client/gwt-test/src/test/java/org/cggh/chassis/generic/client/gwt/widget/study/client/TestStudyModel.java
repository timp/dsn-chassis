/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyModel;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyModelListener;
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
	private StudyFactory testStudyFactory;
	private StudyEntry validStudyEntry;
	private Set<AtomAuthor> testAuthors;
	
	@Before
	public void setUp() {
		
		//Create object to use in all tests
		testModel = new StudyModel();
		
		//create new study entry
		testStudyFactory = new StudyFactory();
		newStudyEntry = testStudyFactory.createEntry();
		
		//create test authors
		testAuthors = new HashSet<AtomAuthor>();
		AtomAuthor testAtomAuthor = testStudyFactory.createAuthor();
		testAtomAuthor.setEmail("foo@bar.com");
		testAuthors.add(testAtomAuthor);
		
		//create a valid study entry
		validStudyEntry = testStudyFactory.createEntry();
		validStudyEntry.setTitle("title foo");
		validStudyEntry.setSummary("summary foo");
		validStudyEntry.setAuthors(new ArrayList<AtomAuthor>(testAuthors));
		validStudyEntry.getStudy().addModule("module1");
		validStudyEntry.getStudy().addModule("module2");
			
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
		Set<String> modulesSet1 = new HashSet<String>();
		modulesSet1.add("module1");		
		Integer status = StudyModel.STATUS_ERROR;
				
				
		//call methods under test
		testModel.setTitle(title);
		testModel.setSummary(summary);
		testModel.setModules(modulesSet1);
		testModel.setAuthors(testAuthors);
		testModel.setStatus(status);
		
		
		// test outcome
		assertEquals(title, testModel.getTitle());
		assertEquals(summary, testModel.getSummary());
		assertEquals(modulesSet1, testModel.getModules());
		assertEquals(testAuthors, testModel.getAuthors());
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
		listener.onStudyEntryChanged(isA(Boolean.class));
		listener.onTitleChanged(invalid, valid, true);	
		listener.onStudyEntryChanged(isA(Boolean.class));
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
		listener.onStudyEntryChanged(isA(Boolean.class));
		listener.onSummaryChanged(invalid, valid, true);
		listener.onStudyEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setSummary(invalid);
		testModel.setSummary(valid);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnModulesChanged() {
		
		//test data
		Set<String> valid = new HashSet<String>();
		valid.add("module1");
		valid.add("module2");

		Set<String> invalid = new HashSet<String>();
		

		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
				
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		listener.onModulesChanged(new HashSet<String>(), valid, true);
		listener.onStudyEntryChanged(isA(Boolean.class));
		listener.onModulesChanged(valid, invalid, false);
		listener.onStudyEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setModules(valid);
		testModel.setModules(invalid);
		
		verify(listener);
	}
	

	@Test
	public void testOnAuthorsChanged() {
		
		Set<AtomAuthor> valid = testAuthors;
		Set<AtomAuthor> invalid = new HashSet<AtomAuthor>();
		
		//place in ready state
		testModel.setStudyEntry(newStudyEntry);
				
		StudyModelListener listener = createMock(StudyModelListener.class);
		
		//set up expectations
		listener.onAuthorsChanged(new HashSet<AtomAuthor>(), valid, true);
		listener.onStudyEntryChanged(isA(Boolean.class));
		listener.onAuthorsChanged(valid, invalid, false);
		listener.onStudyEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAuthors(valid);
		testModel.setAuthors(invalid);
		
		verify(listener);
	}
		
		
	@SuppressWarnings("unchecked")
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
		listener.onModulesChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		listener.onAuthorsChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		
		listener.onStudyEntryChanged(Boolean.TRUE);	
		//assert other events called, but do not check validation again here
		listener.onTitleChanged(valid.getTitle(), valid.getTitle(), true);
		listener.onSummaryChanged(valid.getSummary(), valid.getSummary(), true);
		listener.onModulesChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		listener.onAuthorsChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
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
