/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

public interface DatasetEntryChangeHandler extends ModelChangeHandler {
	
	public void onChange(DatasetEntryChangeEvent e);

}