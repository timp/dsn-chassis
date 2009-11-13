/**
 * 
 */
package org.cggh.chassis.generic.widget.client;


import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.CancelEvent;
import org.cggh.chassis.generic.widget.client.CancelHandler;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.widget.client.ErrorHandler;
import org.cggh.chassis.generic.widget.client.HasMenuEventHandlers;
import org.cggh.chassis.generic.widget.client.MenuEvent;
import org.cggh.chassis.generic.widget.client.MenuEventHandler;
import org.cggh.chassis.generic.widget.client.WidgetMemory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public abstract class MultiWidget 
	extends ChassisWidget 
	implements HasMenuEventHandlers {

	
	
	


	// utility fields
	private Log log;
	
	
	
	
	
	// UI fields
	protected MenuBar menuBar;




	// state fields
	protected Widget activeChild;





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

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
	protected void renderUI() {
		log.enter("renderUI");
		
		this.menuBar = new MenuBar(true);
		
		this.renderChildWidgets();
		
		log.leave();
	}

	
	
	

	protected abstract void renderChildWidgets();




	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");
		
		this.bindMenuBar();
		
		this.registerHandlersForChildWidgetEvents();
		
		log.leave();
	}

	
	
	



	/**
	 * 
	 */
	protected abstract void bindMenuBar();


	
	
	/**
	 * 
	 */
	protected abstract void registerHandlersForChildWidgetEvents();






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
	protected void syncUI() {
		log.enter("syncUI");
		
		if (this.activeChild == null) this.hideAll();
		else this.showOnly(this.activeChild);
		
		log.leave();
	}
	
	



	
	public MenuBar getMenu() {
		return this.menuBar;
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
			
			for (Widget w : MultiWidget.this) {
				if (w instanceof ChassisWidget) {
					if (this.createMnemonic((ChassisWidget)w).equals(mnemonic)) {
						setActiveChild(w, false);
					}
				}
			}
			
			def.callback(this); // no async action so callback immediately

			log.leave();
			return def;
		}

	}



	

	/**
	 * @author aliman
	 *
	 */
	public class CommonErrorHandler implements ErrorHandler {
		private Log log = LogFactory.getLog(CommonErrorHandler.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.widget.client.ErrorHandler#onError(org.cggh.chassis.generic.widget.client.ErrorEvent)
		 */
		public void onError(ErrorEvent e) {
			log.enter("onError");

			Window.alert("an unexpected error has occurred");
			log.error("an unexpected error has occurred", e.getException());

			log.leave();
		}

	}
	
	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class CommonCancelHandler implements CancelHandler {
		private Log log = LogFactory.getLog(CommonCancelHandler.class);
			
		public void onCancel(CancelEvent e) {
			log.enter("onCancel");
			
			History.back();
			
			log.leave();
		}

	}




	public HandlerRegistration addMenuEventHandler(MenuEventHandler h) {
		return this.addHandler(h, MenuEvent.TYPE);
	}



}
