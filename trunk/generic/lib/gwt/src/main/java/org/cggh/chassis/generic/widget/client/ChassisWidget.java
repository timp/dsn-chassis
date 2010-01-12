/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public abstract class ChassisWidget 
	extends Composite 
	implements HasWidgets, IndexedPanel {

	
	
	
	private Log log = LogFactory.getLog(ChassisWidget.class);
	
	
	
	
	
	/**
	 * Set to store handler registrations so they can be removed if needed.
	 */
	protected Set<HandlerRegistration> childWidgetEventHandlerRegistrations = new HashSet<HandlerRegistration>();

	
	
	
	/**
	 * The outer panel that this composite widget wraps.
	 */
	protected Panel boundingBox = new FlowPanel();

	
	
	
	/**
	 * The inner panel that all child widgets should be added to.
	 */
	protected ComplexPanel contentBox = new FlowPanel();
	
	
	
	
	/**
	 * Widget may or may not have memory. If so, store in this field.
	 */
	protected WidgetMemory memory = null;



	
	/**
	 * Construct a widget.
	 */
	public ChassisWidget() {
		log.enter("<constructor>");
		
		log.debug("call init() here, so subclass can override boundingBox and/or contentBox implementation");
		this.init();
		
		log.debug("initialise composite widget");
		this.initWidget(this.boundingBox);
		
		log.debug("set up boundingBox and contentBox");
		this.boundingBox.add(this.contentBox);
		
		String stylePrimaryName = "chassis-" + this.getName();

		log.debug("set style primary name: "+stylePrimaryName);
		this.setStylePrimaryName(stylePrimaryName);

		log.leave();
	}
	
	
	
	/**
	 * Establishes the initialisation life-cycle phase. Extensions to Widget
	 * sub-classes MUST call init() on the super-class BEFORE any further action,
	 * to ensure that the init() call propagates DOWN the class hierarchy from
	 * the top.
	 */
	public void init() {}
	
	
	
	
	/**
	 * Establishes the destruction life-cycle phase. Extensions to Widget
	 * sub-classes MUST call destroy() on the super class AFTER any specific action,
	 * to ensure that the destroy() call propagates UP the class hierarchy from the
	 * bottom. The default behaviour is simply to call unbindUI(). Override this
	 * method to get custom behaviour.
	 */
	public void destroy() {
		this.unbindUI();
	}
	
	

	
	/**
	 * Establishes the render life-cycle phase. Calls the methods renderUI, bindUI
	 * and syncUI in order.
	 */
	public void render() {
		log.enter("render");
		
		this.renderUI();
		this.bindUI();
		this.syncUI();
		
		log.leave();
	}
	
	

	
	/**
	 * This method is responsible for creating and adding the child widgets of
	 * this widget's content box.
	 */
	protected void renderUI() {}

	
	
	
	/**
	 * This method is responsible for attaching event listeners which bind the UI
	 * to the widget state (i.e., the widget's model). These listeners are generally
	 * property change listeners - used to update the state of the UI in response
	 * to changes in the model's properties. This method also attaches event listeners
	 * to child widgets making up the UI to map user interactions to the widget's 
	 * API.
	 */
	protected void bindUI() {}

	
	
	
	/**
	 * This method is responsible for setting the state of the UI based on the 
	 * current state of the widget at the time of rendering.
	 */
	protected void syncUI() {}

	
	
	
	/**
	 * Resets the widget's state to its initial state by calling the init() method, 
	 * and then renders the widget by calling the render() method.
	 */
	public void reset() {
		log.enter("reset");
		
		// TODO review this
		
		this.unbindUI();
		this.init();
		this.render();
		
		log.leave();
	}




	/**
	 * Detach the widget from any event listeners.
	 */
	protected void unbindUI() {
		log.enter("unbindUI");
		
		this.clearChildWidgetEventHandlers();
		
		log.leave();
	}
	
	
	
	
	
	@Override
	protected void onAttach() {
		log.enter("onAttach");
		
		super.onAttach();
	
		// call render() when widget is attached to browser document
		// see http://google-web-toolkit.googlecode.com/svn/javadoc/1.6/com/google/gwt/user/client/ui/Widget.html#onAttach()
		this.render();
		
		log.leave();
	}
	
	
	
	
	
	@Override
	protected void onDetach() {
		log.enter("onDetach");
		
		// TODO should we call destroy() here? If not, where?
		this.destroy();
		
		super.onDetach();
	
		log.leave();
	}
	
	
	
	
	
	public String getName() {
		String s = this.getClass().getName();
		String[] t = s.split("\\.");
		s = t[t.length-1];
		StringBuffer o = new StringBuffer();
		o.append(s.substring(0, 1).toLowerCase());
		o.append(s.substring(1));
		return o.toString();
	}
	
	
	
	
	protected void clearChildWidgetEventHandlers() {
		for (HandlerRegistration hr : this.childWidgetEventHandlerRegistrations) {
			hr.removeHandler();
		}
	}
	
	
	
	
	/**
	 * Expose Widget.addHandler() to package so it can be used by ChassisWidgetModel.
	 * 
	 * @param <H>
	 * @param handler
	 * @param type
	 * @return
	 */
	final <H extends EventHandler> HandlerRegistration addEventHandler(H handler, GwtEvent.Type<H> type) {
		return this.addHandler(handler, type);
	}
	
	
	
	
	
	/**
	 * Create a style name by appending the name passed in to the widget's
	 * primary style name, delimited by a "-".
	 * 
	 * @param suffix
	 * @return
	 */
	public String createStyleName(String suffix) {
		return this.getStylePrimaryName() + "-" + suffix;
	}
	
	
	
	
	/**
	 * Register handler for cancel event.
	 * 
	 * @param h handler to receive events
	 * @return a handler registration to remove the handler if needed
	 */
	public HandlerRegistration addCancelHandler(CancelHandler h) {
		return this.addHandler(h, CancelEvent.TYPE);
	}
	
	
	
	
	public HandlerRegistration addErrorHandler(ErrorHandler h) {
		return this.addHandler(h, ErrorEvent.TYPE);
	}
	
	
	

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget w) {
		this.contentBox.add(w);
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean remove(Widget w) {
		return this.contentBox.remove(w);
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#iterator()
	 */
	public Iterator<Widget> iterator() {
		return this.contentBox.iterator();
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasWidgets#clear()
	 */
	public void clear() {
		this.contentBox.clear();
	}
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#getWidget(int)
	 */
	public Widget getWidget(int index) {
		return this.contentBox.getWidget(index);
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#getWidgetCount()
	 */
	public int getWidgetCount() {
		return this.contentBox.getWidgetCount();
	}
	

	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#getWidgetIndex(com.google.gwt.user.client.ui.Widget)
	 */
	public int getWidgetIndex(Widget child) {
		return this.contentBox.getWidgetIndex(child);
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.IndexedPanel#remove(int)
	 */
	public boolean remove(int index) {
		return this.contentBox.remove(index);
	}
	
	
	
	

	/**
	 * Show only this child widget, hiding all others.
	 * @param child
	 * @return false if the widget is not present
	 */
	public boolean showOnly(Widget child) {
		if (child != null && this.getWidgetIndex(child) >= 0) {
			this.hideAll();
			child.setVisible(true);
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * Hide all child widgets.
	 */
	public void hideAll() {
		for (Widget w : this) {
			w.setVisible(false);
		}
	}

	
	
	
	public WidgetMemory getMemory() {
		return this.memory;
	}

	
	
	
	public void setMemory(WidgetMemory memory) {
		this.memory = memory;
	}
	
	
	
	
	public boolean hasMemory() {
		return (this.memory != null);
	}



	/**
	 * Refresh any data loaded asynchronously
	 */
	public void refresh() {
		
		// default implementation, do nothing
		
	}
	
	
	
	
	/**
	 * Refresh any data loaded asynchronously, calling back with self when complete.
	 * 
	 * @return
	 */
	public Deferred<ChassisWidget> refreshAndCallback() {
	
		// default implementation, do nothing and call back immediately
		Deferred<ChassisWidget> d = new Deferred<ChassisWidget>();
		d.callback(this);
		
		return d;

	}




}
