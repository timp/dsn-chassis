/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atomext.shared.ChassisConstants;

/**
 * @author raok
 *
 */
public class MockConfigurationSetUp {
	
	//test data
	public static final String testUserDetailsServiceEndpointURL = "http://foo.com/users";
	public static final String testUserChassisRolesPrefix = "ROLE_";
	public static final ChassisRole testChassisRoleCoordinator = new ChassisRole(ChassisConstants.coordinatorRoleId, "Coord", "Coordinator");
	public static final ChassisRole testChassisRoleCurator = new ChassisRole(ChassisConstants.curatorRoleId, "Cur", "Curator");
	public static final ChassisRole testChassisRoleGatekeeper = new ChassisRole(ChassisConstants.gatekeeperRoleId, "Gk", "Gate Keeper");
	public static final ChassisRole testChassisRoleSubmitter = new ChassisRole(ChassisConstants.submitterRoleId, "Sub", "Submitter");
	public static final ChassisRole testChassisRoleUser = new ChassisRole(ChassisConstants.userRoleId, "User", "User");
	public static final String testStudyCollectionUrl = "http://foo.com/studies";
	public static final String testStudyQueryServiceURL = "http://foo.com/study_query";
	public static final String testSubmissionCollectionUrl = "http://foo.com/submission";
	public static final String testSubmissionQueryServiceURL = "http://foo.com/submission_query";
	public static final String testDataFileCollectionUrl = "http://foo.com/data_file_feed";
	public static final String testDataFileQueryServiceURL = "http://foo.com/data_file_query";
	public static final String module1Id = "module1Id";
	public static final String module2Id = "module2Id";
	public static final String module1Label = "module1Label";
	public static final String module2Label = "module2Label";
	
	public static void createTestConfiguration() {

		Configuration.testUserDetailsServiceEndpointUrl = testUserDetailsServiceEndpointURL;
		Configuration.testUserChassisRolesPrefix = testUserChassisRolesPrefix;
		Configuration.testChassisRoleCoordinator = testChassisRoleCoordinator;
		Configuration.testChassisRoleCurator = testChassisRoleCurator;
		Configuration.testChassisRoleGatekeeper = testChassisRoleGatekeeper;
		Configuration.testChassisRoleSubmitter = testChassisRoleSubmitter;
		Configuration.testChassisRoleUser = testChassisRoleUser;
		
		
		Configuration.testStudyCollectionUrl = testStudyCollectionUrl;
		Configuration.testStudyQueryServiceUrl = testStudyQueryServiceURL;
		
		Configuration.testSubmissionCollectionUrl = testSubmissionCollectionUrl;
		Configuration.testSubmissionQueryServiceUrl = testSubmissionQueryServiceURL;
		
		Configuration.testDataFileCollectionUrl = testDataFileCollectionUrl;
		Configuration.testDataFileQueryServiceUrl = testDataFileQueryServiceURL;
		
		//Create modules test data
		Map<String, String> testModules = new HashMap<String, String>();
		testModules.put(module1Id, module1Label);
		testModules.put(module2Id, module2Label);

		//Set up ConfigurationBean with test values
		Configuration.useUnitTestConfiguration = true;
		Configuration.testModules = testModules;
		
	}
	
}
