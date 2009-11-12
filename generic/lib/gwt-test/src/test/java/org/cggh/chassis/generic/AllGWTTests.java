/**
 * 
 */
package org.cggh.chassis.generic;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Chassis Generic Lib GWT - All GWT Tests");
		//$JUnit-BEGIN$

		// module org.cggh.chassis.generic.atom.vanilla.Atom
		//suite.addTest(legacy.org.cggh.chassis.generic.atom.vanilla.AllGWTTests.suite());

		// module org.cggh.chassis.generic.twisted.Twisted
		suite.addTest(org.cggh.chassis.generic.async.AllGWTTests.suite());
		
		// module org.cggh.chassis.generic.xml.XML
		suite.addTest(org.cggh.chassis.generic.xml.AllGWTTests.suite());
		
		//$JUnit-END$
		return suite;
	}

}
