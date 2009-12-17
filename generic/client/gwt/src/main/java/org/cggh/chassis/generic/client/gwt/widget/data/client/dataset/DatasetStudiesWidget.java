/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyLink;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.widget.study.client.StudyActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.study.client.ViewStudyActionEvent;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import static org.cggh.chassis.generic.widget.client.HtmlElements.*;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DatasetStudiesWidget extends ChassisWidget {
	
	
	
	// TODO consider factoring out to studies table or something like that
	
	
	
	
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
		
		this.add(RenderUtils.renderResultsTable(rows));
	}

	
	
	
	

	/**
	 * @param entry
	 * @return
	 */
	private Widget[] renderRow(int index, final StudyEntry entry) {
		
		Anchor viewAction = RenderUtils.renderActionAsAnchor("view", new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				
				ViewStudyActionEvent e = new ViewStudyActionEvent();
				e.setEntry(entry);
				fireEvent(e);
				
			}
		});

		FlowPanel actionsPanel = new FlowPanel();
		actionsPanel.add(viewAction);

		Widget[] row = { 
			strongWidget(""+entry.getTitle()+""),
			new HTML(RenderUtils.truncate(entry.getSummary(), 30)), // TODO consider refactor
			new HTML(RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(entry.getAuthors())), // TODO consider refactor
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

	
	
	
	public HandlerRegistration addViewStudyActionHandler(StudyActionHandler h) {
		return this.addHandler(h, ViewStudyActionEvent.TYPE);
	}




	

	
}
