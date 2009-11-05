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
public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Chassis Generic Lib GWT - All Plain Java Tests");
		//$JUnit-BEGIN$
		
		// module org.cggh.chassis.generic.atom.vanilla.Atom
		suite.addTest(legacy.org.cggh.chassis.generic.atom.vanilla.AllJavaTests.suite());
		
		// module org.cggh.chassis.generic.twisted.Twisted
		suite.addTest(org.cggh.chassis.generic.twisted.AllJavaTests.suite());

		// module org.cggh.chassis.generic.xml.XML
		// none
		
		//$JUnit-END$
		return suite;
	}

}
