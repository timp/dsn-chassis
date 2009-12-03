/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class AtomCrudWidgetModel
	<E extends AtomEntry>
	extends AsyncWidgetModel {

	
	
	
	private E entry;
	
	
	
	private String entryId;
	private AtomCrudRequest lastRequest;
	
	
	
	public static class CreatePendingStatus extends AsyncRequestPendingStatus {}
	
	public static final CreatePendingStatus STATUS_CREATE_PENDING = new CreatePendingStatus();		

	public static class RetrievePendingStatus extends AsyncRequestPendingStatus {}
	
	public static final RetrievePendingStatus STATUS_RETRIEVE_PENDING = new RetrievePendingStatus();		

	public static class UpdatePendingStatus extends AsyncRequestPendingStatus {}
	
	public static final UpdatePendingStatus STATUS_UPDATE_PENDING = new UpdatePendingStatus();		

	public static class DeletePendingStatus extends AsyncRequestPendingStatus {}
	
	public static final DeletePendingStatus STATUS_DELETE_PENDING = new DeletePendingStatus();	
	
	
	
	
	
	public HandlerRegistration addEntryChangeHandler(AtomEntryChangeHandler<E> h) {
		return this.addChangeHandler(h, AtomEntryChangeEvent.TYPE);
	}
	
	
	
	
	public void setEntry(E entry) {
		AtomEntryChangeEvent<E> e = new AtomEntryChangeEvent<E>(this.entry, entry);
		this.entry = entry;
		this.fireChangeEvent(e);
	}




	public E getEntry() {
		return this.entry;
	}




	/**
	 * @param entryId the entryId to set
	 */
	public void setEntryId(String entryId) {
		this.entryId = entryId;
		// don't fire events, no-one is interested
	}




	/**
	 * @return the entryId
	 */
	public String getEntryId() {
		return entryId;
	}




	public void setLastRequest(AtomCrudRequest lastRequest) {
		// don't bother to fire an event, nobody is interested
		this.lastRequest = lastRequest;
	}




	public AtomCrudRequest getLastRequest() {
		return lastRequest;
	}






	
	

}
