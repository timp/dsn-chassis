/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionEvent;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.DatasetActionHandler;
import org.cggh.chassis.generic.client.gwt.widget.data.client.dataset.ViewDatasetActionEvent;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
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
	private NewStudyWidget createStudyWidget; 
	private ViewStudyWidget viewStudyWidget;
	private MyStudiesWidget myStudiesWidget;
	private EditStudyWidget editStudyWidget;
	private ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;
	private EditStudyQuestionnaireWidget editStudyQuestionnaireWidget;

	
	
	
	public StudyManagementWidget() {
		super(false, true);
	}
	
	
	
	

	@Override
	protected void renderMainChildren() {
		log.enter("renderMainChildren");
		
		// create child widgets
		
		this.viewStudyWidget = new ViewStudyWidget();
		this.createStudyWidget = new NewStudyWidget();
		this.myStudiesWidget = new MyStudiesWidget();
		this.editStudyWidget = new EditStudyWidget();
		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		
		this.mainChildren.add(this.viewStudyWidget);
		this.mainChildren.add(this.createStudyWidget);
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
				
				editStudyWidget.editStudyEntry(e.getEntry());
				setActiveChild(editStudyWidget);
				
			}

		});
		
		this.viewStudyWidget.addViewStudyQuestionnaireActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				
				viewStudyQuestionnaireWidget.setEntry(e.getEntry());
				setActiveChild(viewStudyQuestionnaireWidget);
				
			}

		});
		
		this.viewStudyWidget.addEditStudyQuestionnaireActionHandler(new StudyActionHandler() {
			
			public void onAction(StudyActionEvent e) {
				
				editStudyQuestionnaireWidget.setEntry(e.getEntry());
				setActiveChild(editStudyQuestionnaireWidget);
				
			}
			
		});
		
		this.viewStudyWidget.addViewDatasetActionHandler(new DatasetActionHandler() {
			
			public void onAction(DatasetActionEvent e) {
				// just bubble
				fireEvent(e);
			}
			
		});
		
		// TODO rewrite below using gwt event pattern
		
		
		this.createStudyWidget.addListener(new NewStudyWidgetPubSubAPI() {
			
			public void onUserActionCreateStudyCancelled() {
				History.back();
			}
			
			public void onNewStudyCreated(StudyEntry studyEntry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.viewEntry(studyEntry.getId());

			}
		});
		
		this.editStudyWidget.addListener(new EditStudyWidgetPubSubAPI() {
			
			public void onUserActionEditStudyCancelled() {
				History.back();
			}				
			
			public void onStudyUpdateSuccess(StudyEntry updatedStudyEntry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.viewEntry(updatedStudyEntry.getId());

			}
		});
		
		this.viewStudyQuestionnaireWidget.addListener(new ViewStudyQuestionnaireWidget.PubSubAPI() {
			
			public void onUserActionViewStudy(StudyEntry entry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.viewEntry(entry.getId());
				
			}
			
			public void onUserActionEditStudyQuestionnaire(StudyEntry entry) {

				setActiveChild(editStudyQuestionnaireWidget);
				editStudyQuestionnaireWidget.setEntry(entry);
				
			}
		});
		
		this.editStudyQuestionnaireWidget.addListener(new EditStudyQuestionnaireWidget.PubSubAPI() {
			
			public void onUserActionEditStudyQuestionnaireCancelled() {
				History.back();
			}
			
			public void onStudyQuestionnaireUpdateSuccess(StudyEntry entry) {

				setActiveChild(viewStudyQuestionnaireWidget);
				viewStudyQuestionnaireWidget.setEntry(entry);

			}
		});
		
	}

	
	
	
	
	@Override
	protected void renderMenuBar() {
		log.enter("renderMenuBar");
		
		log.debug("construct new study menu item");

		Command newStudyCommand = new Command() { 
			public void execute() { 
				log.enter("[anon Command] :: execute");

				createStudyWidget.setUpNewStudy(ChassisUser.getCurrentUserEmail());
				setActiveChild(createStudyWidget);
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

//				viewStudiesWidget.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
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
