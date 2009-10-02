package org.cggh.chassis.generic.client.gwt.widget.submission;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllSubmissionJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.submission.*");
		//$JUnit-BEGIN$
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.submission.viewsubmissions.client.AllJavaTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.submissionmanagement.client.AllJavaTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.submission.model.client.TestSubmissionModel.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.TestSubmissionController.suite());
		//$JUnit-END$
		return suite;
	}

}
