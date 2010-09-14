/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.Map.Entry;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public abstract class AtomQueryService
	<E extends AtomEntry, F extends AtomFeed<E>, Q extends AtomQuery> {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public AtomQueryService(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	
	
	
	protected abstract AtomService<E, F> createPersistenceService();

	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#query(org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery)
	 */
	public Deferred<F> query(Q query) {
		log.enter("query");

		String url = this.serviceUrl + "?";
		
		for (Entry<String, String> param : query.getParams().entrySet()) {
			url += param.getKey() + "=" + param.getValue() + "&";
		}

		log.debug("url: "+url);

		AtomService<E, F> service = this.createPersistenceService();

		Deferred<F> deferredResults = service.getFeed(url);
		
		log.leave();
		return deferredResults;
	}



	/**
	 * @param query
	 * @return
	 */
	public Deferred<E> queryOne(Q query) {
		log.enter("queryOne");
		
		Deferred<F> deferredResults = this.query(query);
		
		Deferred<E> deferredResult = deferredResults.adapt(new Function<F, E>() {

			public E apply(F in) {
				E entry = null;
				if (in.getEntries().size() > 0) {
					entry = in.getEntries().get(0);
				}
				return entry;
			}
			
		});
		
		log.leave();
		return deferredResult;
	}

	
	

}
