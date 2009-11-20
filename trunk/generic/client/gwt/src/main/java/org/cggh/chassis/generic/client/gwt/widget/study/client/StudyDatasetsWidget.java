/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.study.DatasetLink;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class StudyDatasetsWidget extends ChassisWidget {

	
	// TODO consider refactoring with other results table-like widgets,
	// e.g. datasetstudieswidget
	
	
	

	// state variables
	private StudyEntry entry;
	
	
	
	
	// UI variables
	private Widget[] headerRow = { 
		new HTML("<strong>Title</strong>"),
		new HTML("<strong>Summary</strong>"),
		new HTML("<strong>Owners</strong>"),
		new HTML("<strong>Actions</strong>")
	};


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		this.entry = null;
	}
	
	
	
	

	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		this.clear();

		List<Widget[]> rows = new ArrayList<Widget[]>();
		
		if (this.entry != null) {
			
			List<DatasetLink> links = this.entry.getDatasetLinks();
			
			for (int i=0; i<links.size(); i++) {
				
				DatasetEntry se = links.get(i).getEntry();
				if (se != null)	rows.add(this.renderRow(i, se));
				
			}

		}
		
		rows.add(0, this.headerRow);
		
		this.add(RenderUtils.renderResultsTable(rows));
	}

	
	
	
	

	/**
	 * @param entry
	 * @return
	 */
	private Widget[] renderRow(int index, DatasetEntry entry) {
		
		Widget[] row = { 
			new HTML("<strong>"+entry.getTitle()+"</strong>"),
			new HTML(RenderUtils.truncate(entry.getSummary(), 30)),
			new HTML(RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(entry.getAuthors())),
			new HTML("TODO")
		};
		
		return row;
	}


	
	
	/**
	 * @param entry
	 */
	public void setEntry(StudyEntry entry) {
		this.entry = entry;
		this.syncUI();
	}

	
}
