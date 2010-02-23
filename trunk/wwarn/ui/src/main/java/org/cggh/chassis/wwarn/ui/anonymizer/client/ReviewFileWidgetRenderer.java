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
public class ReviewFileWidgetRenderer extends ChassisWidgetRenderer<ReviewFileWidgetModel> {

	private static final Log log = LogFactory.getLog(ReviewFileWidgetRenderer.class);
	
	//private ReviewFileWidget owner;
	
	public ReviewFileWidgetRenderer(ReviewFileWidget owner) {
		
		//this.owner = owner;
	}
	
	
	@UiTemplate("ReviewFileWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, ReviewFileWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField HTMLPanel errorPanel;
		@UiField Label errorLabel; // in errorPanel
	@UiField HTMLPanel pendingPanel;

		
	
	
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
		pendingPanel.setVisible(false);
		
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			// Everything off, hidden.
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


	
}
