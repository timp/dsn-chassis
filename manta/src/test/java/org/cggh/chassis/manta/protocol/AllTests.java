package org.cggh.chassis.manta.protocol;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.cggh.chassis.manta.protocol");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestDraftsCollectionSecurity.class);
		suite.addTestSuite(TestDraftMediaCollectionsSecurity.class);
		suite.addTestSuite(TestStudiesCollectionSecurity.class);
		//$JUnit-END$
		return suite;
	}

}
