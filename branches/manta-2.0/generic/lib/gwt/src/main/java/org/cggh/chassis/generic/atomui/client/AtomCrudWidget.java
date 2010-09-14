/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomFeed;
import org.cggh.chassis.generic.atom.client.AtomQuery;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public abstract class AtomCrudWidget 
	<E extends AtomEntry, 
	F extends AtomFeed<E>, 
	Q extends AtomQuery,
	M extends AtomCrudWidgetModel<E>, 
	R extends AsyncWidgetRenderer<M>,
	C extends AtomCrudWidgetController<E, F, Q>>
	extends	DelegatingWidget<M, R> {

	
	
	
	protected C controller;
	
	
	
	
	protected abstract C createController();
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {

		super.init(); // this will instantiate model and renderer
		
		this.controller = this.createController();

	}
	
	
	
	
	
	
	public HandlerRegistration addCreateSuccessHandler(CreateSuccessHandler<E> h) {
		return this.addHandler(h, CreateSuccessEvent.TYPE);
	}

	
	
	
	public HandlerRegistration addRetrieveSuccessHandler(RetrieveSuccessHandler<E> h) {
		return this.addHandler(h, RetrieveSuccessEvent.TYPE);
	}

	
	
	
	public HandlerRegistration addUpdateSuccessHandler(UpdateSuccessHandler<E> h) {
		return this.addHandler(h, UpdateSuccessEvent.TYPE);
	}

	
	
	
	/**
	 * @param entry
	 */
	public void setEntry(E entry) {
		
		this.model.setEntry(entry);

	}





	
	
}
