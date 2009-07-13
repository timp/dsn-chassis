/**
 * $Id$
 */
package org.cggh.chassis.gwt.lib.atom.client.format;

import org.cggh.chassis.gwt.lib.atom.client.format.TestAtomEntry;

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
		TestSuite suite = new TestSuite("All GWT tests for package: org.cggh.chassis.gwt.lib.atom.client.format");
		suite.addTestSuite(TestAtomEntry.class); 
		return suite;
	}


}
