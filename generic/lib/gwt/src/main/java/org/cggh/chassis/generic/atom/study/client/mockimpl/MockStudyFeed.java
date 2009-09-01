/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFeed;

/**
 * @author aliman
 *
 */
public class MockStudyFeed extends MockAtomFeed implements StudyFeed {

	/**
	 * @param title
	 */
	protected MockStudyFeed(String title, MockStudyFactory factory) {
		super(title, factory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.format.StudyFeed#getStudyEntries()
	 */
	public List<StudyEntry> getStudyEntries() {
		List<StudyEntry> studies = new ArrayList<StudyEntry>();
		for (AtomEntry entry : this.entries) {
			if (entry instanceof StudyEntry) {
				studies.add((StudyEntry)entry);
			}
		}
		return studies;
	}

	@Override
	protected void put(AtomFeed feed) {
		super.put(feed);
	}

}
