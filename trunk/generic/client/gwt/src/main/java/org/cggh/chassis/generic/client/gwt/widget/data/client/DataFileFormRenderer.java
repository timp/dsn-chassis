/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client;

import org.cggh.chassis.generic.atom.rewrite.client.datafile.DataFileEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.widget.data.client.DataFileForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author aliman
 *
 */
public class DataFileFormRenderer {

	
	
	
	private Log log = LogFactory.getLog(DataFileFormRenderer.class);
	private Panel canvas;
	private DataFileEntry model;
	private TextBoxBase titleInput = new TextBox();
	private TextBoxBase summaryInput = new TextArea();
	private FlowPanel titleQuestionPanel;
	private FlowPanel summaryQuestionPanel;


	
	
	
	/**
	 * @param canvas
	 * @param model
	 */
	public DataFileFormRenderer(Panel canvas, DataFileEntry model) {
		this.canvas = canvas;
		this.model = model;
	}





	/**
	 * @param model
	 */
	public void bind(DataFileEntry model) {
		this.model = model;
	}





	/**
	 * 
	 */
	public void render() {
		log.enter("render");
		
		this.canvas.clear();

		
		this.canvas.add(new HTML("<h3>"+Resources.get(Resources.HEADINGTITLEANDSUMMARY)+"</h3>"));
		this.initTitleQuestion();
		this.canvas.add(this.titleQuestionPanel);
		this.initSummaryQuestion();
		this.canvas.add(this.summaryQuestionPanel);			

		log.leave();
		
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
		if (title == null) title = "";
		this.titleInput.setText(title);
		
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
		if (summary == null) summary = "";
		this.summaryInput.setText(summary);
		
		summaryInput.addValueChangeHandler(new SummaryChangeHandler());

		log.leave();		
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
	
	
	
	
}
