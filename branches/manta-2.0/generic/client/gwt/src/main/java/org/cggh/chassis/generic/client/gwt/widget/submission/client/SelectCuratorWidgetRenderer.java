/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.user.UserEntry;
import org.cggh.chassis.generic.atomext.client.user.UserFeed;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;

import static org.cggh.chassis.generic.widget.client.HtmlElements.*;


/**
 * @author aliman
 *
 */
public class SelectCuratorWidgetRenderer 
	extends AsyncWidgetRenderer<SelectCuratorWidgetModel> {

	
	
	private Log log = LogFactory.getLog(SelectCuratorWidgetRenderer.class);
	
	
	
	private SelectCuratorWidgetController controller;

	
	
	// UI fields
	private ListBox curatorsListBox;
	private Button okButton, cancelButton;
	private SubmissionPropertiesWidget submissionPropertiesWidget;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.AsyncWidgetRenderer#renderMainPanel()
	 */
	@Override
	protected void renderMainPanel() {

		this.mainPanel.add(h2Widget("Assign Submission Curator")); // TODO i18n
		
		this.submissionPropertiesWidget = new SubmissionPropertiesWidget();
		this.mainPanel.add(this.submissionPropertiesWidget);
		
		this.mainPanel.add(h3Widget("Select Curator"));
		
		this.mainPanel.add(pWidget("Please select a curator from the list below...")); // TODO i18n
		
		this.curatorsListBox = new ListBox();
		this.mainPanel.add(this.curatorsListBox);
		
		FlowPanel buttonsPanel = new FlowPanel();
		
		this.okButton = new Button("Assign Curator"); // TODO i18n
		this.cancelButton = new Button("Cancel"); // TODO i18n
		buttonsPanel.add(this.okButton);
		buttonsPanel.add(this.cancelButton);
		this.mainPanel.add(buttonsPanel);
		
	}
	
	
	
	
	@Override
	public void registerHandlersForModelChanges() {
		super.registerHandlersForModelChanges();
		
		HandlerRegistration a = this.model.addCuratorsChangeHandler(new CuratorsChangeHandler() {

			public void onChange(CuratorsChangeEvent e) {
				syncUIWithCurators(e.getAfter());
			}
			
		});
		
		this.modelChangeHandlerRegistrations.add(a);

		HandlerRegistration b = this.model.addSubmissionEntryChangeHandler(new SubmissionEntryChangeHandler() {
			public void onChange(SubmissionEntryChangeEvent e) {

				syncUIWithSubmissionEntry(e.getAfter());
				
			}
		});

		this.modelChangeHandlerRegistrations.add(b);

	}

	
	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = this.okButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				// get currently selected curator value
				String curatorEmail = curatorsListBox.getValue(curatorsListBox.getSelectedIndex());
				controller.assignCurator(curatorEmail);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
	}
	
	
	
	
	
	
	/**
	 * @param curators
	 */
	protected void syncUIWithCurators(UserFeed curators) {
		log.enter("syncUIWithCurators");
		
		curatorsListBox.clear();
		
		if (curators != null) {
			for (UserEntry curator : curators.getEntries()) {
				curatorsListBox.addItem(curator.getId(), curator.getId());
			}
		}
		
		log.leave();
	}


	
	
	/**
	 * @param entry
	 */
	protected void syncUIWithSubmissionEntry(SubmissionEntry entry) {
		log.enter("syncUIWithSubmissionEntry");

		submissionPropertiesWidget.setEntry(entry);
		
		log.leave();
	}





	

	@Override
	public void syncUI() {
		super.syncUI();
	
		this.syncUIWithSubmissionEntry(this.model.getSubmissionEntry());
		this.syncUIWithCurators(this.model.getCurators());
		
	}
	
	
	

	/**
	 * @param controller
	 */
	public void setController(SelectCuratorWidgetController controller) {
		this.controller = controller;
	}

}
