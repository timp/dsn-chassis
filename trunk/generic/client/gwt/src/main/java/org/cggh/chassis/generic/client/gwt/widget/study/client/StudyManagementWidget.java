/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MultiWidget;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.MenuItem;

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
	private MyStudiesWidget viewStudiesWidget;
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
		this.viewStudiesWidget = new MyStudiesWidget("view");
		this.editStudyWidget = new EditStudyWidget();
		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		
		this.mainChildren.add(this.viewStudyWidget);
		this.mainChildren.add(this.createStudyWidget);
		this.mainChildren.add(this.viewStudiesWidget);
		this.mainChildren.add(this.editStudyWidget);
		this.mainChildren.add(this.viewStudyQuestionnaireWidget);
		this.mainChildren.add(this.editStudyQuestionnaireWidget);
		
		log.leave();
	}

	
	
	
	
	@Override
	protected void registerHandlersForChildWidgetEvents() {

		// TODO rewrite below using gwt event pattern
		
		this.viewStudyWidget.addViewStudyWidgetListener(new ViewStudyWidgetPubSubAPI() {
			
			public void onUserActionViewStudyQuestionnaire(StudyEntry studyEntry) {

				setActiveChild(viewStudyQuestionnaireWidget);
				viewStudyQuestionnaireWidget.setEntry(studyEntry);
				
			}
			
			public void onUserActionEditStudyQuestionnaire(StudyEntry studyEntry) {

				setActiveChild(editStudyQuestionnaireWidget);
				editStudyQuestionnaireWidget.setEntry(studyEntry);
				
			}
			
			public void onUserActionEditStudy(StudyEntry studyEntryToEdit) {
				
				setActiveChild(editStudyWidget);
				editStudyWidget.editStudyEntry(studyEntryToEdit);
				
			}
		});
		
		this.createStudyWidget.addListener(new NewStudyWidgetPubSubAPI() {
			
			public void onUserActionCreateStudyCancelled() {
				History.back();
			}
			
			public void onNewStudyCreated(StudyEntry studyEntry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.loadStudyEntry(studyEntry);

			}
		});
		
		this.editStudyWidget.addListener(new EditStudyWidgetPubSubAPI() {
			
			public void onUserActionEditStudyCancelled() {
				History.back();
			}				
			
			public void onStudyUpdateSuccess(StudyEntry updatedStudyEntry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.loadStudyEntry(updatedStudyEntry);

			}
		});
		
		this.viewStudiesWidget.addListener(new MyStudiesWidgetPubSubAPI() {
			
			public void onUserActionSelectStudy(StudyEntry studyEntry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.loadStudyEntry(studyEntry);
				
			}
		});
		
		this.viewStudyQuestionnaireWidget.addListener(new ViewStudyQuestionnaireWidget.PubSubAPI() {
			
			public void onUserActionViewStudy(StudyEntry entry) {

				setActiveChild(viewStudyWidget);
				viewStudyWidget.loadStudyEntry(entry);
				
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

				viewStudiesWidget.loadStudiesByAuthorEmail(ChassisUser.getCurrentUserEmail());
				setActiveChild(viewStudiesWidget);
				fireEvent(new MenuEvent());
				
				log.leave();
			} 
		};

		MenuItem viewStudiesMenuItem = new MenuItem("my studies", viewStudiesCommand );
		this.menu.addItem(viewStudiesMenuItem);		
		
		log.leave();
	}




	
	
}
