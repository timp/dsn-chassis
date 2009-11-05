/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFeed;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed;

/**
 * @author aliman
 *
 */
public class MockSubmissionFeed extends MockAtomFeed implements SubmissionFeed {

	/**
	 * @param title
	 * @param factory
	 */
	protected MockSubmissionFeed(String title, MockSubmissionFactory factory) {
		super(title, factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.submission.client.format.SubmissionFeed#getSubmissionEntries()
	 */
	public List<SubmissionEntry> getSubmissionEntries() {
		List<SubmissionEntry> submissions = new ArrayList<SubmissionEntry>();
		for (AtomEntry entry : this.entries) {
			if (entry instanceof SubmissionEntry) {
				submissions.add((SubmissionEntry)entry);
			}
		}
		return submissions;
	}
	
	@Override
	public void put(AtomFeed feed) {
		super.put(feed);
	}	

}
