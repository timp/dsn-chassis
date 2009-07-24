package org.cggh.chassis.wwarn.prototype.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

public class ApplicationSandbox<T> implements EntryPoint, ValueChangeHandler<String> {

	public static final String ID_APPCONTENT = "appcontent";
	private static final String ID_MAINMENU = "mainmenu";
	private static final String ID_USERNAME = "username";
	private static final String ID_PERSPECTIVESWITCHER = "perspectiveswitcher";
	protected static final String STATE_HOME = "submitter/home";
	private static final String STATE_NEWSTUDY = "submitter/studies/new";
	private static final String STATE_MYSTUDIES = "submitter/studies/my";
	private static final String STATE_ALLSTUDIES = "submitter/studies/all";
	private static final String STATE_NEWSUBMISSION = "submitter/submissions/new";
	private static final String STATE_MYSUBMISSIONS = "submitter/submissions/my";
	private static final String STATE_NEWDATADICTIONARY = "submitter/datadictionaries/new";
	private static final String STATE_MYDATADICTIONARIES = "submitter/datadictionaries/my";
	private static final String STATE_ALLDATADICTIONARIES = "submitter/datadictionaries/all";
	
	public void onModuleLoad() {

		GWT.log("ApplicationSandbox.onModuleLoad", null);
		
		initUser();
		initPerspectives();
		initMainMenu();
		
	}

	private void initUser() {
		Element userdetails = Document.get().getElementById(ID_USERNAME);
		userdetails.setInnerHTML("Bob");
	}

	private void initPerspectives() {
		Element ps = Document.get().getElementById(ID_PERSPECTIVESWITCHER);
		ps.setInnerHTML("submitter");
	}

	private void initMainMenu() {
		
		Window.alert("initial history token: "+History.getToken());

		RootPanel menuPanel = RootPanel.get(ID_MAINMENU);
		
		MenuBar mainMenu = new MenuBar();
		menuPanel.add(mainMenu);
		
//		Command homeCommand = new Command() {
//			public void execute() {
//				Window.alert("home menu item selected");
//			}
//		};
		
		mainMenu.addItem("home", new HistoryCommand(STATE_HOME));
		
//		Command studyCommand = new Command() {
//			public void execute() {
//				Window.alert("study item selected");
//			}
//		};
		
		MenuBar studyMenu = new MenuBar(true);
		studyMenu.addItem("new study", new HistoryCommand(STATE_NEWSTUDY));
		studyMenu.addItem("my studies", new HistoryCommand(STATE_MYSTUDIES));
		studyMenu.addItem("all studies", new HistoryCommand(STATE_ALLSTUDIES));
		
		mainMenu.addItem("studies", studyMenu);
		
//		Command submissionCommand = new Command() {
//			public void execute() {
//				Window.alert("submission item selected");
//			}
//		};
		
		MenuBar submissionMenu = new MenuBar(true);
		submissionMenu.addItem("new submission", new HistoryCommand(STATE_NEWSUBMISSION));
		submissionMenu.addItem("my submissions", new HistoryCommand(STATE_MYSUBMISSIONS));

		mainMenu.addItem("submissions", submissionMenu);
		
//		Command ddCommand = new Command() {
//			public void execute() {
//				Window.alert("dd item selected");
//			}
//		};
		
		MenuBar ddMenu = new MenuBar(true);
		ddMenu.addItem("new data dictionary", new HistoryCommand(STATE_NEWDATADICTIONARY));
		ddMenu.addItem("my data dictionaries", new HistoryCommand(STATE_MYDATADICTIONARIES));
		ddMenu.addItem("all data dictionaries", new HistoryCommand(STATE_ALLDATADICTIONARIES));
		
		mainMenu.addItem("data dictionaries", ddMenu);
		
		History.addValueChangeHandler(this);
		
		History.fireCurrentHistoryState();
		
	}
	
	@SuppressWarnings("unchecked")
	public void onValueChange(ValueChangeEvent event) {
		Window.alert("history value changed: "+event.getValue());
	}

}
