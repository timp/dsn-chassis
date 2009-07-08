/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class TestAtomService extends GWTTestCase {

	/* (non-Javadoc)
	 * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
	 */
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void test_constructor_String() {
		
		String collectionURL = "/exist/atom/edit/testfeed";
		AtomService<AtomEntry,AtomFeed> vanillaService = new AtomService<AtomEntry,AtomFeed>(collectionURL);
	}

}
