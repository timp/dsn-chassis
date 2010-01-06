package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

public class SubmitterApplicationEntryPoint implements EntryPoint {

	private Log log = LogFactory.getLog(SubmitterApplicationEntryPoint.class);
	
	static {
		LogFactory.create = AllenSauerLog.create;
//		LogFactory.hide("*");
//		LogFactory.show("org.cggh.chassis.generic.xquestion.client.*");
//		LogFactory.show("org.cggh.chassis.generic.widget.client.*");
//		LogFactory.show("org.cggh.chassis.generic.async.client.*");
	}

	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		log.debug(Document.get().getBody().getInnerHTML());
		
		RootPanel p = RootPanel.get("apploading");
		log.debug("loading panel is null? "+(p == null));

		log.debug("hide loading panel");
		p.setVisible(false);
		
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
