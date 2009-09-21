package org.cggh.chassis.generic.client.gwt.widget.studymanagement.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.studymanagement.client");
		//$JUnit-BEGIN$
		suite.addTest(TestStudyManagementWidgetModel.suite());
		suite.addTest(TestStudyManagementWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
