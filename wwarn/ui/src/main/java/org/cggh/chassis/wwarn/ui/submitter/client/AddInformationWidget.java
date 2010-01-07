package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class AddInformationWidget extends ChassisWidget {

	
	
	
	private String titleId = HTMLPanel.createUniqueId();
	private String subTitleId = HTMLPanel.createUniqueId();
	private String navigationParaId = HTMLPanel.createUniqueId();
	
	
	
	
	private String template = 
		"<h1 id=\""+titleId+"\"></h1>" +
		"<h2 id=\""+subTitleId+"\"></h2>" +
		"<p>TODO</p>" +
		"<p id=\""+navigationParaId+"\"></p>";
	
	
	

	private HTMLPanel content;
	private Anchor homeLink;

	
	
	@Override
	public void renderUI() {
	
		this.content = new HTMLPanel(this.template);
		
		this.content.add(new HTML("Submitter - Submit Data"), this.titleId); // TODO i18n
		
		this.content.add(new HTML("1. Select Study &gt; 2. Upload Files &gt; 3. Submit &gt; <span class=\"currentStep\">4. Add Information</span>"), this.subTitleId); // TODO i18n

		this.homeLink = new Anchor();
		this.homeLink.setText("submitter home"); // TODO i18n
		this.content.add(this.homeLink, this.navigationParaId);
		
		// TODO
		
		this.add(this.content);
	}
	
	
	
	
	@Override
	public void bindUI() {
		
		HandlerRegistration a = this.homeLink.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				fireEvent(new HomeNavigationEvent());
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);

	}
	
	
	
	
	public HandlerRegistration addHomeNavigationEventHandler(HomeNavigationHandler h) {
		return this.addHandler(h, HomeNavigationEvent.TYPE);
	}
	
	
	
	

}
