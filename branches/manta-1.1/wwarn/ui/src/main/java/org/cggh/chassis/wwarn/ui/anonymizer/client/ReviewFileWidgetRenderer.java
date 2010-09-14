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
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;



/**
 * @author lee
 *
 */
public class ReviewFileWidgetRenderer extends ChassisWidgetRenderer<ReviewFileWidgetModel> {

	private static final Log log = LogFactory.getLog(ReviewFileWidgetRenderer.class);
	

	private ReviewFileWidgetController controller;
	
	public ReviewFileWidgetRenderer(ReviewFileWidget owner) {
	}
	
	
	@UiTemplate("ReviewFileWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, ReviewFileWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField HTMLPanel errorPanel;
		@UiField Label errorLabel; // in errorPanel
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel reviewFilePanel;
		
	@UiField Anchor backHomeLink;
	@UiField FlowPanel fileToBeReviewedTableContainer;
	@UiField RadioButton fileCanProceedToCurationButton;
	@UiField RadioButton fileShouldBeClearedBeforeCurationButton;
	@UiField TextArea reviewNotesInput;
	@UiField Button reviewFileButton;
	@UiField Button cancelReviewFileButton;
	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
	}

	
	@Override
	protected void syncUI() {
		
		log.enter("syncUI");
		
		syncUIWithStatus(model.getStatus());
		
		log.leave();
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");

		log.debug("status: " + status);
		
		
		// Hide and disabled everything at this UI level (top level) first, then show as required.
		errorPanel.setVisible(false);
		errorLabel.setVisible(false); //in errorPanel
		pendingPanel.setVisible(false);
		reviewFilePanel.setVisible(false);
			fileToBeReviewedTableContainer.setVisible(false); // in reviewFilePanel
			reviewNotesInput.setEnabled(false); // in reviewFilePanel
			fileCanProceedToCurationButton.setEnabled(false); // in reviewFilePanel
			fileShouldBeClearedBeforeCurationButton.setEnabled(false); // in reviewFilePanel
			reviewFileButton.setEnabled(false); // in reviewFilePanel
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			syncUIWithFileToBeReviewedEntryElement(model.fileToBeReviewedEntryElement.get());

		}

		else if (status instanceof AsyncWidgetModel.ReadyStatus || status instanceof ReviewFileWidgetModel.FileToBeReviewedEntryElementRetrievedStatus) {
			
			fileToBeReviewedTableContainer.setVisible(true);
			
			reviewNotesInput.setText(null);
			fileCanProceedToCurationButton.setValue(false);
			fileShouldBeClearedBeforeCurationButton.setValue(false);
			
			reviewNotesInput.setEnabled(true);
			fileCanProceedToCurationButton.setEnabled(true);
			fileShouldBeClearedBeforeCurationButton.setEnabled(true);
			
			reviewFilePanel.setVisible(true);
			
		}
		
		else if (status instanceof ReviewFileWidgetModel.RetrieveFileToBeReviewedEntryElementPendingStatus) {

			pendingPanel.setVisible(true);
		}
		
		else if (status instanceof ReviewFileWidgetModel.FileToBeReviewedEntryElementNotFoundStatus) {
			
			log.error("FileToBeReviewedEntryElementNotFoundStatus");
			errorLabel.setText("Could not find the entry corresponding to the media id in memory.");	
			errorLabel.setVisible(true);	
		}
		
		else {
			log.error("Unhandled AsyncWidgetModel status");
			errorLabel.setText("Unhandled AsyncWidgetModel status");	
			errorLabel.setVisible(true);
		}
		
		// An error may have occurred in a different syncUI process.
		if (!errorLabel.equals("")) {
			errorPanel.setVisible(true);
		}
		
