/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.HasMenuEventHandlers;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MenuEventHandler;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public abstract class NewPerspectiveBase 
	extends ChassisWidget {
	
	
	
	
	// utility fields
	private Log log;

	
	
	
	// UI fields
	protected FlowPanel menuContainer;
	protected FlowPanel mainPanel;
	protected MenuBar mainMenu;

	
	
	
	// state fields
	protected Set<Widget> mainChildren;
	protected Widget activeChild;

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		this.mainChildren = new HashSet<Widget>();
		this.activeChild = null;

		log.leave();
	}

	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(NewPerspectiveBase.class);
	}






	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		this.menuContainer = new FlowPanel();
		this.add(this.menuContainer);
		
		this.mainPanel = new FlowPanel();
		this.add(this.mainPanel);
		
		this.renderMainChildren();
		
		for (Widget w : this.mainChildren) {
			this.mainPanel.add(w);
		}
		
		this.mainMenu = new MenuBar();
		this.menuContainer.add(this.mainMenu);

		this.renderMenuBar();
		
		log.leave();
	}

	
	
	
	

	/**
	 * 
	 */
	protected  abstract void renderMainChildren();



	

	/**
	 * 
	 */
	protected  abstract void renderMenuBar();

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {

		for (Widget w : this.mainChildren) {
			
			if (w instanceof HasMenuEventHandlers) {
				((HasMenuEventHandlers)w).addMenuEventHandler(new MenuEventHandler() {
					
					public void onMenuCommand(MenuEvent e) {
						Widget source = (Widget) e.getSource();
						setActiveChild(source);
					}
					
				});
			}
		}

	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {

		for (Widget w : this.mainChildren) w.setVisible(false);
		
		if (this.activeChild != null) this.activeChild.setVisible(true);

	}

	
	
	
	
	/**
	 * @param w
	 */
	protected void setActiveChild(Widget w) {
		this.activeChild = w;
		this.syncUI();
	}




}
