/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

/**
 * @author timp
 *
 */
public class CuratorHomeWidget 
	extends DelegatingWidget<CuratorHomeWidgetModel, CuratorHomeWidgetRenderer> {

	private static final Log log = LogFactory.getLog(CuratorHomeWidget.class);
	

	private CuratorHomeWidgetController controller;
		private ListStudiesWidget listStudiesWidget;


	@Override
	protected CuratorHomeWidgetModel createModel() {
		return new CuratorHomeWidgetModel();
	}

	public CuratorHomeWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected CuratorHomeWidgetRenderer createRenderer() {
		return new CuratorHomeWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new CuratorHomeWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
	}
	
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

	

}
