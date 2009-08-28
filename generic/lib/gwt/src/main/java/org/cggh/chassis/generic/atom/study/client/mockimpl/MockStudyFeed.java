/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;
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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getEntries()
	 */
	public List<AtomEntry> getEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getId()
	 */
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getTitle()
	 */
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed#getUpdated()
	 */
	public String getUpdated() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void add(MockAtomEntry mockEntry) {
		// TODO restrict by type
	}


}
