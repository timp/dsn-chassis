package org.cggh.chassis.generic.client.gwt.widget.application;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllApplicationJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.application.*");
		//$JUnit-BEGIN$
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.TestPerspectiveWidgetModel.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.TestPerspectiveWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
