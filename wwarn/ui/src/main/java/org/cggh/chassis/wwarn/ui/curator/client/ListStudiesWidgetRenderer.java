package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;


/**
 * @author timp
 */
public class ListStudiesWidgetRenderer extends
		ChassisWidgetRenderer<ListStudiesWidgetModel> {

	private Log log = LogFactory.getLog(ListStudiesWidgetRenderer.class);
	

	@UiTemplate("ListStudiesWidget.ui.xml")
	interface ListStudiesWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ListStudiesWidgetRenderer> {
	}
	private static ListStudiesWidgetRendererUiBinder uiBinder = 
		GWT.create(ListStudiesWidgetRendererUiBinder.class);


	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel listStudiesPanel;
	



	private ListStudiesWidget owner;
	
	public ListStudiesWidgetRenderer(ListStudiesWidget owner) {
		this.owner = owner;
	}



	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");

		this.modelChangeHandlerRegistrations.add(
				model.status.addChangeHandler(new PropertyChangeHandler<Status>() {
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange<Status>");		
				syncUIWithStatus(e.getAfter());
				log.leave();
			}
		}));

		this.modelChangeHandlerRegistrations.add(
				model.message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		}));


		this.modelChangeHandlerRegistrations.add(
				model.studyFeed.addChangeHandler(new PropertyChangeHandler<Document>() {
			public void onChange(PropertyChangeEvent<Document> e) {
				log.enter("onchange(studyFeed)");
				syncUIWithStudyFeed(e.getAfter());
				log.leave();
			}
		}));

		log.leave();
	}
	

	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));

	}
	

	@Override
	protected void bindUI(ListStudiesWidgetModel model) {
		super.bindUI(model);

		this.pendingPanel.setVisible(true);	
		this.errorPanel.setVisible(false);	
		this.contentPanel.setVisible(true);
	}


	@Override
	protected void syncUI() {
		log.enter("syncUI");
		syncUIWithStatus(model.status.get());

		syncUIWithStudyFeed(model.studyFeed.get());


		log.leave();
	}
	
	protected void syncUIWithStudyFeed(Document studyFeed) {
		log.enter("syncUIWithStudyFeed");
		// TODO make abstract 
		if (studyFeed == null) 
			log.debug("studyFeed null");
		else {
			log.debug("studyFeed :"+studyFeed);

			this.listStudiesPanel.clear();
			this.listStudiesPanel.add(renderStudyFeed(studyFeed));
			pendingPanel.setVisible(false);

		}
		
		log.leave();
	}

	
	private FlexTable renderStudyFeed(Document studyFeed) {
		log.enter("renderStudyFeed");

		List<List<Widget>> rows = new ArrayList<List<Widget>>();
		
		ArrayList<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(strongWidget("Study Title"));		// i18n
		headerRow.add(strongWidget("Modules"));     // i18n
		headerRow.add(strongWidget("Submitters"));  // i18n
		headerRow.add(strongWidget("Actions"));     // i18n

		rows.add(headerRow);

		for (final Element entry : AtomHelper.getEntries(studyFeed.getDocumentElement())) { 

			ArrayList<Widget> row = new ArrayList<Widget>();

			row.add(new HTML(ChassisHelper.getTitle(entry)));
			row.add(new HTML(RenderUtils.join(ChassisHelper.getModules(entry), ", ")));
			row.add(new HTML(RenderUtils.join(ChassisHelper.getAuthorEmails(entry), ", ")));

			Anchor viewStudyNavigationLink = new Anchor("view study"); // i18n
				
			viewStudyNavigationLink.addClickHandler(new ClickHandler() {
					
				public void onClick(ClickEvent e) {
						
					log.enter("onClick");

					log.debug("Firing event...");

					ViewStudyNavigationEvent viewStudyNavigationEvent  = new ViewStudyNavigationEvent();
					viewStudyNavigationEvent.setStudyEntry(entry);

					owner.listStudiesViewStudyNavigationEventChannel.fireEvent(viewStudyNavigationEvent);
						
					log.leave();
						
				}

			});

			row.add(viewStudyNavigationLink);

			rows.add(row);
		}

		FlexTable t = RenderUtils.renderResultsTable(rows);

		log.leave();
		return t;
	}


	protected void syncUIWithStatus(Status status) {
		log.enter("syncUIWithStatus");		
		log.debug("status:" + status);
		
		errorPanel.setVisible(false);	
		if (status == null) {
			// null before being set
			log.debug("Called with null status");
		}
		else if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);	
			contentPanel.setVisible(false);
		}
		else if (status instanceof AsyncWidgetModel.AsyncRequestPendingStatus) {
			pendingPanel.setVisible(true);	
			contentPanel.setVisible(false);
		}
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(true);
		}			
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			model.message.set("Error status given on asynchronous call.");
		}			
		else {
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			model.message.set("Unhandled status:" + status);
		}

		log.leave();
	}



	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");

		if (message != null) {
			log.debug("Setting message to :" + message + ":");
			pendingPanel.setVisible(false);	
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}

		log.leave();
	}
	
	
}

