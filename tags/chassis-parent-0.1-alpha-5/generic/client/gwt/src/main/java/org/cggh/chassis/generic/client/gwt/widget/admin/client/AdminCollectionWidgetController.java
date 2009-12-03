/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.admin.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.HttpDeferred;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomEntry;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFactory;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFeed;
import org.cggh.chassis.generic.atomext.client.exist.ExistAtomService;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class AdminCollectionWidgetController {
	
	
	
	private AdminCollectionWidgetModel model;
	private ExistAtomService<VanillaAtomEntry, VanillaAtomFeed> service;
	private Log log = LogFactory.getLog(AdminCollectionWidgetController.class);
	private VanillaAtomFactory factory;

	
	
	public AdminCollectionWidgetController(
			AdminCollectionWidgetModel model
	) {

		this.model = model;
		this.factory = new VanillaAtomFactory();
		this.service = new ExistAtomService<VanillaAtomEntry, VanillaAtomFeed>(this.factory);
	}
	
	
	
	public Deferred<VanillaAtomFeed> refreshStatus() {
		log.enter("refreshStatus");

		model.setPending(true);
		
		log.debug("collection Title: "+model.getTitle());
		log.debug("collection URL: "+model.getUrl());
		
		HttpDeferred<VanillaAtomFeed> deferredResult = (HttpDeferred<VanillaAtomFeed>) service.getFeed(model.getUrl());
		
		deferredResult.addCallback(new Callback(deferredResult));
		
		deferredResult.addErrback(new Errback(deferredResult));

		log.leave();
		return deferredResult;
	}
	
	
	
	public Deferred<Void> createCollection() {
		log.enter("createCollection");

		model.setPending(true);

		// set up new feed document
		VanillaAtomFeed feed = factory.createFeed();
		feed.setTitle(model.getTitle());
		
		HttpDeferred<Void> deferredResult = (HttpDeferred<Void>) service.postFeed(model.getUrl(), feed);
		
		deferredResult.addCallback(new Callback(deferredResult));
		
		deferredResult.addErrback(new Errback(deferredResult));

		log.leave();
		return deferredResult;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private class Callback implements Function {
		
		private HttpDeferred result;

		private Callback(HttpDeferred result) {
			this.result = result;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
		 */
		public Object apply(Object in) {
			log.enter("[anonymous callback function]");

			model.setPending(false);
			model.setError(false);
			Response response = this.result.getLastResponse();
			model.setResponseHeaders(response.getHeadersAsString());
			model.setResponseText(response.getText());
			model.setStatusCode(response.getStatusCode());
			model.setStatusText(response.getStatusText());

			log.leave();
			return in;
		}
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private class Errback implements Function<Throwable,Throwable> {

		private HttpDeferred result;

		private Errback(HttpDeferred result) {
			this.result = result;
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
		 */
		public Throwable apply(Throwable in) {
			log.enter("[anonymous errback function]");

			model.setPending(false);
			Response response = result.getLastResponse();

			if (response != null) {
				log.debug("found error response");
				model.setError(false);
				model.setResponseHeaders(response.getHeadersAsString());
				model.setResponseText(response.getText());
				model.setStatusCode(response.getStatusCode());
				model.setStatusText(response.getStatusText());
			}

			else {
				model.setError(true);
				log.debug("unexpected error: ["+in.getClass()+"] "+in.getLocalizedMessage());
			}

			log.leave();
			return in;
		}
		

	}

	
}
