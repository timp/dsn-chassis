/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFactory;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
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
public class UploadDataFileRevisionWidgetRenderer 
	extends	AsyncWidgetRenderer<UploadDataFileRevisionWidgetModel> {
	
	
	
	
	private Log log = LogFactory.getLog(UploadDataFileRevisionWidgetRenderer.class);
	private UploadDataFileRevisionWidget owner;
	private UploadDataFileRevisionForm form;
	private FlowPanel buttonsPanel;
	private Button uploadButton;
	private Button cancelButton;

	
	
	
	
	/**
	 * @param reviseDataFileWidget
	 */
	public UploadDataFileRevisionWidgetRenderer(UploadDataFileRevisionWidget owner) {
		this.owner = owner;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.mainPanel.add(h2Widget("Upload Data File Revision")); // TODO i18n
		this.mainPanel.add(pWidget("Use the form below upload a new revision of a data file.")); // TODO i18n

		this.form = new UploadDataFileRevisionForm();
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
		
		this.uploadButton = new Button("Upload Revision"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.uploadButton);
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
		
		HandlerRegistration b = this.model.addDataFileEntryChangeHandler(new DataFileEntryChangeHandler() {
			
			public void onChange(DataFileEntryChangeEvent e) {
				log.enter("onDataFileEntryChanged");
				
				updateForm(e.getAfter());
				
				log.leave();
				
			}

		});
		
		this.modelChangeHandlerRegistrations.add(a);
		this.modelChangeHandlerRegistrations.add(b);
		
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



	private void updateForm(DataFileEntry after) {
		log.enter("updateHiddenDataFile");
		
		this.form.setDataFileEntry(after);
		
		log.leave();
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		HandlerRegistration a = this.uploadButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
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




	
	// TODO possible refactor with NewDataFileWidgetRenderer
	private class FormSubmitCompleteHandler implements SubmitCompleteHandler {

		private Log log = LogFactory.getLog(FormSubmitCompleteHandler.class);

		public void onSubmitComplete(SubmitCompleteEvent event) {
			log.enter("onSubmitComplete");
			
			log.debug("submit results: "+event.getResults());
			
			try {
				
				DataFileFactory factory = new DataFileFactory();
				String results = event.getResults();
				if (results.startsWith("<!--") && results.endsWith("-->")) {
					String content = results.substring(4, results.length()-3);
					log.debug("attempting to parse: "+content);
					DataFileEntry entry = factory.createEntry(content);
					UploadDataFileRevisionSuccessEvent successEvent = new UploadDataFileRevisionSuccessEvent();
					successEvent.setEntry(entry);
					owner.fireEvent(successEvent);
				}
				else {
					owner.fireEvent(new ErrorEvent("could not parse results: "+results));
				}
				
			} catch (Throwable t) {
				
				log.error("caught trying to parse submit results: "+t.getLocalizedMessage(), t);
				owner.fireEvent(new ErrorEvent(t));
				
			}
			
			log.leave();
			
		}

	}

	
	
}
