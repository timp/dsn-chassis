/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeEvent;
import org.cggh.chassis.generic.atomui.client.AtomEntryChangeHandler;
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
public class EditStudyWidgetRenderer 
	extends AsyncWidgetRenderer<AtomCrudWidgetModel<StudyEntry>> {

	
	
	
	private EditStudyWidget owner;
	private EditStudyWidgetController controller;

	
	
	
	// UI fields
	StudyForm form;
	FlowPanel buttonsPanel;
	Button saveButton, cancelButton;
	
	
	
	
	/**
	 * @param owner
	 */
	public EditStudyWidgetRenderer(EditStudyWidget owner) {
		this.owner = owner;
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {

		this.mainPanel.add(h2Widget("Edit Study")); // TODO i18n
		this.mainPanel.add(pWidget("Use the form below to make changes to the study.")); // TODO i18n
		
		this.form = new StudyForm();
		this.mainPanel.add(this.form);
		
		this.renderButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);
		
	}

	
	

	/**
	 * @return
	 */
	private void renderButtonsPanel() {
		
		this.buttonsPanel = new FlowPanel();
		
		this.saveButton = new Button("Save Changes"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.saveButton);
		this.buttonsPanel.add(this.cancelButton);
		
	}

	
	
	
	/**
	 * 
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		
		super.registerHandlersForModelChanges();
		
		HandlerRegistration a = this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				
				updateForm(e.getAfter());
				
			}
			
		});
		
		HandlerRegistration b = this.model.addEntryChangeHandler(new AtomEntryChangeHandler<StudyEntry>() {
			
			public void onEntryChanged(AtomEntryChangeEvent<StudyEntry> e) {
				
				updateForm(e.getAfter());
				
			}
		});
		
		this.modelChangeHandlerRegistrations.add(a);
		this.modelChangeHandlerRegistrations.add(b);
		
	}


	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {

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

	}



	
	/**
	 * 
	 */
	@Override
	public void syncUI() {
		
		super.syncUI();
		
		if (this.model != null) {

			this.updateForm(this.model.getStatus());
			this.updateForm(this.model.getEntry());
			
		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?

		}
		
	}

	
	
	
	
	/**
	 * @param after
	 */
	protected void updateForm(Status status) {
		
		if (status instanceof ReadyStatus) {
			
//			this.form.reset(); 

		}

	}





	/**
	 * @param entry
	 */
	protected void updateForm(StudyEntry entry) {
		
		this.form.setModel(entry);
		
	}

	
	
	

	
	/**
	 * @param controller
	 */
	public void setController(EditStudyWidgetController controller) {
		this.controller = controller;
	}

	
	
	
}
