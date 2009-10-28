package org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client");
		//$JUnit-BEGIN$
		suite.addTest(TestSubmissionManagementWidgetModel.suite());
		suite.addTest(TestSubmissionManagementWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
