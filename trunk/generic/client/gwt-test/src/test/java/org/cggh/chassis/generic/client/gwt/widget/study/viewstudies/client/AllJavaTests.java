package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client");
		//$JUnit-BEGIN$
		suite.addTest(TestViewStudiesWidgetModel.suite());
		suite.addTest(TestViewStudiesWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
