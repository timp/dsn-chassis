/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFactory;
import org.cggh.chassis.generic.atomui.client.CreateSuccessEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;



/**
 * @author aliman
 *
 */
public class NewDataFileWidgetRenderer 
	extends AsyncWidgetRenderer<AsyncWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(NewDataFileWidgetRenderer.class);
	private FlowPanel buttonsPanel;
	private Button cancelButton, createButton;
	private NewDataFileForm form;
	private NewDataFileWidget owner;
	
	
	
	
	
	/**
	 * @param owner
	 */
	public NewDataFileWidgetRenderer(NewDataFileWidget owner) {
		this.owner = owner;
	}





	/**
	 * 
	 */
	@Override
	public void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.mainPanel.add(h2Widget("New Data File")); // TODO i18n
		this.mainPanel.add(pWidget("Use the form below to create a new data file.")); // TODO i18n
		
		this.form = new NewDataFileForm();
		this.mainPanel.add(this.form);
		
		this.renderButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);
		
		log.leave();
	}





	/**
	 * @return
	 */
	private void renderButtonsPanel() {
		log.enter("renderButtonsPanel");
		
		this.buttonsPanel = new FlowPanel();
		
		this.createButton = new Button("Create Data File"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.createButton);
		this.buttonsPanel.add(this.cancelButton);
		
		log.leave();
	}




	/**
	 * 
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





	/**
	 * 
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		HandlerRegistration a = this.createButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
				form.submit();
				
			}
		});
		
		HandlerRegistration b = this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				owner.fireEvent(new CancelEvent());
				
			}
		});
		
		// add submit complete handler
		HandlerRegistration c = this.form.addSubmitCompleteHandler(new FormSubmitCompleteHandler());
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		this.childWidgetEventHandlerRegistrations.add(c);
		
		log.leave();
	}

	
	
	
	private class FormSubmitCompleteHandler implements SubmitCompleteHandler {

		private Log log = LogFactory.getLog(FormSubmitCompleteHandler.class);

		public void onSubmitComplete(SubmitCompleteEvent event) {
			log.enter("onSubmitComplete");
			
			log.debug("submit results: "+event.getResults());
			
			try {
				
				DataFileFactory factory = new DataFileFactory();
				String results = event.getResults();
				if (results.startsWith("<!--") && results.endsWith("-->")) {
					model.setStatus(AsyncWidgetModel.STATUS_READY);
					String content = results.substring(4, results.length()-3);
					log.debug("attempting to parse: "+content);
					DataFileEntry entry = factory.createEntry(content);
					CreateSuccessEvent<DataFileEntry> successEvent = new CreateSuccessEvent<DataFileEntry>();
					successEvent.setEntry(entry);
					owner.fireEvent(successEvent);
				}
				else {
					model.setStatus(AsyncWidgetModel.STATUS_ERROR);
					owner.fireEvent(new ErrorEvent("could not parse results: "+results));
				}
				
			} catch (Throwable t) {
				
				log.error("caught trying to parse submit results: "+t.getLocalizedMessage(), t);
				model.setStatus(AsyncWidgetModel.STATUS_ERROR);
				owner.fireEvent(new ErrorEvent(t));
				
			}
			
			log.leave();
			
		}

	}




	/**
	 * 
	 */
	@Override
	public void syncUI() {
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
		
		log.debug("status: "+status);
		
		if (status instanceof ReadyStatus) {
			
//			this.form.reset(); 
			this.owner.reset();

		}

		log.leave();
	}





}
