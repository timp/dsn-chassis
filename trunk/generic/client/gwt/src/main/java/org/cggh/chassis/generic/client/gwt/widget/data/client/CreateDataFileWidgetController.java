/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.HttpException;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFilePersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ErrorEvent;

/**
 * @author aliman
 *
 */
public class CreateDataFileWidgetController {

	
	
	
	private Log log = LogFactory.getLog(CreateDataFileWidgetController.class);
	private AsyncWidgetModel model;
	private NewDataFileWidget owner;

	
	
	
	/**
	 * @param owner 
	 * @param model
	 */
	public CreateDataFileWidgetController(NewDataFileWidget owner, AsyncWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}




	/**
	 * @param entry
	 */
	public void createDataFileEntry(DataFileEntry entry) {
		log.enter("createDataFileEntry");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		DataFilePersistenceService service = new DataFilePersistenceService();
		
		Deferred<DataFileEntry> entryDeferred = service.postEntry(Configuration.getDataFileFeedURL(), entry);
		
		entryDeferred.addCallback(new CreateDataFileEntryCallback());
		entryDeferred.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
		
	}

	
	
	
	class CreateDataFileEntryCallback implements Function<DataFileEntry,DataFileEntry> {

		private Log log = LogFactory.getLog(this.getClass());

		public DataFileEntry apply(DataFileEntry entry) {
			log.enter("apply");
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);

			CreateDataFileSuccessEvent e = new CreateDataFileSuccessEvent();
			e.setDataFileEntry(entry);
			owner.fireEvent(e);
			
			log.leave();
			return entry;
		}

		
	}
	
	
	
}
