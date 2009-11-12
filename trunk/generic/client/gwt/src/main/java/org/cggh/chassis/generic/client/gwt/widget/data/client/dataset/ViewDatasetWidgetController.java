/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class ViewDatasetWidgetController {
	private ViewDatasetWidget owner;
	private ViewDatasetWidgetModel model;


	
	
	private Log log = LogFactory.getLog(ViewDatasetWidgetController.class);


	
	
	/**
	 * @param owner
	 * @param model
	 */
	public ViewDatasetWidgetController(
			ViewDatasetWidget owner,
			ViewDatasetWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}




	/**
	 * @param id
	 */
	public Deferred<DatasetEntry> viewEntry(String id) {
		log.enter("viewEntry");
		
		// store to use as mnemonic
		this.model.setCurrentEntryId(id);
		
		// set status pending async request
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		// make async request
		DatasetQueryService service = new DatasetQueryService(Configuration.getDatasetQueryServiceUrl());
		DatasetQuery query = new DatasetQuery();
		query.setId(id);
		Deferred<DatasetEntry> deferredResult = service.queryOne(query);
		deferredResult.addCallback(new ViewEntryCallback());
		deferredResult.addErrback(new AsyncErrback(this.owner, this.model));

		log.leave();
		return deferredResult;
	}

	
	
	/**
	 * @author aliman
	 *
	 */
	public class ViewEntryCallback implements Function<DatasetEntry, DatasetEntry> {
		private Log log = LogFactory.getLog(ViewEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DatasetEntry apply(DatasetEntry in) {
			log.enter("apply");
			
			model.setEntry(in);
			model.setStatus(AsyncWidgetModel.STATUS_READY);

			log.leave();
			return in;
		}
	}






}
