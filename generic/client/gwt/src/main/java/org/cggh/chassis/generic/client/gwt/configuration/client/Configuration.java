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
	
	public static native String getStudyFeedURL() /*-{
		return $wnd.CHASSIS.studyFeedURL;
	}-*/;

	public static native String getSubmissionFeedURL() /*-{
		return $wnd.CHASSIS.submissionFeedURL;
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
	
}
