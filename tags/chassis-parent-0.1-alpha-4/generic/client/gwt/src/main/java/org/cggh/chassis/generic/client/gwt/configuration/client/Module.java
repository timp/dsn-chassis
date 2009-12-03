/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.configuration.client;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * @author aliman
 *
 */
public class Module extends JavaScriptObject {

	protected Module() {}
	
	public final native String getId() /*-{ 
		return this.id; 
	}-*/;
	
	public final native String getLabel(String lang) /*-{ 
		return this.label[lang]; 
	}-*/;

}
