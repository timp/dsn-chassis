package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;

public class SelectStudyWidget extends ChassisWidget {

	
	
	
	private String titleId = HTMLPanel.createUniqueId();
	private String subTitleId = HTMLPanel.createUniqueId();
	private String actionsParaId = HTMLPanel.createUniqueId();
	
	
	
	
	private String template = 
		"<h1 id=\""+titleId+"\"></h1>" +
		"<h2 id=\""+subTitleId+"\"></h2>" +
		"<p>TODO</p>" +
		"<p id=\""+actionsParaId+"\"></p>";
	
	
	

	private HTMLPanel content;
	private Button proceedButton;

	
	
	@Override
	public void renderUI() {
	
		this.content = new HTMLPanel(this.template);
		
		this.content.add(new HTML("Submit Data - Select Study"), this.titleId); // TODO i18n
		
		this.content.add(new HTML("<span class=\"currentStep\">1. Select Study</span> &gt; 2. Upload Files &gt; 3. Submit &gt; 4. Add Information"), this.subTitleId); // TODO i18n

		this.proceedButton = new Button("Proceed"); // TODO i18n
		this.content.add(this.proceedButton, this.actionsParaId);
		
		// TODO
		
		this.add(this.content);
	}
	
	
	
	
	@Override
	public void bindUI() {
		
		HandlerRegistration a = this.proceedButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				ProceedActionEvent e = new ProceedActionEvent();
				fireEvent(e);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);

	}
	
	
	
	
	public HandlerRegistration addProceedActionHandler(ProceedActionHandler h) {
		return this.addHandler(h, ProceedActionEvent.TYPE);
	}
	
	
	
	
	
}
