/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author aliman
 *
 */
public abstract class ChassisWidget extends Composite {

	
	
	
	private Log log = LogFactory.getLog(ChassisWidget.class);

	
	
	/**
	 * The outer panel that this composite widget wraps.
	 */
	protected FlowPanel boundingBox = new FlowPanel();

	
	
	/**
	 * The inner panel that all child widgets should be added to.
	 */
	protected FlowPanel contentBox = new FlowPanel();



	
	/** 
	 * Handler manager for this widget.
	 */
	protected HandlerManager handlerManager;
	
	
	
	
	
	/**
	 * Construct a widget.
	 */
	public ChassisWidget() {
		log.enter("<constructor>");
		
		log.debug("initialise composite widget");
		this.initWidget(this.boundingBox);
		
		log.debug("set up boundingBox and contentBox");
		this.boundingBox.add(this.contentBox);
		
		String stylePrimaryName = "chassis-" + this.getName();

		log.debug("set style primary name: "+stylePrimaryName);
		this.setStylePrimaryName(stylePrimaryName);

		log.debug("call init()");
		this.init();
		
		log.leave();
	}
	
	
	

	/**
	 * Establishes the initialisation life-cycle phase. Extensions to Widget
	 * sub-classes MUST call init() on the super-class BEFORE any further action,
	 * to ensure that the init() call propagates DOWN the class hierarchy from
	 * the top.
	 */
	public abstract void init();
	
	
	
	
	/**
	 * Establishes the destruction life-cycle phase. Extensions to Widget
	 * sub-classes MUST call destroy() on the super class AFTER any specific action,
	 * to ensure that the destroy() call propagates UP the class hierarchy from the
	 * bottom.
	 */
	public abstract void destroy();
	
	

	
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
	protected abstract void renderUI();

	
	
	
	/**
	 * This method is responsible for attaching event listeners which bind the UI
	 * to the widget state (i.e., the widget's model). These listeners are generally
	 * property change listeners - used to update the state of the UI in response
	 * to changes in the model's properties. This method also attaches event listeners
	 * to child widgets making up the UI to map user interactions to the widget's 
	 * API.
	 */
	protected abstract void bindUI();

	
	
	
	/**
	 * This method is responsible for setting the state of the UI based on the 
	 * current state of the widget at the time of rendering.
	 */
	protected abstract void syncUI();

	
	
	
	/**
	 * Resets the widget's state to its initial state by calling the init() method, 
	 * and then renders the widget by calling the render() method.
	 */
	public void reset() {
		log.enter("reset");
		
		this.unbindUI();
		this.init();
		this.render();
		
		log.leave();
	}




	/**
	 * Detach the widget from any event listeners.
	 */
	protected abstract void unbindUI();
	
	
	
	
	
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
		
		super.onDetach();
	
		// TODO should we call destroy() here? If not, where?
		
		log.leave();
	}
	
	
	
	
	
	protected abstract String getName();
	
	
	
	
}
