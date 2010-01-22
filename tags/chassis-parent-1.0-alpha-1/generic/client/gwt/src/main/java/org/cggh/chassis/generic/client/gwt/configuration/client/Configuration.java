/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.atomext.shared.ChassisConstants;

import com.google.gwt.core.client.JsArray;


/**
 * @author raok
 *
 */
public class Configuration {

	private static final String lang = "en";
	
	//package private to allow population in test
	static boolean useUnitTestConfiguration = false;
	static String testUserDetailsServiceEndpointUrl;
	static String testUserChassisRolesPrefix;
	static ChassisRole testChassisRoleAdministrator;
	static ChassisRole testChassisRoleCoordinator;
	static ChassisRole testChassisRoleCurator;
	static ChassisRole testChassisRoleGatekeeper;
	static ChassisRole testChassisRoleSubmitter;
	static ChassisRole testChassisRoleUser;
	static String testStudyCollectionUrl;
	static String testStudyQueryServiceUrl;
	static String testStudyQuestionnaireUrl;
	static String testSubmissionCollectionUrl;
	static String testSubmissionQueryServiceUrl;
	static String testDataFileCollectionUrl;
	static String testDataFileQueryServiceUrl;
	static String testDataFileUploadServiceUrl;
	static String testNewDataFileServiceUrl;
	static String testUploadDataFileRevisionServiceUrl;
	static String testDatasetCollectionUrl;
	static String testDatasetQueryServiceUrl;
	static String testMediaCollectionUrl;
	static String testSandboxCollectionUrl;
	static Map<String, String> testModules;


	
	public static String getUserDetailsServiceEndpointUrl() {
		return (useUnitTestConfiguration) ? testUserDetailsServiceEndpointUrl : JsConfiguration.getUserDetailsServiceEndpointUrl();
	}
	
	public static String getUserQueryServiceUrl() {
		return JsConfiguration.getUserQueryServiceUrl();
	}
	
	public static String getUserChassisRolesPrefix() {
		return (useUnitTestConfiguration) ? testUserChassisRolesPrefix : JsConfiguration.getUserChassisRolesPrefix();
	}
	
	public static Set<ChassisRole> getChassisRoles() {
		Set<ChassisRole> roles = new HashSet<ChassisRole>();
		roles.add(Configuration.getChassisRoleAdministrator());
		roles.add(Configuration.getChassisRoleCoordinator());
		roles.add(Configuration.getChassisRoleCurator());
		roles.add(Configuration.getChassisRoleGatekeeper());
		roles.add(Configuration.getChassisRoleSubmitter());
		roles.add(Configuration.getChassisRoleUser());
		return roles;
	}
	
