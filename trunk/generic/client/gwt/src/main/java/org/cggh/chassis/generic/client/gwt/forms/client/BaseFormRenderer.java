/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.forms.client;

import org.cggh.chassis.generic.atom.rewrite.client.AtomEntry;
import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.forms.client.BaseForm.Resources;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * @author aliman
 *
 */
public abstract class BaseFormRenderer<E extends AtomEntry> 
	extends ChassisWidgetRenderer<E> {

	
	
	private Log log = LogFactory.getLog(BaseFormRenderer.class);
	protected Resources resources;
	protected TextBoxBase titleInput, summaryInput;
	protected FlowPanel titleQuestionPanel, summaryQuestionPanel;
	protected E model;

	
	
	
	public BaseFormRenderer() {
		this.resources = this.createResources();
	}
	
	
	
	
	/**
	 * 
	 */
	public void renderUI() {
		log.enter("renderUI");
		
		this.canvas.clear();
		
		this.canvas.add(new HTML("<h3>"+resources.get(Resources.HEADINGTITLEANDSUMMARY)+"</h3>"));
		this.renderTitleQuestion();
		this.canvas.add(this.titleQuestionPanel);
		this.renderSummaryQuestion();
		this.canvas.add(this.summaryQuestionPanel);			
		
		log.leave();
		
	}


	
	

	/**
	 * 
	 */
	protected void renderTitleQuestion() {
		log.enter("renderTitleQuestion");

		this.titleQuestionPanel = new FlowPanel();
		this.titleInput = new TextBox();

		InlineLabel titleLabel = new InlineLabel(text(Resources.QUESTIONLABELTITLE)); 
		this.titleQuestionPanel.add(titleLabel);
		this.titleQuestionPanel.add(titleInput);
		this.titleQuestionPanel.addStyleName(CSS.COMMON_QUESTION);

		log.leave();
	}





	/**
	 * 
	 */
	protected void renderSummaryQuestion() {
		log.enter("renderSummaryQuestion");

		this.summaryQuestionPanel = new FlowPanel();
		this.summaryInput = new TextArea();
		
		Label summaryLabel = new Label(text(Resources.QUESTIONLABELSUMMARY));
		summaryQuestionPanel.add(summaryLabel);
		summaryQuestionPanel.add(summaryInput);
		summaryQuestionPanel.addStyleName(CSS.COMMON_QUESTION);
		
		log.leave();		
	}

	


	/**
	 * 
	 */
	public void bindUI(E model) {
		log.enter("bindUI");
		
		log.debug("unbind to clear anything");
		this.unbindUI();
		
		log.debug("keep reference to model");
		this.model = model;
		
		log.debug("register this as handler for model property change events");
		this.registerHandlersForModelChanges();
		
		log.debug("register handlers for child widget events");
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
		
	}




	/**
	 * 
	 */
	protected void registerHandlersForModelChanges() {
		log.enter("registerHandlersForModelChanges");
		
		// not needed
		
		log.leave();
		
	}




	/**
	 * 
	 */
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
		
		HandlerRegistration a = this.titleInput.addValueChangeHandler(new TitleInputChangeHandler());
		HandlerRegistration b = this.summaryInput.addValueChangeHandler(new SummaryInputChangeHandler());
		
		this.childWidgetEventHandlerRegistrations.add(a);
		this.childWidgetEventHandlerRegistrations.add(b);
		
		log.leave();
		
	}

	
	
	
	
	protected class TitleInputChangeHandler implements ValueChangeHandler<String> {
		
		public void onValueChange(ValueChangeEvent<String> arg0) {
			model.setTitle(arg0.getValue());
		}
		
	}

	
	
	
	protected class SummaryInputChangeHandler implements ValueChangeHandler<String> {

		public void onValueChange(ValueChangeEvent<String> arg0) {
			model.setSummary(arg0.getValue());
		}
		
	}
	
	
	
	




	/**
	 * 
	 */
	public void syncUI() {
		log.enter("syncUI");
		
		if (this.model != null) {

			this.updateTitle();
			this.updateSummary();
			
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
	private void updateTitle() {
		log.enter("updateTitle");
		
		String title = this.model.getTitle();
		if (title == null) title = "";
		this.titleInput.setText(title);
		
		log.leave();
		
	}




	/**
	 * 
	 */
	private void updateSummary() {
		log.enter("updateSummary");
		
		String summary = this.model.getSummary();
		if (summary == null) summary = "";
		this.summaryInput.setText(summary);
		
		log.leave();
	}




	protected abstract Resources createResources();
	
	
	
	
	protected String text(String key) {
		return this.resources.get(key);
	}
	
	
	

}
