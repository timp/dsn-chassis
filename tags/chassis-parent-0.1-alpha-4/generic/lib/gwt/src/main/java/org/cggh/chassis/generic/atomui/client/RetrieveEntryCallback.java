/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.user.client.ui.Widget;

public class RetrieveEntryCallback<E extends AtomEntry> implements Function<E, E> {

	
	
	private Log log = LogFactory.getLog(RetrieveEntryCallback.class);
	private Widget eventSource;
	private AtomCrudWidgetModel<E> model;

	
	
	
	public RetrieveEntryCallback(Widget eventSource, AtomCrudWidgetModel<E> model) {
		this.eventSource = eventSource;
		this.model = model;
	}

	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
	 */
	public E apply(E in) {
		log.enter("apply");

		model.setEntry(in);
		
		model.setStatus(AsyncWidgetModel.STATUS_READY);
		
		RetrieveSuccessEvent<E> e = new RetrieveSuccessEvent<E>();
		e.setEntry(in);
		eventSource.fireEvent(e);

		log.leave();
		return in;
	}
	
	
	

}