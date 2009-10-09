package org.cggh.chassis.generic.client.gwt.widget.application;


import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

public class AllApplicationGWTTests extends GWTTestSuite {

	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"All GWTTests for org.cggh.chassis.generic.client.gwt.widget.application.*");
		//$JUnit-BEGIN$
		suite.addTest(new JUnit4TestAdapter(org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.GWTTestPerspectiveWidgetDefaultRenderer.class));
		//$JUnit-END$
		return suite;
	}

}
