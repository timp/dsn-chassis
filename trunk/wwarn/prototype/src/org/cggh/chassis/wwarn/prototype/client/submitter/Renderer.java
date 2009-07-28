/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter;

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

	public void onIsCurrentPerspectiveChanged(boolean wasCurrent, boolean current) {
		if (current) {
			this.renderMainMenu();
		}
	}

	private void renderMainMenu() {
		RootPanel root = RootPanel.get("mainmenu");
		root.clear();
		/*
		MenuBar mainMenu = new MenuBar();
		root.add(mainMenu);
		mainMenu.addItem("home", new HistoryCommand(SubmitterPerspective.TOKEN_HOME));
		
		MenuBar studyMenu = new MenuBar(true);
		studyMenu.addItem("new study", new HistoryCommand(SubmitterPerspective.TOKEN_NEWSTUDY));
		studyMenu.addItem("my studies", new HistoryCommand(SubmitterPerspective.TOKEN_MYSTUDIES));
		studyMenu.addItem("all studies", new HistoryCommand(SubmitterPerspective.TOKEN_ALLSTUDIES));
		mainMenu.addItem("studies", studyMenu);
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("new submission", new HistoryCommand(SubmitterPerspective.TOKEN_NEWSUBMISSION));
		submissionMenu.addItem("my submissions", new HistoryCommand(SubmitterPerspective.TOKEN_MYSUBMISSIONS));
		mainMenu.addItem("submissions", submissionMenu);
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new data dictionary", new HistoryCommand(SubmitterPerspective.TOKEN_NEWDATADICTIONARY));
		ddMenu.addItem("my data dictionaries", new HistoryCommand(SubmitterPerspective.TOKEN_MYDATADICTIONARIES));
		ddMenu.addItem("all data dictionaries", new HistoryCommand(SubmitterPerspective.TOKEN_ALLDATADICTIONARIES));
		mainMenu.addItem("data dictionaries", ddMenu);*/
	}

}
