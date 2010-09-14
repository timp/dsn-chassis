/**
 * 
 */
package org.cggh.chassis.generic.xquestion;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author aliman
 *
 */
public class AllGWTTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.cggh.chassis.generic.xquestion.XQuestion - All GWT Tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(org.cggh.chassis.generic.xquestion.client.GWTTestXQuestion.class);
		suite.addTestSuite(org.cggh.chassis.generic.xquestion.client.GWTTestXQuestionnaire.class);
		//$JUnit-END$
		return suite;
	}

}
