/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.WidgetMemory;
import org.cggh.chassis.generic.widget.client.WidgetMemory.HistoryManager;

import com.allen_sauer.gwt.log.client.DivLogger;
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
public class ChassisGeneric {

	


	private static Log log = LogFactory.getLog(ChassisGeneric.class);
	
	

	public static void run() {
		log.enter("run");
		
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
	private static void contructChassisClient() {
		log.enter("contructChassisClient");
		
		ChassisClient client = new ChassisClient();
		RootPanel.get("chassisClientPane").add(client);
		
		HistoryManager hm = new WidgetMemory.HistoryManager(client.getMemory());
		History.addValueChangeHandler(hm);
		final String token = History.getToken();
		
		Deferred<UserDetailsTO> deferredUser = client.refreshCurrentUser();

		deferredUser.addCallback(new Function<UserDetailsTO, UserDetailsTO>() {

			public UserDetailsTO apply(UserDetailsTO in) {
				log.enter("anon callback function");
				
				if (token != null && !token.equals("")) {
					log.debug("applying history token: "+token);
					History.newItem(token);
				}

				log.leave();
				return in;
			}
			
		});

		log.leave();
	}




	/**
	 * 
	 */
	private static void constructDeveloperTools() {
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
