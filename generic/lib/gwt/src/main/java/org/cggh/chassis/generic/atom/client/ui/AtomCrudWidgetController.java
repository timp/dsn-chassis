/**
 * 
 */
package org.cggh.chassis.generic.atom.client.ui;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomEntry;
import org.cggh.chassis.generic.atom.client.AtomFeed;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atomext.client.shared.AtomQuery;
import org.cggh.chassis.generic.atomext.client.shared.AtomQueryService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public abstract class AtomCrudWidgetController 
	<E extends AtomEntry, F extends AtomFeed<E>, Q extends AtomQuery> {

	
	
	
	private AtomCrudWidgetModel<E> model;
	private Log log = LogFactory.getLog(AtomCrudWidgetController.class);
	private AtomCrudWidget<E, F, Q, ?, ?, ?> owner;
	private String collectionUrl;
	
	
	
	
	public AtomCrudWidgetController(
		AtomCrudWidget<E, F, Q, ?, ?, ?> owner,
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
		
		this.model.setStatus(AtomCrudWidgetModel.STATUS_CREATE_PENDING);
		
		AtomService<E, F> service = this.createAtomService();
		
		String url = this.collectionUrl;
		
		Deferred<E> deferredEntry = service.postEntry(url, entry);
		
		deferredEntry.addCallback(new CreateEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}
	
	
	
	
	
	public class CreateEntryCallback implements Function<E, E> {
		private Log log = LogFactory.getLog(CreateEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public E apply(E in) {
			log.enter("apply");

			model.setEntry(in);
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			CreateSuccessEvent<E> e = new CreateSuccessEvent<E>();
			e.setEntry(in);
			owner.fireEvent(e);

			log.leave();
			return in;
		}

	}


	
	
	/**
	 * @param entry
	 */
	public void retrieveEntry(String url) {
		log.enter("retrieveEntry");
		
		this.model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);
		
		AtomService<E, F> service = this.createAtomService();
		
		Deferred<E> deferredEntry = service.getEntry(url);
		
		deferredEntry.addCallback(new RetrieveEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}
	
	
	
	



	/**
	 * @param entry
	 */
	public void retrieveExpandedEntry(String id) {
		log.enter("retrieveExpandedEntry");
		
		this.model.setStatus(AtomCrudWidgetModel.STATUS_RETRIEVE_PENDING);
		
		// use query service to retrieve entry, so we can do link expansion
		AtomQueryService<E, F, Q> service = this.createQueryService();
		Q query = this.createQuery();
		query.setId(id);
		
		Deferred<E> deferredEntry = service.queryOne(query);
		
		deferredEntry.addCallback(new RetrieveEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}
	
	
	
	
	
	public class RetrieveEntryCallback implements Function<E, E> {
		private Log log = LogFactory.getLog(RetrieveEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public E apply(E in) {
			log.enter("apply");

			model.setEntry(in);
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			RetrieveSuccessEvent<E> e = new RetrieveSuccessEvent<E>();
			e.setEntry(in);
			owner.fireEvent(e);

			log.leave();
			return in;
		}

	}




	/**
	 * @param entry
	 */
	public void updateEntry(E entry) {
		log.enter("updateEntry");
		
		this.model.setStatus(AtomCrudWidgetModel.STATUS_UPDATE_PENDING);
		
		AtomService<E, F> service = this.createAtomService();
		
		String url = entry.getEditLink().getHref();
		
		Deferred<E> deferredEntry = service.putEntry(url, entry);
		
		deferredEntry.addCallback(new UpdateEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}
	
	
	
	
	public class UpdateEntryCallback implements Function<E, E> {
		private Log log = LogFactory.getLog(UpdateEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public E apply(E in) {
			log.enter("apply");

			model.setEntry(in);
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			UpdateSuccessEvent<E> e = new UpdateSuccessEvent<E>();
			e.setEntry(in);
			owner.fireEvent(e);

			log.leave();
			return in;
		}

	}




	
	
}
