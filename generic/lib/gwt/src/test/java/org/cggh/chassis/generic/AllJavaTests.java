/**
 * 
 */
package org.cggh.chassis.generic;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("All plain Java tests for Chassis generic GWT library");
		//$JUnit-BEGIN$
		suite.addTest(new JUnit4TestAdapter(org.cggh.chassis.generic.twisted.client.TestDeferred.class));
		//$JUnit-END$
		return suite;
	}

}
