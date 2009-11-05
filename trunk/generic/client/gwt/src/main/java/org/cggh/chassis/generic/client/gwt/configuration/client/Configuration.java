/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;


import com.google.gwt.core.client.JsArray;

/**
 * @author aliman
 *
 */
public class Configuration {

	public static native String getUserDetailsServiceEndpointURL() /*-{
		return $wnd.CHASSIS.userDetailsServiceEndpointURL;
	}-*/;
	
	public static native String getUserChassisRolesPrefix() /*-{
		return $wnd.CHASSIS.userChassisRolesPrefix;
	}-*/;
	
	public static native JsChassisRole getChassisRoleAdministrator() /*-{
		return $wnd.CHASSIS.chassisRoleAdministrator;
	}-*/;

	public static native JsChassisRole getChassisRoleCoordinator() /*-{
		return $wnd.CHASSIS.chassisRoleCoordinator;
	}-*/;
	
	public static native JsChassisRole getChassisRoleCurator() /*-{
		return $wnd.CHASSIS.chassisRoleCurator;
	}-*/;
	
	public static native JsChassisRole getChassisRoleGatekeeper() /*-{
		return $wnd.CHASSIS.chassisRoleGatekeeper;
	}-*/;
	
	public static native JsChassisRole getChassisRoleSubmitter() /*-{
		return $wnd.CHASSIS.chassisRoleSubmitter;
	}-*/;
	
	public static native JsChassisRole getChassisRoleUser() /*-{
		return $wnd.CHASSIS.chassisRoleUser;
	}-*/;
	
	public static native String getStudyFeedURL() /*-{
		return $wnd.CHASSIS.studyFeedURL;
	}-*/;

	public static native String getSubmissionFeedURL() /*-{
		return $wnd.CHASSIS.submissionFeedURL;
	}-*/;

	public static native String getDataFileFeedURL() /*-{
		return $wnd.CHASSIS.dataFileFeedURL;
	}-*/;


	public static native String getSandboxFeedURL() /*-{
		return $wnd.CHASSIS.sandboxFeedURL;
	}-*/;

	public static native String getDataFileUploadServiceURL() /*-{
		return $wnd.CHASSIS.dataFileUploadServiceURL;
	}-*/;

	/**
	 * @return
	 */
	public static native String getSubmissionQueryServiceURL()  /*-{
		return $wnd.CHASSIS.submissionQueryServiceURL;
	}-*/;

	public static native JsArray<Module> getModules() /*-{
		return $wnd.CHASSIS.modules;
	}-*/;

	/**
	 * @return
	 */
	public static native String getStudyQueryServiceURL() /*-{
		return $wnd.CHASSIS.studyQueryServiceURL;
	}-*/;
	
	public static native String getDataFileQueryServiceURL() /*-{
		return $wnd.CHASSIS.dataFileQueryServiceURL;
	}-*/;
	
	public static native String getStudyQuestionnaireURL() /*-{
		return $wnd.CHASSIS.studyQuestionnaireURL;
	}-*/;
}
