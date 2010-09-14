/**
 * 
 */
package org.cggh.chassis.generic.async;


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.async.Async - All GWT Tests");
		//$JUnit-BEGIN$

		suite.addTestSuite(org.cggh.chassis.generic.async.client.GWTTestDeferred.class);
		
		//$JUnit-END$
		return suite;
	}

}
