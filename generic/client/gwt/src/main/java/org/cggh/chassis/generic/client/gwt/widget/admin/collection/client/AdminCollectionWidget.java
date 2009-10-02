/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.admin.collection.client;

import org.cggh.chassis.generic.atom.exist.client.protocol.ExistAtomService;
import org.cggh.chassis.generic.atom.exist.client.protocol.impl.ExistAtomServiceImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.format.impl.AtomFactoryImpl;
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
		AtomFactory factory = new AtomFactoryImpl();
		ExistAtomService service = new ExistAtomServiceImpl(factory);
		controller = new AdminCollectionWidgetController(model, service, factory);
		
		// then renderer
		renderer = new AdminCollectionWidgetDefaultRenderer(controller);
		model.addListener(renderer);
		
		// init model
		model.setTitle(title);
		model.setUrl(url);
		
		// init widget
		this.initWidget(renderer.getCanvas());
		
	}
	
	public Deferred<AtomFeed> refreshStatus() {
		return controller.refreshStatus();
	}
	
	public Deferred<Void> createCollection() {
		return controller.createCollection();
	}
	
}
