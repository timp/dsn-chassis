package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.MultiWidget;
import org.cggh.chassis.generic.widget.client.WidgetEvent;
import org.cggh.chassis.generic.widget.client.WidgetEventHandler;

import com.google.gwt.event.shared.HandlerRegistration;

public class SubmitterApplicationWidget extends MultiWidget {

	
	
	
	private SubmitterHomeWidget submitterHomeWidget;
	private SelectStudyWidget selectStudyWidget;
	private UploadFilesWidget uploadFilesWidget;
	private SubmitWidget submitWidget;
	private AddInformationWidget addInformationWidget;

	
	
	
	@Override
	public void renderMainChildren() {
		
		this.submitterHomeWidget = new SubmitterHomeWidget();
		this.mainChildren.add(this.submitterHomeWidget);

		this.selectStudyWidget = new SelectStudyWidget();
		this.mainChildren.add(this.selectStudyWidget);
		
		this.uploadFilesWidget = new UploadFilesWidget();
		this.mainChildren.add(this.uploadFilesWidget);
		
		this.submitWidget = new SubmitWidget();
		this.mainChildren.add(this.submitWidget);
		
		this.addInformationWidget = new AddInformationWidget();
		this.mainChildren.add(this.addInformationWidget);
		
		this.defaultChild = this.submitterHomeWidget;
		this.submitterHomeWidget.refresh();

	}

	
	
	
	@Override
	public void registerHandlersForChildWidgetEvents() {
		super.registerHandlersForChildWidgetEvents();
		
		HandlerRegistration a = this.submitterHomeWidget.addSubmitDataNavigationHandler(new SubmitDataNavigationHandler() {
			
			public void onNavigation(SubmitDataNavigationEvent e) {
				selectStudyWidget.refresh();
				setActiveChild(selectStudyWidget);
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
		HandlerRegistration b = this.selectStudyWidget.addProceedActionHandler(new ProceedActionHandler() {
			
			public void onAction(ProceedActionEvent e) {
//				String selectedStudyId = selectStudyWidget.getSelectedStudyId();
				String selectedStudyId = "abc";
				uploadFilesWidget.setSelectedStudy(selectedStudyId); 
				uploadFilesWidget.refresh();
				setActiveChild(uploadFilesWidget);				
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(b);
		
//		HandlerRegistration c = this.uploadFilesWidget.addProceedActionHandler(new ProceedActionHandler() {
//			
//			public void onAction(ProceedActionEvent e) {
//				String selectedStudyId = uploadFilesWidget.getSelectedStudyId();
//				submitWidget.setSelectedStudy(selectedStudyId);
//				submitWidget.refresh();
//				setActiveChild(submitWidget);				
//			}
//			
//		});
		
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
				setActiveChild(selectStudyWidget);
			}

		});

		this.childWidgetEventHandlerRegistrations.add(c1);

		HandlerRegistration d = this.submitWidget.addProceedActionHandler(new ProceedActionHandler() {
			
			public void onAction(ProceedActionEvent e) {
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
