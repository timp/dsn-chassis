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

import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public class EntryPoint implements com.google.gwt.core.client.EntryPoint {

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
		
		log.trace("create history manager");
		FractalUIHistoryManager<String> historyManager = new FractalUIHistoryManager<String>(app);
		History.addValueChangeHandler(historyManager);

		log.trace("initialise application");
		Deferred<Application> def = app.initialise();
		
		log.trace("add callback to complete post-initialisation");
		def.addCallback(new Function<Application,Application>() {

			public Application apply(Application in) {
				log.trace("callback: fire current history state");
				History.fireCurrentHistoryState();
				return in;
			}
		
		});

		log.leave();
	}


}
