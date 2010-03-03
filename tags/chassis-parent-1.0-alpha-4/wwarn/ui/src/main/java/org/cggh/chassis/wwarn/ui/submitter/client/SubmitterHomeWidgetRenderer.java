/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

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
 * @author lee
 *
 */
public class SubmitterHomeWidgetRenderer extends ChassisWidgetRenderer<SubmitterHomeWidgetModel> {

	private static final Log log = LogFactory.getLog(SubmitterHomeWidgetRenderer.class);
	
	private SubmitterHomeWidget owner;
	
	public SubmitterHomeWidgetRenderer(SubmitterHomeWidget owner) {
		
		this.owner = owner;
	}
	
	
	@UiTemplate("SubmitterHomeWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, SubmitterHomeWidgetRenderer> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel mainPanel;

	@UiField Label countingSubmissionsErrorLabel;	
	@UiField Label submissionsCountPrefixLabel;
	@UiField Label submissionsCountLabel;
	@UiField Label submissionsCountIsZeroSuffixLabel;
	@UiField Label submissionsCountIsOneSuffixLabel;
	@UiField Label submissionsCountIsManySuffixLabel;
	@UiField Label countingFilesErrorLabel;
	@UiField Label countingStudiesErrorLabel;	
	@UiField Label filesCountPrefixLabel;
	@UiField Label filesCountLabel;
	@UiField Label filesCountIsZeroSuffixLabel;
	@UiField Label filesCountIsOneSuffixLabel;
	@UiField Label filesCountIsManySuffixLabel;
	@UiField Label studiesCountPrefixLabel;
	@UiField Label studiesCountLabel;
	@UiField Label studiesCountIsZeroSuffixLabel;
	@UiField Label studiesCountIsOneSuffixLabel;
	@UiField Label studiesCountIsManySuffixLabel;
	@UiField Anchor submitDataLink;
	
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
		
		else if (status instanceof SubmitterHomeWidgetModel.RetrieveSubmissionsPendingStatus) {

			pendingPanel.setVisible(true);

		}

		else if (status instanceof SubmitterHomeWidgetModel.SubmissionsNotFoundStatus) {

			// Everything off, hidden.
			
		}			
		
		else if (status instanceof SubmitterHomeWidgetModel.SubmissionsRetrievedStatus) {

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
		countingSubmissionsErrorLabel.setVisible(false);	
		submissionsCountPrefixLabel.setVisible(false);
		submissionsCountLabel.setVisible(false);
		submissionsCountIsZeroSuffixLabel.setVisible(false);
		submissionsCountIsOneSuffixLabel.setVisible(false);
		submissionsCountIsManySuffixLabel.setVisible(false);
		countingFilesErrorLabel.setVisible(false);
		countingStudiesErrorLabel.setVisible(false);		
		filesCountPrefixLabel.setVisible(false);
		filesCountLabel.setVisible(false);
		filesCountIsZeroSuffixLabel.setVisible(false);
		filesCountIsOneSuffixLabel.setVisible(false);
		filesCountIsManySuffixLabel.setVisible(false);
		studiesCountPrefixLabel.setVisible(false);
		studiesCountLabel.setVisible(false);
		studiesCountIsZeroSuffixLabel.setVisible(false);
		studiesCountIsOneSuffixLabel.setVisible(false);
		studiesCountIsManySuffixLabel.setVisible(false);
		
		// Always show the submit data link
		submitDataLink.setVisible(true);
		
		if (submissions != null) {
			
			
			List<Element> submissionEntries = AtomHelper.getEntries(submissions.getDocumentElement());
			
			submissionsCountLabel.setText(Integer.toString(submissionEntries.size()));			
			
			if (submissionEntries.size() == 0) {

				submissionsCountPrefixLabel.setVisible(true);
				submissionsCountLabel.setVisible(true);				
				submissionsCountIsZeroSuffixLabel.setVisible(true);
				
			}
			
			else {
				
				if (submissionEntries.size() == 1) {

					submissionsCountPrefixLabel.setVisible(true);
					submissionsCountLabel.setVisible(true);					
					submissionsCountIsOneSuffixLabel.setVisible(true);

					countFilesAndStudies(submissionEntries);
				}
				
				else if (submissionEntries.size() > 1) {

					submissionsCountPrefixLabel.setVisible(true);
					submissionsCountLabel.setVisible(true);					
					submissionsCountIsManySuffixLabel.setVisible(true);
					
					countFilesAndStudies(submissionEntries);
				}
				
				else {
					
					countingSubmissionsErrorLabel.setVisible(true);
					
					log.error("Unexpected submissionEntries size: " + Integer.toString(submissionEntries.size()));
				}
				
				
				
			}

		}
		
		log.leave();
		
	}


	private void countFilesAndStudies(List<Element> submissionEntries) {
		
		// Determine the number of unique files and studies in all the submissions for this user. 
		// Each unique file or study will have a unique href attribute value.
		HashSet<String> fileHrefs = new HashSet<String>();
		HashSet<String> studyHrefs = new HashSet<String>();
		
		for (Element submissionEntry : submissionEntries) {

			List<Element> fileLinks = AtomHelper.getLinks(submissionEntry, Chassis.REL_SUBMISSIONPART);
			List<Element> studyLinks = AtomHelper.getLinks(submissionEntry, Chassis.REL_ORIGINSTUDY);
			
			for (Element link : fileLinks) {
				String hrefAttrValue = AtomHelper.getHrefAttr(link);
				fileHrefs.add(hrefAttrValue);
			}
			
			for (Element link : studyLinks) {
				String hrefAttrValue = AtomHelper.getHrefAttr(link);
				studyHrefs.add(hrefAttrValue);
			}					
			
		}
		
		int filesCount = fileHrefs.size();
		int studiesCount = studyHrefs.size();				

		filesCountLabel.setText(Integer.toString(filesCount));

		
		if (filesCount == 0) {
			
			filesCountPrefixLabel.setVisible(true);
			filesCountLabel.setVisible(true);	
			filesCountIsZeroSuffixLabel.setVisible(true);
		}
		
		else if (filesCount ==  1) {
			
			filesCountPrefixLabel.setVisible(true);
			filesCountLabel.setVisible(true);	
			filesCountIsOneSuffixLabel.setVisible(true);	
			
		}
		
		else if (filesCount >  1) {
			
			filesCountPrefixLabel.setVisible(true);
			filesCountLabel.setVisible(true);	
			filesCountIsManySuffixLabel.setVisible(true);
		}
		
		else {
			
			countingFilesErrorLabel.setVisible(true);					
			
			log.error("Unexpected filesCount: " + Integer.toString(filesCount));
			
		}

		studiesCountLabel.setText(Integer.toString(studiesCount));				

		
		if (studiesCount == 0) {
			
			studiesCountPrefixLabel.setVisible(true);
			studiesCountLabel.setVisible(true);	
			studiesCountIsZeroSuffixLabel.setVisible(true);
		}
		
		else if (studiesCount ==  1) {
			
			studiesCountPrefixLabel.setVisible(true);
			studiesCountLabel.setVisible(true);	
			studiesCountIsOneSuffixLabel.setVisible(true);	
			
		}
		
		else if (studiesCount >  1) {
			
			studiesCountPrefixLabel.setVisible(true);
			studiesCountLabel.setVisible(true);	
			studiesCountIsManySuffixLabel.setVisible(true);
		}
		
		else {
			
			countingStudiesErrorLabel.setVisible(true);
			
			log.error("Unexpected studiesCount: " + Integer.toString(studiesCount));
		}
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
	

	
	
	
	@UiHandler("submitDataLink")
	void handleSubmitDataLinkClick(ClickEvent e) {
		owner.submitDataNavigationEventChannel.fireEvent();
	}	
}
