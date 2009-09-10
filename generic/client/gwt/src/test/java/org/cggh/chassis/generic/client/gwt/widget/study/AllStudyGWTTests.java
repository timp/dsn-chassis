package org.cggh.chassis.generic.client.gwt.widget.study;


import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.Test;

public class AllStudyGWTTests extends GWTTestSuite {

	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"All GWTTests for org.cggh.chassis.generic.client.gwt.widget.study.create.client");
		//$JUnit-BEGIN$
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.create.client.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.view.client.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.viewall.client.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.AllGWTTests.suite());
		suite.addTest(org.cggh.chassis.generic.client.gwt.widget.study.edit.client.AllGWTTests.suite());
		//$JUnit-END$
		return suite;
	}

}
