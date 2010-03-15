package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 * @since 13 Jan 2010
 */
public class ViewStudyWidgetRenderer extends
		ChassisWidgetRenderer<ViewStudyWidgetModel> {

	private Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
	
	@UiTemplate("ViewStudyWidget.ui.xml")
	interface ViewStudyWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ViewStudyWidgetRenderer> {
	}
	private static ViewStudyWidgetRendererUiBinder uiBinder = 
		GWT.create(ViewStudyWidgetRendererUiBinder.class);

	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;
	


	private ViewStudyWidget owner;
	private ViewStudyWidgetController controller;

	public ViewStudyWidgetRenderer(ViewStudyWidget owner) {
		this.owner = owner;
	}

	public void setController(ViewStudyWidgetController controller) {
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
		
		else if (status instanceof ViewStudyWidgetModel.RetrieveFeedPendingStatus) {
			pendingPanel.setVisible(true);
		} 
		else if (status instanceof ViewStudyWidgetModel.StudiesRetrievedStatus) {
			
			
		}
		else if (status instanceof ViewStudyWidgetModel.CreateEntryPendingStatus) {
			pendingPanel.setVisible(true);
		}
		else if (status instanceof ViewStudyWidgetModel.StudyCreatedStatus) {
			
			
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
		
		// Turn everything off (that is made visible/enabled here) first, then show/enable as required.
		
		if (studyFeedDoc != null) {
			
			List<Element>  studyEntries = AtomHelper.getEntries(studyFeedDoc.getDocumentElement());
			
			if (!studyEntries.isEmpty()) {
				
				
				int index = 1, selectedIndex = 0;
				
				for (Element element : studyEntries) {
					
					String id = AtomHelper.getId(element);
					
					log.debug("id:"+id);
					
					/*
					if(model.getSelectedStudyId() != null && model.getSelectedStudyId().equals(id)) {
						
						selectedIndex = index;
						
						// Automatic selection.
						// Note that the manual selection event is handled by handleStudySelection()
						log.debug("Enabling the proceed button because a study was selected previously.");
						proceedWithSelectedButton.setEnabled(true);
					}
					*/
					index++;
				}
				
				log.debug("selectedIndex" + selectedIndex);
				
				
			}
		}
		
		log.leave();
	}
	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		
		ChangeHandler studySelectedChangeHandler = new ChangeHandler() {

			public void onChange(ChangeEvent event) {
			}

		};
		
		
	}

	public void error(String err) {

		errorMessage.clear();
		errorMessage.add(new HTML(err));
	}
	
	
}

