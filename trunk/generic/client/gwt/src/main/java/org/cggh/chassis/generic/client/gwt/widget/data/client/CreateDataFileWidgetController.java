/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFilePersistenceService;
import org.cggh.chassis.generic.client.gwt.common.client.ErrorEvent;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.HttpException;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class CreateDataFileWidgetController {

	
	
	
	private Log log = LogFactory.getLog(CreateDataFileWidgetController.class);
	private AsyncWidgetModel model;
	private CreateDataFileWidget owner;

	
	
	
	/**
	 * @param owner 
	 * @param model
	 */
	public CreateDataFileWidgetController(CreateDataFileWidget owner, AsyncWidgetModel model) {
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
		entryDeferred.addErrback(new CreateDataFileEntryErrback());
		
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
	
	
	
	class CreateDataFileEntryErrback implements Function<Throwable,Throwable> {

		private Log log = LogFactory.getLog(this.getClass());

		public Throwable apply(Throwable error) {
			log.enter("apply");
			
			log.error("error saving data file", error);

			if (error instanceof HttpException) {
				HttpException e = (HttpException) error;
				log.debug(e.getLocalizedMessage());
				log.debug("response code: "+e.getResponse().getStatusCode());
				log.debug(e.getResponse().getText());
			}
			
			model.setStatus(AsyncWidgetModel.STATUS_ERROR);
						
			owner.fireEvent(new ErrorEvent(error));
			
			log.leave();
			return error;
		}

		
	}
	

	

	

}
