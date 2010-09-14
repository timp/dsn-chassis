package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;


import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;


/**
 * Manually editted renderer.
 * @author timp
 */
public class ListStudiesWidgetRenderer extends
		ListStudiesWidgetRendererBase {

	private Log log = LogFactory.getLog(ListStudiesWidgetRenderer.class);
	
	public ListStudiesWidgetRenderer(ListStudiesWidget owner) {
		this.owner = owner;
	}


	FlexTable renderStudyFeed(Document studyFeed) {
		log.enter("renderStudyFeed");
		List<List<Widget>> rows = new ArrayList<List<Widget>>();
		
		ArrayList<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(strongWidget("Study Title"));		// i18n
		headerRow.add(strongWidget("Modules"));  		// i18n
		headerRow.add(strongWidget("Submitters"));  	// i18n
		
		headerRow.add(strongWidget("Actions"));     	// i18n
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

}

