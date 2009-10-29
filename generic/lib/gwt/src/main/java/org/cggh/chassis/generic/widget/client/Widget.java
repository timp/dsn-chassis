/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author aliman
 *
 */
public abstract class Widget extends Composite {

	
	
	
	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	protected FlowPanel boundingBox = new FlowPanel();
	protected FlowPanel contentBox = new FlowPanel();
	
	
	
	
	
	/**
	 * Construct a widget.
	 */
	public Widget() {
		this.initWidget(this.boundingBox);
		this.boundingBox.add(this.contentBox);
		String stylePrimaryName = "chassis-" + this.getClass().getSimpleName();
		this.setStylePrimaryName(stylePrimaryName);
	}
	
	
	

	/**
	 * Establishes the initialisation life-cycle phase. Extensions to Widget
	 * sub-classes MUST call init() on the super class BEFORE any further action,
	 * to ensure that the init() call propagates DOWN the class hierarchy from
	 * the top.
	 */
	public abstract void init();
	
	
	
	
	/**
	 * Establishes the destruction life-cycle phase. Extensions to Widget
	 * sub-classes MUST call destroy() on the super class AFTER any specific action,
	 * to ensure that the destro() call propagates UP the class hierarchy from the
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
	 * this widget.
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

	
	
	
}
