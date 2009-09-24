package org.cggh.chassis.generic.client.gwt.widget.study;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllStudyJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.study.*");
		//$JUnit-BEGIN$
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.AllJavaTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.AllJavaTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.model.client.TestStudyModel.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.controller.client.TestStudyController.suite());
		//$JUnit-END$
		return suite;
	}

}
