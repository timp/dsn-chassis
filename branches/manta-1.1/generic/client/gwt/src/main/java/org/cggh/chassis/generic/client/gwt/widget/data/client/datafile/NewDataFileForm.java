/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.shared.ChassisConstants;
import org.cggh.chassis.generic.client.gwt.common.client.RenderUtils;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * @author aliman
 *
 */
public class NewDataFileForm extends ChassisWidget {
	
	
	
	
	private Log log;
	private FormPanel formPanel;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		// nothing to do

		log.leave();

	}


	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewDataFileForm.class);
	}




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.clear();
		
		this.formPanel = new FormPanel();
		this.formPanel.setAction(Configuration.getNewDataFileServiceUrl());
		this.formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		this.formPanel.setMethod(FormPanel.METHOD_POST);
		
		FlowPanel formContentsPanel = new FlowPanel();
		
		// title
		Panel titleQuestionPanel = RenderUtils.renderTitleQuestion("Please provide a title for the new data file:"); // TODO i18n
		formContentsPanel.add(titleQuestionPanel);
		
		// summary
		Panel summaryQuestionPanel = RenderUtils.renderSummaryQuestion("Please provide a summary of the new data file..."); // TODO i18n
		formContentsPanel.add(summaryQuestionPanel);
		
		// upload input
		Panel fileQuestionPanel = RenderUtils.renderFileInputQuestion("Please select the file to upload as the initial revision of the data file:", ChassisConstants.FIELD_MEDIA); // TODO i18n
		formContentsPanel.add(fileQuestionPanel);
		
		// hidden authoremail
		Hidden authorEmailHidden = RenderUtils.renderHiddenAuthorEmail();
		formContentsPanel.add(authorEmailHidden);

		this.formPanel.add(formContentsPanel);
		this.add(this.formPanel);

		log.leave();
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// nothing to do

		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// nothing to do

		log.leave();
	}

	
	
	
	public void submit() {
		this.formPanel.submit();
	}
	
	
	
	
	public HandlerRegistration addSubmitCompleteHandler(SubmitCompleteHandler h) {
		return this.formPanel.addSubmitCompleteHandler(h);
	}
	
	
	
	

}
