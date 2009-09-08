package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.study.create.client");
		//$JUnit-BEGIN$
		suite.addTest(TestCreateStudyWidgetModel.suite());
		suite.addTest(TestCreateStudyWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
