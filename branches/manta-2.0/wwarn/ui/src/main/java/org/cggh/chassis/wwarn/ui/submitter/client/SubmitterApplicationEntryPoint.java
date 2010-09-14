package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * The entry point for the submitter application.
 * 
 * @author aliman
 */
public class SubmitterApplicationEntryPoint implements EntryPoint {

	
	
	
	private Log log = LogFactory.getLog(SubmitterApplicationEntryPoint.class);
	
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
		LogFactory.hide("*");
		LogFactory.show("org.cggh.chassis.wwarn.ui.submitter.*");
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
		SubmitterApplicationWidget widget = new SubmitterApplicationWidget();
		RootPanel.get("content").add(widget);
		
		log.debug("setup history manager");
		HistoryManager hm = new WidgetMemory.HistoryManager(widget.getMemory());
		History.addValueChangeHandler(hm);
		
		log.debug("fire current history state");
		History.fireCurrentHistoryState();
				
		log.leave();
	}

	
	
	
}
