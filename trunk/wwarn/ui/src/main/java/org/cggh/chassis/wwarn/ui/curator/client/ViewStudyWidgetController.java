
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 *
 * @author timp
 *
 */
public class ViewStudyWidgetController {
	
	
	private Log log = LogFactory.getLog(ViewStudyWidgetController.class);
	
	
	private ViewStudyWidget owner;
	private ViewStudyWidgetModel model;

	public ViewStudyWidgetController(ViewStudyWidget owner, ViewStudyWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	public Deferred<Element> retrieveStudyEntry() {
		log.enter("retrieveStudyEntry");
		
		model.status.set(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		Deferred<Element> deferredElement;
		
		log.debug("model.StudyUrl"+ model.studyUrl.get());
		if (!model.studyUrl.isNull()) {
			
			deferredElement = Atom.getEntry(model.studyUrl.get()).adapt(new Function<Document, Element>() {

				public Element apply(Document in) {
					log.debug("retrieveStudyEntry.apply returning " + in.getDocumentElement());
					return in.getDocumentElement();
				}
				
			});
			
		}
		else {
			deferredElement = new Deferred<Element>();
			deferredElement.callback(null);
		}
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredElement.addCallback(new RetrieveStudyEntryCallback());
		deferredElement.addErrback(new DefaultErrback());
		
		log.leave();
		return deferredElement;
	}
	
	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
		
		if (model.studyUrl.get() != null) {
			Deferred<Element> chain = retrieveStudyEntry();
			
			chain.addCallback(new RetrieveStudyEntryCallback());
			
			// handle errors
			chain.addErrback(new DefaultErrback());
			
			// finally callback with owner, in any case
			chain.addBoth(new Function<Object, Object>() {

				public Object apply(Object in) {
					deferredOwner.callback(owner);
					return in;
				}
				
			});

		}
		
		log.leave();
		return deferredOwner;
		
	}

	
	
		

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}




	
	private class RetrieveStudyEntryCallback implements Function<Element, Element> {

		public Element apply(Element studyEntryElement) {
			log.enter("apply");

			
			if (studyEntryElement != null) {

				model.studyEntry.set(studyEntryElement);
				// we have one
				log.debug("RetrieveStudyEntryCallback.apply not null");
				
			}
			
			else {
				
				log.debug("RetrieveStudyEntryCallback.apply null");

			}
			
			log.leave();
			return studyEntryElement;
		}
		
	}
	
	
        			
	
	

}
