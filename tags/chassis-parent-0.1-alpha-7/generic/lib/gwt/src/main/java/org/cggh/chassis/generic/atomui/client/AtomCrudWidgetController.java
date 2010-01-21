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

	
	
	
	protected AtomCrudWidgetModel<E> model;
	private static Log log = LogFactory.getLog(AtomCrudWidgetController.class);
	protected Widget owner;
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
	public Deferred<E> createEntry(E entry) {
		log.enter("createEntry");
		
		AtomService<E, F> service = this.createAtomService();
		Function<E, E> callback = new CreateEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		
		Deferred<E> deferredEntry = createEntry(entry, this.model, service, this.collectionUrl, callback, errback);
		
		log.leave();
		return deferredEntry;
	}
	
	
	
	
	protected static <E extends AtomEntry, F extends AtomFeed<E>> Deferred<E> createEntry(
			E entry,
			AtomCrudWidgetModel<E> model,
			AtomService<E, F> service,
			String collectionUrl,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {
		
		model.setLastRequest(new AtomCrudRequest(collectionUrl, null, null, AtomCrudRequest.RequestType.CREATE));

		model.setStatus(AtomCrudWidgetModel.STATUS_CREATE_PENDING);
		Deferred<E> deferredEntry = service.postEntry(collectionUrl, entry);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);
		return deferredEntry;
	}
	
	
	
	
	
	public Deferred<E> retrieveEntry(String entryUrl) {
		log.enter("retrieveEntry");
				
		log.debug("entryUrl: "+entryUrl);
		
		AtomService<E, F> service = this.createAtomService();
		Function<E, E> callback = new RetrieveEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		
		Deferred<E> deferredEntry = retrieveEntry(this.model, service, entryUrl, callback, errback);
		
		log.leave();
		return deferredEntry;
	}
	
	

	
	protected static <E extends AtomEntry, F extends AtomFeed<E>> Deferred<E> retrieveEntry(
			AtomCrudWidgetModel<E> model,
			AtomService<E, F> service,
			String entryUrl,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {

		model.setLastRequest(new AtomCrudRequest(null, null, entryUrl, AtomCrudRequest.RequestType.RETRIEVE));

		model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);
		Deferred<E> deferredEntry = service.getEntry(entryUrl);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);
		return deferredEntry;

	}


	
	
	public Deferred<E> retrieveExpandedEntry(String entryId) {
		log.enter("retrieveExpandedEntry");
		
		log.debug("entryId: "+entryId);
		
		// use query service to retrieve entry, so we can do link expansion
		
		AtomQueryService<E, F, Q> service = this.createQueryService();
		Q query = this.createQuery();
		Function<E, E> callback = new RetrieveEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		
		Deferred<E> deferredEntry = retrieveExpandedEntry(this.model, service, query, entryId, callback, errback);
		
		log.leave();
		return deferredEntry;
	}
	

	
	
	
	protected static <E extends AtomEntry, F extends AtomFeed<E>, Q extends AtomQuery> Deferred<E> retrieveExpandedEntry(
			AtomCrudWidgetModel<E> model,
			AtomQueryService<E, F, Q> service,
			Q query,
			String entryId,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {

		model.setLastRequest(new AtomCrudRequest(null, entryId, null, AtomCrudRequest.RequestType.RETRIEVEEXPANDED));

		model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);
		model.setEntryId(entryId);
		query.setId(entryId);
		Deferred<E> deferredEntry = service.queryOne(query);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);
		return deferredEntry;

	}


	
	

	
	
	
	/**
	 * @param entry
	 */
	public Deferred<E> updateEntry(E entry) {
		log.enter("updateEntry");
		
		AtomService<E, F> service = this.createAtomService();
		Function<E, E> callback = new UpdateEntryCallback<E>(this.owner, this.model);
		Function<Throwable, Throwable> errback = new AsyncErrback(this.owner, this.model);
		String entryUrl = entry.getEditLink().getHref();
		
		Deferred<E> deferredEntry = updateEntry(this.model, service, entryUrl, entry, callback, errback);
		
		log.leave();
		return deferredEntry;
	}
	
	
	
	
	protected static <E extends AtomEntry, F extends AtomFeed<E>> Deferred<E> updateEntry(
			AtomCrudWidgetModel<E> model,
			AtomService<E, F> service,
			String entryUrl,
			E entry,
			Function<E, E> callback,
			Function<Throwable, Throwable> errback
	) {

		model.setLastRequest(new AtomCrudRequest(null, null, entryUrl, AtomCrudRequest.RequestType.UPDATE));

		model.setStatus(AtomCrudWidgetModel.STATUS_UPDATE_PENDING);
		Deferred<E> deferredEntry = service.putEntry(entryUrl, entry);
		deferredEntry.addCallback(callback);
		deferredEntry.addErrback(errback);
		return deferredEntry;
	}




	
	
}
