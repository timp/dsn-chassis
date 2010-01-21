/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class DatasetEntryChangeEvent extends ModelChangeEvent<DatasetEntry, DatasetEntryChangeHandler> {

	public static final Type<DatasetEntryChangeHandler> TYPE = new Type<DatasetEntryChangeHandler>();
		
	public DatasetEntryChangeEvent(DatasetEntry before, DatasetEntry after) { super(before, after); }

	@Override
	protected void dispatch(DatasetEntryChangeHandler handler) { handler.onChange(this); }

	@Override
	public Type<DatasetEntryChangeHandler> getAssociatedType() { return TYPE; }
	
}