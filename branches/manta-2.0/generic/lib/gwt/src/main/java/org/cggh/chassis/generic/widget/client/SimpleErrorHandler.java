package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.Window;

/**
 * @author aliman
 *
 */
public class SimpleErrorHandler implements ErrorHandler {
	private Log log = LogFactory.getLog(SimpleErrorHandler.class);

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ErrorHandler#onError(org.cggh.chassis.generic.widget.client.ErrorEvent)
	 */
	public void onError(ErrorEvent e) {
		log.enter("onError");

		Window.alert("an unexpected error has occurred");
		log.error("an unexpected error has occurred", e.getException());

		log.leave();
	}

}