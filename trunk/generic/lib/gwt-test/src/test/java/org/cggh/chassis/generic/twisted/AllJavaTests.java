/**
 * 
 */
package org.cggh.chassis.generic.twisted;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.twisted.Twisted - All Plain Java Tests");
		//$JUnit-BEGIN$
		
		// module org.cggh.chassis.generic.twisted.Twisted
		suite.addTest(new JUnit4TestAdapter(org.cggh.chassis.generic.twisted.client.TestDeferred.class));

		//$JUnit-END$
		return suite;
	}

}
