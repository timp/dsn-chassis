/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;


import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
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
		
		this.setOwner(owner);
	}
	
	
	@UiTemplate("AnonymizerHomeWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, AnonymizerHomeWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField HTMLPanel errorPanel;
		@UiField Label errorLabel; // in errorPanel
	@UiField FilesToReviewWidget filesToReviewWidget;
	@UiField FilesToCleanWidget filesToCleanWidget;
	
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
		filesToReviewWidget.setVisible(false);
		filesToCleanWidget.setVisible(false);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			filesToReviewWidget.setVisible(true);
			filesToCleanWidget.setVisible(true);
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


	public void setOwner(AnonymizerHomeWidget owner) {
		this.owner = owner;
	}


	public AnonymizerHomeWidget getOwner() {
		return owner;
	}
	
}
