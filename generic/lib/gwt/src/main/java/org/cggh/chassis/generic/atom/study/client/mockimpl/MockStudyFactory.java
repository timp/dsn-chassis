/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.mockimpl.MockAtomFeed;

/**
 * @author aliman
 *
 */
public class MockStudyFactory extends MockAtomFactory {

	

	@Override
	public MockAtomFeed createMockFeed(String title) {
		return new MockStudyFeed(title);
	}




	public MockStudyEntry mockEntry(StudyEntry entry) {
		MockStudyEntry mockEntry = new MockStudyEntry(this);
		mockEntry.put(entry);
		return mockEntry;
	}
	
	
	

}
