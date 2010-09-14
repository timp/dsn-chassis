/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;
import org.cggh.chassis.generic.widget.client.WidgetEventChannel;

import com.google.gwt.xml.client.Document;


/**
 * @author lee
 *
 */
public class SubmitterHomeWidget 
	extends DelegatingWidget<SubmitterHomeWidgetModel, SubmitterHomeWidgetRenderer> {

	private static final Log log = LogFactory.getLog(SubmitterHomeWidget.class);

	private SubmitterHomeWidgetController controller;
	
	@Override
	protected SubmitterHomeWidgetModel createModel() {
		return new SubmitterHomeWidgetModel();
	}

	public SubmitterHomeWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected SubmitterHomeWidgetRenderer createRenderer() {
		return new SubmitterHomeWidgetRenderer(this);
	}

	// Using init() rather than constructor because reset() uses init().
	public void init() {
		
		super.init();
		
		this.controller = new SubmitterHomeWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
		retrieveSubmissions();
	}
	
	
	public Deferred<Document> retrieveSubmissions() {
	
		log.enter("retrieveSubmissions");
		
		Deferred<Document> deferredDocument = this.controller.retrieveSubmissions();
		
		log.leave();
		
		return deferredDocument;
		
		
	}
		
	
	
	public final WidgetEventChannel submitDataNavigationEventChannel = new WidgetEventChannel(this);

	
	

}
