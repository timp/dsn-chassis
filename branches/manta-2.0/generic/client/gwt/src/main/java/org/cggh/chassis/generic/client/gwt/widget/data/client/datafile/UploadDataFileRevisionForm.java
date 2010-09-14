/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.datafile;

import org.cggh.chassis.generic.atomext.client.datafile.DataFileEntry;
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
public class UploadDataFileRevisionForm extends ChassisWidget {

	
	
	private Log log;
	
	
	
	
	// state fields (model)
	DataFileEntry entry;
	
	
	
	// UI fields (view)
	private FormPanel formPanel;
	private Hidden dataFileHidden;

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		entry = null;

		log.leave();
	}
	
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log  = LogFactory.getLog(UploadDataFileRevisionForm.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		// TODO Auto-generated method stub
		this.formPanel = new FormPanel();
		this.formPanel.setAction(Configuration.getUploadDataFileRevisionServiceUrl());
		this.formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		this.formPanel.setMethod(FormPanel.METHOD_POST);
		
		FlowPanel formContentsPanel = new FlowPanel();

		// upload input
		Panel fileQuestionPanel = RenderUtils.renderFileInputQuestion("Please select the file to upload as the new revision of the data file:", ChassisConstants.FIELD_MEDIA); // TODO i18n
		formContentsPanel.add(fileQuestionPanel);
		
		// summary
		Panel summaryQuestionPanel = RenderUtils.renderSummaryQuestion("Please provide a summary of the new revision..."); // TODO i18n
		formContentsPanel.add(summaryQuestionPanel);

		// hidden authoremail
		Hidden authorEmailHidden = RenderUtils.renderHiddenAuthorEmail();
		formContentsPanel.add(authorEmailHidden);

		// hidden datafile
		this.dataFileHidden = new Hidden();
		this.dataFileHidden.setName(ChassisConstants.FIELD_DATAFILEURL);
		formContentsPanel.add(dataFileHidden);

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

		String value = (this.entry != null) ? this.entry.getEditLink().getHref() : "";
		this.dataFileHidden.setValue(value);

		log.leave();
	}

	
	
	
	/**
	 * 
	 */
	public void submit() {
		log.enter("submit");
		
		this.formPanel.submit();
		
		log.leave();
	}
	
	

	public HandlerRegistration addSubmitCompleteHandler(SubmitCompleteHandler h) {
		return this.formPanel.addSubmitCompleteHandler(h);
	}





	/**
	 * @param entry
	 */
	public void setDataFileEntry(DataFileEntry entry) {
		log.enter("setDataFileEntry");
		
		this.entry = entry;
		this.syncUI();
		
		log.leave();
	}



}
