/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.application.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class AdministratorPerspective extends Perspective {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private AdministratorPerspectiveRenderer renderer;
	private AdministratorPerspectiveController controller;
	private AdministratorPerspectiveModel model;
	
	
	
	public AdministratorPerspective() {
		log.enter("<constructor>");
		
		this.model = new AdministratorPerspectiveModel();
		
		this.controller = new AdministratorPerspectiveController(this.model);
		
		this.renderer = new AdministratorPerspectiveDefaultRenderer(this, this.controller);
		
		this.model.addListener(this.renderer);
		
		this.controller.show(AdministratorHomeWidget.class.getName());
		
		this.initWidget(this.renderer.getCanvas());
		
		log.leave();
	}


}
