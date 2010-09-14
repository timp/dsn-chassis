/**
 * 
 */
package org.cggh.chassis.generic.atomui;


import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.atomUI.AtomUI - All GWT Tests");
		//$JUnit-BEGIN$

		suite.addTestSuite(org.cggh.chassis.generic.atomui.client.GWTTestCreateEntryCallback.class);
		suite.addTestSuite(org.cggh.chassis.generic.atomui.client.GWTTestRetrieveEntryCallback.class);
		suite.addTestSuite(org.cggh.chassis.generic.atomui.client.GWTTestUpdateEntryCallback.class);
		
		//$JUnit-END$
		return suite;
	}

}
