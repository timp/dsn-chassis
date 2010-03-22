
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.QueryParams;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

public class ViewStudyWidgetController {
	
	
	private Log log = LogFactory.getLog(ViewStudyWidgetController.class);
	
	
	private ViewStudyWidget owner;
	private ViewStudyWidgetModel model;


	public Deferred<Element> retrieveStudy() {
		
		log.enter("retrieveStudy");
		
		// Set the model's status to pending.
		model.status.set(ViewStudyWidgetModel.STATUS_RETRIEVE_STUDY_PENDING);

		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_ID, model.studyID.get());		
		
		String studyQueryServiceUrl = Config.get(Config.QUERY_STUDIES_URL) + qp.toUrlQueryString();
		
		log.debug("studyQueryServiceUrl: " + studyQueryServiceUrl);

		log.debug("make get feed request");
		Deferred<Document> deferredDoc = Atom.getFeed(studyQueryServiceUrl);
		
		Deferred<Element> deferredStudyElement = deferredDoc.adapt(new Function<Document, Element>() {

			public Element apply(Document resultsFeedDoc) {
				Element entryElement = AtomHelper.getFirstEntry(resultsFeedDoc.getDocumentElement());
				return entryElement;
			}
			
		});		
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredDoc.addCallback(new RetrieveStudyCallback());
		deferredDoc.addErrback(new DefaultErrback());
		
		log.leave();
		return deferredStudyElement;
	}
	
	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();

		if (model.studyID.get() != null) {
			
			log.debug("model.studyID.get() != null");
			
			Deferred<Element> chain = retrieveStudy();
			
			chain.addCallback(new RetrieveStudyCallback());
			
			
			
			//chain.addCallback(new RetrieveSubmissionCallback());
			
			//chain.addCallback(new RetrieveQuestionnaireCallback());
			
			

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




	
	private class RetrieveStudyCallback implements Function<Element, Element> {

		public Element apply(Element studyEntryElement) {
			
			log.enter("apply");
			
			model.studyEntryElement.set(studyEntryElement);
			
			if (studyEntryElement != null) {
				
				model.status.set(ViewStudyWidgetModel.STATUS_STUDY_RETRIEVED);

			} else {
			
				model.status.set(ViewStudyWidgetModel.STATUS_STUDY_NOT_FOUND);
			}
			
			log.leave();
			
			return studyEntryElement;
		}
		
	}
	
	
     	 
//	private StudySummaryWidget studySummaryWidget;
//	private ViewStudyMetadataWidget viewStudyMetadataWidget;
//	private ListSubmissionsWidget listSubmissionsWidget;
//	private ListCurationsWidget listCurationsWidget;

 
	
	
	public ViewStudyWidgetController(ViewStudyWidget owner, ViewStudyWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	

}
