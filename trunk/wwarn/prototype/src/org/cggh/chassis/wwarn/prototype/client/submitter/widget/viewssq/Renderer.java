/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewssq;

import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.wwarn.prototype.client.style.Styles;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private RootPanel rootPanel;
	private FlowPanel studyPanel;
	private HTML messagePanel;
	private FlowPanel contentPanel;
	private FlowPanel actionsPanelContainer;
	private DockPanel mainPanel;
	private FlowPanel actionsPanel;
	private FlowPanel contentPanelContainer;
	private SubmitterWidgetViewSsq owner;

	Renderer(Controller controller, SubmitterWidgetViewSsq owner) {
		this.controller = controller;
		this.owner = owner;
	}

	void render() {

		rootPanel.clear();
		
		mainPanel = new DockPanel();
		rootPanel.add(mainPanel);
		mainPanel.addStyleName(Styles.MAINPANEL);
		mainPanel.setWidth("100%");

		// actions panel container
		
		actionsPanelContainer = new FlowPanel();
		actionsPanelContainer.addStyleName(Styles.ACTIONSPANELCONTAINER);

		mainPanel.add(actionsPanelContainer, DockPanel.EAST);
		mainPanel.setCellWidth(actionsPanelContainer, "30%");
		
		// actions panel
		
		actionsPanel = new FlowPanel();
		actionsPanelContainer.add(actionsPanel);
		actionsPanel.add(new HTML("<h3>Actions</h3>"));
		actionsPanel.addStyleName(Styles.ACTIONSPANEL);
		
		// content panel container
		
		contentPanelContainer = new FlowPanel();
		contentPanelContainer.addStyleName(Styles.CONTENTPANELCONTAINER);

		mainPanel.add(contentPanelContainer, DockPanel.CENTER);
		mainPanel.setCellWidth(contentPanelContainer, "70%");

		// content panel 
		
		contentPanel = new FlowPanel();
		contentPanelContainer.add(contentPanel);
		contentPanel.addStyleName(Styles.CONTENTPANEL);
		
		contentPanel.add(new HTML("<h2>View Study Site Questionnaire (SSQ)</h2>"));

		messagePanel = new HTML();
		contentPanel.add(messagePanel);

		studyPanel = new FlowPanel();
		contentPanel.add(studyPanel);
		studyPanel.addStyleName(Styles.STUDYPANEL);	
		
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	public void onStudyEntryChanged(StudyEntry from, final StudyEntry to) {
		
		// render study panel
		
		studyPanel.clear();
		
		// title
		studyPanel.add(new HTML("<p><strong>Study title: </strong>"+to.getTitle()+"</p>"));
		
		// Summary
		studyPanel.add(new HTML("<p><strong>Summary: </strong><em>"+to.getSummary()+"</em></p>"));
		
		// Modules
		String modulesHtml = "<p><strong>Modules: </strong>";
		
		if (to.getModule(ChassisNS.CLINICAL)) {
			modulesHtml += "clinical; ";
		}
		
		if (to.getModule(ChassisNS.INVITRO)) {
			modulesHtml += "in vitro; ";
		}

		if (to.getModule(ChassisNS.PHARMACOLOGY)) {
			modulesHtml += "pharmacology; ";
		}

		if (to.getModule(ChassisNS.MOLECULAR)) {
			modulesHtml += "molecular; ";
		}
		
		modulesHtml += "</p>";
		studyPanel.add(new HTML(modulesHtml));	
	
		studyPanel.add(new HTML("<h3>1. @@TODO SSQ</h3>"));
		

		// render actions panel

		actionsPanel.clear();
		
		actionsPanel.add(new HTML("<h3>Actions</h3>"));
		
		VerticalPanel linksPanel = new VerticalPanel();
		actionsPanel.add(linksPanel);
		
		// edit SSQ action
		
		Anchor editSsqLink = new Anchor("edit study site questionnaire (SSQ)");
		editSsqLink.addStyleName(Styles.ACTION);
		editSsqLink.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				owner.fireOnActionEditSsq(to);
			}
		
		});
		linksPanel.add(editSsqLink);
		
		// edit study action
		
		Anchor editStudyLink = new Anchor("edit study title, summary or modules");
		editStudyLink.addStyleName(Styles.ACTION);
		editStudyLink.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				owner.fireOnActionEditStudy(to);
			}
			
		});
		linksPanel.add(editStudyLink);

		// view study action
		
		Anchor viewStudyLink = new Anchor("view study");
		viewStudyLink.addStyleName(Styles.ACTION);
		viewStudyLink.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				owner.fireOnActionViewStudy(to);
			}
		
		});
		linksPanel.add(viewStudyLink);


		
	}

}
