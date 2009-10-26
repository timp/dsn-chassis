/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.form.submission.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.form.submission.client.OldSubmissionFormModel;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetListBoxRenderer;
import org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client.ViewStudiesWidgetModelListener;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class OldSubmissionFormRenderer implements OldSubmissionFormModel.Listener {

	

	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas = new FlowPanel();
	TextBoxBase titleInput = new TextBox();
	TextBoxBase summaryInput = new TextArea();
	private FlowPanel titleQuestionPanel;
	private FlowPanel summaryQuestionPanel;
	private FlowPanel modulesQuestionPanel;
	private FlowPanel studiesQuestionPanel;
	private ArrayList<SelectStudy> selectStudies;
	private OldSubmissionFormController controller;
	
	
	
	
	public OldSubmissionFormRenderer(OldSubmissionFormController controller) {
		log.enter("<constructor>");
		
		this.controller = controller;
	
		// do nothing here, leave to explicit render() call
		
		log.leave();
	}

	
	
	
	public void render() {
		log.enter("render");
		
		this.initCanvas();
		
		this.canvas.add(new HTML("<h3>Submission Title and Summary</h3>"));
		this.initTitleQuestion();
		this.canvas.add(this.titleQuestionPanel);
		this.initSummaryQuestion();
		this.canvas.add(this.summaryQuestionPanel);			

		this.canvas.add(new HTML("<h3>Modules</h3>"));
		this.initModulesQuestion();
		this.canvas.add(this.modulesQuestionPanel);
		
		this.canvas.add(new HTML("<h3>Studies</h3>"));
		this.canvas.add(new HTML("<p>Your new data submission must be linked to at least one study. Use the controls below to link this submission to a study.</p>"));
		this.initStudiesQuestion();
		this.canvas.add(this.studiesQuestionPanel);
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void initCanvas() {
		
		this.canvas.addStyleName(CSS.SUBMISSIONFORM_BASE);
		
	}
	
	
	
	
	/**
	 * 
	 */
	private void initTitleQuestion() {
		log.enter("initTitleQuestion");

		titleQuestionPanel = new FlowPanel();
		InlineLabel titleLabel = new InlineLabel("Please provide a title for the submission:");
		titleQuestionPanel.add(titleLabel);
		titleQuestionPanel.add(titleInput);
		titleQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		titleQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_TITLEQUESTION);

		titleInput.addValueChangeHandler(new TitleChangeHandler());
		
		log.leave();
	}





	/**
	 * 
	 */
	private void initSummaryQuestion() {
		log.enter("initSummaryQuestion");

		this.summaryQuestionPanel = new FlowPanel();
		
		Label summaryLabel = new Label("Please provide a textual summary of the submission...");
		summaryQuestionPanel.add(summaryLabel);
		summaryQuestionPanel.add(summaryInput);
		summaryQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		summaryQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_SUMMARYQUESTION);
		
		summaryInput.addValueChangeHandler(new SummaryChangeHandler());

		log.leave();		
	}

	
	
	
	/**
	 * @param createSubmissionForm
	 */
	private void initModulesQuestion() {
		log.enter("initModulesQuestion");

		// modules question
		
		modulesQuestionPanel = new FlowPanel();
		modulesQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		modulesQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_MODULESQUESTION);
		
		Label modulesLabel = new Label("Please select the modules that this submission is relevant to... (at least one must be selected)");
		modulesQuestionPanel.add(modulesLabel);

		FlexTable boxTable = new FlexTable();
		modulesQuestionPanel.add(boxTable);
		int rowNumber = -1;

		Map<String, String> modulesConfig = ConfigurationBean.getModules();
		
		//Create as many modules checkboxes as required
		for (String moduleId : modulesConfig.keySet()) {
			
			String UILabel = modulesConfig.get(moduleId);
			CheckBox moduleUICheckBox = new CheckBox();
			
			//add to GUI
			int rn = ++rowNumber;
			boxTable.setWidget(rn, 0, new InlineLabel(UILabel));
			boxTable.setWidget(rn, 1, moduleUICheckBox);
			
		}

		log.leave();
	}
	
	
	
	

	/**
	 * @param createSubmissionForm
	 */
	private void initStudiesQuestion() {
		log.enter("initStudiesQuestion");
		
		
		this.studiesQuestionPanel = new FlowPanel();
		studiesQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		studiesQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_STUDIESQUESTION);
		
//		studiesPanel.add(viewStudiesWidgetListBox);
		
		this.selectStudies = new ArrayList<SelectStudy>();
		this.selectStudies.add(new SelectStudy());
		this.renderSelectStudies();
		
//		viewStudiesWidgetListBox.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		log.leave();
	}
	
	
	
	
	
	/**
	 * 
	 */
	private void renderSelectStudies() {

		this.studiesQuestionPanel.clear();
		for (SelectStudy s : this.selectStudies) {
			s.render();
			this.studiesQuestionPanel.add(s);
		}
		
	}





	private class SelectStudy extends Composite {
		
		private Panel canvas = new FlowPanel();
		private ViewStudiesWidget viewStudiesWidgetListBox;
		
		private SelectStudy() {
			this.initWidget(this.canvas);
			this.canvas.addStyleName(CSS.SUBMISSIONFORM_SELECTSTUDY);
			ViewStudiesWidgetModelListener customRenderer = new ViewStudiesWidgetListBoxRenderer();
			viewStudiesWidgetListBox = new ViewStudiesWidget(customRenderer);
			viewStudiesWidgetListBox.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
		}

		/**
		 * 
		 */
		public void render() {
			
			this.canvas.clear();

			this.canvas.add(viewStudiesWidgetListBox);

			final int index = selectStudies.indexOf(SelectStudy.this);

			Button addButton = new Button("+");
			addButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					selectStudies.add(index+1, new SelectStudy());
					renderSelectStudies();
				}

			});
			this.canvas.add(addButton);
			
			if (index > 0) {
				Button removeButton = new Button("-");
				removeButton.addClickHandler(new ClickHandler() {
					
					public void onClick(ClickEvent arg0) {
						selectStudies.remove(index);
						renderSelectStudies();
					}

				});
				this.canvas.add(removeButton);
			}
			
		}
		
		
	}




	/**
	 * @return
	 */
	public Widget getCanvas() {
		return this.canvas;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.form.submission.client.SubmissionFormModel.Listener#onSubmissionEntryChanged(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry, org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	public void onSubmissionEntryChanged(SubmissionEntry before, SubmissionEntry after) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	private class TitleChangeHandler implements ValueChangeHandler<String> {
	
		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.setTitle(arg0.getValue());
		}
		
	}

	
	
	
	private class SummaryChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			controller.setSummary(arg0.getValue());
		}
		
	}

	
	

}
