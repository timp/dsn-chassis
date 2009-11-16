/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.StudyLink;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DatasetStudiesWidget extends ChassisWidget {
	
	
	
	
	// state variables
	private DatasetEntry entry;
	
	
	
	
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
			
			List<StudyLink> links = this.entry.getStudyLinks();
			
			for (int i=0; i<links.size(); i++) {
				
				StudyEntry se = links.get(i).getEntry();
				if (se != null)	rows.add(this.renderRow(i, se));
				
			}

		}
		
		rows.add(0, this.headerRow);
		
		this.add(RenderUtils.renderFlexTable(rows));
	}

	
	
	
	

	/**
	 * @param entry
	 * @return
	 */
	private Widget[] renderRow(int index, StudyEntry entry) {
		
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
	public void setEntry(DatasetEntry entry) {
		this.entry = entry;
		this.syncUI();
	}

}
