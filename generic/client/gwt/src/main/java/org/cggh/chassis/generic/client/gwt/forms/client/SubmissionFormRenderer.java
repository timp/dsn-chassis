/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomLink;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUtils;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.forms.client.SubmissionForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
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
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class SubmissionFormRenderer extends BaseFormRenderer<SubmissionEntry, SubmissionFeed> {

	
	
	private Log log = LogFactory.getLog(SubmissionFormRenderer.class);
	private FlowPanel modulesQuestionPanel, studiesQuestionPanel, selectStudiesPanel;
	private Set<CheckBox> moduleCheckBoxes;
	private List<SelectStudy> selectStudies;
	private Map<String,String> studyLinks = new HashMap<String,String>();
	private Deferred<Map<String, String>> studyLinksDeferred;
	
	
	
	public SubmissionFormRenderer(SubmissionForm owner) {
		super(owner);
	}
	
	
	
	
	public static class Styles {
		public static final String MODULESQUESTION = "modulesQuestion";
		public static final String STUDIESQUESTION = "studiesQuestion";
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.forms.client.BaseFormRenderer#createResources()
	 */
	@Override
	protected org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources createResources() {
		return new Resources();
	}
	
	
	
	
	@Override
	public void renderUI() {
		log.enter("renderUI");
		
		super.renderUI();
		
		this.canvas.add(new HTML("<h3>"+text(Resources.HEADINGMODULES)+"</h3>"));
		this.renderModulesQuestion();
		this.canvas.add(this.modulesQuestionPanel);
		
		this.canvas.add(new HTML("<h3>"+text(Resources.HEADINGSTUDIES)+"</h3>"));
		this.renderStudiesQuestion();
		this.canvas.add(this.studiesQuestionPanel);
		
		log.leave();
	}
	
	
	
	


	private void renderModulesQuestion() {
		log.enter("renderModulesQuestion");
	
		modulesQuestionPanel = new FlowPanel();
		modulesQuestionPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		modulesQuestionPanel.addStyleName(Styles.MODULESQUESTION);
		
		Label modulesLabel = new Label(text(Resources.QUESTIONLABELMODULES));
		modulesQuestionPanel.add(modulesLabel);
	
		moduleCheckBoxes = new HashSet<CheckBox>();
		List<Widget[]> rows = new ArrayList<Widget[]>();
		Map<String, String> modules = Configuration.getModules();

		for (String moduleId : modules.keySet()) {

			CheckBox checkBox = new CheckBox();
			checkBox.setFormValue(moduleId);
			moduleCheckBoxes.add(checkBox);

			String moduleLabelText = modules.get(moduleId);
			Label moduleLabel = new InlineLabel(moduleLabelText);
			
			Widget[] row = { moduleLabel, checkBox };
			rows.add(row);
		}
		
		FlexTable modulesInputTable = RenderUtils.renderResultsTable(rows);
		modulesQuestionPanel.add(modulesInputTable);
	
		log.leave();
	}
	
	
	
	
	private void renderStudiesQuestion() {
		log.enter("renderStudiesQuestion");

		this.studiesQuestionPanel = new FlowPanel();
		this.studiesQuestionPanel.addStyleName(CommonStyles.COMMON_QUESTION);
		this.studiesQuestionPanel.addStyleName(Styles.STUDIESQUESTION);

		this.studiesQuestionPanel.add(new HTML("<p>"+text(Resources.QUESTIONLABELSTUDIES)+"</p>"));
		
		this.selectStudiesPanel = new FlowPanel();
		this.studiesQuestionPanel.add(this.selectStudiesPanel);
		
		this.selectStudies = new ArrayList<SelectStudy>();

		SelectStudy prototype = new SelectStudy();
		this.selectStudies.add(prototype);

		this.refreshStudies();

		log.leave();
	}
	
	
	
	
	
	public void refreshStudies() {

		this.studyLinksDeferred = ChassisUtils.getMapOfStudyLinksToTitlesForCurrentUser();
		
		this.studyLinksDeferred.addCallback(new Function<Map<String,String>, Map<String,String>>() {

			public Map<String, String> apply(Map<String, String> in) {
				studyLinks = in;
				renderSelectStudies();
				return in;
			}
			
		});
		
		// TODO errback?

	}
	
		
		
		
	/**
	 * N.B. this is deferred!
	 */
	private void renderSelectStudies() {
		
		this.selectStudiesPanel.clear();
		for (SelectStudy s : this.selectStudies) {
			s.render();
			this.selectStudiesPanel.add(s);
			HandlerRegistration r = s.addListBoxClickHandler(new StudiesClickHandler());
			this.childWidgetEventHandlerRegistrations.add(r);
		}
	
	}

	
	
	
	// TODO redesign this for simplicity, and also figure out when list of studies needs to get refreshed!
	
	private class SelectStudy extends Composite {
		
		private Panel canvas = new FlowPanel();
		private ListBox listBox;
		
		private SelectStudy() {
			this.initWidget(this.canvas);
			this.canvas.addStyleName("selectStudy"); // TODO fix CSS
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
		
		private HandlerRegistration addListBoxClickHandler(ClickHandler h) {
			return listBox.addClickHandler(h);
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
			listBox.addItem("(please select one)", null);
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
	
	

	
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");

		super.registerHandlersForChildWidgetEvents();
		
		for (CheckBox b : this.moduleCheckBoxes) {
			HandlerRegistration r = b.addValueChangeHandler(new ModulesChangeHandler());
			this.childWidgetEventHandlerRegistrations.add(r);
		}
		
		log.leave();
	}
	
	
	
	
	private class ModulesChangeHandler implements ValueChangeHandler<Boolean> {
	
		public void onValueChange(ValueChangeEvent<Boolean> event) {
			
			CheckBox source = (CheckBox) event.getSource();
			
			if (event.getValue()) {
				model.getSubmission().addModule(source.getFormValue());
			}
			else {
				model.getSubmission().removeModule(source.getFormValue());
			}
			
		}
			
	}




	private class StudiesClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
	
			List<String> links = new ArrayList<String>();
			for (SelectStudy s : selectStudies) {
				String value = s.getSelectedValue();
				if (value != null) links.add(value);
			}
			
			model.setStudyLinks(links);
			
		}
	
	}
	
	
	
	

	@Override
	public void syncUI() {
		log.enter("syncUI");
		
		super.syncUI();
		
		if (this.model != null) {

			this.updateModules();
			this.updateStudies();
			
		}
		else {

			// TODO could this method legitimately be called when model is null,
			// or should we throw an error here if model is null?
			log.warn("model is null, not updating anything");

		}

		log.leave();
	}

	
	
	/**
	 * 
	 */
	private void updateModules() {
		log.enter("updateModules");
		
		for (CheckBox b : this.moduleCheckBoxes) {
			if (this.model.getSubmission().getModules().contains(b.getFormValue())) {
				b.setValue(true, false);
			}
		}

		log.leave();
	}




	/**
	 * 
	 */
	private void updateStudies() {
		log.enter("updateStudies");
		
		List<AtomLink> links = this.model.getStudyLinks();
		for (int i=1; i<links.size(); i++) {
			if (i > 0) {
				SelectStudy s = new SelectStudy();
				this.selectStudies.add(s);
			}
			String link = links.get(i).getHref(); // TODO fix for absolute uris
			selectStudies.get(i).setSelectedValue(link);
		}

		log.leave();
	}


	

}
