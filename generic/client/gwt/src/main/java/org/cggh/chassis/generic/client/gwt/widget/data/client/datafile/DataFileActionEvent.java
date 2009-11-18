/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;



import org.cggh.chassis.generic.atom.client.ui.EventWithAtomEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;

public abstract class DataFileActionEvent 
	extends EventWithAtomEntry<DataFileActionHandler, DataFileEntry> {
	
	public DataFileActionEvent() {}
	
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(DataFileActionHandler handler) {
		handler.onAction(this);
	}

}