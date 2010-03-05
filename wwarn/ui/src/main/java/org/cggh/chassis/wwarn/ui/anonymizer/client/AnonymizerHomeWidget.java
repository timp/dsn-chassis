/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;



/**
 * @author lee
 *
 */
public class AnonymizerHomeWidget 
	extends DelegatingWidget<AnonymizerHomeWidgetModel, AnonymizerHomeWidgetRenderer> {

	private static final Log log = LogFactory.getLog(AnonymizerHomeWidget.class);

	private AnonymizerHomeWidgetController controller;
	
	@Override
	protected AnonymizerHomeWidgetModel createModel() {
		return new AnonymizerHomeWidgetModel();
	}

	public AnonymizerHomeWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected AnonymizerHomeWidgetRenderer createRenderer() {
		return new AnonymizerHomeWidgetRenderer(this);
	}

	public void init() {
		
		super.init();
		
		this.setController(new AnonymizerHomeWidgetController(this, this.model));
		
	}
	
	@Override
	public void refresh() {
		
		log.enter("refresh");

		assert (this.renderer != null);
		assert (this.renderer.filesToReviewWidgetUiField != null);
		assert (this.renderer.filesToCleanWidgetUiField != null);
		
		this.renderer.filesToReviewWidgetUiField.refresh();
		this.renderer.filesToCleanWidgetUiField.refresh();
		
		log.leave();
	}

	public void setController(AnonymizerHomeWidgetController controller) {
		this.controller = controller;
	}

	public AnonymizerHomeWidgetController getController() {
		return controller;
	}

	public final WidgetEventChannel reviewFileNavigationEventChannel = new WidgetEventChannel(this);		
	public final WidgetEventChannel cleanFileNavigationEventChannel = new WidgetEventChannel(this);	
}
