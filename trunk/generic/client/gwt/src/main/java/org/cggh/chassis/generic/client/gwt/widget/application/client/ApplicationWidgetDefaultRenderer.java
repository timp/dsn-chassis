/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.application.client;

import org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.PerspectiveWidget;
import org.cggh.chassis.generic.client.gwt.widget.application.client.perspective.PerspectiveWidgetAPI;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidget;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.UserDetailsWidgetAPI;
import org.cggh.chassis.generic.user.gwtrpc.client.GWTUserDetailsServiceAsync;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author raok
 *
 */
public class ApplicationWidgetDefaultRenderer implements ApplicationWidgetModelListener {

	//Expose view elements for testing purposes.
	final Panel userDetailsWidgetCanvas = new SimplePanel();
	final Panel activePerspectiveWidgetPanel = new SimplePanel();
	Panel submitterPerspectiveWidgetCanvas = new SimplePanel();
	Panel curatorPerspectiveWidgetCanvas = new SimplePanel();
	Panel coordinatorPerspectiveWidgetCanvas = new SimplePanel();
	Panel gatekeeperPerspectiveWidgetCanvas = new SimplePanel();
	Panel userPerspectiveWidgetCanvas = new SimplePanel();
	
	final private Panel canvas;

	
	// make package private to allow access to parent widget
	PerspectiveWidgetAPI submitterPerspectiveWidget;
	final UserDetailsWidgetAPI userDetailsWidget;

	public ApplicationWidgetDefaultRenderer(Panel canvas, GWTUserDetailsServiceAsync userService) {
		this.canvas = canvas;
		
		//initialise user details widget
		userDetailsWidget = new UserDetailsWidget(userDetailsWidgetCanvas, userService);
		
		initCanvas();
	}

	private void initCanvas() {

		VerticalPanel mainPanel = new VerticalPanel();
		
		mainPanel.add(userDetailsWidgetCanvas);
		mainPanel.add(activePerspectiveWidgetPanel);
		
		canvas.add(mainPanel);
		
	}

	public void onPerspectiveChanged(Integer before, Integer after) {

		if (after == ApplicationWidgetModel.STATUS_SUBMITTER_PERSPECTIVE) {
			
			activePerspectiveWidgetPanel.clear();
			
			activePerspectiveWidgetPanel.add(submitterPerspectiveWidgetCanvas);
			
		} else if (after == ApplicationWidgetModel.STATUS_CURATOR_PERSPECTIVE) {
			
			activePerspectiveWidgetPanel.clear();
			
			activePerspectiveWidgetPanel.add(curatorPerspectiveWidgetCanvas);
			
		} else if (after == ApplicationWidgetModel.STATUS_COORDINATOR_PERSPECTIVE) {
			
			activePerspectiveWidgetPanel.clear();
			
			activePerspectiveWidgetPanel.add(coordinatorPerspectiveWidgetCanvas);
			
		} else if (after == ApplicationWidgetModel.STATUS_GATEKEEPER_PERSPECTIVE) {
			
			activePerspectiveWidgetPanel.clear();
			
			activePerspectiveWidgetPanel.add(gatekeeperPerspectiveWidgetCanvas);
			
		} else if (after == ApplicationWidgetModel.STATUS_USER_PERSPECTIVE) {
			
			activePerspectiveWidgetPanel.clear();
			
			activePerspectiveWidgetPanel.add(userPerspectiveWidgetCanvas);
			
		}
		
	}

	public void onUserRefreshed(String userId) {

		// initialise perspective widgets
		submitterPerspectiveWidget = new PerspectiveWidget(submitterPerspectiveWidgetCanvas, userId);
		
		//TODO initialise other widgets
		curatorPerspectiveWidgetCanvas.add(new Label("curator perspective placeholder"));
		coordinatorPerspectiveWidgetCanvas.add(new Label("coordinator perspective placeholder"));
		gatekeeperPerspectiveWidgetCanvas.add(new Label("gatekeeper perspective placeholder"));
		userPerspectiveWidgetCanvas.add(new Label("user perspective placeholder"));
		
	}

}
