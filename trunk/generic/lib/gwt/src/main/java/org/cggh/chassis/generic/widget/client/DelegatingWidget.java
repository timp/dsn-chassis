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
	
	
	
	
	private Log log;
	protected R renderer;
	protected M model;
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.ChassisWidget#init()
	 */
	@Override
	public void init() {
		ensureLog();
		log.enter("init");

		this.model = createModel();
		this.renderer = createRenderer();
		
		if (renderer != null) this.renderer.setCanvas(this.contentBox);
		else log.error("renderer is null, cannot set canvas", new NullPointerException());
		
		log.leave();
	}
	
	
	

	
	
	/**
	 * @return
	 */
	protected abstract M createModel();


	
	
	/**
	 * @return
	 */
	protected abstract R createRenderer();





	/**
	 * 
	 */
	private void ensureLog() {
		if (log == null) log = LogFactory.getLog(DelegatingWidget.class);
	}






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
