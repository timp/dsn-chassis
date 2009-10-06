/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JsArray;


/**
 * @author raok
 *
 */
public class ConfigurationBean {

	public static boolean useUnitTestConfiguration = false;
	public static String testUserDetailsServiceEndpointURL;
	public static String testUserChassisRolesPrefix;
	public static String testStudyFeedURL;
	public static String testSubmissionFeedURL;
	public static String testSubmissionQueryServiceURL;
	public static Map<String, String> testModules;
	public static String testStudyQueryServiceURL;
	
	public static String getUserDetailsServiceEndpointURL() {
		return (useUnitTestConfiguration) ? testUserDetailsServiceEndpointURL : Configuration.getUserDetailsServiceEndpointURL();
	}
	
	public static String getUserChassisRolesPrefix() {
		return (useUnitTestConfiguration) ? testUserChassisRolesPrefix : Configuration.getUserChassisRolesPrefix();
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
				String label = modules.get(i).getLabel("en");
				
				modulesMap.put(id, label);
			}
			
			return modulesMap;
		}
	}
	
	public static String getStudyQueryServiceURL() {
		return (useUnitTestConfiguration) ? testStudyQueryServiceURL : Configuration.getStudyQueryServiceURL();
	}
	
}
