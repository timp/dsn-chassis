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


import com.google.gwt.xml.client.Element;
 

/**
 * Manually editted renderer.
 * @author timp
 */
public class ViewStudyWidgetRenderer extends
		ViewStudyWidgetRendererBase {

	private Log log = LogFactory.getLog(ViewStudyWidgetRenderer.class);
	
	public ViewStudyWidgetRenderer(ViewStudyWidget owner) {
		this.owner = owner;
	}


	Widget renderStudyEntry(Element studyEntry) {
		log.enter("renderStudyEntry");
		List<List<Widget>> rows = new ArrayList<List<Widget>>();
		
		ArrayList<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(strongWidget("Study Title")); // i18n
		headerRow.add(strongWidget("Modules"));     // i18n
		headerRow.add(strongWidget("Submitters"));  // i18n
		headerRow.add(strongWidget("Published"));   // i18n
		headerRow.add(strongWidget("Updated"));     // i18n

		rows.add(headerRow);
		
		{ 
			Element entry = studyEntry;


			ArrayList<Widget> row = new ArrayList<Widget>();

			row.add(new HTML(ChassisHelper.getTitle(entry)));
			row.add(new HTML(RenderUtils.join(ChassisHelper.getModules(entry), ", ")));
			row.add(new HTML(RenderUtils.join(ChassisHelper.getAuthorEmails(entry), ", ")));
			row.add(new HTML(ChassisHelper.getPublishedAsDate(entry)));
			row.add(new HTML(ChassisHelper.getUpdatedAsDate(entry)));

			rows.add(row);
		} 
		FlexTable t = RenderUtils.renderFirstRowHeadingResultsAsFirstColumnHeadingTable(rows);
		log.leave();
		return strongWidget("View Study");
	}

}

