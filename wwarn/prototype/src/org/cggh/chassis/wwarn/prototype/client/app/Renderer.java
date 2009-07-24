/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.app;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.wwarn.prototype.client.shared.Perspective;
import org.cggh.chassis.wwarn.prototype.client.shared.User;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.History;

/**
 * @author aliman
 *
 */
class Renderer implements ModelListener {

	private Controller controller;
	private List<Perspective> perspectives = new ArrayList<Perspective>();

	Renderer(Controller controller) {
		this.controller = controller;
		this.init();
	}

	void init() {

		// init event handlers
		HistoryListener<String> historyListener = new HistoryListener<String>(this.controller);
		History.addValueChangeHandler(historyListener);
		
	}

	public void onApplicationStateChanged(String oldState, String newState) {
		// TODO Auto-generated method stub
		
	}

	public void onUserChanged(User oldUser, User newUser) {
		Document.get().getElementById("username").setInnerHTML(newUser.getName());
	}

	
	


	public void onCurrentPerspectiveChanged(Perspective oldPerspective, Perspective newPerspective) {
		Document.get().getElementById("currentperspective").setInnerHTML(newPerspective.getName());
	}

	public void onPerspectivesChanged(List<Perspective> oldPerspectives,
			List<Perspective> newPerspectives) {
		// TODO Auto-generated method stub
		
	}

}
