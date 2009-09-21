package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.userdetails.client");
		//$JUnit-BEGIN$
		suite.addTest(TestUserDetailsWidgetModel.suite());
		suite.addTest(TestUserDetailsWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
