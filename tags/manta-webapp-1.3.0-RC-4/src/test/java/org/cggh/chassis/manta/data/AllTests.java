package org.cggh.chassis.manta.data;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.cggh.chassis.manta.protocol");
		//$JUnit-BEGIN$
		
		suite.addTestSuite(TestStudyInfoFlatten.class);
		
		//$JUnit-END$
		return suite;
	}

}
