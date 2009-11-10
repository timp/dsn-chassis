/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;

import com.google.gwt.event.shared.GwtEvent.Type;

public class DataFileEntryChangeEvent extends ModelChangeEvent<DataFileEntry, DataFileEntryChangeHandler> {

	public static final Type<DataFileEntryChangeHandler> TYPE = new Type<DataFileEntryChangeHandler>();
		
	public DataFileEntryChangeEvent(DataFileEntry before, DataFileEntry after) { super(before, after); }

	@Override
	protected void dispatch(DataFileEntryChangeHandler handler) { handler.onDataFileEntryChanged(this); }

	@Override
	public Type<DataFileEntryChangeHandler> getAssociatedType() { return TYPE; }
	
}