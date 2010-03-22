/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.xml.client.Element;


/**
 * @author timp
 *
 */
public class StudySummaryWidget 
	 	extends ChassisWidget {


	private static final Log log = LogFactory.getLog(StudySummaryWidget.class);
	
	private Element studyEntry;

	@UiTemplate("StudySummaryWidget.ui.xml")
	interface StudySummaryWidgetRendererUiBinder extends
			UiBinder<HTMLPanel, StudySummaryWidget> {
	}
	private static StudySummaryWidgetRendererUiBinder uiBinder = 
		GWT.create(StudySummaryWidgetRendererUiBinder.class);

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


	private StudyActionsWidget studyActionsWidget;


	
	public StudySummaryWidget() {
		super();
		this.studyActionsWidget = new StudyActionsWidget();
	}





	public Status getStatus() {
		if (status.get() == null)
			status.set(AsyncWidgetModel.STATUS_INITIAL);
		return status.get();
	}	





	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
	}
	
	
	public void setStudyEntry(Element studyEntry) { 
		this.studyEntry = studyEntry;
	}
	
	
	@Override
	protected void renderUI() {
		// TODO Auto-generated method stub
		super.renderUI();
	}


	@Override
	protected void syncUI() {
		// TODO Auto-generated method stub
		super.syncUI();
	}

	@Override
	public void refresh() {
		log.enter("refresh");
		studyActionsWidget.refresh(); 
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
