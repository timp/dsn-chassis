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

/**
 * @author aliman
 *
 */
public class ShareDatasetWidgetController {

	
	
	private Log log = LogFactory.getLog(ShareDatasetWidgetController.class);
	private ShareDatasetWidgetModel model;
	private ShareDatasetWidget owner;

	
	
	
	/**
	 * @param owner 
	 * @param model
	 */
	public ShareDatasetWidgetController(ShareDatasetWidget owner, ShareDatasetWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	/**
	 * @param id
	 */
	public void retrieveEntry(String id) {
		log.enter("retrieveEntry");
		
		log.debug("set status to retrieve dataset pending");
		
		this.model.setStatus(ShareDatasetWidgetModel.STATUS_RETRIEVE_DATASET_PENDING);
		
		log.debug("now retrieve expanded dataset");
		
		DatasetQueryService service = new DatasetQueryService(Configuration.getDatasetQueryServiceUrl());
		DatasetQuery query = new DatasetQuery();
		query.setId(id);
		
		Deferred<DatasetEntry> deferredEntry = service.queryOne(query);
		
		log.debug("add callbacks to handle async response");
		
		Function<DatasetEntry, DatasetEntry> callback = new Function<DatasetEntry, DatasetEntry>() {

			public DatasetEntry apply(DatasetEntry in) {
				log.enter("[anon callback]");

				log.debug("retrieve dataset was a success");
				
				model.setStatus(ShareDatasetWidgetModel.STATUS_DATASET_RETRIEVED);
				
				model.setDatasetEntry(in);
				
				log.leave();
				return in;
			}
			
		};
		
		deferredEntry.addCallback(callback);
		
		deferredEntry.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
	}

	
	
	
}
