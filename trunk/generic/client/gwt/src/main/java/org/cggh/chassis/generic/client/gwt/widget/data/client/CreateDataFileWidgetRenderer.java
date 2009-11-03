/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CancelEvent;
import org.cggh.chassis.generic.client.gwt.forms.client.DataFileForm;
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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;

/**
 * @author aliman
 *
 */
public class CreateDataFileWidgetRenderer extends ChassisWidgetRenderer {
	
	
	
	
	private Log log = LogFactory.getLog(CreateDataFileWidgetRenderer.class);
	private CreateDataFileWidgetController controller;
	private FlowPanel buttonsPanel, savingPanel, mainPanel;
	private Button cancelButton, createButton;
	private DataFileForm form;
	private AsyncWidgetModel model;
	private CreateDataFileWidget owner;

	
	
	
	
	
	
	/**
	 * @param owner
	 */
	public CreateDataFileWidgetRenderer(CreateDataFileWidget owner) {
		this.owner = owner;
	}





	/**
	 * 
	 */
	public void renderUI() {
		log.enter("renderUI");
		
		this.canvas.clear();

		this.savingPanel = new FlowPanel();
		this.savingPanel.add(new InlineLabel("saving...")); // TODO i18n
		this.canvas.add(this.savingPanel);

		this.mainPanel = new FlowPanel();
		
		this.mainPanel.add(new HTML("<h2>New Data File</h2>")); // TODO i18n
		this.mainPanel.add(new HTML("<p>Use the form below to create a new data file.</p>")); // TODO i18n
		
		this.form = new DataFileForm();
		this.mainPanel.add(this.form);
		
		this.renderButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);
		
		this.canvas.add(this.mainPanel);

		log.leave();
		
	}





	/**
	 * @return
	 */
	private FlowPanel renderButtonsPanel() {
		log.enter("renderButtonsPanel");
		
		this.buttonsPanel = new FlowPanel();
		
		this.createButton = new Button("Create Data File"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.createButton);
		this.buttonsPanel.add(this.cancelButton);
		
		log.leave();
		return null;
	}





	/**
	 * @param model
	 */
	public void bindUI(AsyncWidgetModel model) {
		log.enter("bindUI");
		
		log.debug("unbind to clear anything");
		this.unbindUI();
		
		log.debug("keep reference to model");
		this.model = model;
		
		log.debug("register this as handler for model property change events");
		this.registerHandlersForModelChanges();
		
		log.debug("register handlers for child widget events");
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
		
	}





	/**
	 * 
	 */
	private void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		HandlerRegistration a = this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				log.enter("onStatusChanged");
				
				updatePanelVisibility(e.getAfter());
				
				log.leave();
			}
			
		});
		
		this.modelChangeHandlerRegistrations.add(a);
		
		log.leave();
		
	}





	/**
	 * 
	 */
	private void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		this.createButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				log.enter("onClick");
				
				controller.createDataFileEntry(form.getModel());
				
				log.leave();
				
			}
		});
		
		this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				log.enter("onClick");
				
				owner.fireEvent(new CancelEvent());
				
				log.leave();
				
			}
		});
		
		log.leave();
		
	}





	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		if (this.model != null) {

			log.debug("sync panel visibility");
			this.updatePanelVisibility(this.model.getStatus());
			
		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?
			log.warn("model is null, not updating anything");

		}
		
		log.leave();
	}





	/**
	 * 
	 */
	public void unbindUI() {
		log.enter("unbindUI");
		
		this.clearModelChangeHandlers();
		this.clearChildWidgetEventHandlers();
		
		log.leave();
		
	}





	/**
	 * @param controller
	 */
	public void setController(CreateDataFileWidgetController controller) {
		this.controller = controller;
	}

	
	
	
	
	/**
	 * @param after
	 */
	protected void updatePanelVisibility(Status status) {
		log.enter("updatePanelVisibility");
		
		if (status == null || status instanceof InitialStatus) {
			
			this.savingPanel.setVisible(false);
			this.mainPanel.setVisible(false);
			
		}
		else if (status instanceof AsyncRequestPendingStatus) {
			
			this.mainPanel.setVisible(false);
			this.savingPanel.setVisible(true);
			
		}
		else if (status instanceof ReadyStatus) {
			
			this.savingPanel.setVisible(false);
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
