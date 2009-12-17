/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
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
public class NewStudyWidgetRenderer extends
		AsyncWidgetRenderer<AtomCrudWidgetModel<StudyEntry>> {

	
	
	
	private NewStudyWidget owner;
	private NewStudyWidgetController controller;

	
	
	// UI fields
	private StudyForm form;
	private FlowPanel buttonsPanel;
	private Button createButton;
	private Button cancelButton;




	/**
	 * @param owner
	 */
	public NewStudyWidgetRenderer(NewStudyWidget owner) {
		this.owner = owner;
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {

		this.mainPanel.add(h2Widget("New Study")); // TODO i18n

		this.mainPanel.add(pWidget("Use the form below to create a new study.")); // TODO i18n
		
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
		
		this.createButton = new Button("Create Study"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		
		this.buttonsPanel.add(this.createButton);
		this.buttonsPanel.add(this.cancelButton);
		
	}




	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForModelChanges()
	 */
	@Override
	protected void registerHandlersForModelChanges() {

		super.registerHandlersForModelChanges();

		HandlerRegistration a = this.model.addStatusChangeHandler(new StatusChangeHandler() {
			
			public void onStatusChanged(StatusChangeEvent e) {
				
				syncForm(e.getAfter());
				
			}
			
		});
		
		this.modelChangeHandlerRegistrations.add(a);

	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		HandlerRegistration a = this.createButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				controller.createEntry(form.getModel());
				
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




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#syncUI()
	 */
	@Override
	protected void syncUI() {

		super.syncUI();
		
		if (this.model != null) {

			this.syncForm(this.model.getStatus());
			
		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?

		}

	}

	
	
	
	/**
	 * @param after
	 */
	protected void syncForm(Status status) {
		
		if (status instanceof ReadyStatus) {
			
			this.form.reset(); 

		}

	}







	/**
	 * @param controller
	 */
	public void setController(NewStudyWidgetController controller) {
		this.controller = controller;
	}

	
	
	
}
