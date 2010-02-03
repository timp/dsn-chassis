/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;


public class DatasetFeedChangeEvent extends ModelChangeEvent<DatasetFeed, DatasetFeedChangeHandler> {

	public static final Type<DatasetFeedChangeHandler> TYPE = new Type<DatasetFeedChangeHandler>();
		
	public DatasetFeedChangeEvent(DatasetFeed before, DatasetFeed after) { super(before, after); }

	@Override
	protected void dispatch(DatasetFeedChangeHandler handler) { handler.onChange(this); }

	@Override
	public Type<DatasetFeedChangeHandler> getAssociatedType() { return TYPE; }
	
}