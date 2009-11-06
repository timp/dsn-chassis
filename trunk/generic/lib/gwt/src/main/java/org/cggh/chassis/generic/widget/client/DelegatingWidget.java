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
public abstract class DelegatingWidget
	<M, R extends ChassisWidgetRenderer<M>> 
	extends ChassisWidget {
	
	
	
	
	private Log log = LogFactory.getLog(DelegatingWidget.class);
	protected R renderer;
	protected M model;
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#renderUI()
	 */
	@Override
	protected void renderUI() {
		log.enter("renderUI");

		// delegate to renderer
		if (this.renderer != null) this.renderer.renderUI();
		else log.warn("renderer is null");

		log.leave();
	}


	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#bindUI()
	 */
	@Override
	protected void bindUI() {
		log.enter("bindUI");

		// delegate to renderer
		if (this.renderer != null) this.renderer.bindUI(this.model);
		else log.warn("renderer is null");

		log.leave();
	}
	
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#syncUI()
	 */
	@Override
	protected void syncUI() {
		log.enter("syncUI");

		// delegate to renderer
		if (this.renderer != null) this.renderer.syncUI();
		else log.warn("renderer is null");

		log.leave();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#unbindUI()
	 */
	@Override
	protected void unbindUI() {
		log.enter("unbindUI");

		// delegate to renderer
		if (this.renderer != null) this.renderer.unbindUI();
		else log.warn("renderer is null");
		
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
