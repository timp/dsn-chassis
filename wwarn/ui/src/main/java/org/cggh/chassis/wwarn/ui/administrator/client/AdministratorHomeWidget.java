package org.cggh.chassis.wwarn.ui.administrator.client;

import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class AdministratorHomeWidget extends ChassisWidget {

	
	
	private String[][] collections = {
			{ "Studies", Config.get(Config.COLLECTION_STUDIES_URL) },	
			{ "Media", Config.get(Config.COLLECTION_MEDIA_URL) },
			{ "Submissions", Config.get(Config.COLLECTION_SUBMISSIONS_URL) },
			{ "Reviews", Config.get(Config.COLLECTION_REVIEWS_URL) },
			{ "Derivations", Config.get(Config.COLLECTION_DERIVATIONS_URL) },
	};

	
	
	private String titleId = HTMLPanel.createUniqueId();
	private String welcomeParaId = HTMLPanel.createUniqueId();
	private String adminCollectionsDivId = HTMLPanel.createUniqueId();

	
	
	private String template = 
		"<h1 id=\""+titleId+"\"></h1>" +
		"<p id=\""+welcomeParaId+"\"></p>" +
		"<div id=\""+adminCollectionsDivId+"\"></div>";
	
	
	

	private HTMLPanel contentPanel;
	
	
	
	
	private AdminCollectionsWidget adminCollectionsWidget;

	
	
	
	@Override
	public void renderUI() {
	
		this.contentPanel = new HTMLPanel(this.template);
		
		this.contentPanel.add(new HTML("Administrator - Home"), this.titleId); // TODO i18n
		
		this.contentPanel.add(new HTML("Welcome to the Chassis/WWARN administrator home page."), this.welcomeParaId); // TODO i18n
	
		this.adminCollectionsWidget = new AdminCollectionsWidget(collections);
		this.contentPanel.add(this.adminCollectionsWidget, this.adminCollectionsDivId);
		
		this.add(this.contentPanel);
				
	}
	
	
	
	
	@Override
	public void bindUI() {
	
		
	}
	
	
	
	
	@Override
	public void refresh() {
		this.adminCollectionsWidget.refresh();
	}

		
}
