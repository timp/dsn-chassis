/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.editssq;

import org.cggh.chassis.gwt.lib.common.client.ChassisNS;
import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.wwarn.prototype.client.style.Styles;

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

	Renderer(Controller controller) {
		this.controller = controller;
	}

	void render() {
		rootPanel.clear();

		VerticalPanel vp1 = new VerticalPanel();
		vp1.setSpacing(0);
		vp1.setWidth("100%");
		rootPanel.add(vp1);
		
		vp1.add(new HTML("<h2>Edit Study Site Questionnaire (SSQ)</h2>"));
		
		studyPanel = new FlowPanel();
		vp1.add(studyPanel);
		studyPanel.addStyleName(Styles.STUDYPANEL);

		vp1.add(new HTML("<p>Please answer the following questions...</p>"));
		
		vp1.add(new HTML("<h3>1. @@TODO</h3>"));
		
		// TODO more
	}

	void setRootPanel(RootPanel root) {
		this.rootPanel = root;
	}

	public void onStudyEntryChanged(StudyEntry from, StudyEntry to) {
		
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
		studyPanel.add(new HTML(modulesHtml));	}

}
