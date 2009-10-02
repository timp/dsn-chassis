package org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client");
		//$JUnit-BEGIN$
		suite.addTest(TestViewSubmissionsWidgetController.suite());
		suite.addTest(TestViewSubmissionsWidgetModel.suite());
		//$JUnit-END$
		return suite;
	}

}
