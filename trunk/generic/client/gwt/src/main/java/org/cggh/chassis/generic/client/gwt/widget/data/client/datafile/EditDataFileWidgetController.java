/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFilePersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class EditDataFileWidgetController {


	private EditDataFileWidgetModel model;
	private EditDataFileWidget owner;

	/**
	 * @param model
	 */
	public EditDataFileWidgetController(EditDataFileWidget owner, EditDataFileWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	private Log log = LogFactory.getLog(EditDataFileWidgetController.class);

	/**
	 * @param entry
	 */
	public void putEntry(DataFileEntry entry) {
		log.enter("putEntry");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DataFilePersistenceService service = new DataFilePersistenceService();
		
		String url = Configuration.getDataFileCollectionUrl() + entry.getEditLink().getHref(); // assume relative
		
		Deferred<DataFileEntry> deferredEntry = service.putEntry(url, entry);
		
		deferredEntry.addCallback(new PutEntryCallback());
		deferredEntry.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}

	/**
	 * @author aliman
	 *
	 */
	public class PutEntryCallback implements Function<DataFileEntry, DataFileEntry> {
		private Log log = LogFactory.getLog(PutEntryCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public DataFileEntry apply(DataFileEntry in) {
			log.enter("apply");

			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			UpdateDataFileSuccessEvent e = new UpdateDataFileSuccessEvent();
			e.setEntry(in);
			owner.fireEvent(e);

			log.leave();
			return in;
		}

	}
}
