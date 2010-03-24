/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

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
public class FilesToCleanWidget 
	extends DelegatingWidget<FilesToCleanWidgetModel, FilesToCleanWidgetRenderer> {

	private static final Log log = LogFactory.getLog(FilesToCleanWidget.class);
	
	private FilesToCleanWidgetController controller;
	
	@Override
	protected FilesToCleanWidgetModel createModel() {
		return new FilesToCleanWidgetModel();
	}

	public FilesToCleanWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected FilesToCleanWidgetRenderer createRenderer() {
		return new FilesToCleanWidgetRenderer(this);
	}

	public void init() {
		
		log.enter("init");		
		
		super.init();
		
		this.controller = new FilesToCleanWidgetController(this, this.model);

		log.leave();
	}
	
	@Override
	public void refresh() {
		
		log.enter("refresh");
		
		retrieveFilesToClean();
		
		log.leave();
	}

	public Deferred<Document> retrieveFilesToClean() {
		
		log.enter("retrieveFilesToClean");
		
		Deferred<Document> deferredDocument = this.controller.retrieveFilesToClean();
		
		log.leave();
		
		return deferredDocument;
		
		
	}	

	public final WidgetEventChannel cleanFileNavigationEventChannel = new WidgetEventChannel(this);		

}
