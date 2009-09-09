/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

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
	
}
