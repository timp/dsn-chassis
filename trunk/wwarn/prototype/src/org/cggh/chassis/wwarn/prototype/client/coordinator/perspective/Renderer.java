/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.coordinator.perspective;


import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModelListener;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModelReadOnly;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.SetMainWidgetCommand;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.allreqs.CoordinatorWidgetAllSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.allsubmissions.CoordinatorWidgetAllSubmissions;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.approvedreqs.CoordinatorWidgetApprovedSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.apprsubmissions.CoordinatorWidgetSubmissionsApprovedForRelease;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.cursubmissions.CoordinatorWidgetSubmissionsUnderCuration;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.deniedreqs.CoordinatorWidgetDeniedSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.home.CoordinatorWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.coordinator.widget.pendingreqs.CoordinatorWidgetPendingSubmissionRequests;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allreleasecriteria.CuratorWidgetAllReleaseCriteria;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allstdatadicts.CuratorWidgetAllStandardDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.allsubmissions.CuratorWidgetAllSubmissions;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.home.CuratorWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.mysubmissions.CuratorWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.mytasks.CuratorWidgetMyDelegatedTasks;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newreleasecriteria.CuratorWidgetNewReleaseCriteria;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newstdatadict.CuratorWidgetNewStandardDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.curator.widget.newtask.CuratorWidgetDelegateNewTask;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.perspective.GatekeeperPerspective;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.allreqs.GatekeeperWidgetAllRequests;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.approvedreqs.GatekeeperWidgetApprovedRequests;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.deniedreqs.GatekeeperWidgetDeniedRequests;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.home.GatekeeperWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.pendingreqs.GatekeeperWidgetPendingRequests;
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
import org.cggh.chassis.wwarn.prototype.client.widget.WidgetFactory;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements BasePerspectiveModelListener {

	private BasePerspectiveController controller;
	private MenuBar mainMenu;
	private Logger log;
	private FractalUIComponent mainWidget = null;
	private CoordinatorPerspective owner;
	private BasePerspectiveModelReadOnly model;

	Renderer(CoordinatorPerspective owner, BasePerspectiveController controller, BasePerspectiveModel model) {
		this.owner = owner;
		this.controller = controller;
		this.model = model;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}


	public void onIsCurrentPerspectiveChanged(boolean from, boolean to) {
		log.enter("onIsCurrentPerspectiveChanged");
		log.enter("to: "+to);
		
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
		mainMenu.addItem("home", new SetMainWidgetCommand(controller, CoordinatorWidgetHome.class.getName()));
		
		MenuBar submissionRequestMenu = new MenuBar(true);
		submissionRequestMenu.addItem("requests pending gatekeeper review", new SetMainWidgetCommand(controller, CoordinatorWidgetPendingSubmissionRequests.class.getName()));
		submissionRequestMenu.addItem("gatekeeper approved", new SetMainWidgetCommand(controller, CoordinatorWidgetApprovedSubmissionRequests.class.getName()));
		submissionRequestMenu.addItem("gatekeeper denied", new SetMainWidgetCommand(controller, CoordinatorWidgetDeniedSubmissionRequests.class.getName()));
		submissionRequestMenu.addItem("all requests", new SetMainWidgetCommand(controller, CoordinatorWidgetAllSubmissionRequests.class.getName()));
		mainMenu.addItem("submission requests", submissionRequestMenu);
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("submissions under curation", new SetMainWidgetCommand(controller, CoordinatorWidgetSubmissionsUnderCuration.class.getName()));
		submissionMenu.addItem("submissions approved for release", new SetMainWidgetCommand(controller, CoordinatorWidgetSubmissionsApprovedForRelease.class.getName()));
		submissionMenu.addItem("all submissions", new SetMainWidgetCommand(controller, CoordinatorWidgetAllSubmissions.class.getName()));
		mainMenu.addItem("submissions", submissionMenu);

	}


	public void onMainWidgetChanged(String from, String to) {
		log.enter("onMainWidgetChanged");
		
		if (mainWidget != null) {
			this.owner.removeChild(mainWidget);
			mainWidget  = null;
		}
		
		mainWidget = WidgetFactory.create(to, null);

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
