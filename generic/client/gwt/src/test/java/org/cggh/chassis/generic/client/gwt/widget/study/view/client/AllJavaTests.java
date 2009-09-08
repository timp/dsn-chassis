package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.study.view.client");
		//$JUnit-BEGIN$
		suite.addTest(TestViewStudyWidgetModel.suite());
		suite.addTest(TestViewStudyWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
