package org.cggh.chassis.generic.client.gwt.application.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationEntryPoint implements EntryPoint {

	public void onModuleLoad() {

		ChassisClient application = new ChassisClient();
		
		RootPanel.get("application").add(application);

	}

}
