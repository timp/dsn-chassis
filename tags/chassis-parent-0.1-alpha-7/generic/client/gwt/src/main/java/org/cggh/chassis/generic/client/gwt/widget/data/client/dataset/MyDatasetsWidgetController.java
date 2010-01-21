/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetFeed;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQueryService;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class MyDatasetsWidgetController {

	private Log log = LogFactory.getLog(MyDatasetsWidgetController.class);
	private MyDatasetsWidget owner;
	private MyDatasetsWidgetModel model;

	
	
	// TODO consider refactoring with mydatafileswidgetcontroller
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public MyDatasetsWidgetController(
			MyDatasetsWidget owner,
			MyDatasetsWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	
	/**
	 * 
	 */
	public void refreshDatasets() {
		log.enter("refreshDatasets");

		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DatasetQueryService service = new DatasetQueryService(Configuration.getDatasetQueryServiceUrl());
		
		DatasetQuery query = new DatasetQuery();
		query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		Deferred<DatasetFeed> deferredFeed = service.query(query);
		
		deferredFeed.addCallback(new RefreshDatasetsCallback());
		deferredFeed.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}

	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class RefreshDatasetsCallback implements Function<DatasetFeed, DatasetFeed> {
		private Log log = LogFactory.getLog(RefreshDatasetsCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DatasetFeed apply(DatasetFeed in) {
			log.enter("apply");
			
			model.setFeed(in);
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			log.leave();
			return in;
		}

	}
	
	
	
	
}
