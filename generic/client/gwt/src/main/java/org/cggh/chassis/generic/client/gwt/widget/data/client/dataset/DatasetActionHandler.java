/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import com.google.gwt.event.shared.EventHandler;

public interface DatasetActionHandler 
	extends EventHandler {

	public void onAction(DatasetActionEvent e);
	
}