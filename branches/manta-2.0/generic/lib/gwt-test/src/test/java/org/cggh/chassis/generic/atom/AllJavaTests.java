/**
 * 
 */
package org.cggh.chassis.generic.atom;


import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.log.client.SystemOutLog;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllJavaTests {

	static {
		LogFactory.create = SystemOutLog.create;
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.atom.Atom - All Plain Java Tests");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.TestCallbackWithNoContent.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.TestGetEntryCallback.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.TestGetFeedCallback.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.TestPostEntryCallback.class);
		suite.addTestSuite(org.cggh.chassis.generic.atom.client.TestPutEntryCallback.class);

		//$JUnit-END$
		return suite;
	}

}
