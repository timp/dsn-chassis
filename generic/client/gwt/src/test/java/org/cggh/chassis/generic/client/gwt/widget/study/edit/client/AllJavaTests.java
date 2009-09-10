package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJavaTests extends TestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"All plain Java tests for org.cggh.chassis.generic.client.gwt.widget.study.create.client");
		//$JUnit-BEGIN$
		suite.addTest(TestEditStudyWidgetModel.suite());
		suite.addTest(TestEditStudyWidgetController.suite());
		//$JUnit-END$
		return suite;
	}

}
