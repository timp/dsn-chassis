/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author raok
 *
 */
public class TestConfigurationSetUp {
	
	//test data
	public static final String testUserDetailsServiceEndpointURL = "http://foo.com/users";
	public static final String testUserChassisRolesPrefix = "ROLE_";
	public static final ChassisRole testChassisRoleCoordinator = new ChassisRole(ConfigurationBean.coordinatorId, "Coord", "Coordinator");
	public static final ChassisRole testChassisRoleCurator = new ChassisRole(ConfigurationBean.curatorId, "Cur", "Curator");
	public static final ChassisRole testChassisRoleGatekeeper = new ChassisRole(ConfigurationBean.gatekeeperId, "Gk", "Gate Keeper");
	public static final ChassisRole testChassisRoleSubmitter = new ChassisRole(ConfigurationBean.submitterId, "Sub", "Submitter");
	public static final ChassisRole testChassisRoleUser = new ChassisRole(ConfigurationBean.userId, "User", "User");
	public static final String testStudyFeedURL = "http://foo.com/studies";
	public static final String testStudyQueryServiceURL = "http://foo.com/study_query";
	public static final String testSubmissionFeedURL = "http://foo.com/submission";
	public static final String testSubmissionQueryServiceURL = "http://foo.com/submission_query";
	public static final String module1Id = "module1Id";
	public static final String module2Id = "module2Id";
	public static final String module1Label = "module1Label";
	public static final String module2Label = "module2Label";
	
	public static void createTestConfiguration() {

		ConfigurationBean.testUserDetailsServiceEndpointURL = testUserDetailsServiceEndpointURL;
		ConfigurationBean.testUserChassisRolesPrefix = testUserChassisRolesPrefix;
		ConfigurationBean.testChassisRoleCoordinator = testChassisRoleCoordinator;
		ConfigurationBean.testChassisRoleCurator = testChassisRoleCurator;
		ConfigurationBean.testChassisRoleGatekeeper = testChassisRoleGatekeeper;
		ConfigurationBean.testChassisRoleSubmitter = testChassisRoleSubmitter;
		ConfigurationBean.testChassisRoleUser = testChassisRoleUser;
		
		
		ConfigurationBean.testStudyFeedURL = testStudyFeedURL;
		ConfigurationBean.testStudyQueryServiceURL = testStudyQueryServiceURL;
		
		ConfigurationBean.testSubmissionFeedURL = testSubmissionFeedURL;
		ConfigurationBean.testSubmissionQueryServiceURL = testSubmissionQueryServiceURL;
		
		//Create modules test data
		Map<String, String> testModules = new HashMap<String, String>();
		testModules.put(module1Id, module1Label);
		testModules.put(module2Id, module2Label);

		//Set up ConfigurationBean with test values
		ConfigurationBean.useUnitTestConfiguration = true;
		ConfigurationBean.testModules = testModules;
		
	}
	
}
