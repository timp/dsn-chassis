/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQuery;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQueryService;
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
public class MyDataFilesWidgetController {

	
	
	

	private Log log = LogFactory.getLog(MyDataFilesWidgetController.class);
	private MyDataFilesWidgetModel model;
	private MyDataFilesWidget owner;

	
	
	
	/**
	 * @param model
	 */
	public MyDataFilesWidgetController(MyDataFilesWidget owner, MyDataFilesWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}


	
	
	
	/**
	 * 
	 */
	public void refreshDataFiles() {
		log.enter("refreshDataFiles");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DataFileQueryService service = new DataFileQueryService(Configuration.getDataFileQueryServiceUrl());
		
		DataFileQuery query = new DataFileQuery();
		query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		Deferred<DataFileFeed> deferredFeed = service.query(query);
		
		deferredFeed.addCallback(new RefreshDataFilesCallback());
		deferredFeed.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class RefreshDataFilesCallback implements Function<DataFileFeed, DataFileFeed> {
		private Log log = LogFactory.getLog(RefreshDataFilesCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DataFileFeed apply(DataFileFeed in) {
			log.enter("apply");
			
			model.setFeed(in);
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			log.leave();
			return in;
		}

	}






}
