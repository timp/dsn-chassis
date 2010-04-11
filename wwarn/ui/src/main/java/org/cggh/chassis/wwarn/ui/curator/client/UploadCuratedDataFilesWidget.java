package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;


import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 *
 * @author timp
 *
 */
public class UploadCuratedDataFilesWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(UploadCuratedDataFilesWidget.class);


	@UiTemplate("UploadCuratedDataFilesWidget.ui.xml")
	interface UploadCuratedDataFilesWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, UploadCuratedDataFilesWidget> {
	}
	private static UploadCuratedDataFilesWidgetRendererUiBinder uiBinder = 
		GWT.create(UploadCuratedDataFilesWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;

	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel uploadCuratedDataFilesPanel;
	
	public final ObservableProperty<Element> studyEntry = new ObservableProperty<Element>();


	public final ObservableProperty<String> message = new ObservableProperty<String>();




	
	@Override
	public void refresh() {
		log.enter("refresh");
		syncUIWithStudyEntry(studyEntry.get());
		this.setVisible(true);
		log.leave();	
	}
	

	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		this.clear();
		this.add(uiBinder.createAndBindUi(this));

		log.leave();
	}


	@Override
	protected void bindUI() {
		super.bindUI();


		// Model changes


		message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
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
		errorPanel.setVisible(false);	
		contentPanel.setVisible(true);
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
		
	protected void syncUIWithStudyEntry(Element studyEntry) {
		log.enter("syncUIWithStudyEntry");
		// TODO needs to be a method in an extension
		this.uploadCuratedDataFilesPanel.clear();
		this.uploadCuratedDataFilesPanel.add(strongWidget("syncUIWithStudyEntry"));
		log.leave();
	}

}
