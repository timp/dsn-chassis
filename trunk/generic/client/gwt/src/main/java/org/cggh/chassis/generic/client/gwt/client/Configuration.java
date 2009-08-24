/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.client;

/**
 * @author aliman
 *
 */
public class Configuration {

	public static native String getUserDetailsServiceEndpointURL() /*-{
		return $wnd.CHASSIS.userDetailsServiceEndpointURL;
	}-*/;
	
}
