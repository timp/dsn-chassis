/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class DataFileFeedChangeEvent extends ModelChangeEvent<DataFileFeed, DataFileFeedChangeHandler> {

	public static final Type<DataFileFeedChangeHandler> TYPE = new Type<DataFileFeedChangeHandler>();
		
	public DataFileFeedChangeEvent(DataFileFeed before, DataFileFeed after) { super(before, after); }

	@Override
	protected void dispatch(DataFileFeedChangeHandler handler) { handler.onChange(this); }

	@Override
	public Type<DataFileFeedChangeHandler> getAssociatedType() { return TYPE; }
	
}