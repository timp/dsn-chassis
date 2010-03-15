/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

import com.google.gwt.xml.client.Document;

/**
 * @author timp
 *
 */
public class ListStudiesWidget 
	extends DelegatingWidget<ListStudiesWidgetModel, ListStudiesWidgetRenderer> {

	private static final Log log = LogFactory.getLog(ListStudiesWidget.class);
	

	private ListStudiesWidgetController controller;
		
   
	@Override
	protected ListStudiesWidgetModel createModel() {
		return new ListStudiesWidgetModel();
	}

	public ListStudiesWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected ListStudiesWidgetRenderer createRenderer() {
		return new ListStudiesWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new ListStudiesWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
