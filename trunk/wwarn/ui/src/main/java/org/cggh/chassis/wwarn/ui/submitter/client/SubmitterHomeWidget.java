/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.DelegatingWidget;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.xml.client.Document;


/**
 * @author lee
 *
 */
public class SubmitterHomeWidget 
	extends DelegatingWidget<SubmitterHomeWidgetModel, SubmitterHomeWidgetRenderer> {

	// Give this widget (owner) its log.
	private static final Log log = LogFactory.getLog(SubmitterHomeWidget.class);

	// Give this widget its undefined controller.
	private SubmitterHomeWidgetController controller;
	
	// Allow this widget to create its model.
	@Override
	protected SubmitterHomeWidgetModel createModel() {
		return new SubmitterHomeWidgetModel();
	}

	// Allow this widget to create its renderer.
	@Override
	protected SubmitterHomeWidgetRenderer createRenderer() {
		return new SubmitterHomeWidgetRenderer(this);
	}

	
	// Allow this widget to construct itself.
	public SubmitterHomeWidget() {
		
		// Call the constructors of all super classes to initialise them.
		// Java does this automatically if omitted?
		super();
		
		// Give this widget its controller, which needs this widget's model.
		this.controller = new SubmitterHomeWidgetController(this, this.model);


	}
	
	@Override
	public void refresh() {
		retrieveSubmissions();
	}
	
	
	// Allow this widget to retrieve and refresh stuff, via its controller.
	public Deferred<Document> retrieveSubmissions() {
	
		log.enter("retrieveSubmissions");
		
		// Retrieve the submissions as a deferred document from the controller.
		Deferred<Document> deferredDocument = this.controller.retrieveSubmissions();
		
		log.leave();
		
		// Return the deferred document to ...
		return deferredDocument;
		
		
	}
		
	
	
	// Define methods for handler registration, supplied SomeHandler and returning this.addHandler(SomeHandler, SomeEvent.TYPE).

	public HandlerRegistration addSubmitDataNavigationHandler(SubmitDataNavigationHandler h) {
		return this.addHandler(h, SubmitDataNavigationEvent.TYPE);
	}
	

}
