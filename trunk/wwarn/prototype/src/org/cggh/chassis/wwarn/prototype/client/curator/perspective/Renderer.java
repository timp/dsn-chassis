/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;


import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponentFactory;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveModelListener;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.PerspectiveModelReadOnly;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.SetMainWidgetCommand;
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
class Renderer implements PerspectiveModelListener {

	private PerspectiveController controller;
	private MenuBar mainMenu;
	private Logger log;
	private FractalUIComponent mainWidget = null;
	private CuratorPerspective owner;
	private PerspectiveModelReadOnly model;

	Renderer(CuratorPerspective owner, PerspectiveController controller, PerspectiveModel model) {
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
		mainMenu.addItem("home", new SetMainWidgetCommand(controller, CuratorWidgetHome.class.getName()));
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("my assigned submissions", new SetMainWidgetCommand(controller, CuratorWidgetMySubmissions.class.getName()));
		submissionMenu.addItem("all submissions", new SetMainWidgetCommand(controller, CuratorWidgetAllSubmissions.class.getName()));
		mainMenu.addItem("submissions", submissionMenu);
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new standard data dictionary", new SetMainWidgetCommand(controller, CuratorWidgetNewStandardDataDictionary.class.getName()));
		ddMenu.addItem("all standard data dictionaries", new SetMainWidgetCommand(controller, CuratorWidgetAllStandardDataDictionaries.class.getName()));
		mainMenu.addItem("data dictionaries", ddMenu);

		MenuBar rcMenu = new MenuBar(true);
		rcMenu.addItem("new release criteria specification", new SetMainWidgetCommand(controller, CuratorWidgetNewReleaseCriteria.class.getName()));
		rcMenu.addItem("all release criteria specifications", new SetMainWidgetCommand(controller, CuratorWidgetAllReleaseCriteria.class.getName()));
		mainMenu.addItem("release criteria", rcMenu);

		MenuBar tMenu = new MenuBar(true);
		tMenu.addItem("delegate new task", new SetMainWidgetCommand(controller, CuratorWidgetDelegateNewTask.class.getName()));
		tMenu.addItem("my delegated tasks", new SetMainWidgetCommand(controller, CuratorWidgetMyDelegatedTasks.class.getName()));
		mainMenu.addItem("tasks", tMenu);	
		
	}


	public void onMainWidgetChanged(String from, String to) {
		log.enter("onMainWidgetChanged");
		
		if (mainWidget != null) {
			this.owner.removeChild(mainWidget);
			mainWidget  = null;
		}
		
		mainWidget = FractalUIComponentFactory.create(to, null);

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



}
