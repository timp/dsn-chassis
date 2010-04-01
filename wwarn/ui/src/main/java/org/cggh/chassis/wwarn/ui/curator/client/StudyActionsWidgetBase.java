package org.cggh.chassis.wwarn.ui.curator.client;


import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;


public abstract class StudyActionsWidgetBase extends ChassisWidget {

	protected static final Log log = LogFactory.getLog(StudyActionsWidget.class);

	@UiTemplate("StudyActionsWidget.ui.xml")
		protected
		interface StudyActionsWidgetRendererUiBinder extends
				UiBinder<HTMLPanel, StudyActionsWidgetBase> {
		}

	private static StudyActionsWidgetRendererUiBinder uiBinder = GWT.create(StudyActionsWidgetRendererUiBinder.class);
	@UiField
	HTMLPanel mainPanel;
	@UiField
	HTMLPanel contentPanel;
	@UiField
	HTMLPanel errorPanel;
	@UiField
	FlowPanel errorMessage;
	@UiField
	FlowPanel studyActionsPanel;
	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();
	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	public final WidgetEventChannel studyActionsListStudiesNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsViewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsListStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsEditStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel studyActionsUploadDataFilesWizardNavigationEventChannel = new WidgetEventChannel(this);

	public StudyActionsWidgetBase() {
		super();
	}

	@Override
	public void refresh() {
		log.enter("refresh");
		log.leave();	
	}

	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.clear();
		this.add(uiBinder.createAndBindUi(this));
		errorPanel.setVisible(false);	
		
	
		log.leave();
	}

	@Override
	protected void bindUI() {
		super.bindUI();
	
		// Much the same as registerHandlersForChildWidgetEvents in ChassisWidgetRenderer
	
	
		// Model changes
	
	
		message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		});
	
	
		studyUrl.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onchange(studyUrl)");
				syncUIWithStudyUrl(e.getAfter());
				log.leave();
			}
		});
	
	
		studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				syncUIWithStudyEntry(e.getAfter());
				log.leave();
			}
		});
	
	
	
	}

	@Override
	protected void syncUI() {
		log.enter("syncUI");
	    syncUIWithStudyEntry(studyEntry.get());
		log.leave();
	}

	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");
	
		if (message != null) {
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}
	
		log.leave();
	}

	protected void syncUIWithStudyUrl(String studyUrl) {
		log.enter("syncUIWithStudyUrl");
		// TODO needs to be a method in an extension
		log.leave();
	}

	protected void syncUIWithStudyEntry(Element studyEntry) {
		log.enter("syncUIWithStudyEntry");
	
		if (studyEntry == null) 
			log.debug("studyEntry null");
		else {
			log.debug("studyEntry :"+studyEntry);
	
	
			this.studyActionsPanel.clear();
			this.studyActionsPanel.add(renderStudyEntry(studyEntry));
	
		}
		
		log.leave();
	}

	abstract Widget renderStudyEntry(Element studyEntry);

}