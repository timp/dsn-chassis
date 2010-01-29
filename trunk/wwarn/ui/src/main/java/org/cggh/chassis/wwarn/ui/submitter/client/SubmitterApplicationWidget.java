package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;
import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;

public class SubmitterApplicationWidget extends MultiWidget {

	
	private Log log = LogFactory.getLog(SubmitterApplicationWidget.class);
	
	private SubmitterHomeWidget submitterHomeWidget;
	private SelectStudyWidget selectStudyWidget;
	private UploadFilesWidget uploadFilesWidget;
	private SubmitWidget submitWidget;
	private AddInformationWidget addInformationWidget;

	
	
	
	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");
		
		this.submitterHomeWidget = new SubmitterHomeWidget();
		this.mainChildren.add(this.submitterHomeWidget);

		this.selectStudyWidget = new SelectStudyWidget();
		this.selectStudyWidget.refresh();
		this.mainChildren.add(this.selectStudyWidget);
		
		this.uploadFilesWidget = new UploadFilesWidget();
		this.mainChildren.add(this.uploadFilesWidget);
		
		this.submitWidget = new SubmitWidget();
		this.mainChildren.add(this.submitWidget);
		
		this.addInformationWidget = new AddInformationWidget();
		this.mainChildren.add(this.addInformationWidget);
		
		this.defaultChild = this.submitterHomeWidget;
		this.submitterHomeWidget.refresh();

		log.leave();

	}

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = submitterHomeWidget.submitDataNavigationEventChannel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				selectStudyWidget.setSelectedStudy(null);
				selectStudyWidget.refresh();
				setActiveChild(selectStudyWidget);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(a);
		
		
		HandlerRegistration b = selectStudyWidget.proceed.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				String selectedStudyId = selectStudyWidget.getSelectedStudyId();
				uploadFilesWidget.setSelectedStudy(selectedStudyId); 
				uploadFilesWidget.refresh();
				setActiveChild(uploadFilesWidget);				
			}
		});
		this.childWidgetEventHandlerRegistrations.add(b);
		
		HandlerRegistration b1 = selectStudyWidget.cancel.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				uploadFilesWidget.setSelectedStudy(null); 
				setActiveChild(submitterHomeWidget);				
			}
		});
		this.childWidgetEventHandlerRegistrations.add(b1);
		
		
		
		HandlerRegistration c = uploadFilesWidget.proceed.addHandler(new WidgetEventHandler() {
			public void onEvent(WidgetEvent e) {
				String selectedStudyId = uploadFilesWidget.getSelectedStudyId();
				submitWidget.setSelectedStudy(selectedStudyId);
				submitWidget.refresh();
				setActiveChild(submitWidget);				
			}
		});
		this.childWidgetEventHandlerRegistrations.add(c);
		
		
		HandlerRegistration c1 = this.uploadFilesWidget.addStepBackNavigationHandler(new StepBackNavigationHandler() {
			public void onNavigation(StepBackNavigationEvent e) {
				selectStudyWidget.refresh();
				setActiveChild(selectStudyWidget);
			}
		});
		this.childWidgetEventHandlerRegistrations.add(c1);


		
		HandlerRegistration cError = this.uploadFilesWidget.addErrorHandler(new ErrorHandler(){

			public void onError(ErrorEvent e) {
				uploadFilesWidget.getModel().setErrorMessage(e.getMessage());
				uploadFilesWidget.getModel().setStatus(UploadFilesWidgetModel.STATUS_ERROR);
				Window.alert(e.getMessage());
			}});
		
		this.childWidgetEventHandlerRegistrations.add(cError);
		
		
		
		HandlerRegistration d = this.submitWidget.addProceedActionHandler(new ProceedActionHandler() {
			
			public void onAction(ProceedActionEvent e) {
				String submissionId = submitWidget.getSubmissionId();
				addInformationWidget.setSubmission(submissionId);
				addInformationWidget.refresh();
				setActiveChild(addInformationWidget);
			}
		});
		
		this.childWidgetEventHandlerRegistrations.add(d);

		HandlerRegistration d1 = this.submitWidget.addStepBackNavigationHandler(new StepBackNavigationHandler() {
			
			public void onNavigation(StepBackNavigationEvent e) {
				String selectedStudyId = submitWidget.getSelectedStudyId();
				uploadFilesWidget.setSelectedStudy(selectedStudyId);
				uploadFilesWidget.refresh();
				setActiveChild(uploadFilesWidget);
			}

		});

		this.childWidgetEventHandlerRegistrations.add(d1);

		HandlerRegistration d2 = this.submitWidget.addBackToStartNavigationHandler(new BackToStartNavigationHandler() {
			
			public void onNavigation(BackToStartNavigationEvent e) {
				String selectedStudyId = submitWidget.getSelectedStudyId();
				selectStudyWidget.setSelectedStudy(selectedStudyId);
				selectStudyWidget.refresh();
				setActiveChild(selectStudyWidget);
			}

		});

		HandlerRegistration dError = this.submitWidget.addErrorHandler(new ErrorHandler(){

			public void onError(ErrorEvent e) {
				submitWidget.getModel().setErrorMessage(e.getMessage());
				submitWidget.getModel().setStatus(UploadFilesWidgetModel.STATUS_ERROR);
				Window.alert(e.getMessage());
			}});
		
		this.childWidgetEventHandlerRegistrations.add(dError);

		this.childWidgetEventHandlerRegistrations.add(d2);

		HandlerRegistration e = this.addInformationWidget.addHomeNavigationEventHandler(new HomeNavigationHandler() {
			
			public void onNavigation(HomeNavigationEvent e) {
				
				submitterHomeWidget.refresh();
				setActiveChild(submitterHomeWidget);
			}
			
		});

		this.childWidgetEventHandlerRegistrations.add(e);

	}
	
	
	
	
}
