package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.widget.client.MultiWidget;

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
				setActiveChild(selectStudyWidget);
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(a);
		
		HandlerRegistration b = this.selectStudyWidget.addProceedActionHandler(new ProceedActionHandler() {
			
			public void onAction(ProceedActionEvent e) {
				uploadFilesWidget.setSelectedStudy("abc"); // TODO get this from somewhere
				uploadFilesWidget.refresh();
				setActiveChild(uploadFilesWidget);				
			}
			
		});
		
		this.childWidgetEventHandlerRegistrations.add(b);
		
		HandlerRegistration c = this.uploadFilesWidget.addProceedActionHandler(new ProceedActionHandler() {
			
			public void onAction(ProceedActionEvent e) {
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

		HandlerRegistration e = this.addInformationWidget.addHomeNavigationEventHandler(new HomeNavigationHandler() {
			
			public void onNavigation(HomeNavigationEvent e) {
				
				submitterHomeWidget.refresh();
				setActiveChild(submitterHomeWidget);
			}
			
		});

		this.childWidgetEventHandlerRegistrations.add(e);

	}
	
	
	
	
}
