/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.mockimpl;


/**
 * @author aliman
 *
 */
public class MockAtomFactory {


	
	
	public MockAtomFeed createMockFeed(String title) {
		return new MockAtomFeed(title);
	}




	/**
	 * @param feedURL
	 * @return
	 */
	public MockAtomEntry createMockEntry(String feedURL) {
		return new MockAtomEntry(feedURL);
	}
	
	
	
	
	public MockAtomEntry createMockEntry() {
		return new MockAtomEntry();
	}
	
	
}
