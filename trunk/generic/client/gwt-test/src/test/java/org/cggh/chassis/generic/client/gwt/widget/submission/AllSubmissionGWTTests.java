package org.cggh.chassis.generic.client.gwt.widget.submission;


import org.cggh.chassis.generic.client.gwt.widget.submission.client.GWTTestCreateSubmissionWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.GWTTestEditSubmissionWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.GWTTestSubmissionManagementWidgetDefaultRenderer;
import org.cggh.chassis.generic.client.gwt.widget.submission.client.GWTTestViewSubmissionsWidgetDefaultRenderer;
//import org.cggh.chassis.generic.client.gwt.widget.submission.view.client.GWTTestViewSubmissionWidgetDefaultRenderer;

import com.google.gwt.junit.tools.GWTTestSuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

public class AllSubmissionGWTTests extends GWTTestSuite {

	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite(
				"All GWTTests for org.cggh.chassis.generic.client.gwt.widget.submission.*");
		//$JUnit-BEGIN$
		suite.addTest(new JUnit4TestAdapter(GWTTestCreateSubmissionWidgetDefaultRenderer.class));
//		suite.addTest(new JUnit4TestAdapter(GWTTestViewSubmissionWidgetDefaultRenderer.class));
		suite.addTest(new JUnit4TestAdapter(GWTTestViewSubmissionsWidgetDefaultRenderer.class));
		suite.addTest(new JUnit4TestAdapter(GWTTestSubmissionManagementWidgetDefaultRenderer.class));
		suite.addTest(new JUnit4TestAdapter(GWTTestEditSubmissionWidgetDefaultRenderer.class));
		//$JUnit-END$
		return suite;
	}

}
