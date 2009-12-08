/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ErrorStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.InitialStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * @author raok
 *
 */
public abstract class AsyncWidgetRenderer
	<M extends AsyncWidgetModel>
	extends ChassisWidgetRenderer<M> {
	
	
	
	
	private Log log = LogFactory.getLog(AsyncWidgetRenderer.class);
	protected FlowPanel asyncRequestPendingPanel, mainPanel;


	
	
	
	/**
	 * @param owner
	 */
	public AsyncWidgetRenderer() {}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.canvas.clear();
		
		this.asyncRequestPendingPanel = new FlowPanel();
		this.asyncRequestPendingPanel.add(new InlineLabel("pending...")); // TODO i18n
		this.canvas.add(this.asyncRequestPendingPanel);
		
		this.mainPanel = new FlowPanel();
		this.canvas.add(this.mainPanel);
		
		this.renderMainPanel();

		log.leave();
	}







	/**
	 * 
	 */
	protected abstract void renderMainPanel();





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForModelChanges()
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		HandlerRegistration a = this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				log.enter("onStatusChanged");
				
				syncPanelVisibility(e.getAfter());
				
				log.leave();
			}
			
		});
		
		this.modelChangeHandlerRegistrations.add(a);
		
		log.leave();
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		if (this.model != null) {

			log.debug("sync panel visibility");
			this.syncPanelVisibility(this.model.getStatus());
			
		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?
			log.warn("model is null, not updating anything");

		}
		
		log.leave();
		
	}

	
	
	

	/**
	 * @param after
	 */
	protected void syncPanelVisibility(Status status) {
		log.enter("syncPanelVisibility");
		
		if (status == null || status instanceof InitialStatus) {
			
			this.asyncRequestPendingPanel.setVisible(false);
			this.mainPanel.setVisible(true);
			
		}
		else if (status instanceof AsyncRequestPendingStatus) {
			
			this.mainPanel.setVisible(false);
			this.asyncRequestPendingPanel.setVisible(true);
			
		}
		else if (status instanceof ReadyStatus) {
			
			this.asyncRequestPendingPanel.setVisible(false);
			this.mainPanel.setVisible(true);

		}
		else if (status instanceof ErrorStatus) {
			
			log.error("TODO handle error status");
			
		}
		else {

			log.error("unexpected status: "+status.getClass().getName());
			
		}
		
		log.leave();
	}






}
