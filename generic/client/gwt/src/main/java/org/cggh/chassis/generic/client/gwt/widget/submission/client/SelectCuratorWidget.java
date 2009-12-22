/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

/**
 * @author aliman
 *
 */
public class SelectCuratorWidget 
	extends DelegatingWidget<SelectCuratorWidgetModel, SelectCuratorWidgetRenderer> {

	
	
	private Log log = LogFactory.getLog(SelectCuratorWidget.class);
	private SelectCuratorWidgetController controller;
	
	
	
	@Override
	protected SelectCuratorWidgetModel createModel() {
		return new SelectCuratorWidgetModel();
	}

	
	
	
	@Override
	protected SelectCuratorWidgetRenderer createRenderer() {
		return new SelectCuratorWidgetRenderer();
	}

	
	
	
	@Override
	public void init() {
		
		super.init(); // will instantiate the model and renderer
		
		this.controller = new SelectCuratorWidgetController(this, this.model);
		this.renderer.setController(this.controller);
	}



	/**
	 * @param entry
	 */
	public void setSubmissionEntry(SubmissionEntry entry) {
		log.enter("setSubmissionEntry");

		// store in model
		this.model.setSubmissionEntry(entry);
		
		log.leave();
	}




	/**
	 * 
	 */
	public void refreshCurators() {
		log.enter("refreshCurators");
		
		// delegate to a controller
		this.controller.refreshCurators();
		
		log.leave();
	}


}
