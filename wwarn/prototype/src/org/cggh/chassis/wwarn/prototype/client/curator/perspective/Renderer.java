/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;


import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allreleasecriteria.CuratorWidgetAllReleaseCriteria;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allstdatadicts.CuratorWidgetAllStandardDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allsubmissions.CuratorWidgetAllSubmissions;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.home.CuratorWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.mysubmissions.CuratorWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.mytasks.CuratorWidgetMyDelegatedTasks;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newreleasecriteria.CuratorWidgetNewReleaseCriteria;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newstdatadict.CuratorWidgetNewStandardDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newtask.CuratorWidgetDelegateNewTask;
import org.cggh.chassis.wwarn.prototype.client.submitter.perspective.SubmitterPerspective;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.alldatadicts.SubmitterWidgetAllDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.allstudies.SubmitterWidgetAllStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.home.SubmitterWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mydatadicts.SubmitterWidgetMyDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mystudies.SubmitterWidgetMyStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mysubmissions.SubmitterWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newdatadict.SubmitterWidgetNewDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy.SubmitterWidgetNewStudy;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newsubmission.SubmitterWidgetNewSubmission;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private MenuBar mainMenu;
	private Logger log;
	private FractalUIComponent mainWidget = null;
	private CuratorPerspective owner;
	private ModelReadOnly model;

	Renderer(CuratorPerspective owner, Controller controller, ModelReadOnly model) {
		this.owner = owner;
		this.controller = controller;
		this.model = model;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}


	public void onIsCurrentPerspectiveChanged(boolean from, boolean to) {
		log.enter("onIsCurrentPerspectiveChanged");
		
		if (to) {
			
			log.trace("render main menu");
			this.renderMainMenu();
			
			log.trace("update main panel content");
			this.renderMainWidget();

//			// hack for now
//			RootPanel.get("appcontent").clear();

		}
		
		log.leave();
	}

	private void renderMainMenu() {
		log.enter("renderMainMenu");
		
		log.trace("clear main menu panel");
		RootPanel root = RootPanel.get("mainmenu");
		root.clear();
		
		if (this.mainMenu == null) {
			this.createMainMenu();
		}
		
		log.trace("add main menu to panel");
		root.add(this.mainMenu);
		
		log.leave();
	}

	private void renderMainWidget() {
		log.enter("renderMainWidget");
		
		RootPanel root = RootPanel.get("appcontent");
		
		if (mainWidget != null) {
			log.trace("mainWidget set root panel and render");
			mainWidget.setRootPanel(root);
			mainWidget.render();
		}
		
		log.leave();
	}


	private void createMainMenu() {
		
		mainMenu = new MenuBar();
		mainMenu.addItem("home", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_HOME));
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("my assigned submissions", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_MYSUBMISSIONS));
		submissionMenu.addItem("all submissions", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_ALLSUBMISSIONS));
		mainMenu.addItem("submissions", submissionMenu);
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new standard data dictionary", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_NEWSTANDARDDATADICTIONARY));
		ddMenu.addItem("all standard data dictionaries", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_ALLSTANDARDDATADICTIONARIES));
		mainMenu.addItem("data dictionaries", ddMenu);

		MenuBar rcMenu = new MenuBar(true);
		rcMenu.addItem("new release criteria specification", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_NEWRELEASECRITERIA));
		rcMenu.addItem("all release criteria specifications", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_ALLRELEASECRITERIA));
		mainMenu.addItem("release criteria", rcMenu);

		MenuBar tMenu = new MenuBar(true);
		tMenu.addItem("delegate new task", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_DELEGATENEWTASK));
		tMenu.addItem("my delegated tasks", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_MYDELEGATEDTASKS));
		mainMenu.addItem("tasks", tMenu);	
		
	}


	public void onMainWidgetChanged(String from, String to) {
		log.enter("onMainWidgetChanged");
		
		if (mainWidget != null) {
			this.owner.removeChild(mainWidget);
			mainWidget  = null;
		}
		
		this.createMainWidget(to);

		if (mainWidget != null) { // TODO review this

			// hook up to owner to plug into history 
			this.owner.addChild(mainWidget);

			if (this.model.getIsCurrentPerspective()) {
				log.trace("render main widget");
				renderMainWidget();
			}
		
		}
		
		log.leave();
	}

	
	
	
	private void createMainWidget(String widgetName) {
		log.enter("createMainWidget");

		if (widgetName == null) {
			// do nothing
			log.trace("widgetName is null");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_HOME)) {
			CuratorWidgetHome widget = new CuratorWidgetHome();
			mainWidget = widget;
			log.trace("created curator main widget: home");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_MYSUBMISSIONS)) {
			CuratorWidgetMySubmissions widget = new CuratorWidgetMySubmissions();
			mainWidget = widget;
			log.trace("created curator main widget: my submissions");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_ALLSUBMISSIONS)) {
			CuratorWidgetAllSubmissions widget = new CuratorWidgetAllSubmissions();
			mainWidget = widget;
			log.trace("created curator main widget: all submissions");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_NEWSTANDARDDATADICTIONARY)) {
			CuratorWidgetNewStandardDataDictionary widget = new CuratorWidgetNewStandardDataDictionary();
			mainWidget = widget;
			log.trace("created curator main widget: new standard data dictionary");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_ALLSTANDARDDATADICTIONARIES)) {
			CuratorWidgetAllStandardDataDictionaries widget = new CuratorWidgetAllStandardDataDictionaries();
			mainWidget = widget;
			log.trace("created curator main widget: all standard data dictionaries");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_NEWRELEASECRITERIA)) {
			CuratorWidgetNewReleaseCriteria widget = new CuratorWidgetNewReleaseCriteria();
			mainWidget = widget;
			log.trace("created curator main widget: new release criteria");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_ALLRELEASECRITERIA)) {
			CuratorWidgetAllReleaseCriteria widget = new CuratorWidgetAllReleaseCriteria();
			mainWidget = widget;
			log.trace("created curator main widget: all release criteria");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_DELEGATENEWTASK)) {
			CuratorWidgetDelegateNewTask widget = new CuratorWidgetDelegateNewTask();
			mainWidget = widget;
			log.trace("created curator main widget: delegate new task");
		}
		else if (widgetName.equals(CuratorPerspective.WIDGET_MYDELEGATEDTASKS)) {
			CuratorWidgetMyDelegatedTasks widget = new CuratorWidgetMyDelegatedTasks();
			mainWidget = widget;
			log.trace("created curator main widget: my delegated tasks");
		}
		else {
			log.trace("TODO widget name: "+widgetName);
		}

		log.leave();
	}



}