		log.leave();
	}
	
	protected void syncUIWithFileToBeReviewedEntryElement(Element fileToBeReviewedEntryElement) {
		
		log.enter("syncUIWithFileToBeReviewedEntryElement");
		
		errorPanel.setVisible(false);
		errorLabel.setVisible(false); // in errorPanel
		fileToBeReviewedTableContainer.clear();
		fileToBeReviewedTableContainer.setVisible(false);
		
		if (fileToBeReviewedEntryElement != null) {

			log.debug("populating fileToBeReviewedTable....");
			
			FlexTable fileToBeReviewedTable = renderFileToBeReviewedTable(fileToBeReviewedEntryElement);
			fileToBeReviewedTableContainer.add(fileToBeReviewedTable);
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
		} else {
			// This may be a transient error, because a file has not been selected yet, but the widget has loaded.
			errorLabel.setText("fileToBeReviewedEntryElement was null");
			errorLabel.setVisible(true);
		}
		
		// An error may have occurred in a different syncUI process.
		if (!errorLabel.equals("")) {
			errorPanel.setVisible(true);
		}
		
		log.leave();
	}

	
	private FlexTable renderFileToBeReviewedTable(Element entry) {
		
		List<List<Widget>> rows = new ArrayList<List<Widget>>();

		List<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(new Label("File Name"));       // i18n
		headerRow.add(new Label("Date Submitted"));  //  i18n
		headerRow.add(new Label("Actions"));         //  i18n
		
		rows.add(headerRow);
		
		
		String title = AtomHelper.getTitle(entry);

		String submittedDate = ChassisHelper.getSubmissionPublishedAsDate(entry);
		
		String url = AtomHelper.getContent(entry).getAttribute("src");
		
		Anchor downloadLink = new Anchor("download"); // i18n
		
		downloadLink.setHref(url);
		downloadLink.setTarget("_blank");
		
		List<Widget> row = new ArrayList<Widget>();
		row.add(new Label(title));
		row.add(new Label(submittedDate));	
		row.add(downloadLink);
		
		rows.add(row);
		
		return RenderUtils.renderResultsTable(rows);
	}	
	

	@Override
	public void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});

		model.fileToBeReviewedEntryElement.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				syncUIWithFileToBeReviewedEntryElement(e.getAfter());
			}
		});

	}

	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ValueChangeHandler<Boolean> personalDataReviewButtonsChangeHandler = new ValueChangeHandler<Boolean>() {
			
			public void onValueChange(ValueChangeEvent<Boolean> e) {
				
				log.enter("onValueChange");
				
				if (fileCanProceedToCurationButton.getValue() || fileShouldBeClearedBeforeCurationButton.getValue()) {
					
					reviewFileButton.setEnabled(true);
				}
				else {
					reviewFileButton.setEnabled(false);
				}
				
				log.leave();
			}
			
		};
		
		fileCanProceedToCurationButton.addValueChangeHandler(personalDataReviewButtonsChangeHandler);
		fileShouldBeClearedBeforeCurationButton.addValueChangeHandler(personalDataReviewButtonsChangeHandler);
		
	}	
	
	
	@UiHandler("backHomeLink")
	void handleBackHomeLinkClick(ClickEvent e) {
		
		log.enter("handleBackHomeLinkClick");
		
		controller.backToStart();
		
		log.leave();
	}
	
	@UiHandler("reviewFileButton")
	void handleReviewFileButtonClick(ClickEvent e) {
		
		log.enter("handleReviewFileButtonClick");

		errorPanel.setVisible(false);
		errorLabel.setVisible(false); // in errorPanel				
		
		String reviewSubjectHref = AtomHelper.getEditLinkHrefAttr(model.getFileToBeReviewedEntryElement());
		
		log.debug("using reviewSubjectHref: " + reviewSubjectHref);
		
		String reviewOutcome = "";
		
		if (fileCanProceedToCurationButton.getValue()) {
			
			reviewOutcome = fileCanProceedToCurationButton.getText(); 
			
		} 
		else if (fileShouldBeClearedBeforeCurationButton.getValue()) {
			
			reviewOutcome = fileShouldBeClearedBeforeCurationButton.getText(); 
			
		} else {
			
			log.error("Unhandled case for radio buttons. Maybe the form was submitted before an option was chosen?");
			
		}
		
		if (reviewSubjectHref != null && reviewOutcome != "") {

			log.debug("still using reviewSubjectHref: " + reviewSubjectHref);
			
			controller.reviewFileAndBackToStart(reviewSubjectHref, reviewOutcome, reviewNotesInput.getValue());
			
		} else {

			if (reviewSubjectHref == null) {
				errorLabel.setText("reviewSubjectHref was null.");
			}
			else if (reviewOutcome == "") {
				errorLabel.setText("reviewOutcome was blank.");
				
			} else {
				
				errorLabel.setText("Unhandled error. Maybe the error-handling logic is faulty?");
			}
			
		}
		
		if (!errorLabel.equals("")) {
			errorLabel.setVisible(true);
			errorPanel.setVisible(true);
		}				
		
		log.leave();	
	}
	
	@UiHandler("cancelReviewFileButton")
	void handleCancelReviewFileButtonClick(ClickEvent e) {
		
		log.enter("handleCancelReviewFileButtonClick");
		
		controller.backToStart();
		
		log.leave();
	}
	
	public void setController(ReviewFileWidgetController controller) {
		this.controller = controller;
	}	
	
}
