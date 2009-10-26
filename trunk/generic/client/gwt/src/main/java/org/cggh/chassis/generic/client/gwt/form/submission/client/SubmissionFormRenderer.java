/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.form.submission.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUtils;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisResources;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.form.submission.client.SubmissionForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Function;

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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author aliman
 *
 */
public class SubmissionFormRenderer {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas;
	private SubmissionEntry model;
	private ArrayList<SelectStudy> selectStudies;
	private TextBoxBase titleInput = new TextBox();
	private TextBoxBase summaryInput = new TextArea();
	private FlowPanel titleQuestionPanel;
	private FlowPanel summaryQuestionPanel;
	private FlowPanel modulesQuestionPanel;
	private FlowPanel studiesQuestionPanel;
	private Map<String,String> studyLinks = new HashMap<String,String>();
	private ChassisResources resources;
	private FlowPanel selectStudiesPanel;

	
	
	
	public SubmissionFormRenderer(Panel canvas, SubmissionEntry model) {
		this.canvas = canvas;
		this.model = model;
	}
	
	
	
	
	
	public void bind(SubmissionEntry model) {
		this.model = model;
	}
	
	
	
	
	

	
	public void render() {
		log.enter("render");
		
		this.initCanvas();
		
		this.canvas.add(new HTML("<h3>"+Resources.get(Resources.HEADINGTITLEANDSUMMARY)+"</h3>"));
		this.initTitleQuestion();
		this.canvas.add(this.titleQuestionPanel);
		this.initSummaryQuestion();
		this.canvas.add(this.summaryQuestionPanel);			

		this.canvas.add(new HTML("<h3>"+Resources.get(Resources.HEADINGMODULES)+"</h3>"));
		this.initModulesQuestion();
		this.canvas.add(this.modulesQuestionPanel);
		
		this.canvas.add(new HTML("<h3>"+Resources.get(Resources.HEADINGSTUDIES)+"</h3>"));
		this.initStudiesQuestion();
		this.canvas.add(this.studiesQuestionPanel);
		
		log.leave();
	}
	
	
	
	
	/**
	 * 
	 */
	private void initCanvas() {
		
		this.canvas.clear();
		this.canvas.addStyleName(CSS.SUBMISSIONFORM_BASE);
		
	}
	
	
	
	
	/**
	 * 
	 */
	private void initTitleQuestion() {
		log.enter("initTitleQuestion");

		titleQuestionPanel = new FlowPanel();
		InlineLabel titleLabel = new InlineLabel(Resources.get(Resources.QUESTIONLABELTITLE)); 
		titleQuestionPanel.add(titleLabel);
		titleQuestionPanel.add(titleInput);
		titleQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		titleQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_TITLEQUESTION);

		String title = this.model.getTitle();
		if (title != null) {
			log.debug("found model title: "+title);
			this.titleInput.setText(title);
		}
		
		titleInput.addValueChangeHandler(new TitleChangeHandler());
		
