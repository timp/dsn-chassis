/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.create.client;

import org.cggh.chassis.generic.client.gwt.common.client.CSS;
import org.cggh.chassis.generic.client.gwt.form.submission.client.SubmissionForm;
import org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author raok
 *
 */
public class CreateSubmissionWidgetDefaultRenderer implements CreateSubmissionWidgetModel.Listener {
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas = new FlowPanel();
	private CreateSubmissionWidgetController controller;
	private SubmissionForm form;
	private Button cancelCreateSubmissionUI = new Button("Cancel", new CancelCreateSubmissionUIClickHandler());
	private Button saveNewSubmissionEntryUI = new Button("Create Submission", new SaveNewSubmissionUIClickHandler());
	private FlowPanel buttonsPanel;
	private FlowPanel savingPanel;
	private FlowPanel mainPanel;
	
	
	
	/**
	 * @param owner
	 * @param controller
	 */
	public CreateSubmissionWidgetDefaultRenderer(CreateSubmissionWidgetController controller) {
		
		this.controller = controller;
		this.canvas.addStyleName(CSS.CREATESUBMISSION_BASE);
		this.render();

	}

	
	
	
	public void render() {
		log.enter("render");
		
		this.canvas.clear();

		this.savingPanel = new FlowPanel();
		this.savingPanel.setVisible(false);
		this.savingPanel.add(new InlineLabel("saving..."));
		this.canvas.add(this.savingPanel);
		
		this.mainPanel = new FlowPanel();
		this.mainPanel.setVisible(false);
		this.canvas.add(this.mainPanel);
		
		this.mainPanel.add(new HTML("<h2>New Data Submission</h2>"));

		this.mainPanel.add(new HTML("<p>Use the form below to create a new data submission.</p>"));
		
		this.form = new SubmissionForm();
		this.form.render();
		this.mainPanel.add(this.form);
		
		this.initButtonsPanel();
		this.mainPanel.add(this.buttonsPanel);
		
		log.leave();
	}



	
	/**
	 * @return
	 */
	public Widget getCanvas() {
		return this.canvas;
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.CreateSubmissionWidgetModel.Listener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {

		if (after == CreateSubmissionWidgetModel.STATUS_READY) {
			
			this.savingPanel.setVisible(false);
			this.mainPanel.setVisible(true);
			
		}
		else if (before == CreateSubmissionWidgetModel.STATUS_READY && after == CreateSubmissionWidgetModel.STATUS_CREATE_PENDING) {

			this.mainPanel.setVisible(false);
			this.savingPanel.setVisible(true);

		}
		else if (before == CreateSubmissionWidgetModel.STATUS_CREATE_PENDING && after == CreateSubmissionWidgetModel.STATUS_CREATE_SUCCESS) {

			// TODO anything?
			
		}
		else if (after == CreateSubmissionWidgetModel.STATUS_CREATE_ERROR) {

			// TODO anything?
			
		}
		else if (after == CreateSubmissionWidgetModel.STATUS_CANCELLED) {

			// TODO anything?
			
		}
		else {
			throw new Error("unexpected state transition");
		}
		
	}



	



	private void initButtonsPanel() {

		buttonsPanel = new FlowPanel();
		buttonsPanel.add(saveNewSubmissionEntryUI);
		buttonsPanel.add(cancelCreateSubmissionUI);
		buttonsPanel.addStyleName(CSS.CREATESUBMISSION_ACTIONS);

	}

	
	
	
	private class CancelCreateSubmissionUIClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelCreateSubmissionEntry();
		}
		
	}
	
	private class SaveNewSubmissionUIClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.createSubmissionEntry(form.getModel());
		}
		
	}

	/**
	 * @return
	 */
	public SubmissionForm getForm() {
		return this.form;
	}
		

	
}
