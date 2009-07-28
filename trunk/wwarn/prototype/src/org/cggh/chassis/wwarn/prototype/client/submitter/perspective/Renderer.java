/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.perspective;

import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.HMVCComponent;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.home.SubmitterWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy.SubmitterWidgetNewStudy;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private Logger log;
	private MenuBar mainMenu = null;
	private SubmitterPerspective owner;

	Renderer(SubmitterPerspective owner, Controller controller) {
		this.owner = owner;
		this.controller = controller;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}

	public void onIsCurrentPerspectiveChanged(boolean wasCurrent, boolean current) {
		if (current) {
			this.renderMainMenu();
		}
	}

	private void renderMainMenu() {
		log.enter("renderMainMenu");
		
		log.info("clear main menu panel");
		RootPanel root = RootPanel.get("mainmenu");
		root.clear();
		
		if (this.mainMenu == null) {
			this.createMainMenu();
		}
		
		log.info("add main menu to panel");
		root.add(this.mainMenu);
		
		log.leave();
	}

	private void createMainMenu() {
		log.enter("createMainMenu");

		log.info("instantiate main menu bar");
		this.mainMenu = new MenuBar();
		
		mainMenu.addItem("home", new Command() {
			public void execute() {	controller.setMainWidget(SubmitterPerspective.WIDGET_HOME); }
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

		RootPanel appcontent = RootPanel.get(SubmitterPerspective.ELEMENTID_APPCONTENT);
		HMVCComponent child = null;
		
		if (to.equals(SubmitterPerspective.WIDGET_HOME)) {
			SubmitterWidgetHome widget = new SubmitterWidgetHome();
			widget.setRootPanel(appcontent);
			widget.initialise();
			child = widget;
		}
		else if (to.equals(SubmitterPerspective.WIDGET_NEWSTUDY)) {
			SubmitterWidgetNewStudy widget = new SubmitterWidgetNewStudy();
			widget.setRootPanel(appcontent);
			widget.initialise();
			child = widget;
		}
		else {
			// TODO other widgets
			appcontent.clear();
		}

		// hook up to owner to plug into history 
		this.owner.clearChildren();
		this.owner.addChild(child);

	}

}
