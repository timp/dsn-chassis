/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.atom.vanilla.Atom - All GWT Tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(org.cggh.chassis.generic.atom.vanilla.client.format.impl.GWTTestAtomEntryImpl.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.vanilla.client.format.impl.GWTTestAtomFactoryImpl.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.GWTTestAtomServiceImpl.class);
		//$JUnit-END$
		return suite;
	}

}
