/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class NewDatasetWidgetController {

	
	
	private NewDatasetWidget owner;
	private AsyncWidgetModel model;
	private Log log = LogFactory.getLog(NewDatasetWidgetController.class);

	
	
	
	/**
	 * @param owner
	 */
	public NewDatasetWidgetController(NewDatasetWidget owner, AsyncWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}


	
	
	
	/**
	 * @param entry
	 */
	public void postEntry(DatasetEntry entry) {
		log.enter("postEntry");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DatasetPersistenceService service = new DatasetPersistenceService();
		
		String url = Configuration.getDatasetCollectionUrl();
		Deferred<DatasetEntry> deferredEntry = service.postEntry(url, entry);
		
		deferredEntry.addCallback(new PostEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}
	
	
	
	
	public class PostEntryCallback implements Function<DatasetEntry, DatasetEntry> {
		private Log log = LogFactory.getLog(PostEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DatasetEntry apply(DatasetEntry in) {
			log.enter("apply");

			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			CreateDatasetSuccessEvent e = new CreateDatasetSuccessEvent();
			e.setEntry(in);
			owner.fireEvent(e);

			log.leave();
			return in;
		}

	}



}
