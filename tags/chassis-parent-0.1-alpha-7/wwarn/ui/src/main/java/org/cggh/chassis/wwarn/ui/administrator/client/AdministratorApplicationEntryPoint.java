package org.cggh.chassis.wwarn.ui.administrator.client;

import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
//import org.cggh.chassis.generic.widget.client.WidgetMemory;
//import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;
//
//import com.google.gwt.user.client.History;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class AdministratorApplicationEntryPoint implements EntryPoint {

	
	
	
	private Log log = LogFactory.getLog(AdministratorApplicationEntryPoint.class);
	
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
//		LogFactory.hide("*");
//		LogFactory.show("org.cggh.chassis.generic.xquestion.client.*");
//		LogFactory.show("org.cggh.chassis.generic.widget.client.*");
//		LogFactory.show("org.cggh.chassis.generic.async.client.*");
	}

	
	
	
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		RootPanel loadingPanel = RootPanel.get("apploading");

		log.debug("hide loading panel");
		loadingPanel.setVisible(false);
		
		log.debug("instantiate main application widget");
		AdministratorHomeWidget widget = new AdministratorHomeWidget();
		RootPanel.get("content").add(widget);
		widget.refresh();
		
//		log.debug("setup history manager");
//		HistoryManager hm = new WidgetMemory.HistoryManager(widget.getMemory());
//		History.addValueChangeHandler(hm);
//		
//		log.debug("fire current history state");
//		History.fireCurrentHistoryState();
				
		log.leave();
	}

	
	
	

}
