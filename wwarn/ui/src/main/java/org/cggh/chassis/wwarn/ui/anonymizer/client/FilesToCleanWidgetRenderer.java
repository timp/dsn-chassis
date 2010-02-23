/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;


import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
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
public class FilesToCleanWidgetRenderer extends ChassisWidgetRenderer<FilesToCleanWidgetModel> {

	private static final Log log = LogFactory.getLog(FilesToCleanWidgetRenderer.class);
	
	private FilesToCleanWidget owner;
	
	public FilesToCleanWidgetRenderer(FilesToCleanWidget owner) {
		
		this.owner = owner;
	}
	
	
	@UiTemplate("FilesToCleanWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, FilesToCleanWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField HTMLPanel errorPanel;
		@UiField Label errorLabel; // in errorPanel
	@UiField HTMLPanel pendingPanel;	
	@UiField Label noFilesToCleanLabel;
	@UiField HTMLPanel filesToCleanPanel;	
		@UiField FlowPanel filesToCleanTableContainer; // in filesToCleanPanel	
	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
	}

	
	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
		syncUIWithFilesToCleanFeedDoc(model.filesToCleanFeedDoc.get());
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");

		// Hide everything at this UI level (top level) first, then show as required.
		errorPanel.setVisible(false);
		pendingPanel.setVisible(false);
		filesToCleanPanel.setVisible(false);
		
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			// Everything off, hidden.
		}
		
		else if (status instanceof AsyncWidgetModel.AsyncRequestPendingStatus) {
		
			pendingPanel.setVisible(true);
			
		}
		
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {
			
			filesToCleanPanel.setVisible(true);
			
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


	private void syncUIWithFilesToCleanFeedDoc(Document filesToCleanFeedDoc) {

		filesToCleanTableContainer.clear();
		
		// Hide everything (that is made visible here) first, then show as required.
		noFilesToCleanLabel.setVisible(false);
		filesToCleanTableContainer.setVisible(false);
		
		if (filesToCleanFeedDoc != null) {
			
			List<Element> entries = AtomHelper.getEntries(filesToCleanFeedDoc.getDocumentElement());
			
			if (entries.size() == 0) {
				noFilesToCleanLabel.setVisible(true);
			}
			
			else {

				FlexTable filesToReviewTable = renderFilesToCleanTable(entries);
				filesToCleanTableContainer.add(filesToReviewTable);				
				
				filesToCleanTableContainer.setVisible(true);

			}

		}
			
		
	}	
	

	
	private FlexTable renderFilesToCleanTable(List<Element> entries) {

		List<Widget[]> rows = new ArrayList<Widget[]>();

		Widget[] headerRow = {
			new Label("File Name"), // TODO i18n
			new Label("Date Submitted"),  // TODO i18n
			new Label("Date Reviewed"),  // TODO i18n
			new Label("Actions") // TODO i18n
		};
		rows.add(headerRow);
		
		for (Element entry : entries) {
			
			String fileName = AtomHelper.getTitle(entry);
			
			String submittedDate = AtomHelper.getPublished(entry);
			
			// TODO: Provide the reviewed date. 
			
			String reviewedDate = AtomHelper.getPublished(entry);
			
			// TODO: Provide a link to clean. 
			
			String reviewLink = "clean";
			
			Widget[] row = {
				new Label(fileName),
				new Label(submittedDate),	
				new Label(reviewedDate),
				new Label(reviewLink)	
			};
			rows.add(row);
			
		}
		
		return RenderUtils.renderResultsTable(rows);
	}	
	
	@Override
	public void registerHandlersForModelChanges() {
		
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});

		model.filesToCleanFeedDoc.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUIWithFilesToCleanFeedDoc(e.getAfter());
			}

		});				
		
	}


	// TODO: Wire the handler up to the cleanFile link. 
	void handleCleanFileLinkClick(ClickEvent e) {
		owner.cleanFileNavigationEventChannel.fireEvent();
	}		
	
}
