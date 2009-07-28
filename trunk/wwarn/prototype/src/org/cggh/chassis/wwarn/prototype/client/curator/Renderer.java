/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.curator;


import org.cggh.chassis.wwarn.prototype.client.shared.HistoryCommand;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;

	Renderer(Controller controller) {
		this.controller = controller;
	}


	public void onIsCurrentPerspectiveChanged(boolean from, boolean to) {
		if (to) {
			this.renderMainMenu();
		}
	}

	private void renderMainMenu() {
		RootPanel root = RootPanel.get("mainmenu");
		root.clear();
		
/*		MenuBar mainMenu = new MenuBar();
		root.add(mainMenu);
		mainMenu.addItem("home", new HistoryCommand(CuratorPerspective.TOKEN_HOME));
		
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("my submissions", new HistoryCommand(CuratorPerspective.TOKEN_MYSUBMISSION));
		submissionMenu.addItem("all submissions", new HistoryCommand(CuratorPerspective.TOKEN_ALLSUBMISSIONS));
		mainMenu.addItem("submissions", submissionMenu);
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new standard data dictionary", new HistoryCommand(CuratorPerspective.TOKEN_NEWSTANDARDDATADICTIONARY));
		ddMenu.addItem("all standard data dictionaries", new HistoryCommand(CuratorPerspective.TOKEN_ALLSTANDARDDATADICTIONARIES));
		mainMenu.addItem("data dictionaries", ddMenu);

		MenuBar rcMenu = new MenuBar(true);
		rcMenu.addItem("new release criteria", new HistoryCommand(CuratorPerspective.TOKEN_NEWRELEASECRITERIA));
		rcMenu.addItem("all release criteria", new HistoryCommand(CuratorPerspective.TOKEN_ALLRELEASECRITERIA));
		mainMenu.addItem("release criteria", rcMenu);

		MenuBar tMenu = new MenuBar(true);
		tMenu.addItem("delegate new task", new HistoryCommand(CuratorPerspective.TOKEN_DELEGATENEWTASKS));
		tMenu.addItem("my delegated tasks", new HistoryCommand(CuratorPerspective.TOKEN_MYDELEGATEDTASKS));
		mainMenu.addItem("tasks", tMenu);*/
	}




}
