/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;
import org.cggh.chassis.gwt.lib.twisted.client.Function;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIHistoryManager;
import org.cggh.chassis.wwarn.prototype.client.app.Application;
import org.cggh.chassis.wwarn.prototype.client.app.ApplicationEventListener;

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
//		app.addListener(this);
		
		log.trace("create history manager");
		FractalUIHistoryManager<String> historyManager = new FractalUIHistoryManager<String>(app);
		History.addValueChangeHandler(historyManager);

		log.trace("initialise application");
		Deferred def = app.initialise();
		
		log.trace("add callback to complete post-initialisation");
		def.addCallback(new Function() {

			public Object apply(Object in) {
				log.trace("callback: fire current history state");
				History.fireCurrentHistoryState();
				return null;
			}
		
		});

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
