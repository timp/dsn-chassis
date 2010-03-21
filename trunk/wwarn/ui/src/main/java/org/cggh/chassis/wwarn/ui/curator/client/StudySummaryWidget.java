/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.ObservableProperty;

import org.cggh.chassis.generic.widget.client.WidgetEventChannel;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.AsyncRequestPendingStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.ReadyStatus;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel.Status;
import org.cggh.chassis.wwarn.ui.curator.client.StudySummaryWidgetModel.ReadyForInteractionStatus;
import org.cggh.chassis.wwarn.ui.curator.client.StudySummaryWidgetModel.RetrieveQuestionnairePendingStatus;
import org.cggh.chassis.wwarn.ui.curator.client.StudySummaryWidgetModel.RetrieveStudyPendingStatus;
import org.cggh.chassis.wwarn.ui.curator.client.StudySummaryWidgetModel.SaveStudyPendingStatus;
import org.cggh.chassis.wwarn.ui.curator.client.StudySummaryWidgetRenderer.StudySummaryWidgetRendererUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
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
			UiBinder<HTMLPanel, StudySummaryWidgetRenderer> {
	}
	private static StudySummaryWidgetRendererUiBinder uiBinder = 
		GWT.create(StudySummaryWidgetRendererUiBinder.class);

	@UiField HTMLPanel mainPanel;
	@UiField HTMLPanel contentPanel;
	@UiField HTMLPanel pendingPanel;
	@UiField HTMLPanel errorPanel;
	@UiField FlowPanel errorMessage;



	
	
	public final ObservableProperty<String> studyUrl = new ObservableProperty<String>();
	public final ObservableProperty<Element> studyEntryElement = new ObservableProperty<Element>();
	public final ObservableProperty<Status> status = new ObservableProperty<Status>();
	public final ObservableProperty<String> message = new ObservableProperty<String>();




	
	public Status getStatus() {
		if (status.get() == null)
			status.set(AsyncWidgetModel.STATUS_INITIAL);
		return status.get();
	}	



	public StudyActionsWidget studyActionsWidget;


	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
	}
	
	
	public void setStudyEntry(Element studyEntry) { 
		this.studyEntry = studyEntry;
	}
	
	
	@Override
	public void render() {
		super.render();
		if (studyEntry == null) 
			return;
		
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
