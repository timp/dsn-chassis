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

		suite.addTest(org.cggh.chassis.generic.async.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.atom.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.atomui.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.xml.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.xquestion.AllGWTTests.suite());
		
		//$JUnit-END$
		return suite;
	}

}
