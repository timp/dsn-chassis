/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.userdetails.client;

import org.cggh.chassis.generic.client.gwt.configuration.client.ChassisRole;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentRoleChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentRoleChangeHandler;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentUserChangeEvent;
import org.cggh.chassis.generic.client.gwt.widget.userdetails.client.NewUserDetailsWidgetModel.CurrentUserChangeHandler;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.user.transfer.UserDetailsTO;
import org.cggh.chassis.generic.widget.client.ChassisWidget;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class NewUserDetailsWidget extends ChassisWidget {
	
	
	
	
	private Log log;
	private NewUserDetailsWidgetModel model;
	private NewUserDetailsWidgetRenderer renderer;
	private NewUserDetailsWidgetController controller;

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#getName()
	 */
	@Override
	protected String getName() {
		return "userDetailsWidget";
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		log = LogFactory.getLog(NewUserDetailsWidget.class); // instantiate here because called from superclass constructor
		log.enter("init");

		log.debug("instantiate a model");
		this.model = new NewUserDetailsWidgetModel(this);
		
		log.debug("instantiate a controller");
		this.controller = new NewUserDetailsWidgetController(this.model);
		
		log.debug("instantiate a renderer");
		this.renderer = new NewUserDetailsWidgetRenderer();
		
		log.debug("set renderer canvas");
		this.renderer.setCanvas(this.contentBox);
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		// delegate to renderer
		this.renderer.renderUI();

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// delegate to renderer
		this.renderer.bindUI(this.model);

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// delegate to renderer
		this.renderer.syncUI();

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");

		// unbind
		this.unbindUI();

		// TODO anything else?

		log.leave();

	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");

		// delegate to renderer
		this.renderer.unbindUI();

		log.leave();

	}




	public Deferred<UserDetailsTO> refreshCurrentUserDetails() {
		log.enter("refreshCurrentUserDetails");

		// delegate to controller
		Deferred<UserDetailsTO> deferredUser = this.controller.refreshCurrentUserDetails();
		
		log.leave();
		return deferredUser;
	}

	
	
	
	public void setCurrentRole(ChassisRole role) {
		log.enter("setCurrentRole");
		
		// delegate to controller
		this.controller.setCurrentRole(role);
		
		log.leave();
	}
	
	
	
	
	
	public HandlerRegistration addCurrentUserChangeHandler(CurrentUserChangeHandler h) {
		return this.model.addChangeHandler(h, CurrentUserChangeEvent.TYPE);
	}
	
	
	
	
	
	public HandlerRegistration addCurrentRoleChangeHandler(CurrentRoleChangeHandler h) {
		return this.model.addChangeHandler(h, CurrentRoleChangeEvent.TYPE);
	}
	
	
	
	
	
}
