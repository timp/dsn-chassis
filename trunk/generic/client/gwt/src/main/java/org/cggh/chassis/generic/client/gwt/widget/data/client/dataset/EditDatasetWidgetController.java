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
public class EditDatasetWidgetController {

	private EditDatasetWidget owner;
	private EditDatasetWidgetModel model;
	private Log log = LogFactory.getLog(EditDatasetWidgetController.class);


	// TODO consider refactor with editdatafilewidget controller and editstudywidgetcontroller
	
	/**
	 * @param owner
	 * @param model
	 */
	public EditDatasetWidgetController(
			EditDatasetWidget owner,
			EditDatasetWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	/**
	 * @param entry
	 */
	public void putEntry(DatasetEntry entry) {
		log.enter("putEntry");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DatasetPersistenceService service = new DatasetPersistenceService();
		
		String url = Configuration.getDatasetCollectionUrl() + entry.getEditLink().getHref(); // assume relative
		
		Deferred<DatasetEntry> deferredEntry = service.putEntry(url, entry);
		
		deferredEntry.addCallback(new PutEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}

	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class PutEntryCallback implements Function<DatasetEntry, DatasetEntry> {
		private Log log = LogFactory.getLog(PutEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DatasetEntry apply(DatasetEntry in) {
			log.enter("apply");

			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			UpdateDatasetSuccessEvent e = new UpdateDatasetSuccessEvent();
			e.setEntry(in);
			owner.fireEvent(e);

			log.leave();
			return in;
		}

	}
	
	
	
}
