package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

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

	@UiField FlowPanel tablePanel;
	
	
	private ListStudiesWidget owner;
	
    public ListStudiesWidgetRenderer(ListStudiesWidget owner) {
		this.owner = owner;
	}


	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		errorPanel.setVisible(false);	
		
		this.tablePanel = new FlowPanel();
		this.canvas.add(this.tablePanel);

		log.leave();
	}
	
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		syncUIWithStatus(model.getStatus());

		// Why is this the only place that works
        renderTable(model.studyFeed.get());
		
		
		log.leave();
	}
	
	
	@Override
	protected void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		model.status.addChangeHandler(new PropertyChangeHandler<Status>() {
		
			public void onChange(PropertyChangeEvent<Status> e) {
				log.enter("onChange");
			
				log.debug("Status " + e.getAfter());
				syncUIWithStatus(e.getAfter());
			
				log.leave();
			}
		});
		model.studyFeed.addChangeHandler(new PropertyChangeHandler<Document>() {
			
			public void onChange(PropertyChangeEvent<Document> e) {
				log.enter("onChange2");
				Document document = e.getAfter();
				log.debug("About to render with document " + document);
				renderTable(document);
				log.leave();
				throw new RuntimeException("this is never called, why not?");
			}
			
		});
		
	}

	private void renderTable(Document studyFeed){
		log.enter("renderTable");
		log.debug("studyFeed:" + studyFeed);
		List<Widget[]> rows = new ArrayList<Widget[]>();
		
		Widget[] headerRow = {
			strongWidget("Study Title"), // TODO i18n
			strongWidget("Modules"),     // TODO i18n
			strongWidget("Submitters"),  // TODO i18n
			strongWidget("Actions"),     // TODO i18n
		};
		
		rows.add(headerRow);
		
		for (final String[] entryFields : getFields(studyFeed)) {
			Anchor viewStudyLink = new Anchor("view study"); // TODO i18n
			
			viewStudyLink.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent e) {
					
					log.enter("onClick");
					
					log.debug("Firing event...");
					
					ViewStudyNavigationEvent viewStudyNavigationEvent  = new ViewStudyNavigationEvent();
					viewStudyNavigationEvent.setStudyId(entryFields[3]);
					
					owner.viewStudyNavigationEventChannel.fireEvent(viewStudyNavigationEvent);
					
					log.leave();
					
				}

			});
			Widget[] row = {
					new HTML(entryFields[0]),
					new HTML(entryFields[1]),
					new HTML(entryFields[2]),
					viewStudyLink
			};
			rows.add(row);
		}
		FlexTable table = RenderUtils.renderResultsTable(rows);
		this.tablePanel.clear();
		this.tablePanel.add(table);
		
		pendingPanel.setVisible(false);	
		log.leave();
	}
	
	// TODO Use feed
	private String[][] getFields(Document studyFeed) {
		log.enter("getFields");
		if(studyFeed == null)
			log.debug("Get fields studyFeed null");
		else
			for (Element study : AtomHelper.getEntries(studyFeed.getDocumentElement())) { 
				log.debug(study.toString());
			}
		log.leave();
		return new String[][]{
				{
					"Uganda-Kampala 2006",
					"Clinical, in Vitro",
					"alice@example.org, bob@example.org, outcaa@example.org, jane@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"Uganda-Kampala 2007",
					"Clinical",
					"alice@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"Uganda-Kampala 2008",
					"Clinical",
					"alice@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"Uganda-Kampala 2009",
					"Clinical",
					"alice@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"A Molecular Study 2006",
					"Molecular",
					"bob@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"A Molecular Study in Sri Lanka 2007",
					"Molecular",
					"bob@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"A Molecular Study 2008",
					"Molecular",
					"bob@example.org, jon@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"A Molecular Study in Sri Lanka 2009",
					"Molecular",
					"bob@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				},{
					"A Study in Sri Lanka 2010",
					"Pharmacology, Clinical, Molecular, in Vitro",
					"bob@example.org, outcaa@example.org",
					"urn:uuid:b4f4ded6-ec33-4df7-b096-f21517393a29" 
				}
			};
	}


	protected void syncUIWithStatus(Status status) {

		if (mainPanel == null) {
			log.debug("Panels instantiated:" + mainPanel);
			return;
		}
		log.enter("syncUIWithStatus");		
		log.debug("status:" + status);
		if (status instanceof AsyncWidgetModel.InitialStatus) {
			pendingPanel.setVisible(true);	
		}
		else if (status == ListStudiesWidgetModel.STATUS_RETRIEVE_STUDY_FEED_PENDING) {

		}			
		else if (status == ListStudiesWidgetModel.STATUS_READY_FOR_INTERACTION) {
			renderTable(model.studyFeed.get());
			pendingPanel.setVisible(false);	
		}			
		
		else if (status instanceof AsyncWidgetModel.ReadyStatus) {

			pendingPanel.setVisible(false);	
		}			
		
		else if (status instanceof AsyncWidgetModel.ErrorStatus) {

			
			error("Error status given on asynchronous call.");
		}			
		
		else {

			error("Unhandled status:" + status);
		}

		log.leave();
	}
	
	
	
	public void error(String err) {
		log.enter("error");
		log.debug("Error:" + err);
		pendingPanel.setVisible(false);	
		contentPanel.setVisible(false);
		errorMessage.clear();
		errorMessage.add(new HTML(err));
		errorPanel.setVisible(true);
		log.leave();
	}
	
	
}

