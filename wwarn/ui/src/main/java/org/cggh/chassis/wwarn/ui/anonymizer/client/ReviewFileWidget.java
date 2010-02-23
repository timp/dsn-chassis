/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;


/**
 * @author lee
 *
 */
public class ReviewFileWidget 
	extends DelegatingWidget<ReviewFileWidgetModel, ReviewFileWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ReviewFileWidget.class);
	
	//private ReviewFileWidgetController controller;
	
	@Override
	protected ReviewFileWidgetModel createModel() {
		return new ReviewFileWidgetModel();
	}

	public ReviewFileWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ReviewFileWidgetRenderer createRenderer() {
		return new ReviewFileWidgetRenderer(this);
	}

	public void init() {

		log.enter("init");		
		
		super.init();
		
		//this.controller = new ReviewFileWidgetController(this, this.model);
		

		log.leave();
	}
	
	@Override
	public void refresh() {
		
		log.enter("refresh");
		
		
		log.leave();
	}




}