		log.leave();
	}





	/**
	 * 
	 */
	private void initSummaryQuestion() {
		log.enter("initSummaryQuestion");

		this.summaryQuestionPanel = new FlowPanel();
		
		Label summaryLabel = new Label(Resources.get(Resources.QUESTIONLABELSUMMARY));
		summaryQuestionPanel.add(summaryLabel);
		summaryQuestionPanel.add(summaryInput);
		summaryQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		summaryQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_SUMMARYQUESTION);
		
		String summary = this.model.getSummary();
		if (summary != null) {
			this.summaryInput.setText(summary);
		}
		
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
		
		Label modulesLabel = new Label(Resources.get(Resources.QUESTIONLABELMODULES));
		modulesQuestionPanel.add(modulesLabel);

		FlexTable boxTable = new FlexTable();
		modulesQuestionPanel.add(boxTable);
		int rowNumber = -1;

		Map<String, String> modulesConfig = ConfigurationBean.getModules();
		
		//Create as many modules checkboxes as required
		for (String moduleId : modulesConfig.keySet()) {
			
			String UILabel = modulesConfig.get(moduleId);
			CheckBox moduleUICheckBox = new CheckBox();
			moduleUICheckBox.setFormValue(moduleId);
			
			if (this.model.getModules().contains(moduleId)) {
				moduleUICheckBox.setValue(true, false);
			}
			
			moduleUICheckBox.addValueChangeHandler(new ModulesChangeHandler());
			
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
		this.studiesQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		this.studiesQuestionPanel.addStyleName(CSS.SUBMISSIONFORM_STUDIESQUESTION);
		this.studiesQuestionPanel.add(new HTML("<p>"+Resources.get(Resources.QUESTIONLABELSTUDIES)+"</p>"));
		
		this.selectStudiesPanel = new FlowPanel();
		this.studiesQuestionPanel.add(this.selectStudiesPanel);
		
		this.selectStudies = new ArrayList<SelectStudy>();

		SelectStudy prototype = new SelectStudy();
		this.selectStudies.add(prototype);

		List<AtomLink> links = this.model.getStudyLinks();
		for (int i=1; i<links.size(); i++) {
			SelectStudy s = new SelectStudy();
			this.selectStudies.add(s);
		}

		this.refreshStudyLinks();
		
		log.leave();
	}
	
	
		
	

	/**
	 * 
	 */
	private void renderSelectStudies() {
		
		this.selectStudiesPanel.clear();
		for (SelectStudy s : this.selectStudies) {
			s.render();
			this.selectStudiesPanel.add(s);
		}

		List<AtomLink> links = this.model.getStudyLinks();
		for (int i=0; i<links.size(); i++) {
			String link = Configuration.getSubmissionFeedURL() + links.get(i).getHref(); // TODO fix for absolute uris
			selectStudies.get(i).setSelectedValue(link);
		}
		
	}
	
	
	
	
	
	private void refreshStudyLinks() {

		ChassisUtils.getMapOfStudyLinksToTitlesForCurrentUser().addCallback(new Function<Map<String,String>, Map<String,String>>() {

			public Map<String, String> apply(Map<String, String> in) {
				studyLinks = in;
				renderSelectStudies();
				return in;
			}
			
		});

	}

	
	
	
	// TODO redesign this for simplicity, and also figure out when list of studies needs to get refreshed!
	
	private class SelectStudy extends Composite {
		
		private Panel canvas = new FlowPanel();
		private ListBox listBox;
		
		private SelectStudy() {
			this.initWidget(this.canvas);
			this.canvas.addStyleName(CSS.SUBMISSIONFORM_SELECTSTUDY);
		}
		
		private String getSelectedValue() {
			if (listBox != null) {
				return listBox.getValue(listBox.getSelectedIndex());
			}
			return null;
		}
		
		private void setSelectedValue(String value) {
			for (int i=0; i<listBox.getItemCount(); i++) {
				if (listBox.getValue(i).equals(value)) {
					listBox.setSelectedIndex(i);
				}
			}
		}

		/**
		 * 
		 */
		private void render() {
			
			this.canvas.clear();
			
			String previousValue = null;
			if (listBox != null) {
				previousValue = listBox.getValue(listBox.getSelectedIndex());
			}
			
			listBox = new ListBox();
			int cutoff = 60;
			if (studyLinks != null) {
				for (String link : studyLinks.keySet()) {
					String title = studyLinks.get(link);
					if (title.length() > cutoff) {
						title = title.substring(0, cutoff) + "...";
					}
					listBox.addItem(title, link);
				}
			}

			if (previousValue != null) {
				this.setSelectedValue(previousValue);
			}
			
			listBox.addClickHandler(new StudiesClickHandler());
			
			this.canvas.add(listBox);
			
			final int index = selectStudies.indexOf(SelectStudy.this);

			Button addButton = new Button("+");
			addButton.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent arg0) {
					SelectStudy s = new SelectStudy();
					selectStudies.add(index+1, s);
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
	
	
	
	

	private class TitleChangeHandler implements ValueChangeHandler<String> {
	
		public void onValueChange(ValueChangeEvent<String> arg0) {
			model.setTitle(arg0.getValue());
		}
		
	}

	
	
	
	private class SummaryChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			model.setSummary(arg0.getValue());
		}
		
	}
	
	
	
	
	private class ModulesChangeHandler implements ValueChangeHandler<Boolean> {
		
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			
			CheckBox source = (CheckBox) event.getSource();
			
			if (event.getValue()) {
				model.addModule(source.getFormValue());
			}
			else {
				model.removeModule(source.getFormValue());
			}
			
		}
			
	}
	
	
	
	
	private class StudiesClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {

			List<String> links = new ArrayList<String>();
			for (SelectStudy s : selectStudies) {
				links.add(s.getSelectedValue());
			}
			
			model.setStudyLinks(links);
			
		}

	}
	
	
	
	
	

}
