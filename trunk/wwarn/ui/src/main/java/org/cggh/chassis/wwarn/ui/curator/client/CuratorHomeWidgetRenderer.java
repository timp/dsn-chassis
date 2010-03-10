/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.HashSet;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;


/**
 * @author timp
 *
 */
public class CuratorHomeWidgetRenderer extends ChassisWidgetRenderer<CuratorHomeWidgetModel> {

	private static final Log log = LogFactory.getLog(CuratorHomeWidgetRenderer.class);
	
	private CuratorHomeWidget owner;
	
	public CuratorHomeWidgetRenderer(CuratorHomeWidget owner) {
		
		this.owner = owner;
	}
	
	
	@UiTemplate("CuratorHomeWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, CuratorHomeWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel mainPanel;

	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
	}

	
	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
		syncUIWithSubmissions(model.submissionFeed.get());
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");

		// Hide everything at this UI level (top level) first, then show as required.
		
		errorPanel.setVisible(false);	
		mainPanel.setVisible(false);		
		pendingPanel.setVisible(false);	
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			// Everything off, hidden.
		}
		
		else if (status instanceof CuratorHomeWidgetModel.RetrieveSubmissionsPendingStatus) {

			pendingPanel.setVisible(true);

		}

		else if (status instanceof CuratorHomeWidgetModel.SubmissionsNotFoundStatus) {

			// Everything off, hidden.
			
		}			
		
		else if (status instanceof CuratorHomeWidgetModel.SubmissionsRetrievedStatus) {

			mainPanel.setVisible(true);
			
		}		
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			
			error("Error status given on asynchronous call. Maybe a bad submissions query URL.");
			errorPanel.setVisible(true);
		}			
		
		else {

			error("Unhandled status:" + status);
			errorPanel.setVisible(true);
		}
		
		log.leave();
	}


	public void error(String err) {
		errorMessage.clear();
		errorMessage.add(new HTML(err));
		
	}
	private void syncUIWithSubmissions(Document submissions) {
		
		log.enter("syncUIWithSubmissions");

		// Hide everything in this UI scope (the main panel) first, then show as required.
		
		// In the main panel.
		
		// Always show the submit data link
		//listStudiesWidgetUiField.setVisible(true);
		

		log.leave();
		
	}


	
	
	@Override
	public void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});
		
		model.submissionFeed.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUIWithSubmissions(e.getAfter());
			}
		});
	}
	

	
	
	
}
