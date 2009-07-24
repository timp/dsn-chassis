/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

/**
 * @author aliman
 * @param <I>
 *
 */
class HistoryListener<I> implements ValueChangeHandler<I> {

	private Controller controller;

	HistoryListener(Controller controller) {
		this.controller = controller;
	}
	
	public void onValueChange(ValueChangeEvent<I> event) {
		this.controller.setStateToken(event.getValue().toString());
	}

}
