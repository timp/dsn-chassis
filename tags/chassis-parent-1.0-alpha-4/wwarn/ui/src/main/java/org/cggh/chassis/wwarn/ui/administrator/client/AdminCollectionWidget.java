/**
 * 
 */
package org.cggh.chassis.wwarn.ui.administrator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFeed;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class AdminCollectionWidget extends Composite {
	
	private AdminCollectionWidgetModel model;
	private AdminCollectionWidgetController controller;
	private AdminCollectionWidgetRenderer renderer;

	public AdminCollectionWidget(String title, String url) {
		
		// model first
		model = new AdminCollectionWidgetModel();
		
		// then controller
		controller = new AdminCollectionWidgetController(model);
		
		// then renderer
		renderer = new AdminCollectionWidgetRenderer(controller);
		model.addListener(renderer);
		
		// init model
		model.setTitle(title);
		model.setUrl(url);
		
		// init widget
		this.initWidget(renderer.getCanvas());
		
	}
	
	public Deferred<VanillaAtomFeed> refreshStatus() {
		return controller.refreshStatus();
	}
	
	public Deferred<Void> createCollection() {
		return controller.createCollection();
	}
	
	public Label getTitleLabel() {
		return this.renderer.titleLabel;
	}

	/**
	 * @return
	 */
	public Widget getUrlAnchor() {
		return this.renderer.urlAnchor;
	}

	/**
	 * @return
	 */
	public Widget getActionsPanel() {
		return this.renderer.actionsPanel;
	}

	/**
	 * @return
	 */
	public Widget getStatusCodeLabel() {
		return this.renderer.statusCodeLabel;
	}

	/**
	 * @return
	 */
	public Widget getStatusTextLabel() {
		return this.renderer.statusTextLabel;
	}
	
}
