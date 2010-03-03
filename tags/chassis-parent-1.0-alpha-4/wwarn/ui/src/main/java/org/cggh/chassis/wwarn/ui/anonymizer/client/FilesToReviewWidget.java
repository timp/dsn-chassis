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
import com.google.gwt.xml.client.Element;


/**
 * @author lee
 *
 */
public class FilesToReviewWidget 
	extends DelegatingWidget<FilesToReviewWidgetModel, FilesToReviewWidgetRenderer> {

	private static final Log log = LogFactory.getLog(FilesToReviewWidget.class);
	
	private FilesToReviewWidgetController controller;
	
	@Override
	protected FilesToReviewWidgetModel createModel() {
		return new FilesToReviewWidgetModel();
	}

	public FilesToReviewWidgetModel getModel() {
		return model;
	}
	

	@Override
	protected FilesToReviewWidgetRenderer createRenderer() {
		return new FilesToReviewWidgetRenderer(this);
	}

	public void init() {

		log.enter("init");		
		
		super.init();
		
		this.controller = new FilesToReviewWidgetController(this, this.model);	
		
		this.renderer.setController(this.controller);
		
		// In order to ready the widget's view, call a refresh before presenting.

		log.leave();
	}
	
	@Override
	public void refresh() {
		
		log.enter("refresh");
		
		retrieveFilesToReview();
		
		log.leave();
	}

	public Deferred<Document> retrieveFilesToReview() {
		
		log.enter("retrieveFilesToReview");
		
		Deferred<Document> deferredDocument = this.controller.retrieveFilesToReview();
		
		log.leave();
		
		return deferredDocument;
		
		
	}	

	public final WidgetEventChannel reviewFileNavigationEventChannel = new WidgetEventChannel(this);

	
	public Element getFileToBeReviewedEntryElement() { 
		return model.getFileToBeReviewedEntryElement();
	}
	
}
