/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator.perspective;


import org.cggh.chassis.wwarn.prototype.client.shared.GWTLogger;
import org.cggh.chassis.wwarn.prototype.client.shared.Logger;

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

	Renderer(Controller controller) {
		this.controller = controller;
		this.log = new GWTLogger();
		this.log.setCurrentClass(Renderer.class.getName());
	}


	public void onIsCurrentPerspectiveChanged(boolean from, boolean to) {
		log.enter("onIsCurrentPerspectiveChanged");
		
		if (to) {
			
			log.trace("render main menu");
			this.renderMainMenu();
			
			log.trace("update main panel content");
			// hack for now
			RootPanel.get("appcontent").clear();
		}
		
		log.leave();
	}

	private void renderMainMenu() {
		RootPanel root = RootPanel.get("mainmenu");
		root.clear();

		if (mainMenu == null) {
			this.createMainMenu();
		}
		
		root.add(mainMenu);
	}


	private void createMainMenu() {
		
		mainMenu = new MenuBar();
		mainMenu.addItem("home", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_HOME));
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("my submissions", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_MYSUBMISSIONS));
		submissionMenu.addItem("all submissions", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_ALLSUBMISSIONS));
		mainMenu.addItem("submissions", submissionMenu);
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new standard data dictionary", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_NEWSTANDARDDATADICTIONARY));
		ddMenu.addItem("all standard data dictionaries", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_ALLSTANDARDDATADICTIONARIES));
		mainMenu.addItem("data dictionaries", ddMenu);

		MenuBar rcMenu = new MenuBar(true);
		rcMenu.addItem("new release criteria", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_NEWRELEASECRITERIA));
		rcMenu.addItem("all release criteria", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_ALLRELEASECRITERIA));
		mainMenu.addItem("release criteria", rcMenu);

		MenuBar tMenu = new MenuBar(true);
		tMenu.addItem("delegate new task", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_DELEGATENEWTASK));
		tMenu.addItem("my delegated tasks", new SetMainWidgetCommand(controller, CuratorPerspective.WIDGET_MYDELEGATEDTASKS));
		mainMenu.addItem("tasks", tMenu);	
		
	}




}
