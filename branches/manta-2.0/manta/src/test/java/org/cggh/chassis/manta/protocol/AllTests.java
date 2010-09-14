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
		suite.addTestSuite(TestStudyInfoCollectionSecurity.class);
		suite.addTestSuite(TestSubmittedMediaCollectionsSecurity.class);
		suite.addTestSuite(TestCuratedMediaCollectionsSecurity.class);
		suite.addTestSuite(TestDerivationsCollectionsSecurity.class);
		suite.addTestSuite(TestPersonalDataReviewsCollectionsSecurity.class);
		suite.addTestSuite(TestAllSubmittedMediaCollectionSecurity.class);
		suite.addTestSuite(TestAllCuratedMediaCollectionSecurity.class);
		suite.addTestSuite(TestAllDerivationsCollectionSecurity.class);
		suite.addTestSuite(TestAllPersonalDataReviewsCollectionSecurity.class);
		//$JUnit-END$
		return suite;
	}

}
