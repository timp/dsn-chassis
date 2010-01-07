package org.cggh.chassis.wwarn.ui.administrator.client;

import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class AdministratorHomeWidget extends ChassisWidget {

	
	
	private String[][] collections = {
			{ "Studies", Config.get("collection.studies.url") },	
			{ "Media", Config.get("collection.media.url") },
			{ "Submissions", Config.get("collection.submissions.url") },
			{ "Reviews", Config.get("collection.reviews.url") }
	};

	
	
	private String titleId = HTMLPanel.createUniqueId();
	private String welcomeParaId = HTMLPanel.createUniqueId();
	private String adminCollectionsDivId = HTMLPanel.createUniqueId();

	
	
	private String template = 
		"<h1 id=\""+titleId+"\"></h1>" +
		"<p id=\""+welcomeParaId+"\"></p>" +
		"<div id=\""+adminCollectionsDivId+"\"></div>";
	
	
	

	private HTMLPanel content;
	
	
	
	
	private AdminCollectionsWidget adminCollectionsWidget;

	
	
	
	@Override
	public void renderUI() {
	
		this.content = new HTMLPanel(this.template);
		
		this.content.add(new HTML("Administrator - Home"), this.titleId); // TODO i18n
		
		this.content.add(new HTML("Welcome to the Chassis/WWARN administrator home page."), this.welcomeParaId); // TODO i18n
	
		this.adminCollectionsWidget = new AdminCollectionsWidget(collections);
		this.content.add(this.adminCollectionsWidget, this.adminCollectionsDivId);
		
		this.add(this.content);
				
	}
	
	
	
	
	@Override
	public void bindUI() {
	
		
	}
	
	
	
	
	@Override
	public void refresh() {
		this.adminCollectionsWidget.refresh();
	}

		
}
