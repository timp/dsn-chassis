/**
 * 
 */
package org.cggh.chassis.generic.widget.client;


import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.HasMenuEventHandlers;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MenuEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class MultiWidget 
	extends ChassisWidget 
	implements HasMenuEventHandlers {

	
	
	


	// utility fields
	private Log log;
	
	
	
	
	
	// UI fields
	protected FlowPanel menuContainer;
	protected FlowPanel mainPanel;
	protected MenuBar menu;




	// state fields
	protected Widget activeChild;
	protected Widget defaultChild;
	protected Set<Widget> mainChildren;
	
	
	
	
	
	// configuration fields
	protected boolean showMenu = false;
	private boolean verticalMenu = true;



	
	public MultiWidget() {}

	
	
	public MultiWidget(boolean showMenu, boolean verticalMenu) {
		this.showMenu = showMenu;
		this.verticalMenu = verticalMenu;
	}
	
	


	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.mainChildren = new HashSet<Widget>();
		this.activeChild = null;
		this.memory = new Memory();
		
		log.leave();
	}
	
	
	

	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(MultiWidget.class);
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	public void renderUI() {
		log.enter("renderUI");
		
		this.menu = new MenuBar(this.verticalMenu);
		
		if (this.showMenu) {
			this.menuContainer = new FlowPanel();
			this.add(this.menuContainer);
			this.menuContainer.add(this.menu);
		}
		
		this.mainPanel = new FlowPanel();
		this.add(this.mainPanel);

		this.renderMainChildren();

		for (Widget w : this.mainChildren) {
			this.mainPanel.add(w);
		}

		this.renderMenuBar(); // has to come last, because menus from child widgets won't be ready until widget is added to main panel

		log.leave();
	}

	
	
	
	/**
	 * Override this method to render main children.
	 */
	public void renderMainChildren() {}




	/**
	 * Override this method to render a menu bar.
	 */
	public void renderMenuBar() {}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	public void bindUI() {
		log.enter("bindUI");
		
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
	}

	
	
	




	
	
	/**
	 * 
	 */
	protected void registerHandlersForChildWidgetEvents() {

		for (Widget w : this.mainChildren) {
			
			if (w instanceof HasMenuEventHandlers) {
				((HasMenuEventHandlers)w).addMenuEventHandler(new MenuEventHandler() {
					
					public void onMenuCommand(MenuEvent e) {
						ChassisWidget source = (ChassisWidget) e.getSource();
						setActiveChild(source);
					}
					
				});
			}
		}

	}






	/**
	 * @param child
	 */
	protected void setActiveChild(Widget child) {
		this.setActiveChild(child, true);
	}

	
	
	
	
	protected void setActiveChild(Widget child, boolean memorise) {
		log.enter("setActiveChild");
		
		this.activeChild = child;
		this.syncUI();
		
		if (child instanceof ChassisWidget) {
			ChassisWidget cw = (ChassisWidget) child;
			this.memory.setChild(cw.getMemory());
		}
		else {
			this.memory.setChild(null);
		}
		
		if (memorise) {
			this.memory.memorise();
		}
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	public void syncUI() {
		log.enter("syncUI");
		
		for (Widget w : this.mainChildren) {
			w.setVisible(false);
		}
		
		if (this.activeChild != null) this.activeChild.setVisible(true);
		
		log.leave();
	}
	
	



	
	public MenuBar getMenu() {
		return this.menu;
	}
	
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class Memory extends WidgetMemory {
		private Log log = LogFactory.getLog(Memory.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#createMnemonic()
		 */
		@Override
		public String createMnemonic() {
			log.enter("createMnemonic");

			String mnemonic = null;
			
			if (activeChild != null && activeChild instanceof ChassisWidget) {
				mnemonic = this.createMnemonic((ChassisWidget)activeChild);
			}
			
			log.debug("mnemonic: "+mnemonic);

			log.leave();
			return mnemonic;
		}
		
		protected String createMnemonic(ChassisWidget w) {
			// turn the widget name into something slightly more aesthetically pleasing
			return w.getName().toLowerCase().replaceAll("widget", "");
		}

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.WidgetMemory#remember(java.lang.String)
		 */
		@Override
		public Deferred<WidgetMemory> remember(String mnemonic) {
			log.enter("remember");

			log.debug("mnemonic: "+mnemonic);
			
			Deferred<WidgetMemory> def = new Deferred<WidgetMemory>();
			
			if (mnemonic == null || mnemonic.equals("")) {
				log.debug("mnemonic is empty, setting default child active");
				if (defaultChild != null) {
					setActiveChild(defaultChild, false);
				}
			}
			else {
				log.debug("searching for child matching mnemonic");
				for (Widget w : mainChildren) {
					if (w instanceof ChassisWidget) {
						ChassisWidget cw = (ChassisWidget) w;
						if (this.createMnemonic(cw).equals(mnemonic)) {
							log.debug("found matching child, setting active child: "+cw.getName());
							setActiveChild(w, false);
						}
					}
				}
			}
			
			def.callback(this); // no async action so callback immediately

			log.leave();
			return def;
		}

	}



	

	public HandlerRegistration addMenuEventHandler(MenuEventHandler h) {
		return this.addHandler(h, MenuEvent.TYPE);
	}



	
	@Override
	public void refresh() {
		if (this.activeChild instanceof ChassisWidget) {
			ChassisWidget cw = (ChassisWidget) this.activeChild;
			cw.refresh();
		}
	}





}
