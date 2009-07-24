/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private List<Perspective> perspectives = new ArrayList<Perspective>();
	private RootPanel userDetailsRootPanel;
	private Label userNameLabel;
	private Label currentPerspectiveLabel;
	private MenuBar switchPerspectivesMenu;
	private ListBox switchPerspectivesList;

	Renderer(Controller controller) {
		this.controller = controller;
		this.init();
	}

	void init() {

		// init canvas
		this.userDetailsRootPanel = RootPanel.get("userdetails");
		HorizontalPanel p = new HorizontalPanel();
		this.userDetailsRootPanel.add(p);
		
		p.add(new Label("logged in as:"));

		this.userNameLabel = new Label();
		this.userNameLabel.getElement().setId("usernamelabel");
		p.add(this.userNameLabel);
		
		p.add(new Label("| current perspective:"));

		this.currentPerspectiveLabel = new Label();
		this.currentPerspectiveLabel.getElement().setId("currentperspective");
		p.add(this.currentPerspectiveLabel);
		
		p.add(new Label("| switch perspective:"));
//		this.switchPerspectivesMenu = new MenuBar();
//		p.add(this.switchPerspectivesMenu);

		this.switchPerspectivesList = new ListBox();
		this.switchPerspectivesList.setVisibleItemCount(1);
		p.add(this.switchPerspectivesList);
		p.setSpacing(5);
		
		// init event handlers
		
		HistoryListener<String> historyListener = new HistoryListener<String>(this.controller);
		History.addValueChangeHandler(historyListener);
		
		this.switchPerspectivesList.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
//				Window.alert("list box changed: "+switchPerspectivesList.getValue(switchPerspectivesList.getSelectedIndex()));
				String token = switchPerspectivesList.getValue(switchPerspectivesList.getSelectedIndex());
				History.newItem(token);
			}});

	}

	public void onApplicationStateChanged(String oldState, String newState) {
		// TODO Auto-generated method stub
		
	}

	public void onUserChanged(User oldUser, User newUser) {
//		DOM.getElementById("username").setInnerHTML(newUser.getName());
		this.userNameLabel.setText(newUser.getName());
	}

	
	


	public void onCurrentPerspectiveChanged(Perspective oldPerspective, Perspective newPerspective) {
//		DOM.getElementById("currentperspective").setInnerHTML(newPerspective.getName());
		this.currentPerspectiveLabel.setText(newPerspective.getName());
	}

	
	
	public void onPerspectivesChanged(List<Perspective> oldPerspectives, List<Perspective> newPerspectives) {

		if (newPerspectives.size() > 1) {
			this.renderPerspectiveSwitcher(newPerspectives);
		}

	}

	
	
	void renderPerspectiveSwitcher(List<Perspective> perspectives) {

//		// clear out old menu
//		this.switchPerspectivesMenu.clearItems();
//
//		// add new items
//		MenuBar m = new MenuBar(true);
//		for (Perspective p : perspectives) {
//			m.addItem(p.getName(), new HistoryCommand(p.getHomeToken()));
//		}
//		this.switchPerspectivesMenu.addItem("switch perspective", m);

		this.switchPerspectivesList.clear();
		for (Perspective p : perspectives) {
			this.switchPerspectivesList.addItem(p.getName(), p.getHomeToken());
		}
		
	}

}
