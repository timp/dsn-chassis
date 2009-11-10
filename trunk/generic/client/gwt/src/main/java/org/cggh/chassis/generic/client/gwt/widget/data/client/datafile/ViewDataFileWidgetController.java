/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQuery;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileQueryService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class ViewDataFileWidgetController {

	
	
	
	private Log log = LogFactory.getLog(ViewDataFileWidgetController.class);
	private ViewDataFileWidgetModel model;
	private ViewDataFileWidget owner;

	
	
	
	
	/**
	 * @param model
	 */
	public ViewDataFileWidgetController(ViewDataFileWidget owner, ViewDataFileWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}





	/**
	 * @param id
	 */
	public Deferred<DataFileEntry> getEntry(String id) {
		log.enter("getEntry");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DataFileQueryService service = new DataFileQueryService(Configuration.getDataFileQueryServiceURL());
		DataFileQuery query = new DataFileQuery();
		query.setId(id);
		Deferred<DataFileEntry> deferredResult = service.queryOne(query);
		deferredResult.addCallback(new GetEntryCallback());
		deferredResult.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();	
		return deferredResult;
	}

	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class GetEntryCallback implements Function<DataFileEntry, DataFileEntry> {
		private Log log = LogFactory.getLog(GetEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DataFileEntry apply(DataFileEntry in) {
			log.enter("apply");
			
			model.setEntry(in);
			model.setStatus(AsyncWidgetModel.STATUS_READY);

			log.leave();
			return in;
		}
	}





	
}
