/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.study.client.format;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class AllGWTTests extends GWTTestSuite {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("All GWT tests for package: org.cggh.chassis.gwt.lib.study.client.format");
		suite.addTestSuite(TestStudyEntry.class);
		return suite;
	}


}
