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
public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.async.Async - All Plain Java Tests");
		//$JUnit-BEGIN$
		
		// module org.cggh.chassis.generic.twisted.Twisted
		suite.addTestSuite(org.cggh.chassis.generic.async.client.TestDeferred.class);

		//$JUnit-END$
		return suite;
	}

}
