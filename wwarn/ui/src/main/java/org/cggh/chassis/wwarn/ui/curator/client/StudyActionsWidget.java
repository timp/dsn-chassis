/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import static org.cggh.chassis.generic.widget.client.HtmlElements.emWidget;
import static org.cggh.chassis.generic.widget.client.HtmlElements.strongWidget;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;


import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 *
 */
public class StudyActionsWidget 
	 	extends ChassisWidget {

	private static final Log log = LogFactory.getLog(StudyActionsWidget.class);
	

	@UiTemplate("StudyActionsWidget.ui.xml")
	interface StudyActionsWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudyActionsWidget> {
	}
	private static StudyActionsWidgetRendererUiBinder uiBinder = 
		GWT.create(StudyActionsWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;

	@UiField FlowPanel tablePanel;



	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();
	public final ObservableProperty<Element> studyEntryElement = new ObservableProperty<Element>();
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();
	
	
	public final WidgetEventChannel viewStudyNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel editStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudyRevisionsNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel viewStudyQuestionnaireNavigationEventChannel = new WidgetEventChannel(this);
	public final WidgetEventChannel listStudiesNavigationEventChannel = new WidgetEventChannel(this);


	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
	}
	
	@Override
	public void refresh() {
		log.enter("refresh");
		syncUI();
		log.leave();	
	}


	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.tablePanel = new FlowPanel();
		this.add(this.tablePanel);
		

		log.leave();
	}

	@Override
	protected void syncUI() {
		log.enter("syncUI");
		renderTable();
		log.leave();
	}
	// TODO make anchors from study 
    private void renderTable() {
    	log.enter("renderTable");
		this.tablePanel = new FlowPanel();
		this.add(this.tablePanel);
		List<Widget[]> rows = new ArrayList<Widget[]>();
		Widget[] row = {
				strongWidget("Actions"),                         // TODO i18n
				emWidget("List all studies"),                    // TODO i18n
				emWidget("View study"),                          // TODO i18n
				emWidget("Upload curated data files "),          // TODO i18n
				emWidget("View study questionnaire"),            // TODO i18n
				emWidget("Edit study questionnaire"),            // TODO i18n
				emWidget("View study questionnaire history"),    // TODO i18n
			};
			
		rows.add(row);
		rows.add(row);
		
		FlexTable table = RenderUtils.renderResultsTable(rows);
		this.tablePanel.clear();
		this.tablePanel.add(table);
		log.leave();
    }

	
    	
	public void error(String err) {
		log.enter("error");
		log.debug("Error:" + err);
		pendingPanel.setVisible(false);	
		contentPanel.setVisible(false);
		errorMessage.clear();
		errorMessage.add(new HTML(err));
		errorPanel.setVisible(true);
		log.leave();
	}
	

	

}
