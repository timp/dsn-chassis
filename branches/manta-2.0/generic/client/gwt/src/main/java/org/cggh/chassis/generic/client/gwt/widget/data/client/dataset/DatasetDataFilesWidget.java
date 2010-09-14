/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.DataFileLink;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.DataFileActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.datafile.ViewDataFileActionEvent;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DatasetDataFilesWidget extends ChassisWidget {

	
	
	
	// state variables
	private DatasetEntry entry;
	
	
	
	
	// UI variables
	private Widget[] headerRow = { 
		strongWidget("Title"),
		strongWidget("Summary"),
		strongWidget("Owners"),
		strongWidget("Actions")
	};


	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		this.entry = null;
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		// nothing to do
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		// nothing to do
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		this.clear();

		List<Widget[]> rows = new ArrayList<Widget[]>();
		
		if (this.entry != null) {
			
			List<DataFileLink> links = this.entry.getDataFileLinks();
			
			for (int i=0; i<links.size(); i++) {
				
				DataFileEntry se = links.get(i).getEntry();
				if (se != null)	rows.add(this.renderRow(i, se));
				
			}

		}
		
		rows.add(0, this.headerRow);
		
		this.add(RenderUtils.renderResultsTable(rows));
	}

	
	
	
	

	// TODO refactor with my datafiles widget renderer

	/**
	 * @param entry
	 * @return
	 */
	private Widget[] renderRow(int index, final DataFileEntry entry) {
		
		Anchor viewAction = RenderUtils.renderActionAsAnchor("view", new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				ViewDataFileActionEvent e = new ViewDataFileActionEvent();
				e.setEntry(entry);
				fireEvent(e);
				
			}
		});
		
		FlowPanel actionsPanel = new FlowPanel();
		actionsPanel.add(viewAction);

		Widget[] row = { 
			new Label(entry.getTitle()),
			new Label(RenderUtils.truncate(entry.getSummary(), 30)),
			new Label(RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(entry.getAuthors())),
			actionsPanel
		};
		
		return row;
	}


	
	
	/**
	 * @param entry
	 */
	public void setEntry(DatasetEntry entry) {
		this.entry = entry;
		this.syncUI();
	}
	
	
	
	
	public HandlerRegistration addViewDataFileActionHandler(DataFileActionHandler h) {
		return this.addHandler(h, ViewDataFileActionEvent.TYPE);
	}








}
