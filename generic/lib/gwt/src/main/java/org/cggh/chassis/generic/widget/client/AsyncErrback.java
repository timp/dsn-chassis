/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Function;
import org.cggh.chassis.generic.twisted.client.HttpException;

/**
 * @author aliman
 *
 */
public class AsyncErrback implements Function<Throwable, Throwable> {
	
	
	
	
	private Log log = LogFactory.getLog(AsyncErrback.class);
	private ChassisWidget eventSource;
	private AsyncWidgetModel model;
	
	
	
	
	
	public AsyncErrback(ChassisWidget eventSource, AsyncWidgetModel model) {
		this.eventSource = eventSource;
		this.model = model;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.twisted.client.Function#apply(java.lang.Object)
	 */
	public Throwable apply(Throwable error) {
		log.enter("apply");
		
		log.error("unexpected error", error);

		if (error instanceof HttpException) {
			HttpException e = (HttpException) error;
			log.debug(e.getLocalizedMessage());
			log.debug("response code: "+e.getResponse().getStatusCode());
			log.debug(e.getResponse().getText());
		}
		
		if (this.model != null)	this.model.setStatus(AsyncWidgetModel.STATUS_ERROR);
					
		if (this.eventSource != null) this.eventSource.fireEvent(new ErrorEvent(error));
		
		log.leave();
		return error;
	}
	
	
	

}
