/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;


import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;



/**
 * @author lee
 *
 */
public class AnonymizerHomeWidgetRenderer extends ChassisWidgetRenderer<AnonymizerHomeWidgetModel> {

	private static final Log log = LogFactory.getLog(AnonymizerHomeWidgetRenderer.class);
	
	private AnonymizerHomeWidget owner;
	
	public AnonymizerHomeWidgetRenderer(AnonymizerHomeWidget owner) {
		
		this.owner = owner;
	}
	
	
	@UiTemplate("AnonymizerHomeWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, AnonymizerHomeWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField HTMLPanel errorPanel;
	@UiField Label errorLabel; // in errorPanel
	@UiField FilesToReviewWidget filesToReviewWidgetUiField;
	@UiField FilesToCleanWidget filesToCleanWidgetUiField;
	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
	}

	
	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");
		
		log.debug("status: " + status);

		// Hide everything at this UI level (top level) first, then show as required.
		errorPanel.setVisible(false);
		filesToReviewWidgetUiField.setVisible(false);
		filesToCleanWidgetUiField.setVisible(false);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			filesToReviewWidgetUiField.setVisible(true);
			filesToCleanWidgetUiField.setVisible(true);
			
		}
		
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			errorLabel.setText("AsyncWidgetModel.ErrorStatus");
			errorPanel.setVisible(true);
		}			
		
		else {

			errorLabel.setText("Unhandled AsyncWidgetModel status");
			errorPanel.setVisible(true);			
			
		}
		
		log.leave();
	}


	
	@Override
	public void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});

	}

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();	
		
		HandlerRegistration a = filesToReviewWidgetUiField.reviewFileNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				// just bubble
				owner.reviewFileNavigationEventChannel.fireEvent(e);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);		
		
		HandlerRegistration b = filesToCleanWidgetUiField.cleanFileNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				// just bubble
				owner.cleanFileNavigationEventChannel.fireEvent(e);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(b);				

	}


	

	
}
