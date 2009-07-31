/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib;

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
		TestSuite suite = new TestSuite("All GWT tests for Chassis GWT libraries");
		suite.addTest(org.cggh.chassis.gwt.lib.xml.client.AllGWTTests.suite()); 
		suite.addTest(org.cggh.chassis.gwt.lib.atom.client.format.AllGWTTests.suite()); 
		suite.addTest(org.cggh.chassis.gwt.lib.atom.client.protocol.AllGWTTests.suite()); 
		suite.addTest(org.cggh.chassis.gwt.lib.study.client.format.AllGWTTests.suite()); 
		suite.addTestSuite(org.cggh.chassis.gwt.lib.twisted.client.GWTTestDeferred.class);
		return suite;
	}


}
