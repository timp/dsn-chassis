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
		TestSuite suite = new TestSuite("All GWT Java tests for Chassis generic GWT library");
		//$JUnit-BEGIN$
		suite.addTestSuite(org.cggh.chassis.generic.twisted.client.GWTTestDeferred.class);
		suite.addTestSuite(org.cggh.chassis.generic.xml.client.GWTTestXML.class);
		//$JUnit-END$
		return suite;
	}

}
