/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.client.gwt.common.client.CommonStyles;
import org.cggh.chassis.generic.client.gwt.forms.client.SubmissionForm;
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
public class EditSubmissionWidgetDefaultRenderer implements EditSubmissionWidgetModel.Listener {

	
	
	

	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas = new FlowPanel();
	private EditSubmissionWidgetController controller;
	private SubmissionForm form;
	private Button cancelEditSubmissionUI = new Button("Cancel", new CancelEditSubmissionUIClickHandler());
	private Button saveNewSubmissionEntryUI = new Button("Save Changes", new SaveNewSubmissionUIClickHandler());
	private FlowPanel buttonsPanel;
	private FlowPanel savingPanel;
	private FlowPanel mainPanel;
	
	
	
	/**
	 * @param owner
	 * @param controller
	 */
	public EditSubmissionWidgetDefaultRenderer(EditSubmissionWidgetController controller) {
		
		this.controller = controller;
		this.canvas.addStyleName(CommonStyles.EDITSUBMISSION_BASE);
		this.render();

	}
	
	
	
	public SubmissionForm getForm() {
		return this.form;
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
		
		this.mainPanel.add(new HTML("<h2>Edit Data Submission</h2>"));

		this.mainPanel.add(new HTML("<p>Use the form below to edit your data submission.</p>"));
		
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
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.create.client.EditSubmissionWidgetModel.Listener#onStatusChanged(int, int)
	 */
	public void onStatusChanged(int before, int after) {
		log.enter("onStatusChanged( "+before+", "+after+" )");

		if (after == EditSubmissionWidgetModel.STATUS_READY) {
			
			this.savingPanel.setVisible(false);
			this.mainPanel.setVisible(true);
			
		}
		else if (before == EditSubmissionWidgetModel.STATUS_READY && after == EditSubmissionWidgetModel.STATUS_UPDATE_PENDING) {

			this.mainPanel.setVisible(false);
			this.savingPanel.setVisible(true);

		}
		else if (before == EditSubmissionWidgetModel.STATUS_UPDATE_PENDING && after == EditSubmissionWidgetModel.STATUS_UPDATE_SUCCESS) {

			// TODO anything?
			
		}
		else if (after == EditSubmissionWidgetModel.STATUS_UPDATE_ERROR) {

			// TODO anything?
			
		}
		else if (after == EditSubmissionWidgetModel.STATUS_CANCELLED) {

			// TODO anything?
			
		}
		else {
			throw new Error("unexpected state transition");
		}
		
		log.leave();
		
	}



	



	private void initButtonsPanel() {

		buttonsPanel = new FlowPanel();
		buttonsPanel.add(saveNewSubmissionEntryUI);
		buttonsPanel.add(cancelEditSubmissionUI);
		buttonsPanel.addStyleName(CommonStyles.EDITSUBMISSION_ACTIONS);

	}

	
	
	
	private class CancelEditSubmissionUIClickHandler implements ClickHandler {

		public void onClick(ClickEvent arg0) {
			controller.cancelEditSubmissionEntry();
		}
		
	}
	
	
	
	
	
	private class SaveNewSubmissionUIClickHandler implements ClickHandler {
		
		public void onClick(ClickEvent arg0) {
			controller.updateSubmissionEntry(form.getModel());
		}
		
	}
	

}
