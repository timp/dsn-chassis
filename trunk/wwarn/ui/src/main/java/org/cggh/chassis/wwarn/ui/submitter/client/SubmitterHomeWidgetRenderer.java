/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

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
//import org.cggh.chassis.wwarn.ui.submitter.client.SubmitterHomeWidgetModel.SubmissionsChangeEvent;
//import org.cggh.chassis.wwarn.ui.submitter.client.SubmitterHomeWidgetModel.SubmissionsChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
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
	
	@UiField HTMLPanel mainPanel;
	@UiField Label submissionsPendingLabel;
	@UiField Anchor submitDataLink;
	
	@UiField Label submissionsCountPrefixLabel;
	@UiField Label submissionsCountLabel;
	@UiField Label submissionsCountIsZeroSuffixLabel;
	@UiField Label submissionsCountIsOneSuffixLabel;
	@UiField Label submissionsCountIsManySuffixLabel;
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
	
	@Override
	protected void renderUI() {

		HTMLPanel ui = uiBinder.createAndBindUi(this);
		
		this.canvas.clear();
		this.canvas.add(ui);
		
	}

	
	@Override
	protected void syncUI() {
		syncUIWithStatus(model.getStatus());
//		syncUIWithSubmissions(model.getSubmissions());
		syncUIWithSubmissions(model.submissionFeed.get());
	}	
	

	private void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {

			errorPanel.setVisible(false);
			
			mainPanel.setVisible(false);
			
		}
		
		else if (status instanceof SubmitterHomeWidgetModel.RetrieveSubmissionsPendingStatus) {

			
			errorPanel.setVisible(false);
			
			mainPanel.setVisible(true);
			
			// In the main panel.
			submissionsPendingLabel.setVisible(true);
			
			
		}

		else if (status instanceof SubmitterHomeWidgetModel.SubmissionsNotFoundStatus) {

			errorPanel.setVisible(false);
			
			mainPanel.setVisible(true);
			
			// In the main panel.
			submissionsPendingLabel.setVisible(false);
				
			
		}			
		
		else if (status instanceof SubmitterHomeWidgetModel.SubmissionsRetrievedStatus) {

			errorPanel.setVisible(false);
			
			mainPanel.setVisible(true);
				
			// In the main panel.
			submissionsPendingLabel.setVisible(false);
		}		
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			errorPanel.setVisible(true);
			
			mainPanel.setVisible(false);
			
			log.error("Error status given on asynchronous call. Maybe a bad submissions query URL.");
			
		}			
		
		else {

			errorPanel.setVisible(true);

			mainPanel.setVisible(false);
			
			log.error("Unhandled status.");
			
		}
		
		log.leave();
	}


	private void syncUIWithSubmissions(Document submissions) {
		
		log.enter("syncUIWithSubmissions");
		
		if (submissions != null) {
			
			
			List<Element> submissionEntries = AtomHelper.getEntries(submissions.getDocumentElement());
			
			submissionsCountLabel.setText(Integer.toString(submissionEntries.size()));
			submissionsCountPrefixLabel.setVisible(true);
			submissionsCountLabel.setVisible(true);
			
			if (submissionEntries.size() == 0) {
				
				submissionsCountIsZeroSuffixLabel.setVisible(true);
				submissionsCountIsOneSuffixLabel.setVisible(false);
				submissionsCountIsManySuffixLabel.setVisible(false);
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
			}
			
			else {
				
				submissionsCountIsZeroSuffixLabel.setVisible(false);
				
				if (submissionEntries.size() == 1) {
					
					submissionsCountIsOneSuffixLabel.setVisible(true);
					submissionsCountIsManySuffixLabel.setVisible(false);
					
				}
				
				else if (submissionEntries.size() > 1) {
					
					submissionsCountIsOneSuffixLabel.setVisible(false);
					submissionsCountIsManySuffixLabel.setVisible(true);
					
				}
				
				else {
					
					log.error("Unexpected submissionEntries size: " + Integer.toString(submissionEntries.size()));
				}
				
				// TODO: Resolve inter-label spacing issue in Firefox 3.5.7.
				
				// Count the files and studies.
				int filesCount = 0;
				int studiesCount = 0;
				for (Element submissionEntry : submissionEntries) {

					List<Element> fileLinks = AtomHelper.getLinks(submissionEntry, Chassis.REL_SUBMISSIONPART);
					List<Element> studyLinks = AtomHelper.getLinks(submissionEntry, Chassis.REL_ORIGINSTUDY);
					
					filesCount += fileLinks.size();
					studiesCount += studyLinks.size();
					
				}

				filesCountLabel.setText(Integer.toString(filesCount));
				filesCountPrefixLabel.setVisible(true);
				filesCountLabel.setVisible(true);				
				
				if (filesCount == 0) {
					
					filesCountIsZeroSuffixLabel.setVisible(true);
					filesCountIsOneSuffixLabel.setVisible(false);
					filesCountIsManySuffixLabel.setVisible(false);
				}
				
				else if (filesCount ==  1) {
					
					filesCountIsZeroSuffixLabel.setVisible(false);
					filesCountIsOneSuffixLabel.setVisible(true);
					filesCountIsManySuffixLabel.setVisible(false);	
					
				}
				
				else if (filesCount >  1) {
					
					filesCountIsZeroSuffixLabel.setVisible(false);
					filesCountIsOneSuffixLabel.setVisible(false);
					filesCountIsManySuffixLabel.setVisible(true);
				}
				
				else {
					
					log.error("Unexpected filesCount: " + Integer.toString(filesCount));
				}

				studiesCountLabel.setText(Integer.toString(studiesCount));				
				studiesCountPrefixLabel.setVisible(true);
				studiesCountLabel.setVisible(true);				
				
				if (studiesCount == 0) {
					
					studiesCountIsZeroSuffixLabel.setVisible(true);
					studiesCountIsOneSuffixLabel.setVisible(false);
					studiesCountIsManySuffixLabel.setVisible(false);
				}
				
				else if (studiesCount ==  1) {
					
					studiesCountIsZeroSuffixLabel.setVisible(false);
					studiesCountIsOneSuffixLabel.setVisible(true);
					studiesCountIsManySuffixLabel.setVisible(false);	
					
				}
				
				else if (studiesCount >  1) {
					
					studiesCountIsZeroSuffixLabel.setVisible(false);
					studiesCountIsOneSuffixLabel.setVisible(false);
					studiesCountIsManySuffixLabel.setVisible(true);
				}
				
				else {
					
					log.error("Unexpected studiesCount: " + Integer.toString(studiesCount));
				}				
				
			}

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
		
//		model.addSubmissionsChangeHandler(new SubmissionsChangeHandler() {
//			public void onChange(SubmissionsChangeEvent e) {
//				syncUIWithSubmissions(e.getAfter());
//			}
//		});
		
		model.submissionFeed.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUIWithSubmissions(e.getAfter());
			}
		});
	}
	

	
	
	
	@UiHandler("submitDataLink")
	void handleSubmitDataLinkClick(ClickEvent e) {
		this.owner.fireEvent(new SubmitDataNavigationEvent());
	}	
}
