/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public abstract class DelegatingChassisWidget
	<M, R extends ChassisWidgetRenderer<M>> 
	extends ChassisWidget {
	
	
	
	
	private Log log = LogFactory.getLog(DelegatingChassisWidget.class);
	protected R renderer;
	protected M model;
	
	
	
	
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
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");

		// delegate to renderer
		this.renderer.unbindUI();

		log.leave();

	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#destroy()
	 */
	@Override
	public void destroy() {
		log.enter("destroy");

		this.unbindUI();

		// TODO anything else?

		log.leave();
	}
	
	
	
	

}
