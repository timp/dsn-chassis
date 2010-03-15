package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.xml.client.Document;

/**
 * @author timp
 * @since 13 Jan 2010
 */
public class CuratorHomeWidgetRenderer extends
		ChassisWidgetRenderer<CuratorHomeWidgetModel> {

	private Log log = LogFactory.getLog(CuratorHomeWidgetRenderer.class);
	
	@UiTemplate("CuratorHomeWidget.ui.xml")
	interface CuratorHomeWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, CuratorHomeWidgetRenderer> {
	}
	private static CuratorHomeWidgetRendererUiBinder uiBinder = 
		GWT.create(CuratorHomeWidgetRendererUiBinder.class);

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel mainPanel;
	private CuratorHomeWidget owner;
	private CuratorHomeWidgetController controller;

	public CuratorHomeWidgetRenderer(CuratorHomeWidget owner) {
		this.owner = owner;
	}

	public void setController(CuratorHomeWidgetController controller) {
		this.controller = controller;
	}

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		/*
		model.addStatusChangeHandler(new StatusChangeHandler() {
			public void onStatusChanged(StatusChangeEvent e) {
				syncUIWithStatus(e.getAfter());
			}
		});
		
		model.studyFeed.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				syncUiWithStudyFeedDoc(e.getAfter());
			}
		});
		*/
	}

	@Override
	protected void syncUI() {
		/*
		syncUIWithStatus(model.getStatus());
		syncUiWithStudyFeedDoc(model.getStudyFeedDoc());
		*/
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
		// Hide everything (that is made visible here) first, then show as required.
		pendingPanel.setVisible(false);
		errorPanel.setVisible(false);
		
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			
		}
		/*
		else if (status instanceof CuratorHomeWidgetModel.RetrieveFeedPendingStatus) {
			pendingPanel.setVisible(true);
		} 
		else if (status instanceof CuratorHomeWidgetModel.StudiesRetrievedStatus) {
			
			
		}
		else if (status instanceof CuratorHomeWidgetModel.CreateEntryPendingStatus) {
			pendingPanel.setVisible(true);
		}
		else if (status instanceof CuratorHomeWidgetModel.StudyCreatedStatus) {
			
			
		}
		*/
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			error("Error status: " + status + " " + model.message);
			errorPanel.setVisible(true);
		}
		else { 
			error("Unexpected status: " + status);
			errorPanel.setVisible(true);
		}
		log.leave();
	}
	
	/** Clear and re-initialise, setting selected id. 
	 * @param studyFeedDoc */
	void syncUiWithStudyFeedDoc(Document studyFeedDoc) {
		
		log.enter("syncUiWithStudyFeedDoc");
		/*
		
		// Turn everything off (that is made visible/enabled here) first, then show/enable as required.
		selectExistingStudyPanel.setVisible(false);
		proceedWithSelectedButton.setEnabled(false);
		
		studySelect.clear();
		
		if (studyFeedDoc != null) {
			
			List<Element>  studyEntries = AtomHelper.getEntries(studyFeedDoc.getDocumentElement());
			
			if (!studyEntries.isEmpty()) {
				
				studySelect.addItem("Please select an existing Study", null);
				
				int index = 1, selectedIndex = 0;
				
				for (Element element : studyEntries) {
					
					String id = AtomHelper.getId(element);
					
					log.debug("id:"+id);
					
					studySelect.addItem(AtomHelper.getTitle(element), id);
					
					if(model.getSelectedStudyId() != null && model.getSelectedStudyId().equals(id)) {
						
						selectedIndex = index;
						
						// Automatic selection.
						// Note that the manual selection event is handled by handleStudySelection()
						log.debug("Enabling the proceed button because a study was selected previously.");
						proceedWithSelectedButton.setEnabled(true);
					}
					index++;
				}
				
				log.debug("selectedIndex" + selectedIndex);
				
				studySelect.setItemSelected(selectedIndex, true);
				selectExistingStudyPanel.setVisible(true);
				
			}
		}
		*/
		log.leave();
	}
	
	
	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
	}

	public void error(String err) {

		errorMessage.clear();
		errorMessage.add(new HTML(err));
	}
	
	
}

