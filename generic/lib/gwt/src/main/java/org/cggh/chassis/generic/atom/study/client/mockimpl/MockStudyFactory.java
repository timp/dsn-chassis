/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFeed;

/**
 * @author aliman
 *
 */
public class MockStudyFactory extends MockAtomFactory implements StudyFactory {

	

	@Override
	public MockAtomFeed createMockFeed(String title) {
		return new MockStudyFeed(title, this);
	}



	@Override
	protected MockAtomEntry copy(AtomEntry entry) {
		MockStudyEntry mockEntry = new MockStudyEntry(this);
		mockEntry.put(entry);
		return mockEntry;
	}
	
	
	
	@Override 
	protected MockAtomFeed copy(AtomFeed feed) {
		MockStudyFeed copy = new MockStudyFeed(feed.getTitle(), this);
		copy.put(feed);
		return copy;		
	}
	
	
	public MockStudyEntry createMockEntry() {
		return new MockStudyEntry(this);
	}
	
	
	
	public AtomEntry createEntry() {
		return createMockEntry();
	}
	
	
	
	
	public StudyEntry createStudyEntry() {
		return createMockEntry();
	}
	
	

}
