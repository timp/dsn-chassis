/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.client.gwt.widget.studymanagement.client.StudyManagementWidget;
import org.cggh.chassis.generic.client.gwt.widget.submitter.home.client.SubmitterHomeWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class AdministratorPerspectiveDefaultRenderer implements	AdministratorPerspectiveRenderer {

	
	
	
	public static final String STYLENAME_BASE = "chassis-administratorPerspective";
	public static final String STYLENAME_MAINMENU = STYLENAME_BASE + "-mainMenu";
	
	
	
	
	private Log log = LogFactory.getLog(this.getClass());
	private Panel canvas;
	private MenuBar mainMenu;
	private AdministratorPerspectiveController controller;
	private List<Widget> widgets = new ArrayList<Widget>();
	private AdministratorPerspective owner;
	private AdministratorHomeWidget administratorHomeWidget;

	

	
	/**
	 * @param owner
	 * @param controller
	 */
	public AdministratorPerspectiveDefaultRenderer(
			AdministratorPerspective owner,
			AdministratorPerspectiveController controller) {
		log.enter("<constructor>");
		
		this.owner = owner;
		this.controller = controller;
		
		this.constructCanvas();
		
		log.leave();
	}

	
	

	
	
	
	
	
	
	/**
	 * 
	 */
	private void constructCanvas() {

		this.canvas = new FlowPanel();
		this.canvas.addStyleName(STYLENAME_BASE);
		
		this.constructHomeWidget();
		
		this.constructMenuBar();
		
		this.renderCanvas();
		
	}





	/**
	 * 
	 */
	private void constructHomeWidget() {

		this.administratorHomeWidget = new AdministratorHomeWidget();
		this.administratorHomeWidget.setVisible(false);
		this.widgets.add(this.administratorHomeWidget);
		
	}





	/**
	 * 
	 */
	private void constructMenuBar() {
		log.enter("constructMenuBar");
		
		this.mainMenu = new MenuBar();
		this.mainMenu.addStyleName(STYLENAME_MAINMENU);
		
		this.mainMenu.addItem("home", new Command() {
			public void execute() {	controller.show(AdministratorHomeWidget.class.getName()); } // TODO
		});
		
		this.canvas.add(this.mainMenu);

		log.leave();
	}
	
	
	
	
	private void renderCanvas() {
		for (Widget w : this.widgets) {
			this.canvas.add(w);
		}
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.SubmitterPerspectiveRenderer#getCanvas()
	 */
	public Panel getCanvas() {
		return this.canvas;
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.application.client.SubmitterPerspectiveModelListener#onVisibilityChanged(java.lang.String, boolean)
	 */
	public void onWidgetVisibilityChanged(String widgetName, boolean visible) {
		log.enter("onWidgetVisibilityChanged");
		
		for (Widget w : this.widgets) {
			if (w.getClass().getName().equals(widgetName)) {
				log.debug("setting visibility: "+widgetName+"; "+visible);
				w.setVisible(visible);
			}
		}
		
		log.leave();
	}

	
	
	
}
