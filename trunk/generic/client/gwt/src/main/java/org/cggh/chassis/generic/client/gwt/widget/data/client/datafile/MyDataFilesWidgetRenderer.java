/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileFeed;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class MyDataFilesWidgetRenderer 
	extends AsyncWidgetRenderer<MyDataFilesWidgetModel> {
	
	
	
	private Log log = LogFactory.getLog(MyDataFilesWidgetRenderer.class);
	private FlowPanel resultsTableContainer;
	private MyDataFilesWidget owner;

	
	
	
	
	public MyDataFilesWidgetRenderer(MyDataFilesWidget owner) {
		this.owner = owner;
	}
	

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {
		log.enter("renderMainPanel");
		
		this.canvas.add(new HTML("<h2>My Data Files</h2>")); // TODO i18n

		this.canvas.add(new HTML("<p>Listed below are all of the data files you own.</p>")); // TODO i18n

		this.resultsTableContainer = new FlowPanel();
		this.canvas.add(this.resultsTableContainer);
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForChildWidgetEvents()
	 */
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		// TODO Auto-generated method stub

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer#registerHandlersForModelChanges()
	 */
	@Override
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");

		super.registerHandlersForModelChanges();

		HandlerRegistration a = this.model.addChangeHandler(new DataFileFeedChangeHandler() {
			
			public void onChange(DataFileFeedChangeEvent e) {
				log.enter("onChange");
				
				updateResultsTable(e.getAfter());
				
				log.leave();
			}
		});
		
		this.modelChangeHandlerRegistrations.add(a);

		log.leave();
	}

	
	
	
	/**
	 * @param feed
	 */
	protected void updateResultsTable(DataFileFeed feed) {
		log.enter("updateResultsTable");
		
		this.resultsTableContainer.clear();
		
		if (feed != null) {
			
			Widget[] headerRow = {
				new HTML("<strong>Title</strong>"),	
				new HTML("<strong>Summary</strong>"),	
				new HTML("<strong>Owners</strong>"),	
				new HTML("<strong>Actions</strong>")
			};
			
			List<Widget[]> rows = new ArrayList<Widget[]>();
			rows.add(headerRow);
			
			for (DataFileEntry entry : feed.getEntries()) {
				
				Widget[] row = this.renderRow(entry);
				rows.add(row);
				
			}
			
			FlexTable table = RenderUtils.renderFlexTable(rows);
			this.resultsTableContainer.add(table);
			
		}
		
		log.leave();
	}
	
	
	
	
	protected Widget[] renderRow(final DataFileEntry entry) {

		Anchor viewAction = RenderUtils.renderActionAsAnchor("view", new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				log.enter("onClick");
				
				ViewDataFileActionEvent e = new ViewDataFileActionEvent();
				e.setEntry(entry);
				owner.fireEvent(e);
				
				log.leave();
			}
		});
		
		Widget[] row = {
				new HTML("<strong>"+entry.getTitle()+"</strong>"),	
				new HTML(RenderUtils.truncate(entry.getSummary(), 20)),	
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
		
		this.updateResultsTable(this.model.getFeed());
		
		log.leave();
	}



}
