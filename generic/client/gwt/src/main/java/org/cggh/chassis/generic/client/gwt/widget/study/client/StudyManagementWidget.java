/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomui.client.CreateSuccessEvent;
import org.cggh.chassis.generic.atomui.client.CreateSuccessHandler;
import org.cggh.chassis.generic.atomui.client.UpdateSuccessEvent;
import org.cggh.chassis.generic.atomui.client.UpdateSuccessHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.study.client.sq.ViewStudyQuestionnaireWidget;
import org.cggh.chassis.generic.client.gwt.widget.study.client.sq.EditStudyQuestionnaireWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.CancelHandler;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class StudyManagementWidget 
	extends MultiWidget {

	
	
	
	
	// utility fields
	private Log log = LogFactory.getLog(StudyManagementWidget.class);
	
	
	
	
	// UI fields
	private NewStudyWidget newStudyWidget; 
	private ViewStudyWidget viewStudyWidget;
	private MyStudiesWidget myStudiesWidget;
	private EditStudyWidget editStudyWidget;
	private ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;
	private EditStudyQuestionnaireWidget editStudyQuestionnaireWidget;

	
	
	
	public StudyManagementWidget() {
		super(false, true);
	}
	
	
	
	

	@Override
	public void renderMainChildren() {
		log.enter("renderMainChildren");
		
		// create child widgets
		
		this.viewStudyWidget = new ViewStudyWidget();
		this.newStudyWidget = new NewStudyWidget();
		this.myStudiesWidget = new MyStudiesWidget();
		this.editStudyWidget = new EditStudyWidget();
		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		
		this.mainChildren.add(this.viewStudyWidget);
		this.mainChildren.add(this.newStudyWidget);
		this.mainChildren.add(this.myStudiesWidget);
		this.mainChildren.add(this.editStudyWidget);
		this.mainChildren.add(this.viewStudyQuestionnaireWidget);
		this.mainChildren.add(this.editStudyQuestionnaireWidget);
		
		log.leave();
	}

	
	
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		this.myStudiesWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
			
				// N.B. the order of these two calls must be as follows, otherwise history is broken
				viewStudyWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewStudyWidget);

			}

		});
		
		this.viewStudyWidget.addEditStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				
				editStudyWidget.editEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(editStudyWidget);
				
			}

		});
		
		this.viewStudyWidget.addViewStudyQuestionnaireActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				
				viewStudyQuestionnaireWidget.viewEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(viewStudyQuestionnaireWidget);
				
			}

		});
		
		this.viewStudyWidget.addEditStudyQuestionnaireActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				
				editStudyQuestionnaireWidget.editEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(editStudyQuestionnaireWidget);
				
			}
			
		});
		
		this.viewStudyWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				// just bubble
				fireEvent(e);
			}
			
		});
		
		this.newStudyWidget.addCancelHandler(new CancelHandler() {
			
			public void onCancel(CancelEvent e) {
				History.back();
			}
		});
		
		this.newStudyWidget.addCreateSuccessHandler(new CreateSuccessHandler<StudyEntry>() {
			
			public void onCreateSuccess(CreateSuccessEvent<StudyEntry> e) {
				viewStudyWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewStudyWidget);
			}
		});

		this.editStudyWidget.addCancelHandler(new CancelHandler() {
			
			public void onCancel(CancelEvent e) {
				History.back();
			}
		});
		
		this.editStudyWidget.addUpdateSuccessHandler(new UpdateSuccessHandler<StudyEntry>() {
			
			public void onUpdateSuccess(UpdateSuccessEvent<StudyEntry> e) {
				viewStudyWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewStudyWidget);
			}
		});

		this.viewStudyQuestionnaireWidget.addEditStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				editStudyWidget.editEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(editStudyWidget);
			}

		});
		
		this.viewStudyQuestionnaireWidget.addViewStudyActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
			
				// N.B. the order of these two calls must be as follows, otherwise history is broken
				viewStudyWidget.viewEntry(e.getEntry().getId());
				setActiveChild(viewStudyWidget);

			}

		});
				
		this.viewStudyQuestionnaireWidget.addEditStudyQuestionnaireActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				editStudyQuestionnaireWidget.editEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(editStudyQuestionnaireWidget);
			}

		});

		this.editStudyQuestionnaireWidget.addUpdateSuccessHandler(new UpdateSuccessHandler<StudyEntry>() {
			
			public void onUpdateSuccess(UpdateSuccessEvent<StudyEntry> e) {
				viewStudyQuestionnaireWidget.viewEntry(e.getEntry().getEditLink().getHref());
				setActiveChild(viewStudyQuestionnaireWidget);
			}
			
		});
		
		this.editStudyQuestionnaireWidget.addCancelHandler(new CancelHandler() {
			
			public void onCancel(CancelEvent e) {
				History.back();
			}
			
		});
		
	}

	
	
	
	
	@Override
	public void renderMenuBar() {
		log.enter("renderMenuBar");
		
		log.debug("construct new study menu item");

		Command newStudyCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");

				newStudyWidget.reset();
				setActiveChild(newStudyWidget);
				fireEvent(new MenuEvent());
				
				log.leave();
			} 
		};

		MenuItem newStudyMenuItem = new MenuItem("new study", newStudyCommand );
		this.menu.addItem(newStudyMenuItem);
		
		log.debug("construct my studies menu item");
		
		Command viewStudiesCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");

				myStudiesWidget.refreshStudies();
				setActiveChild(myStudiesWidget);
				fireEvent(new MenuEvent());
				
				log.leave();
			} 
		};

		MenuItem viewStudiesMenuItem = new MenuItem("my studies", viewStudiesCommand );
		this.menu.addItem(viewStudiesMenuItem);		
		
		log.leave();
	}




	
	@Override
	protected void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		super.setActiveChild(child, memorise);
		
		if (child == myStudiesWidget) {
			myStudiesWidget.refreshStudies();
		}

		log.leave();
	}





	public HandlerRegistration addViewDatasetActionHandler(DatasetActionHandler h) {
		return this.addHandler(h, ViewDatasetActionEvent.TYPE);
	}





	/**
	 * @param id
	 */
	public void viewStudy(String id) {
		log.enter("viewStudy");

		this.viewStudyWidget.viewEntry(id);
		this.setActiveChild(this.viewStudyWidget);
		
		log.leave();
	}


	
	
}
