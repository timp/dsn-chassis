/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * @author aliman
 *
 */
public class JsChassisRole extends JavaScriptObject {

	protected JsChassisRole() {}
	
	public final native String getPermissionSuffix() /*-{ 
		return this.permissionSuffix; 
	}-*/;
	
	public final native String getLabel(String lang) /*-{ 
		return this.label[lang]; 
	}-*/;

}
