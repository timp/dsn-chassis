/**
 * 
 */
package org.cggh.chassis.generic.xml;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.xml.XML - All GWT Tests");
		//$JUnit-BEGIN$

		// module org.cggh.chassis.generic.xml.XML
		suite.addTestSuite(org.cggh.chassis.generic.xml.client.GWTTestXML.class);
		suite.addTestSuite(org.cggh.chassis.generic.xml.client.GWTTestXMLNS.class);
		
		//$JUnit-END$
		return suite;
	}

}
