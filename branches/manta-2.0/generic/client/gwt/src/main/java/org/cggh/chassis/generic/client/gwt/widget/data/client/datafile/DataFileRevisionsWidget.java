/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.atomext.client.datafile.RevisionLink;
import org.cggh.chassis.generic.atomext.client.media.MediaEntry;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class DataFileRevisionsWidget extends ChassisWidget {
	
	
	
	// utility variables
	private Log log;

	
	

	// state variables
	private DataFileEntry entry;
	
	
	
	
	// UI variables
	private Widget[] headerRow = { 
		new Label("Revision"),
		new Label("Date"),
		new Label("Uploaded By"),
		new Label("Original File Name"),
		new Label("Summary"),
		new Label("Actions")
	};
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.entry = null;

		log.leave();
	}


	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DataFileRevisionsWidget.class);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");


		// nothing else to do

		log.leave();
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// nothing to do

		log.leave();
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		this.clear();

		List<Widget[]> rows = new ArrayList<Widget[]>();

		rows.add(this.headerRow);
		
		if (this.entry != null) {
			
			List<RevisionLink> links = this.entry.getRevisionLinks();
			
			// sort in date created order
			Collections.sort(links, new Comparator<RevisionLink>() {

				public int compare(RevisionLink o1, RevisionLink o2) {
					return o1.getEntry().getPublished().compareTo(o2.getEntry().getPublished());
				}
				
			});
			
			for (int i=0; i<links.size(); i++) {
				
				MediaEntry me = links.get(i).getEntry();
				if (me != null)	rows.add(this.renderRow(i, me));
				
			}

		}
		
		
		this.add(RenderUtils.renderResultsTable(rows));

		log.leave();
	}

	
	
	
	/**
	 * @param entry
	 * @return
	 */
	private Widget[] renderRow(int index, MediaEntry entry) {
		log.enter("renderRow");
		
		Widget[] row = { 
			new Label(Integer.toString(index+1)),
			new Label(entry.getPublished()),
			new Label(RenderUtils.renderAtomAuthorsAsCommaDelimitedEmailString(entry.getAuthors())),
			new Label(entry.getTitle()),
			new Label(RenderUtils.truncate(entry.getSummary(), 20)),
			new HTML("<a href='"+Configuration.getMediaCollectionUrl() + "/" + entry.getEditMediaLink().getHref() + "'>download</a>")
		};
		
		log.leave();
		return row;
	}




	public void setEntry(DataFileEntry entry) {
		log.enter("setEntry");
		
		if (entry != null) log.debug(entry.toString());
		
		this.entry = entry;
		this.syncUI();
		log.leave();
	}


}
