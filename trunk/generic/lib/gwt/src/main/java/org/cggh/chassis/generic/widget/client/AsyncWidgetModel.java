/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class AsyncWidgetModel extends ChassisWidgetModel {

	
	
	
	private Log log;
	
	
	
	
	private Status status;


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetModel#init()
	 */
	@Override
	protected void init() {
		ensureLog();
		log.enter("init");

		this.status = STATUS_INITIAL;

		log.leave();

	}
	
	
	
	
	
	/**
	 * 
	 */
	private void ensureLog() {
		log = LogFactory.getLog(AsyncWidgetModel.class); // instantiate here because called from superclass constructor
	}





	public void setStatus(Status status) {
		StatusChangeEvent e = new StatusChangeEvent(this.status, status);
		this.status = status;
		this.fireChangeEvent(e);
	}





	public Status getStatus() {
		return status;
	}





	public static class Status {}
	
	public static class InitialStatus extends Status {}
	
	public static final InitialStatus STATUS_INITIAL = new InitialStatus();

	public static class AsyncRequestPendingStatus extends Status {}
	
	public static final AsyncRequestPendingStatus STATUS_ASYNC_REQUEST_PENDING = new AsyncRequestPendingStatus();		
	
	public static class ReadyStatus extends Status {}
	
	public static final ReadyStatus STATUS_READY = new ReadyStatus();

  public static class ErrorStatus extends Status {}
  
  public static final ErrorStatus STATUS_ERROR = new ErrorStatus();

  public static class NotFoundStatus extends Status {}
  
  public static final NotFoundStatus STATUS_NOT_FOUND = new NotFoundStatus();

		
	
	
	
	public HandlerRegistration addStatusChangeHandler(StatusChangeHandler h) {
		return this.addChangeHandler(h, StatusChangeEvent.TYPE);
	}
	
	
	
	
	
	public interface StatusChangeHandler extends ModelChangeHandler {
		
		public void onStatusChanged(StatusChangeEvent e);

	}
	
	
	

	
	public static class StatusChangeEvent extends ModelChangeEvent<Status, StatusChangeHandler> {

		public static final Type<StatusChangeHandler> TYPE = new Type<StatusChangeHandler>();
			
		public StatusChangeEvent(Status before, Status after) { super(before, after); }

		@Override
		protected void dispatch(StatusChangeHandler handler) { handler.onStatusChanged(this); }

		@Override
		public Type<StatusChangeHandler> getAssociatedType() { return TYPE; }
		
	}
	
	
	
	

	
	
}
