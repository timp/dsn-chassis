/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.CancelHandler;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;
import org.cggh.chassis.generic.widget.client.HasMenuEventHandlers;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MenuEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class NewStudyManagementWidget 
	extends ChassisWidget 
	implements HasMenuEventHandlers {

	
	
	
	
	// utility fields
	private Log log;
	
	
	
	
	// UI fields
	private NewStudyWidget createStudyWidget; 
	private ViewStudyWidget viewStudyWidget;
	private MyStudiesWidget viewStudiesWidget;
	private EditStudyWidget editStudyWidget;
	private MenuBar menuBar;
	private ViewStudyQuestionnaireWidget viewStudyQuestionnaireWidget;
	private EditStudyQuestionnaireWidget editStudyQuestionnaireWidget;

	
	
	
	// state fields
	private Widget activeChild;

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.memory = new Memory();
		
		log.leave();
	}

	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewStudyManagementWidget.class);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");
		
		// create child widgets
		this.viewStudyWidget = new ViewStudyWidget();
		this.createStudyWidget = new NewStudyWidget();
		this.viewStudiesWidget = new MyStudiesWidget("view");
		this.editStudyWidget = new EditStudyWidget();
		this.viewStudyQuestionnaireWidget = new ViewStudyQuestionnaireWidget();
		this.editStudyQuestionnaireWidget = new EditStudyQuestionnaireWidget();
		
		this.add(this.viewStudyWidget);
		this.add(this.createStudyWidget);
		this.add(this.viewStudiesWidget);
		this.add(this.editStudyWidget);
		this.add(this.viewStudyQuestionnaireWidget);
		this.add(this.editStudyQuestionnaireWidget);
		
		this.menuBar = new MenuBar(true);

		log.leave();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {

		this.bindMenuBar();
		
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

	
	
	
	
	/**
	 * 
	 */
	private void bindMenuBar() {
		log.enter("bindMenuBar");
		
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
		menuBar.addItem(newStudyMenuItem);
		
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
		menuBar.addItem(viewStudiesMenuItem);		
		
		log.leave();
	}




	
	/**
	 * @param child
	 */
	protected void setActiveChild(Widget child) {
		this.setActiveChild(child, true);
	}

	
	
	
	
	protected void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		this.activeChild = child;
		this.syncUI();
		
		if (child instanceof ChassisWidget) {
			ChassisWidget cw = (ChassisWidget) child;
			this.memory.setChild(cw.getMemory());
		}
		else {
			this.memory.setChild(null);
		}
		
		if (memorise) {
			this.memory.memorise();
		}
		
		log.leave();
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");
		
		if (this.activeChild == null) this.hideAll();
		else this.showOnly(this.activeChild);
		
		log.leave();
	}
	
	



	
	public MenuBar getMenu() {
		return this.menuBar;
	}
	
	
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class Memory extends WidgetMemory {
		private Log log = LogFactory.getLog(Memory.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			log.enter("createMnemonic");

			String mnemonic = null;
			
			if (activeChild != null && activeChild instanceof ChassisWidget) {
				mnemonic = this.createMnemonic((ChassisWidget)activeChild);
			}
			
			log.debug("mnemonic: "+mnemonic);

			log.leave();
			return mnemonic;
		}
		
		protected String createMnemonic(ChassisWidget w) {
			// turn the widget name into something slightly more aesthetically pleasing
			return w.getName().toLowerCase().replaceAll("widget", "");
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			log.enter("remember");

			Deferred<WidgetMemory> def = new Deferred<WidgetMemory>();
			
			for (Widget w : NewStudyManagementWidget.this) {
				if (w instanceof ChassisWidget) {
					if (this.createMnemonic((ChassisWidget)w).equals(mnemonic)) {
						setActiveChild(w, false);
					}
				}
			}
			
			def.callback(this); // no async action so callback immediately

			log.leave();
			return def;
		}

	}



	

	
	/**
	 * @author aliman
	 *
	 */
	public class CommonErrorHandler implements ErrorHandler {
		private Log log = LogFactory.getLog(CommonErrorHandler.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.ErrorHandler#onError(org.cggh.chassis.generic.widget.client.ErrorEvent)
		 */
		public void onError(ErrorEvent e) {
			log.enter("onError");

			Window.alert("an unexpected error has occurred");
			log.error("an unexpected error has occurred", e.getException());

			log.leave();
		}

	}
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class CommonCancelHandler implements CancelHandler {
		private Log log = LogFactory.getLog(CommonCancelHandler.class);
			
		public void onCancel(CancelEvent e) {
			log.enter("onCancel");
			
			History.back();
			
			log.leave();
		}

	}





	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.HasMenuEventHandlers#addMenuEventHandler(org.cggh.chassis.generic.widget.client.MenuEventHandler)
	 */
	public HandlerRegistration addMenuEventHandler(MenuEventHandler h) {
		return this.addHandler(h, MenuEvent.TYPE);
	}



	
	
	
	
}