	public static ChassisRole getChassisRoleAdministrator() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleAdministrator;
			
		} else {
			
			JsChassisRole role = JsConfiguration.getChassisRoleAdministrator();
			
			return new ChassisRole(ChassisConstants.administratorRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleCoordinator() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleCoordinator;
			
		} else {
			
			JsChassisRole role = JsConfiguration.getChassisRoleCoordinator();
			
			return new ChassisRole(ChassisConstants.coordinatorRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleCurator() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleCurator;
			
		} else {
			
			JsChassisRole role = JsConfiguration.getChassisRoleCurator();
			
			return new ChassisRole(ChassisConstants.curatorRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleGatekeeper() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleGatekeeper;
			
		} else {
			
			JsChassisRole role = JsConfiguration.getChassisRoleGatekeeper();
			
			return new ChassisRole(ChassisConstants.gatekeeperRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleSubmitter() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleSubmitter;
			
		} else {
			
			JsChassisRole role = JsConfiguration.getChassisRoleSubmitter();
			
			return new ChassisRole(ChassisConstants.submitterRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleUser() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleUser;
			
		} else {
			
			JsChassisRole role = JsConfiguration.getChassisRoleUser();
			
			return new ChassisRole(ChassisConstants.userRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static String getStudyCollectionUrl() {
		return (useUnitTestConfiguration) ? testStudyCollectionUrl : JsConfiguration.getStudyCollectionUrl();				
	}
	
	public static String getStudyQueryServiceUrl() {
		return (useUnitTestConfiguration) ? testStudyQueryServiceUrl : JsConfiguration.getStudyQueryServiceUrl();
	}

	public static String getStudyQuestionnaireUrl() {
		return (useUnitTestConfiguration) ? testStudyQuestionnaireUrl : JsConfiguration.getStudyQuestionnaireUrl();
	}

	public static String getSubmissionCollectionUrl() {
		return (useUnitTestConfiguration) ? testSubmissionCollectionUrl : JsConfiguration.getSubmissionCollectionUrl();				
	}
	
	public static String getSubmissionQueryServiceUrl() {
		return (useUnitTestConfiguration) ? testSubmissionQueryServiceUrl : JsConfiguration.getSubmissionQueryServiceUrl();
	}
	
	public static String getDataFileCollectionUrl() {
		return (useUnitTestConfiguration) ? testDataFileCollectionUrl : JsConfiguration.getDataFileCollectionUrl();
	}
	
	public static String getDataFileQueryServiceUrl() {
		return (useUnitTestConfiguration) ? testDataFileQueryServiceUrl : JsConfiguration.getDataFileQueryServiceUrl();
	}
	
	public static String getDataFileUploadServiceUrl() {
		return (useUnitTestConfiguration) ? testDataFileUploadServiceUrl : JsConfiguration.getDataFileUploadServiceUrl();
	}

	public static String getNewDataFileServiceUrl() {
		return (useUnitTestConfiguration) ? testNewDataFileServiceUrl : JsConfiguration.getNewDataFileServiceUrl();
	}

	public static String getUploadDataFileRevisionServiceUrl() {
		return (useUnitTestConfiguration) ? testUploadDataFileRevisionServiceUrl : JsConfiguration.getUploadDataFileRevisionServiceUrl();
	}

	public static String getDatasetCollectionUrl() {
		return (useUnitTestConfiguration) ? testDatasetCollectionUrl : JsConfiguration.getDatasetCollectionUrl();
	}

	public static String getDatasetQueryServiceUrl() {
		return (useUnitTestConfiguration) ? testDatasetQueryServiceUrl : JsConfiguration.getDatasetQueryServiceUrl();
	}
	
	public static String getMediaCollectionUrl() {
		return (useUnitTestConfiguration) ? testMediaCollectionUrl : JsConfiguration.getMediaCollectionUrl();
	}
	
	public static String getSandboxCollectionUrl() {
		return (useUnitTestConfiguration) ? testSandboxCollectionUrl : JsConfiguration.getSandboxCollectionUrl();
	}
	

	public static Map<String, String> getModules() {
		
		if (useUnitTestConfiguration) {
			return testModules;
		} else {
			//get modules from config and convert to more useable format
			JsArray<Module> modules = JsConfiguration.getModules();
			
			Map<String, String> modulesMap = new HashMap<String, String>();
			for (int i = 0; i < modules.length(); ++i) {
				
				String id = modules.get(i).getId();
				String label = modules.get(i).getLabel(lang);
				
				modulesMap.put(id, label);
			}
			
			return modulesMap;
		}
	}

	/**
	 * @param selectedRoleId
	 * @return
	 */
	public static ChassisRole getChassisRole(int selectedRoleId) {
		
		for (ChassisRole role : Configuration.getChassisRoles()) {
			if (selectedRoleId == role.roleId) {
				return role;
			}
		}
		
		return null;
	}

	
	
	
	
	/**
	 * @return
	 */
	public static String getNetworkName() {
		return JsConfiguration.getNetworkName();
	}

	
	
	
	
	/**
	 * @return
	 */
	public static String getDataSharingAgreementUrl() {
		return JsConfiguration.getDataSharingAgreementUrl();
	}

	
	
	
	/**
	 * @return
	 */
	public static String getReviewCollectionUrl() {
		return JsConfiguration.getReviewCollectionUrl();
	}	
	
	
	
	
}
