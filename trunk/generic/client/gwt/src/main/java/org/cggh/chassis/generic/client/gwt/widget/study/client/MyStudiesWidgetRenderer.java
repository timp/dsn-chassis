/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.List;


import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class MyStudiesWidgetRenderer 
	extends AsyncWidgetRenderer<MyStudiesWidgetModel>

{


	
	// TODO consider refactoring with mydatasetswidgetrenderer
	
	
	
	
	private Log log = LogFactory.getLog(MyStudiesWidgetRenderer.class);
	private FlowPanel resultsTableContainer;
	private MyStudiesWidget owner;

	
	
	
	/**
	 * @param owner
	 */
	public MyStudiesWidgetRenderer(MyStudiesWidget owner) {
		this.owner = owner;
	}






	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");

		this.mainPanel.add(h2Widget("My Studies")); // TODO i18n

		this.mainPanel.add(pWidget("The table below lists all of the studies that you own...")); // TODO i18n

		this.resultsTableContainer = new FlowPanel();
		this.mainPanel.add(this.resultsTableContainer);

		log.leave();
	}

	
	
	

	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		// nothing to do

		log.leave();

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForModelChanges()
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");

		super.registerHandlersForModelChanges();

		HandlerRegistration a = this.model.addChangeHandler(new StudyFeedChangeHandler() {
			
			public void onChange(StudyFeedChangeEvent e) {
				log.enter("onChange");
				
				syncResultsTable(e.getAfter());
				
				log.leave();
			}
		});
		
		this.modelChangeHandlerRegistrations.add(a);
		
		log.leave();

	}

	
	
	
	/**
	 * @param feed
	 */
	protected void syncResultsTable(StudyFeed feed) {
		log.enter("syncResultsTable");
		
		this.resultsTableContainer.clear();
		
		if (feed != null) {
			
			String[] headers = {
					"Title",	
					"Summary",	
					"Owners",	
					"Actions"
			};
			
			Widget[] headerRow = RenderUtils.renderLabels(headers);
			
			List<Widget[]> rows = new ArrayList<Widget[]>();
			rows.add(headerRow);
			
			for (StudyEntry entry : feed.getEntries()) {
				
				Widget[] row = this.renderRow(entry);
				rows.add(row);
				
			}
			
			FlexTable table = RenderUtils.renderResultsTable(rows);
			this.resultsTableContainer.add(table);
			
		}
		
		log.leave();
	}
	
	
	
	
	protected Widget[] renderRow(final StudyEntry entry) {

		Anchor viewAction = RenderUtils.renderActionAsAnchor("view", new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				log.enter("onClick");
				
				ViewStudyActionEvent e = new ViewStudyActionEvent();
				e.setEntry(entry);
				owner.fireEvent(e);
				
				log.leave();
			}
		});
		
		Widget[] row = {
				new Label(entry.getTitle()),	
				new Label(RenderUtils.truncate(entry.getSummary(), 40)),	
				RenderUtils.renderAtomAuthorsAsLabel(entry, false),	
				viewAction
		};
		
		return row;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		super.syncUI();
		
		this.syncResultsTable(this.model.getFeed());
		
		log.leave();
	}


	
}
