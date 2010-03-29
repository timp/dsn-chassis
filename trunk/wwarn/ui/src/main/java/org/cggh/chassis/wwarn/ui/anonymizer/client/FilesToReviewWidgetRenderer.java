/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;


import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;



/**
 * @author lee
 *
 */
public class FilesToReviewWidgetRenderer extends ChassisWidgetRenderer<FilesToReviewWidgetModel> {

	private static final Log log = LogFactory.getLog(FilesToReviewWidgetRenderer.class);
	
	private FilesToReviewWidget owner;
	
	private FilesToReviewWidgetController controller;
	
	public FilesToReviewWidgetRenderer(FilesToReviewWidget owner) {
		
		this.setOwner(owner);
	}

	
	
	@UiTemplate("FilesToReviewWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, FilesToReviewWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField HTMLPanel errorPanel;
		@UiField Label errorLabel; // in errorPanel
	@UiField HTMLPanel pendingPanel;
	@UiField Label noFilesToReviewLabel;
	@UiField HTMLPanel filesToReviewPanel;	
		@UiField FlowPanel filesToReviewTableContainer; // in filesToReviewPanel

		
	
	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
	}

	
	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
		syncUIWithFilesToReviewFeedDoc(model.filesToReviewFeedDoc.get());
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");

		log.debug("status: " + status);
		
		// Hide everything at this UI level (top level) first, then show as required.
		errorPanel.setVisible(false);
		pendingPanel.setVisible(false);
		filesToReviewPanel.setVisible(false);
		
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			// Everything off, hidden.
		}
		
		else if (status instanceof FilesToReviewWidgetModel.RetrieveFilesToReviewPendingStatus) {
		
			pendingPanel.setVisible(true);
			
		}
		
		else if (status instanceof FilesToReviewWidgetModel.FilesToReviewRetrievedStatus) {
			
			filesToReviewPanel.setVisible(true);
			
		}

		else if (status instanceof FilesToReviewWidgetModel.FilesToReviewNotFoundStatus) {

			errorLabel.setText("FilesToReviewWidgetModel.FilesToReviewNotFoundStatus");
			errorPanel.setVisible(true);
		}		
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			errorLabel.setText("AsyncWidgetModel.ErrorStatus: " + model.getErrorMessage());
			errorPanel.setVisible(true);
		}			
		
		else {

			errorLabel.setText("Unhandled AsyncWidgetModel status");
			errorPanel.setVisible(true);			
			
		}
		
		log.leave();
	}
	
	private void syncUIWithFilesToReviewFeedDoc(Document filesToReviewFeedDoc) {

		filesToReviewTableContainer.clear();
		
		// Hide everything (that is made visible here) first, then show as required.
		noFilesToReviewLabel.setVisible(false);
		filesToReviewTableContainer.setVisible(false);
		
		if (filesToReviewFeedDoc != null) {
			
			List<Element> entries = AtomHelper.getEntries(filesToReviewFeedDoc.getDocumentElement());
			
			if (entries.size() == 0) {
				noFilesToReviewLabel.setVisible(true);
			}
			
			else {

				FlexTable filesToReviewTable = renderFilesToReviewTable(entries);
				filesToReviewTableContainer.add(filesToReviewTable);				
				
				filesToReviewTableContainer.setVisible(true);

			}

		}
			
		
	}	

	
	private FlexTable renderFilesToReviewTable(List<Element> entries) {

		log.enter("renderFilesToReviewTable");
		
		List<List<Widget>> rows = new ArrayList<List<Widget>>();

		List<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(new Label("File Name")); // i18n
		headerRow.add(new Label("Date Submitted"));  // i18n
		headerRow.add(new Label("Actions")); // i18n
		
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String fileName = AtomHelper.getTitle(entry);
			
			String submittedDate = ChassisHelper.getSubmissionPublishedAsDate(entry);

			final Element fileToBeReviewedEntryElement = entry;
			
			Anchor reviewLink = new Anchor("review"); // TODO i18n
			
			reviewLink.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					controller.selectFileToBeReviewedEntryElement(fileToBeReviewedEntryElement);
					
					log.debug("Firing reviewFile event...");
					
					ReviewFileNavigationEvent e2 = new ReviewFileNavigationEvent();
					
					e2.setFileToBeReviewedEntryElement(fileToBeReviewedEntryElement);
					
					owner.reviewFileNavigationEventChannel.fireEvent(e2);
					
					log.leave();
					
				}

			});
			
			List<Widget> row = new ArrayList<Widget>();
			row.add(new Label(fileName));
			row.add(new Label(submittedDate));	
			row.add(reviewLink);	
			
			rows.add(row);
			
		}
		
		log.leave();
		
		return RenderUtils.renderResultsTable(rows);
	}


	@Override
	public void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});

		model.filesToReviewFeedDoc.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUIWithFilesToReviewFeedDoc(e.getAfter());
			}

		});		
		
	}

	public void setOwner(FilesToReviewWidget owner) {
		this.owner = owner;
	}

	public FilesToReviewWidget getOwner() {
		return owner;
	}


	public void setController(FilesToReviewWidgetController controller) {
		this.controller = controller;
		
	}


	
}
