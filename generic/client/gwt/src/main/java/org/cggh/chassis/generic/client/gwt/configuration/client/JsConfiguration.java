/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;


import com.google.gwt.core.client.JsArray;

/**
 * @author aliman
 *
 */
public class JsConfiguration {

	public static native String getUserDetailsServiceEndpointUrl() /*-{
		return $wnd.CHASSIS.userDetailsServiceEndpointUrl;
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
	
	public static native String getStudyCollectionUrl() /*-{
		return $wnd.CHASSIS.studyCollectionUrl;
	}-*/;

	public static native String getSubmissionCollectionUrl() /*-{
		return $wnd.CHASSIS.submissionCollectionUrl;
	}-*/;

	public static native String getDataFileCollectionUrl() /*-{
		return $wnd.CHASSIS.dataFileCollectionUrl;
	}-*/;

	public static native String getDatasetCollectionUrl() /*-{
		return $wnd.CHASSIS.datasetCollectionUrl;
	}-*/;

	public static native String getSandboxCollectionUrl() /*-{
		return $wnd.CHASSIS.sandboxCollectionUrl;
	}-*/;

	public static native String getReviewCollectionUrl() /*-{
		return $wnd.CHASSIS.reviewCollectionUrl;
	}-*/;

	public static native String getDataFileUploadServiceUrl() /*-{
		return $wnd.CHASSIS.dataFileUploadServiceUrl;
	}-*/;

	public static native String getSubmissionQueryServiceUrl()  /*-{
		return $wnd.CHASSIS.submissionQueryServiceUrl;
	}-*/;

	public static native JsArray<Module> getModules() /*-{
		return $wnd.CHASSIS.modules;
	}-*/;

	public static native String getStudyQueryServiceUrl() /*-{
		return $wnd.CHASSIS.studyQueryServiceUrl;
	}-*/;
	
	public static native String getDataFileQueryServiceUrl() /*-{
		return $wnd.CHASSIS.dataFileQueryServiceUrl;
	}-*/;
	
	public static native String getDatasetQueryServiceUrl() /*-{
		return $wnd.CHASSIS.datasetQueryServiceUrl;
	}-*/;

	public static native String getStudyQuestionnaireUrl() /*-{
		return $wnd.CHASSIS.studyQuestionnaireUrl;
	}-*/;

	public static native String getMediaCollectionUrl() /*-{
		return $wnd.CHASSIS.mediaCollectionUrl;
	}-*/;

	public static native String getNewDataFileServiceUrl() /*-{
		return $wnd.CHASSIS.newDataFileServiceUrl;
	}-*/;
	
	public static native String getUploadDataFileRevisionServiceUrl() /*-{
		return $wnd.CHASSIS.uploadDataFileRevisionServiceUrl;
	}-*/;

	public static native String getNetworkName() /*-{
		return $wnd.CHASSIS.networkName;
	}-*/;

	public static native String getDataSharingAgreementUrl() /*-{
		return $wnd.CHASSIS.dataSharingAgreementUrl;
	}-*/;

	public static native String getUserQueryServiceUrl() /*-{
		return $wnd.CHASSIS.userQueryServiceUrl;
	}-*/;
	
	
}
