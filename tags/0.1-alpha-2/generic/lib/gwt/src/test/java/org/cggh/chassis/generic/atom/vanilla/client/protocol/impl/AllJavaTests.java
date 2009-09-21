/**
 * 
 */
package org.cggh.chassis.generic.atom.vanilla.client.protocol.impl;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.cggh.chassis.generic.atom.vanilla.client.protocol.impl");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestGetEntryCallback.class);
		suite.addTestSuite(TestGetFeedCallback.class);
		suite.addTestSuite(TestPostEntryCallback.class);
		suite.addTestSuite(TestPutEntryCallback.class);
		suite.addTestSuite(TestDeleteEntryCallback.class);
		//$JUnit-END$
		return suite;
	}

}
