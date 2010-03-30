package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;

import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeEvent;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.StatusChangeHandler;
import org.cggh.chassis.generic.widget.client.ChassisWidgetRenderer;
import org.cggh.chassis.generic.widget.client.PropertyChangeEvent;
import org.cggh.chassis.generic.widget.client.PropertyChangeHandler;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 * @author timp
 */
public class ViewQuestionnaireWidgetRenderer extends
		ChassisWidgetRenderer<ViewQuestionnaireWidgetModel> {

	private Log log = LogFactory.getLog(ViewQuestionnaireWidgetRenderer.class);
	

	@UiTemplate("ViewQuestionnaireWidget.ui.xml")
	interface ViewQuestionnaireWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, ViewQuestionnaireWidgetRenderer> {
	}
	private static ViewQuestionnaireWidgetRendererUiBinder uiBinder = 
		GWT.create(ViewQuestionnaireWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel viewQuestionnairePanel;
	



	private ViewQuestionnaireWidget owner;
	
	public ViewQuestionnaireWidgetRenderer(ViewQuestionnaireWidget owner) {
		this.owner = owner;
	}



	@Override
	protected void registerHandlersForModelChanges() {

	
		this.modelChangeHandlerRegistrations.add(
				model.message.addChangeHandler(new PropertyChangeHandler<String>() {
			public void onChange(PropertyChangeEvent<String> e) {
				log.enter("onChange<String>");		
				syncUIWithMessage(e.getAfter());
				log.leave();
			}
		}));

	
		this.modelChangeHandlerRegistrations.add(
				model.studyEntry.addChangeHandler(new PropertyChangeHandler<Element>() {
			public void onChange(PropertyChangeEvent<Element> e) {
				log.enter("onchange(studyEntry)");
				syncUIWithStudyEntry(e.getAfter());
				log.leave();
			}
		}));


	}
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {
		log.enter("registerHandlersForChildWidgetEvents");
				
		log.leave();
	}



	@Override
	protected void renderUI() {

		this.canvas.clear();
		this.canvas.add(uiBinder.createAndBindUi(this));
		
	}
	

	@Override
	protected void bindUI(ViewQuestionnaireWidgetModel model) {
		super.bindUI(model);
		this.errorPanel.setVisible(false);	
		this.contentPanel.setVisible(true);
	}


	@Override
	protected void syncUI() {
	
		syncUIWithStudyEntry(model.studyEntry.get());


	}
	
	protected void syncUIWithStudyEntry(Element studyEntry) {
		log.enter("syncUIWithStudyEntry");
		// TODO make abstract 
		if (studyEntry == null) 
			log.debug("studyEntry null");
		else {
			log.debug("studyEntry :"+studyEntry);

			this.viewQuestionnairePanel.clear();
			this.viewQuestionnairePanel.add(renderStudyEntry(studyEntry));		}
		
		log.leave();
	}

	
	private FlexTable renderStudyEntry(Element studyEntry) {
		log.enter("renderStudyEntry");
		List<List<Widget>> rows = new ArrayList<List<Widget>>();
		
		ArrayList<Widget> headerRow = new ArrayList<Widget>();
		headerRow.add(strongWidget("Title"));		// i18n
		headerRow.add(strongWidget("Authors"));  	// i18n
		rows.add(headerRow);

		Element entry = studyEntry;

		ArrayList<Widget> row = new ArrayList<Widget>();
		
		row.add(new HTML(ChassisHelper.getTitle(entry)));
		row.add(new HTML(RenderUtils.join(ChassisHelper.getAuthorEmails(entry), ", ")));

		rows.add(row);
		FlexTable t = RenderUtils.renderFirstRowHeadingResultsAsFirstColumnHeadingTable(rows);
		

		log.leave();
		return t;
	}
		



	protected void syncUIWithMessage(String message) {
		log.enter("syncUIWithMessage");

		if (message != null) {
			log.debug("Setting message to :" + message + ":");
			contentPanel.setVisible(false);
			errorMessage.clear();
			errorMessage.add(new HTML(message));
			errorPanel.setVisible(true);
		}

		log.leave();
	}



}

