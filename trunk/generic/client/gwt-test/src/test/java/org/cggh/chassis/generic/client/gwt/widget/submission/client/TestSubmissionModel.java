/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionModel;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.SubmissionModelListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author raok
 *
 */
public class TestSubmissionModel {

	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestSubmissionModel.class);
	}

	private SubmissionModel testModel;
	private SubmissionEntry newSubmissionEntry;
	private SubmissionFactory mockSubmissionFactory;
	private SubmissionEntry validSubmissionEntry;
	private Set<AtomAuthor> testAuthors;
	
	@Before
	public void setUp() {
		
		//Create object to use in all tests
		testModel = new SubmissionModel();
		
		//create new submission entry
		mockSubmissionFactory = new SubmissionFactory();
		newSubmissionEntry = mockSubmissionFactory.createEntry();
		
		//create test authors
		testAuthors = new HashSet<AtomAuthor>();
		AtomAuthor testAtomAuthor = mockSubmissionFactory.createAuthor();
		testAtomAuthor.setEmail("foo@bar.com");
		testAuthors.add(testAtomAuthor);
		
		//create a valid submission entry
		validSubmissionEntry = mockSubmissionFactory.createEntry();
		validSubmissionEntry.setAuthors(new ArrayList<AtomAuthor>(testAuthors));
		validSubmissionEntry.setTitle("title foo");
		validSubmissionEntry.setSummary("summary foo");
		validSubmissionEntry.addStudyLink("http://example.com/studies/study1");
		validSubmissionEntry.addStudyLink("http://example.com/studies/study2");
		validSubmissionEntry.getSubmission().addModule("module1");
		validSubmissionEntry.getSubmission().addModule("module2");
			
	}
	
	
	@Test
	public void testStatusConstants() {
		
		assertEquals(new Integer(0), SubmissionModel.STATUS_INITIAL);
		assertEquals(new Integer(1), SubmissionModel.STATUS_LOADING);
		assertEquals(new Integer(2), SubmissionModel.STATUS_LOADED);
		assertEquals(new Integer(3), SubmissionModel.STATUS_SAVING);
		assertEquals(new Integer(4), SubmissionModel.STATUS_SAVED);
		assertEquals(new Integer(5), SubmissionModel.STATUS_ERROR);
		assertEquals(new Integer(6), SubmissionModel.STATUS_CANCELLED);
		
	}
	
	
	@Test
	public void testInitialState() {
				
		// test initial state
		assertNull(testModel.getSubmissionEntry());
		assertEquals(SubmissionModel.STATUS_INITIAL, testModel.getStatus());
		
		
	}
		
		
	@Test
	public void testGettersSetters() {
		
		testModel.setSubmissionEntry(newSubmissionEntry);
		assertEquals(newSubmissionEntry, testModel.getSubmissionEntry());
		
				
		//test data
		String title = "title foo";
		String summary = "summary foo";
		String studylink1 = "link1";
		String studylink2 = "link2";
		Set<String> studyLinks = new HashSet<String>();
		studyLinks.add(studylink1);
		studyLinks.add(studylink2);
		Set<String> modulesSet1 = new HashSet<String>();
		modulesSet1.add("module1");		
		Integer status = SubmissionModel.STATUS_ERROR;
		
				
		//call methods under test
		testModel.setAuthors(testAuthors);
		testModel.setTitle(title);
		testModel.setSummary(summary);
		testModel.setStudyLinks(studyLinks);
		testModel.setModules(modulesSet1);
		testModel.setStatus(status);
		
		
		// test outcome
		assertEquals(testAuthors, testModel.getAuthors());
		assertEquals(title, testModel.getTitle());
		assertEquals(summary, testModel.getSummary());
		assertEquals(studyLinks, testModel.getStudyLinks());
		assertEquals(modulesSet1, testModel.getModules());
		assertEquals(status, testModel.getStatus());
		
	}
	

	@Test
	public void testOnAuthorsChanged() {
		
		Set<AtomAuthor> valid = testAuthors;
		Set<AtomAuthor> invalid = new HashSet<AtomAuthor>();
		
		//place in ready state
		testModel.setSubmissionEntry(newSubmissionEntry);
				
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onAuthorsChanged(new HashSet<AtomAuthor>(), valid, true);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		listener.onAuthorsChanged(valid, invalid, false);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setAuthors(valid);
		testModel.setAuthors(invalid);
		
		verify(listener);
	}
	
	
	
	@Test
	public void testOnTitleChanged() {
		
		//place in ready state
		testModel.setSubmissionEntry(newSubmissionEntry);
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = validSubmissionEntry.getTitle();
		
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onTitleChanged(null, invalid, false);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		listener.onTitleChanged(invalid, valid, true);	
		listener.onSubmissionEntryChanged(isA(Boolean.class));
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
		testModel.setSubmissionEntry(newSubmissionEntry);
		
		//test data
		//TODO test validation more rigorously
		String invalid = "";
		String valid = validSubmissionEntry.getSummary();
		
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onSummaryChanged(null, invalid, false);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		listener.onSummaryChanged(invalid, valid, true);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setSummary(invalid);
		testModel.setSummary(valid);
		
		verify(listener);
		
	}
	
	@Test
	public void testOnStudyLinksChanged() {
		
		//place in ready state
		testModel.setSubmissionEntry(newSubmissionEntry);
		
		//test data
		Set<String> invalid = new HashSet<String>();
		Set<String> valid = new HashSet<String>();
		valid.add("http://example.com/studies/study1");
		valid.add("http://example.com/studies/study2");
		
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onStudyLinksChanged(new HashSet<String>(), invalid, false);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		listener.onStudyLinksChanged(invalid, valid, true);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setStudyLinks(invalid);
		testModel.setStudyLinks(valid);
		
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
		testModel.setSubmissionEntry(newSubmissionEntry);
				
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onModulesChanged(new HashSet<String>(), valid, true);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		listener.onModulesChanged(valid, invalid, false);
		listener.onSubmissionEntryChanged(isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setModules(valid);
		testModel.setModules(invalid);
		
		verify(listener);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSubmissionEntryChanged() {
		
		//test data
		SubmissionEntry invalid = newSubmissionEntry;
		SubmissionEntry valid = validSubmissionEntry;	
		
		
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onSubmissionEntryChanged(Boolean.FALSE);	
		//assert other events called, but do not check validation again here
		listener.onTitleChanged(invalid.getTitle(), invalid.getTitle(), false);
		listener.onSummaryChanged(invalid.getSummary(), invalid.getSummary(), false);
		listener.onStudyLinksChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		listener.onModulesChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		listener.onAuthorsChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		
		listener.onSubmissionEntryChanged(Boolean.TRUE);	
		//assert other events called, but do not check validation again here
		listener.onTitleChanged(valid.getTitle(), valid.getTitle(), true);
		listener.onSummaryChanged(valid.getSummary(), valid.getSummary(), true);
		listener.onStudyLinksChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		listener.onModulesChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		listener.onAuthorsChanged(isA(Set.class), isA(Set.class), isA(Boolean.class));
		replay(listener);
		
		// register with model
		testModel.addListener(listener);
		
		// call method under test
		testModel.setSubmissionEntry(invalid);
		testModel.setSubmissionEntry(valid);
		
		verify(listener);		
		
	}
	
	@Test
	public void testOnStatusChanged() {
		
		//test data
		Integer status1 = SubmissionModel.STATUS_ERROR;
		Integer status2 = SubmissionModel.STATUS_LOADED;
		
		SubmissionModelListener listener = createMock(SubmissionModelListener.class);
		
		//set up expectations
		listener.onStatusChanged(SubmissionModel.STATUS_INITIAL, status1);
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
