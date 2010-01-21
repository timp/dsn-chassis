/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.common.client;

/**
 * @author aliman
 *
 */
public class ChassisResources {
	
	
	public static native String get(String className, String lang, String key) /*-{
		return $wnd.CHASSIS.resources[className][key][lang];
	}-*/;

}
