/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import org.cggh.chassis.generic.widget.client.CancelEvent;
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

/**
 * @author aliman
 *
 */
public class EditDatasetWidgetRenderer 
	extends AsyncWidgetRenderer<AtomCrudWidgetModel<DatasetEntry>>

{

	
	
	
	private Log log = LogFactory.getLog(EditDatasetWidgetRenderer.class);

	
	
	
	private EditDatasetWidget owner;
	private DatasetForm form;
	private FlowPanel buttonsPanel;
	private Button saveButton;
	private Button cancelButton;




	private EditDatasetWidgetController controller;





	/**
	 * @param owner
	 */
	public EditDatasetWidgetRenderer(EditDatasetWidget owner) {
		this.owner = owner;
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {

		this.mainPanel.add(h2Widget("Edit Dataset")); // TODO i18n
		this.mainPanel.add(pWidget("Use the form below to make changes to the dataset.")); // TODO i18n
		
		this.form = new DatasetForm();
		this.mainPanel.add(this.form);
		
		this.renderButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);

	}
	
	
	
	
	
	
	/**
	 * @return
	 */
	private void renderButtonsPanel() {
		log.enter("renderButtonsPanel");
		
		this.buttonsPanel = new FlowPanel();
		
		this.saveButton = new Button("Save Changes"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.saveButton);
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
		
		HandlerRegistration b = this.model.addEntryChangeHandler(new AtomEntryChangeHandler<DatasetEntry>() {
			
			public void onEntryChanged(AtomEntryChangeEvent<DatasetEntry> e) {
				log.enter("onChange");
				
				updateForm(e.getAfter());
				
				log.leave();
			}
		});
		
		this.modelChangeHandlerRegistrations.add(a);
		this.modelChangeHandlerRegistrations.add(b);
		
		log.leave();
	}





	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		HandlerRegistration a = this.saveButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				controller.updateEntry(form.getModel());
				
			}
		});
		
		HandlerRegistration b = this.cancelButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				owner.fireEvent(new CancelEvent());
				
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);

		log.leave();	
		
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
			this.updateForm(this.model.getEntry());
			
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
			
//			this.form.reset(); 

		}

		log.leave();
	}





	/**
	 * @param entry
	 */
	protected void updateForm(DatasetEntry entry) {
		log.enter("updateForm");
		
		this.form.setModel(entry);
		
		log.leave();
	}

	
	
	

	/**
	 * @param controller
	 */
	public void setController(EditDatasetWidgetController controller) {
		this.controller = controller;
	}



	
	
}
