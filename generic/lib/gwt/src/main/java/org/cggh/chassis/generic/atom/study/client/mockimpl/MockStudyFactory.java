/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.mockimpl;

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




	@Override
	public MockAtomEntry createMockEntry(String feedURL) {
		return new MockStudyEntry(feedURL);
	}
	
	
	

}
