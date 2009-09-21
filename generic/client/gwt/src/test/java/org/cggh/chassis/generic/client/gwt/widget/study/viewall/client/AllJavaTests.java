package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.study.viewall.client");
		//$JUnit-BEGIN$
		suite.addTest(TestViewAllStudiesWidgetModel.suite());
		suite.addTest(TestViewAllStudiesWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
