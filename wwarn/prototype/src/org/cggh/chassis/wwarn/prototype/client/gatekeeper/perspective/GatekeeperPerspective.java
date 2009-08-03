/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.gatekeeper.perspective;

import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspective;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveController;
import org.cggh.chassis.wwarn.prototype.client.base.perspective.BasePerspectiveModel;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.allreqs.GatekeeperWidgetAllRequests;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.approvedreqs.GatekeeperWidgetApprovedRequests;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.deniedreqs.GatekeeperWidgetDeniedRequests;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.home.GatekeeperWidgetHome;
import org.cggh.chassis.wwarn.prototype.client.gatekeeper.widget.pendingreqs.GatekeeperWidgetPendingRequests;
import org.cggh.chassis.wwarn.prototype.client.user.RoleNames;
import org.cggh.chassis.wwarn.prototype.client.widget.WidgetFactory;



/**
 * @author aliman
 *
 */
public class GatekeeperPerspective extends BasePerspective {


	
	private Renderer renderer;

	
	
	public GatekeeperPerspective() {
		super();
		this.log.setCurrentClass(GatekeeperPerspective.class.getName());
		this.init();
	}
	

	
	private void init() {
		log.enter("init");
		
		log.trace("register main components");
		WidgetFactory.register(GatekeeperWidgetAllRequests.class.getName(), GatekeeperWidgetAllRequests.creator);
		WidgetFactory.register(GatekeeperWidgetApprovedRequests.class.getName(), GatekeeperWidgetApprovedRequests.creator);
		WidgetFactory.register(GatekeeperWidgetDeniedRequests.class.getName(), GatekeeperWidgetDeniedRequests.creator);
		WidgetFactory.register(GatekeeperWidgetPendingRequests.class.getName(), GatekeeperWidgetPendingRequests.creator);
		WidgetFactory.register(GatekeeperWidgetHome.class.getName(), GatekeeperWidgetHome.creator);

		log.trace("init model");
		model = new BasePerspectiveModel();

		log.trace("init controller");
		controller = new BasePerspectiveController(model, this); 

		log.trace("init renderer");
		renderer = new Renderer(this, controller, model);
		model.addListener(renderer);
		
		log.leave();
	}


	
	public String getRoleName() {
		return RoleNames.GATEKEEPER;
	}


	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String getDefaultMainWidgetName() {
		return GatekeeperWidgetHome.class.getName();
	}


}
