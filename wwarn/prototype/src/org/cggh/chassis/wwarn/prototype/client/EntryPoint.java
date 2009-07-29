/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client;

import org.cggh.chassis.wwarn.prototype.client.app.Application;
import org.cggh.chassis.wwarn.prototype.client.app.ApplicationEventListener;
import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCHistoryManager;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;

import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public class EntryPoint implements com.google.gwt.core.client.EntryPoint, ApplicationEventListener {

	Logger log;
	
	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {
		log = new GWTLogger();
		log.setCurrentClass(EntryPoint.class.getName());
		log.enter("onModuleLoad");

		log.trace("create application");
		Application app = new Application();
		app.addListener(this);
		
		log.trace("create history manager");
		HMVCHistoryManager<String> historyManager = new HMVCHistoryManager<String>(app);
		History.addValueChangeHandler(historyManager);

		log.trace("initialise application");
		app.initialise();

		log.leave();
	}

	public void onInitialisationFailure(String message) {
		// TODO anything?
	}

	public void onInitialisationSuccess() {
		log.enter("onInitialisationSuccess");
		
		log.trace("fire current history state");
		History.fireCurrentHistoryState();
		
		log.leave();
	}

}
