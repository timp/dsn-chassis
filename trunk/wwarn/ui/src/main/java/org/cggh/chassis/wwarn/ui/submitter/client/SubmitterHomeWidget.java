package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class SubmitterHomeWidget extends ChassisWidget {

	
	
	private String titleId = HTMLPanel.createUniqueId();
	private String welcomeParaId = HTMLPanel.createUniqueId();
	private String progressParaId = HTMLPanel.createUniqueId();
	private String submitDataListItemId = HTMLPanel.createUniqueId();
	
	
	
	
	private String template = 
		"<h1 id=\""+titleId+"\"></h1>" +
		"<p id=\""+welcomeParaId+"\"></p>" +
		"<p id=\""+progressParaId+"\"></p>" +
		"<ul><li id=\""+submitDataListItemId+"\"></li></ul>";
	
	
	

	private HTMLPanel content;
	private Anchor submitDataLink;
	
	
	
	
	@Override
	public void renderUI() {
	
		this.content = new HTMLPanel(this.template);
		
		this.content.add(new HTML("Submit Data - Home"), this.titleId); // TODO i18n
		
		this.content.add(new HTML("Welcome to the WWARN data submission home page."), this.welcomeParaId); // TODO i18n
		
		this.content.add(new HTML("TODO progress"), this.progressParaId); // TODO
		
		this.submitDataLink = new Anchor();
		this.submitDataLink.setText("Submit Data"); // TODO i18n
		this.submitDataLink.addStyleName("mainNavigation");
		this.content.add(this.submitDataLink, this.submitDataListItemId);
		
		this.add(this.content);
				
	}
	
	
	
	
	@Override
	public void bindUI() {
	
		HandlerRegistration a = this.submitDataLink.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				fireEvent(new SubmitDataNavigationEvent());
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
	}
	
	

	
	public HandlerRegistration addSubmitDataNavigationHandler(SubmitDataNavigationHandler h) {
		return this.addHandler(h, SubmitDataNavigationEvent.TYPE);
	}
	
	
	
}
