package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

public class AllGWTTests extends GWTTestSuite {

	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"All GWTTests for org.cggh.chassis.generic.client.gwt.widget.study.create.client");
		//$JUnit-BEGIN$
		suite.addTest(new JUnit4TestAdapter(GWTTestViewAllStudiesWidgetDefaultRenderer.class));
		//$JUnit-END$
		return suite;
	}

}
