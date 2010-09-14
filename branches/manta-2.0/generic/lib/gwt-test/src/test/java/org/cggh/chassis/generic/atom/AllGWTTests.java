/**
 * 
 */
package org.cggh.chassis.generic.atom;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.atom.Atom - All GWT Tests");
		//$JUnit-BEGIN$

		suite.addTestSuite(org.cggh.chassis.generic.atom.client.GWTTestAtomServiceImpl.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.vanilla.GWTTestVanillaAtomFactory.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.vanilla.GWTTestVanillaAtomEntry.class);
		
		//$JUnit-END$
		return suite;
	}

}
