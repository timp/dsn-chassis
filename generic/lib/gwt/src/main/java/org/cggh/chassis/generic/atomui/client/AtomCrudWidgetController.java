/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomFeed;
import org.cggh.chassis.generic.atom.client.AtomQuery;
import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public abstract class AtomCrudWidgetController 
	<E extends AtomEntry, F extends AtomFeed<E>, Q extends AtomQuery> {

	
	
	
	AtomCrudWidgetModel<E> model;
	private static Log log = LogFactory.getLog(AtomCrudWidgetController.class);
	Widget owner;
	protected String collectionUrl;
	
	
	
	
	public AtomCrudWidgetController(
		Widget owner,
		AtomCrudWidgetModel<E> model,
		String collectionUrl) {
		this.owner = owner;
		this.model = model;
		this.collectionUrl = collectionUrl;
	}
	
	
	
	
	public abstract AtomService<E,F> createAtomService();
	
	
	
	
	
	public abstract AtomQueryService<E, F, Q> createQueryService();
	
	
	
	
	public abstract Q createQuery();
	
	
	
	/**
	 * @param entry
	 */
	public void createEntry(E entry) {
		log.enter("createEntry");
		
		AtomService<E, F> service = this.createAtomService();
		Function<E, E> callback = new CreateEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		
		createEntry(entry, this.model, service, this.collectionUrl, callback, errback);
		
		log.leave();
	}
	
	
	
	
	protected static <E extends AtomEntry, F extends AtomFeed<E>> void createEntry(
			E entry,
			AtomCrudWidgetModel<E> model,
			AtomService<E, F> service,
			String collectionUrl,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {
		
		model.setStatus(AtomCrudWidgetModel.STATUS_CREATE_PENDING);
		Deferred<E> deferredEntry = service.postEntry(collectionUrl, entry);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);
		
	}
	
	
	
	
	
	public void retrieveEntry(String entryUrl) {
		log.enter("retrieveEntry");
				
		AtomService<E, F> service = this.createAtomService();
		Function<E, E> callback = new RetrieveEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		
		retrieveEntry(this.model, service, entryUrl, callback, errback);
		
		log.leave();
	}
	
	

	
	protected static <E extends AtomEntry, F extends AtomFeed<E>> void retrieveEntry(
			AtomCrudWidgetModel<E> model,
			AtomService<E, F> service,
			String entryUrl,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {

		model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);
		Deferred<E> deferredEntry = service.getEntry(entryUrl);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);

	}


	
	
	public void retrieveExpandedEntry(String entryId) {
		log.enter("retrieveExpandedEntry");
		
		// use query service to retrieve entry, so we can do link expansion
		
		AtomQueryService<E, F, Q> service = this.createQueryService();
		Q query = this.createQuery();
		Function<E, E> callback = new RetrieveEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		
		retrieveExpandedEntry(this.model, service, query, entryId, callback, errback);
		
		log.leave();
	}
	

	
	
	
	protected static <E extends AtomEntry, F extends AtomFeed<E>, Q extends AtomQuery> void retrieveExpandedEntry(
			AtomCrudWidgetModel<E> model,
			AtomQueryService<E, F, Q> service,
			Q query,
			String entryId,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {

		model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);
		query.setId(entryId);
		Deferred<E> deferredEntry = service.queryOne(query);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);

	}


	
	

	
	
	
	/**
	 * @param entry
	 */
	public void updateEntry(E entry) {
		log.enter("updateEntry");
		
		AtomService<E, F> service = this.createAtomService();
		Function<E, E> callback = new UpdateEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		String entryUrl = entry.getEditLink().getHref();
		
		updateEntry(this.model, service, entryUrl, entry, callback, errback);
		
		log.leave();
	}
	
	
	
	
	protected static <E extends AtomEntry, F extends AtomFeed<E>> void updateEntry(
			AtomCrudWidgetModel<E> model,
			AtomService<E, F> service,
			String entryUrl,
			E entry,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {

		model.setStatus(AtomCrudWidgetModel.STATUS_UPDATE_PENDING);
		Deferred<E> deferredEntry = service.putEntry(entryUrl, entry);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);
		
	}




	
	
}
