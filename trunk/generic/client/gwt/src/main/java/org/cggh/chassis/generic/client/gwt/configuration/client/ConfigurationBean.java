/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import java.util.HashMap;
import java.util.Map;

import org.cggh.chassis.generic.atom.chassis.base.constants.ChassisConstants;

import com.google.gwt.core.client.JsArray;


/**
 * @author raok
 *
 */
public class ConfigurationBean {

	private static final String lang = "en";
	
	//package private to allow population in test
	static boolean useUnitTestConfiguration = false;
	static String testUserDetailsServiceEndpointURL;
	static String testUserChassisRolesPrefix;
	static ChassisRole testChassisRoleCoordinator;
	static ChassisRole testChassisRoleCurator;
	static ChassisRole testChassisRoleGatekeeper;
	static ChassisRole testChassisRoleSubmitter;
	static ChassisRole testChassisRoleUser;
	static String testStudyFeedURL;
	static String testSubmissionFeedURL;
	static String testSubmissionQueryServiceURL;
	static Map<String, String> testModules;
	static String testStudyQueryServiceURL;

	
	public static String getUserDetailsServiceEndpointURL() {
		return (useUnitTestConfiguration) ? testUserDetailsServiceEndpointURL : Configuration.getUserDetailsServiceEndpointURL();
	}
	
	public static String getUserChassisRolesPrefix() {
		return (useUnitTestConfiguration) ? testUserChassisRolesPrefix : Configuration.getUserChassisRolesPrefix();
	}
	
	public static ChassisRole getChassisRoleCoordinator() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleCoordinator;
			
		} else {
			
			JsChassisRole role = Configuration.getChassisRoleCoordinator();
			
			return new ChassisRole(ChassisConstants.coordinatorRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleCurator() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleCurator;
			
		} else {
			
			JsChassisRole role = Configuration.getChassisRoleCurator();
			
			return new ChassisRole(ChassisConstants.curatorRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleGatekeeper() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleGatekeeper;
			
		} else {
			
			JsChassisRole role = Configuration.getChassisRoleGatekeeper();
			
			return new ChassisRole(ChassisConstants.gatekeeperRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleSubmitter() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleSubmitter;
			
		} else {
			
			JsChassisRole role = Configuration.getChassisRoleSubmitter();
			
			return new ChassisRole(ChassisConstants.submitterRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static ChassisRole getChassisRoleUser() {
		if (useUnitTestConfiguration) {
			
			return testChassisRoleUser;
			
		} else {
			
			JsChassisRole role = Configuration.getChassisRoleUser();
			
			return new ChassisRole(ChassisConstants.userRoleId, role.getPermissionSuffix(), role.getLabel(lang));
		}
			
	}
	
	public static String getStudyFeedURL() {
		return (useUnitTestConfiguration) ? testStudyFeedURL : Configuration.getStudyFeedURL();				
	}
	
	public static String getSubmissionFeedURL() {
		return (useUnitTestConfiguration) ? testSubmissionFeedURL : Configuration.getSubmissionFeedURL();				
	}
	
	public static String getSubmissionQueryServiceURL() {
		return (useUnitTestConfiguration) ? testSubmissionQueryServiceURL : Configuration.getSubmissionQueryServiceURL();
	}
	
	public static Map<String, String> getModules() {
		
		if (useUnitTestConfiguration) {
			return testModules;
		} else {
			//get modules from config and convert to more useable format
			JsArray<Module> modules = Configuration.getModules();
			
			Map<String, String> modulesMap = new HashMap<String, String>();
			for (int i = 0; i < modules.length(); ++i) {
				
				String id = modules.get(i).getId();
				String label = modules.get(i).getLabel(lang);
				
				modulesMap.put(id, label);
			}
			
			return modulesMap;
		}
	}
	
	public static String getStudyQueryServiceURL() {
		return (useUnitTestConfiguration) ? testStudyQueryServiceURL : Configuration.getStudyQueryServiceURL();
	}	
	
}
