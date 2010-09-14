package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
public class SimpleCancelHandler implements CancelHandler {
	private Log log = LogFactory.getLog(SimpleCancelHandler.class);
		
	public void onCancel(CancelEvent e) {
		log.enter("onCancel");
		
		History.back();
		
		log.leave();
	}

}