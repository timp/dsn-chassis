package org.cggh.chassis.wwarn.ui.administrator.client;

public class Config {

	
	
	
	public static native String get(String key) /*-{
		return $wnd.config[key];
	}-*/;
	
	
	
	
}
