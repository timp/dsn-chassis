/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public abstract class PerspectiveBase 
	extends ChassisWidget {
	
	
	
	
	// utility fields
	private Log log;

	
	
	
	// UI fields
	protected FlowPanel menuContainer;
	protected FlowPanel mainPanel;
	protected MenuBar mainMenu;

	
	
	
	// state fields
	protected Set<ChassisWidget> mainChildren;
	protected ChassisWidget activeChild;

	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");
		
		this.mainChildren = new HashSet<ChassisWidget>();
		this.activeChild = null;
		
		this.memory = new Memory();

		log.leave();
	}

	
	
	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(PerspectiveBase.class);
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
						ChassisWidget source = (ChassisWidget) e.getSource();
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
	protected void setActiveChild(ChassisWidget w) {
		this.setActiveChild(w, true);
	}



	
	

	/**
	 * @param w
	 */
	protected void setActiveChild(ChassisWidget w, boolean memorise) {
		
		this.activeChild = w;
		
		if (this.activeChild != null) {
			this.memory.setChild(this.activeChild.getMemory());
		}
		else {
			this.memory.setChild(null);
		}
		
		this.syncUI();
		
		if (memorise) {
			this.memory.memorise();
		}
		
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

			Deferred<WidgetMemory> def = new Deferred<WidgetMemory>();
			
			for (ChassisWidget w : mainChildren) {
				if (this.createMnemonic(w).equals(mnemonic)) {
					setActiveChild(w, false);
				}
			}
			
			def.callback(this); // no async action so callback immediately

			log.leave();
			return def;
		}

	}



	
	
	

}
