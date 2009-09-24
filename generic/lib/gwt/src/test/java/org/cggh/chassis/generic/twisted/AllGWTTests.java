/**
 * 
 */
package org.cggh.chassis.generic.twisted;


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.twisted.Twisted - All GWT Tests");
		//$JUnit-BEGIN$

		suite.addTestSuite(org.cggh.chassis.generic.twisted.client.GWTTestDeferred.class);
		
		//$JUnit-END$
		return suite;
	}

}
