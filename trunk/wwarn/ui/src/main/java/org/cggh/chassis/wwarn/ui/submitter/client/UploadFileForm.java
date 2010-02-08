package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

public class UploadFileForm extends ChassisWidget {

	private Log log = LogFactory.getLog(UploadFileForm.class);
	
	
	public static final String FIELD_FILE = "file";
	public static final String FIELD_SUMMARY = "summary";
	public static final String FIELD_FILENAME = "filename";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_OTHERTYPE = "othertype";

	
	
	@UiTemplate("UploadFileForm.ui.xml")
	interface MyUiBinder extends UiBinder<HTMLPanel, UploadFileForm> {}
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	
	
	@UiField FormPanel formPanel;
	@UiField FileUpload fileInput;
	@UiField TextArea summaryInput;
	@UiField ListBox typeInput;
	@UiField HTMLPanel otherTypePanel;
	@UiField TextBox otherTypeInput;
	
	
	
	public UploadFileForm() {
		super();
		this.add(uiBinder.createAndBindUi(this));
	}
	
	
	
	@Override
	protected void renderUI() {
				
		renderFormPanel();
		
		renderFileTypes();
		
	}



	private void renderFormPanel() {

		log.enter("renderFormPanel");
		
		formPanel.setAction(Config.get(Config.FORMHANDLER_FILEUPLOAD_URL));
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		
		fileInput.setName(FIELD_FILE);
		summaryInput.setName(FIELD_SUMMARY);
		typeInput.setName(FIELD_TYPE);
		otherTypeInput.setName(FIELD_OTHERTYPE);
		
		
		log.leave();
	}



	private void renderFileTypes() {

		typeInput.addItem("Data File - the file contains original data", Chassis.TERM_DATAFILE); // TODO i18n
		typeInput.addItem("Data Dictionary - the file contains definitions of fields/variables used in other files", Chassis.TERM_DATADICTIONARY); // TODO i18n
		typeInput.addItem("Protocol - the file contains a description of a protocol used in the study", Chassis.TERM_PROTOCOL); // TODO i18n
		typeInput.addItem("Other - please specify", Chassis.TERM_OTHER); // TODO i18n
		
	}
	
	
	
	
	@UiHandler("typeInput")
	void handleTypeInputClick(ClickEvent e) {
		syncUI();
	}
	

	
	
	@Override
	protected void syncUI() {

		log.enter("syncUI");
		
		String value = typeInput.getValue(typeInput.getSelectedIndex());
		
		if (value != null && value.equals(Chassis.TERM_OTHER)) {
			otherTypePanel.setVisible(true);
		}
		else {
			otherTypePanel.setVisible(false);
		}
		
		
		log.leave();
	}

	
	
	
	public void submit() {
		formPanel.submit();
	}
	
	
	
	public HandlerRegistration addSubmitCompleteHandler(SubmitCompleteHandler h) {
		return formPanel.addSubmitCompleteHandler(h);
	}
	
	
	
}
