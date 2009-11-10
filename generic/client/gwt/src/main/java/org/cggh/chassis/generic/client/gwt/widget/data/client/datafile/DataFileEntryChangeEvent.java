/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class DataFileEntryChangeEvent extends ModelChangeEvent<DataFileEntry, DataFileEntryChangeHandler> {

	public static final Type<DataFileEntryChangeHandler> TYPE = new Type<DataFileEntryChangeHandler>();
		
	public DataFileEntryChangeEvent(DataFileEntry before, DataFileEntry after) { super(before, after); }

	@Override
	protected void dispatch(DataFileEntryChangeHandler handler) { handler.onChange(this); }

	@Override
	public Type<DataFileEntryChangeHandler> getAssociatedType() { return TYPE; }
	
}