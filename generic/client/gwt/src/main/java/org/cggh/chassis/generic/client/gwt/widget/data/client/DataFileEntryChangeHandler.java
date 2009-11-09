/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

public interface DataFileEntryChangeHandler extends ModelChangeHandler {
	
	public void onDataFileEntryChanged(DataFileEntryChangeEvent e);

}