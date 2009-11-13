/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.main.client;

import org.cggh.chassis.generic.client.gwt.application.client.ChassisClient;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;

import com.allen_sauer.gwt.log.client.DivLogger;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class MainEntryPoint implements EntryPoint {
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
		LogFactory.hide("*");
		LogFactory.show("org.cggh.chassis.generic.client.gwt.application.*");
		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.data.*");
		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.study.*");
	}




	private Log log = LogFactory.getLog(this.getClass());
	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log.enter("onModuleLoad");
		
		contructChassisClient();
		
		constructDeveloperTools();
		
		RootPanel.get("apploading").addStyleName("invisible");
		RootPanel.get("apploaded").removeStyleName("invisible");
		
		log.info("application loaded");

		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	private void contructChassisClient() {
		ChassisClient client = new ChassisClient();
		RootPanel.get("chassisClientPane").add(client);
		
		HistoryManager hm = new WidgetMemory.HistoryManager(client.getMemory());
		History.addValueChangeHandler(hm);
		History.fireCurrentHistoryState();

	}




	/**
	 * 
	 */
	private void constructDeveloperTools() {
		log.enter("constructDeveloperTools");

		final RootPanel loggerPane = RootPanel.get("loggerPane");
		loggerPane.setVisible(false);
		
		DivLogger l = com.allen_sauer.gwt.log.client.Log.getLogger(DivLogger.class);
		if (l != null) {

			Anchor logShowHide = new Anchor();
			logShowHide.setText("show/hide log");
			
			RootPanel.get("developerToolsPane").add(logShowHide);
			logShowHide.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					loggerPane.setVisible(!loggerPane.isVisible());
				}
				
			});
			
			Widget divLogger = l.getWidget();
			loggerPane.add(divLogger);

		}


		log.debug("developer tools constructed");
		
		log.leave();
	}

}
