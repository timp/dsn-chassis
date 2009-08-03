/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

import org.cggh.chassis.gwt.lib.log.client.GWTLogger;
import org.cggh.chassis.gwt.lib.log.client.Logger;
import org.cggh.chassis.gwt.lib.ui.fractal.client.FractalUIComponent;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModelListener;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModelReadOnly;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.SetMainWidgetCommand;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.alldatadicts.SubmitterWidgetAllDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.allstudies.SubmitterWidgetAllStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.home.SubmitterWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mydatadicts.SubmitterWidgetMyDataDictionaries;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mystudies.SubmitterWidgetMyStudies;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.mysubmissions.SubmitterWidgetMySubmissions;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newdatadict.SubmitterWidgetNewDataDictionary;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy.SubmitterWidgetNewStudy;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newsubmission.SubmitterWidgetNewSubmission;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements BasePerspectiveModelListener {

	private BasePerspectiveController controller;
	private Logger log;
	private MenuBar mainMenu = null;
	private SubmitterPerspective owner;
	private BasePerspectiveModelReadOnly model;
	private FractalUIComponent mainWidget = null;

	Renderer(SubmitterPerspective owner, BasePerspectiveController controller, BasePerspectiveModel model) {
		this.owner = owner;
		this.controller = controller;
		this.model = model;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}

	public void onIsCurrentPerspectiveChanged(boolean wasCurrent, boolean current) {
		if (current) {
			this.renderMainMenu();
			this.renderMainWidget();
		}
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
		log.enter("createMainMenu");

		log.trace("instantiate main menu bar");
		this.mainMenu = new MenuBar();
		
		mainMenu.addItem("home", new Command() {
			public void execute() {	controller.setMainWidget(BasePerspective.WIDGET_HOME); }
		});
		
		MenuBar studyMenu = new MenuBar(true);
		studyMenu.addItem("new study", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_NEWSTUDY));
		studyMenu.addItem("my studies", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_MYSTUDIES));	
		studyMenu.addItem("all studies", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_ALLSTUDIES));
		mainMenu.addItem("studies", studyMenu);
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("new submission", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_NEWSUBMISSION));
		submissionMenu.addItem("my submissions", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_MYSUBMISSIONS));
		mainMenu.addItem("submissions", submissionMenu);
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new data dictionary", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_NEWDATADICTIONARY));
		ddMenu.addItem("my data dictionaries", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_MYDATADICTIONARIES));
		ddMenu.addItem("all data dictionaries", new SetMainWidgetCommand(controller, SubmitterPerspective.WIDGET_ALLDATADICTIONARIES));
		mainMenu.addItem("data dictionaries", ddMenu);
	
		log.leave();
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
		else if (widgetName.equals(SubmitterPerspective.WIDGET_HOME)) {
			SubmitterWidgetHome widget = new SubmitterWidgetHome();
			mainWidget = widget;
			log.trace("created submitter widget home");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_NEWSTUDY)) {
			SubmitterWidgetNewStudy widget = new SubmitterWidgetNewStudy();
			mainWidget = widget;
			log.trace("created submitter widget new study");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_MYSTUDIES)) {
			SubmitterWidgetMyStudies widget = new SubmitterWidgetMyStudies();
			mainWidget = widget;
			log.trace("created submitter widget my studies");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_ALLSTUDIES)) {
			SubmitterWidgetAllStudies widget = new SubmitterWidgetAllStudies();
			mainWidget = widget;
			log.trace("created submitter widget all studies");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_NEWSUBMISSION)) {
			SubmitterWidgetNewSubmission widget = new SubmitterWidgetNewSubmission();
			mainWidget = widget;
			log.trace("created submitter widget new submission");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_MYSUBMISSIONS)) {
			SubmitterWidgetMySubmissions widget = new SubmitterWidgetMySubmissions();
			mainWidget = widget;
			log.trace("created submitter widget my submissions");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_NEWDATADICTIONARY)) {
			SubmitterWidgetNewDataDictionary widget = new SubmitterWidgetNewDataDictionary();
			mainWidget = widget;
			log.trace("created submitter widget new data dictionary");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_MYDATADICTIONARIES)) {
			SubmitterWidgetMyDataDictionaries widget = new SubmitterWidgetMyDataDictionaries();
			mainWidget = widget;
			log.trace("created submitter widget my data dictionaries");
		}
		else if (widgetName.equals(SubmitterPerspective.WIDGET_ALLDATADICTIONARIES)) {
			SubmitterWidgetAllDataDictionaries widget = new SubmitterWidgetAllDataDictionaries();
			mainWidget = widget;
			log.trace("created submitter widget all data dictionaries");
		}
		else {
			log.trace("unexpected widget name: "+widgetName);
		}

		log.leave();
	}

}
