/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.main.client;

import org.cggh.chassis.generic.client.gwt.application.client.ChassisClient;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.allen_sauer.gwt.log.client.DivLogger;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class MainEntryPoint implements EntryPoint {
	
	
	
	static {
		LogFactory.create = AllenSauerLog.create;
	}




	private Log log = LogFactory.getLog(this.getClass());
	
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		ChassisClient client = new ChassisClient();
		
		RootPanel.get("chassisClientPane").add(client);
		
		constructDeveloperTools();

	}

	
	
	
	/**
	 * 
	 */
	private void constructDeveloperTools() {
		log.enter("constructDeveloperTools");

		final RootPanel loggerPane = RootPanel.get("loggerPane");
		loggerPane.setVisible(false);
		
		Button logShowHideButton = new Button("show/hide log");
		RootPanel.get("developerToolsPane").add(logShowHideButton);
		logShowHideButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				loggerPane.setVisible(!loggerPane.isVisible());
			}
			
		});
		
		Widget divLogger = com.allen_sauer.gwt.log.client.Log.getLogger(DivLogger.class).getWidget();
		loggerPane.add(divLogger);


		log.trace("developer tools constructed");
		
		log.leave();
	}

}
