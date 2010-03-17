package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 */
public class UploadCuratedDataFilesWidgetRenderer extends
		ChassisWidgetRenderer<UploadCuratedDataFilesWidgetModel> {

	private Log log = LogFactory.getLog(UploadCuratedDataFilesWidgetRenderer.class);
	
	@UiTemplate("UploadCuratedDataFilesWidget.ui.xml")
	interface UploadCuratedDataFilesWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, UploadCuratedDataFilesWidgetRenderer> {
	}
	private static UploadCuratedDataFilesWidgetRendererUiBinder uiBinder = 
		GWT.create(UploadCuratedDataFilesWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField FlowPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	private UploadCuratedDataFilesWidget owner;
  public UploadCuratedDataFilesWidgetRenderer(UploadCuratedDataFilesWidget owner) {
		this.owner = owner;
	}


	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
	}
	
	@Override
	protected void registerHandlersForModelChanges() {
		
	}

	@Override
	protected void syncUI() {
	}

	protected void syncUIWithStatus(Status status) {

		log.enter("syncUIWithStatus");		
		
		log.leave();
	}
	
	/** Clear and re-initialise, setting selected id. 
	 * @param studyFeedDoc */
	void syncUiWithStudyFeedDoc(Document studyFeedDoc) {
		
		log.enter("syncUiWithStudyFeedDoc");
		
		// Turn everything off (that is made visible/enabled here) first, then show/enable as required.
		
		log.leave();
	}
	
	
	public void error(String err) {

		errorMessage.clear();
		errorMessage.add(new HTML(err));
	}
	
	
}

