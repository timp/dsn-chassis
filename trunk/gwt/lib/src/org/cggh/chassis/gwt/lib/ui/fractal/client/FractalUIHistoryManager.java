/**
 * 
 */
package org.cggh.chassis.gwt.lib.ui.fractal.client;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;




/**
 * @author aliman
 *
 */
public class FractalUIHistoryManager<I> implements ValueChangeHandler<I> {


	protected FractalUIComponent top;
	protected Logger log;




	public FractalUIHistoryManager(FractalUIComponent top) {
		this.top = top;
		this.log = new GWTLogger();
		this.initLog();
	}


	
	
	public FractalUIHistoryManager(FractalUIComponent top, Logger log) {
		this.top = top;
		this.log = log;
		this.initLog();
	}




	private void initLog() {
		this.log.setCurrentClass(FractalUIHistoryManager.class.getName());
	}




	public void onValueChange(ValueChangeEvent<I> event) {
		log.enter("onValueChange");
		
		String historyToken = event.getValue().toString();
		log.trace("historyToken: "+historyToken);
		
		JSONValue stateToken = null;
		
		try {
			log.trace("try to parse history token");
			stateToken = JSONParser.parse(historyToken);
		} catch (Throwable ex) {
			log.trace("exception parsing history token");
		}
		
		log.trace("capture history event");
		this.top.captureHistoryEvent(stateToken);
		
		log.leave();
	}
	
	
	
}
