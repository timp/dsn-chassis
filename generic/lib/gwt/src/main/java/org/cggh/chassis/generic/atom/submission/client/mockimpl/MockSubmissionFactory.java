/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.mockimpl;

import org.cggh.chassis.generic.atom.submission.client.format.Submission;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFeed;

import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class MockSubmissionFactory extends MockAtomFactory implements SubmissionFactory {

	

	@Override
	public MockAtomFeed createMockFeed(String title) {
		return createMockSubmissionFeed(title);
	}
	
	
	
	public MockSubmissionFeed createMockSubmissionFeed(String title) {
		return new MockSubmissionFeed(title, this);
	}



	@Override
	protected MockAtomEntry copy(AtomEntry entry) {
		MockSubmissionEntry mockEntry = new MockSubmissionEntry(this);
		mockEntry.put(entry);
		return mockEntry;
	}
	
	
	
	@Override 
	protected MockAtomFeed copy(AtomFeed feed) {
		MockSubmissionFeed copy = new MockSubmissionFeed(feed.getTitle(), this);
		copy.put(feed);
		return copy;		
	}
	
	
	public MockSubmissionEntry createMockEntry() {
		return new MockSubmissionEntry(this);
	}
	
	
	
	public AtomEntry createEntry() {
		return createMockEntry();
	}
	
	
	
	
	public SubmissionEntry createSubmissionEntry() {
		return createMockEntry();
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory#createSubmission(com.google.gwt.xml.client.Element)
	 */
	public Submission createSubmission(Element submissionElement) {
		// not needed
		return null;
	}

}
