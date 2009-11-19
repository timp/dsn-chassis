/**
 * 
 */
package org.cggh.chassis.generic.atomui;


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
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.atomui.AtomUI - All Plain Java Tests");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(org.cggh.chassis.generic.atomui.client.TestAtomCrudWidgetController.class);

		//$JUnit-END$
		return suite;
	}

}
