package org.cggh.chassis.generic.client.gwt.widget.submission;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllSubmissionJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.submission.*");
		//$JUnit-BEGIN$
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.submission.client.TestSubmissionModel.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.submission.client.TestSubmissionController.suite());
		//$JUnit-END$
		return suite;
	}

}
