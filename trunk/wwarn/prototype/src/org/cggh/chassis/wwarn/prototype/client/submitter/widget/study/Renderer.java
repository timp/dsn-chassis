/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.study;

import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.wwarn.prototype.client.style.Styles;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author aliman
 *
 */
public class Renderer implements ModelListener {

	private RootPanel rootPanel;
	@SuppressWarnings("unused")
	private Controller controller;
	private HTML messagePanel;
	private FlowPanel studyPanel;
	private FlowPanel contentPanel;
	private FlowPanel actionsPanelContainer;
	private DockPanel mainPanel;
	private FlowPanel actionsPanel;
	private FlowPanel contentPanelContainer;

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	void render() {
		rootPanel.clear();
		
		mainPanel = new DockPanel();
		rootPanel.add(mainPanel);
		mainPanel.addStyleName(Styles.MAINPANEL);

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
		
		contentPanel.add(new HTML("<h2>Study</h2>"));

		messagePanel = new HTML();
		contentPanel.add(messagePanel);

		studyPanel = new FlowPanel();
		contentPanel.add(studyPanel);
		studyPanel.addStyleName(Styles.STUDYPANEL);
		
	}

	public void onMessageChanged(String from, String to) {
		messagePanel.setHTML("<p>"+to+"</p>");
	}

	public void onStudyEntryChanged(StudyEntry from, StudyEntry to) {
		
		// render study panel
		
		studyPanel.clear();
		
		// title
		studyPanel.add(new HTML("<p><strong>Title: </strong>"+to.getTitle()+"</p>"));
		
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
		
		// render actions panel

		actionsPanel.clear();
		
		actionsPanel.add(new HTML("<h3>Actions</h3>"));
		
		actionsPanel.add(new HTML("<p>Edit study title, summary or modules.</p>"));
		actionsPanel.add(new HTML("<p>View study site questionnaire (SSQ).</p>"));
		actionsPanel.add(new HTML("<p>Edit study site questionnaire (SSQ).</p>"));		
		
	}

}
