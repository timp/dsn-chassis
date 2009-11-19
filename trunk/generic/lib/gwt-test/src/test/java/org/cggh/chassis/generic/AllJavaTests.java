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
		
		suite.addTest(org.cggh.chassis.generic.async.AllJavaTests.suite());
		suite.addTest(org.cggh.chassis.generic.atom.AllJavaTests.suite());
		suite.addTest(org.cggh.chassis.generic.atomui.AllJavaTests.suite());

		//$JUnit-END$
		return suite;
	}

}
