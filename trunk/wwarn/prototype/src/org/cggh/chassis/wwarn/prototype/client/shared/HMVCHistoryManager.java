/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.shared;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.History;




/**
 * @author aliman
 *
 */
public class HMVCHistoryManager<I> implements ValueChangeHandler<I> {


	protected HMVCComponent top;
	protected Logger log;




	public HMVCHistoryManager(HMVCComponent top) {
		this.top = top;
		this.log = new GWTLogger();
		this.initLog();
	}


	
	
	public HMVCHistoryManager(HMVCComponent top, Logger log) {
		this.top = top;
		this.log = log;
		this.initLog();
	}




	private void initLog() {
		this.log.setCurrentClass(HMVCHistoryManager.class.getName());
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
