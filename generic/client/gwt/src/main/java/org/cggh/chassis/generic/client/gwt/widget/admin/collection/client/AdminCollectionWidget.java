/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.admin.collection.client;

import org.cggh.chassis.generic.atom.client.vanilla.VanillaAtomFeed;
import org.cggh.chassis.generic.twisted.client.Deferred;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author aliman
 *
 */
public class AdminCollectionWidget extends Composite {
	
	private AdminCollectionWidgetModel model;
	private AdminCollectionWidgetController controller;
	private AdminCollectionWidgetDefaultRenderer renderer;

	public AdminCollectionWidget(String title, String url) {
		
		// model first
		model = new AdminCollectionWidgetModel();
		
		// then controller
		controller = new AdminCollectionWidgetController(model);
		
		// then renderer
		renderer = new AdminCollectionWidgetDefaultRenderer(controller);
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
	
}
