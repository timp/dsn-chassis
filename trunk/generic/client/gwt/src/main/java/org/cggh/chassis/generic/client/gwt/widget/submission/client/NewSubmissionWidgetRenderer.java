/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.forms.client.SubmissionForm;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
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

/**
 * @author raok
 *
 */
public class NewSubmissionWidgetRenderer 
	extends AsyncWidgetRenderer<AsyncWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(NewSubmissionWidgetRenderer.class);
	private NewSubmissionWidget owner;
	private NewSubmissionWidgetController controller;
	private FlowPanel buttonsPanel;
	private Button cancelButton, createButton;
	private SubmissionForm form;


	
	
	
	/**
	 * @param owner
	 */
	public NewSubmissionWidgetRenderer(NewSubmissionWidget owner) {
		this.owner = owner;
	}





	/**
	 * @param controller
	 */
	public void setController(NewSubmissionWidgetController controller) {
		this.controller = controller;
	}






	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.mainPanel.add(new HTML("<h2>New Data Submission</h2>")); // TODO i18n
		this.mainPanel.add(new HTML("<p>Use the form below to create a new data submission.</p>")); // TODO i18n
		
		this.form = new SubmissionForm();
		this.mainPanel.add(this.form);
		
		this.renderButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);		

		log.leave();
	}






	/**
	 * 
	 */
	private void renderButtonsPanel() {
		log.enter("renderButtonsPanel");
		
		this.buttonsPanel = new FlowPanel();
		
		this.createButton = new Button("Create Submission"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.createButton);
		this.buttonsPanel.add(this.cancelButton);
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		this.createButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {

				controller.createSubmissionEntry(form.getModel());

			}
		});
		
		this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				owner.fireEvent(new CancelEvent());

			}
		});
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForModelChanges()
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		super.registerHandlersForModelChanges();
		
		HandlerRegistration a = this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				log.enter("onStatusChanged");
				
				updateForm(e.getAfter());
				
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
		
		super.syncUI();
		
		if (this.model != null) {

			this.updateForm(this.model.getStatus());
			
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
	protected void updateForm(Status status) {
		log.enter("updateForm");
		
		if (status instanceof ReadyStatus) {
			
			this.form.reset();  

		}
		
		log.leave();
	}





	/**
	 * @return
	 */
	public SubmissionForm getForm() {
		return this.form;
	}






	
}
