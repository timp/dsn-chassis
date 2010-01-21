/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ModelChangeEvent;
import org.cggh.chassis.generic.widget.client.ModelChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class ViewStudyWidgetModel extends AsyncWidgetModel {

	
	
	private Element entryElement = null;
	
	
	
	public void setEntry(Element entryElement) {
		EntryChangeEvent e = new EntryChangeEvent(this.entryElement, entryElement);
		this.entryElement = entryElement;
		this.fireChangeEvent(e);
	}
	
	
	
	public HandlerRegistration addEntryChangeHandler(EntryChangeHandler h) {
		return this.addChangeHandler(h, EntryChangeEvent.TYPE);
	}
	
	
	
	
	public static class EntryChangeEvent extends ModelChangeEvent<Element, EntryChangeHandler> {

		public static final Type<EntryChangeHandler> TYPE = new Type<EntryChangeHandler>();
		
		public EntryChangeEvent(Element before, Element after) {
			super(before, after);
		}

		@Override
		protected void dispatch(EntryChangeHandler h) {
			h.onChange(this);
		}

		@Override
		public com.google.gwt.event.shared.GwtEvent.Type<EntryChangeHandler> getAssociatedType() {
			return TYPE;
		}
		
	}
	
	
	
	public interface EntryChangeHandler extends ModelChangeHandler {
		
		public void onChange(EntryChangeEvent e);
		
	}



	/**
	 * @return
	 */
	public Element getEntry() {
		return this.entryElement;
	}
	
}
