/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author aliman
 *
 */
public class AddInformationWidgetView {

	
	
	private Log log = LogFactory.getLog(AddInformationWidgetView.class);
	
	
	@UiTemplate("AddInformationWidget.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, AddInformationWidgetView> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);
	
	
	
	private AddInformationWidgetController controller;
	
	
	
	HTMLPanel root = uiBinder.createAndBindUi(this);
	
	
	
	@UiField Panel titlePanel;
	@UiField Panel mainContentPanel;
	@UiField Panel retrieveSubmissionPendingPanel;
	@UiField SimplePanel studyQuestionnaireContainer;
	
	@UiField Label studyTitleLabel;
	
	@UiField Anchor submitterHomeLink1;
	@UiField Anchor submitterHomeLink2;
	@UiField Button saveChangesButton1;
	@UiField Button saveChangesButton2;

	@UiField Panel filesTableContainer;
	
	@UiField Panel retrieveStudyPendingPanel;
	@UiField Panel saveStudyPendingPanel;
	@UiField Panel studyQuestionnairePanel;



	private XQuestionnaire studyQuestionnaire;
	
	
	
	
	/**
	 * @param controller
	 */
	public AddInformationWidgetView(AddInformationWidgetController controller) {
		this.controller = controller;
		controller.setView(this);
	}




	@UiHandler("submitterHomeLink1")
	void onSubmitterHomeLink1Click(ClickEvent e) {
		log.enter("onSubmitterHomeLinkClick");
		
		controller.navigateToSubmitterHome();
		
		log.leave();
	}


	
	@UiHandler("submitterHomeLink2")
	void onSubmitterHomeLink2Click(ClickEvent e) {
		log.enter("onSubmitterHomeLinkClick");
		
		controller.navigateToSubmitterHome();
		
		log.leave();
	}
	
	
	
	@UiHandler("saveChangesButton1")
	void onSaveChangesButton1Click(ClickEvent e) {
		controller.saveStudy();
	}



	@UiHandler("saveChangesButton2")
	void onSaveChangesButton2Click(ClickEvent e) {
		controller.saveStudy();
	}



	/**
	 * @param questionnaire
	 */
	public void setQuestionnaire(XQuestionnaire questionnaire) {
		log.enter("setQuestionnaire");
		
		studyQuestionnaire = questionnaire;
		studyQuestionnaireContainer.clear();
		studyQuestionnaireContainer.setWidget(questionnaire);

		log.leave();
	}

	
	
	public XQuestionnaire getQuestionnaire() {
		return studyQuestionnaire;
	}



}
