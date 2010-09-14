package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * The entry point for the curator application.
 * 
 * @author aliman
 */
public class CuratorApplicationEntryPoint implements EntryPoint {

	
	
	
	private Log log = LogFactory.getLog(CuratorApplicationEntryPoint.class);
	
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
		LogFactory.hide("*");
		LogFactory.show("org.cggh.chassis.wwarn.ui.curator.client.*");
//		LogFactory.show("org.cggh.chassis.wwarn.ui.curator.client.ViewStudyWidgetController");
//		LogFactory.show("org.cggh.chassis.wwarn.ui.curator.client.ViewStudyWidgetRenderer");
//		LogFactory.show("org.cggh.chassis.generic.widget.client.*");
//		LogFactory.show("org.cggh.chassis.wwarn.ui.curator.client.CuratorApplication*");
//		LogFactory.show("org.cggh.chassis.generic.xml.client.XML");
//		LogFactory.show("org.cggh.chassis.generic.xquestion.client.XQSModelBase");
//		LogFactory.show("org.cggh.chassis.generic.widget.client.*");
//		LogFactory.show("org.cggh.chassis.generic.async.client.*");
	}

	
	
	
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		RootPanel loadingPanel = RootPanel.get("apploading");

		log.debug("hide loading panel");
		loadingPanel.setVisible(false);
		
		log.debug("instantiate main application widget");
		CuratorApplicationWidget widget = new CuratorApplicationWidget();
		RootPanel.get("content").add(widget);

		// don't call refresh here, allow widget memory to call refresh
		// as appropriate to history state
//		log.debug("call refresh");
//		widget.refresh();
		
		log.debug("setup history manager");
		HistoryManager hm = new WidgetMemory.HistoryManager(widget.getMemory());
		History.addValueChangeHandler(hm);
		
		log.debug("fire current history state");
		History.fireCurrentHistoryState();
				
		log.leave();
	}

	
	
	
}
