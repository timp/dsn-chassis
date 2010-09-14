
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;

import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * BE SURE TO EDIT THE TEMPLATE NOT THE RENDERED RESULT
 *
 * DELETE_TO_MANUALLY_EDIT
 *
 *
 * 
 *
 * @author timp
 *
 */
public class ListStudiesWidgetController {
	
	
	private Log log = LogFactory.getLog(ListStudiesWidgetController.class);
	
	
	private ListStudiesWidget owner;
	private ListStudiesWidgetModel model;

	public ListStudiesWidgetController(ListStudiesWidget owner, ListStudiesWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}


    					
	
       	            
			
	public Deferred<Document> retrieveStudyFeed() {
		log.enter("retrieveStudyFeed");
		
		model.status.set(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		String queryServiceUrl = Config.get(Config.COLLECTION_STUDIES_URL);

		log.debug("QUERY_STUDYFEED_URL:"+queryServiceUrl);
		
		Deferred<Document> deferredDoc = Atom.getFeed(queryServiceUrl);
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredDoc.addCallback(new RetrieveStudyFeedCallback());
		deferredDoc.addErrback(new DefaultErrback());
		
		log.leave();
		return deferredDoc;
	}
	
	private class RetrieveStudyFeedCallback implements Function<Document, Document> {

		@Override
		public Document apply(Document in) {
			log.debug("RetrieveStudyFeedCallback.apply setting model status to " + AsyncWidgetModel.STATUS_READY);
			model.status.set(AsyncWidgetModel.STATUS_READY);
			model.studyFeed.set(in);
			return in;
		}

	}
	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();

			
		Deferred<Document> chain = retrieveStudyFeed();
			
		// handle errors
		chain.addErrback(new DefaultErrback());
			
		// finally callback with owner, in any case
		chain.addBoth(new Function<Object, Object>() {

			public Object apply(Object in) {
				deferredOwner.callback(owner);
				return in;
			}
				
		});

		log.leave();
		return deferredOwner;
		
	}

	
	

                	  
	
	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);

			// TODO Do we want stack trace?
			// TODO Should we be getting the message from ErrorEvent ?
		    //StringWriter sw = new StringWriter();
		    //PrintWriter pw = new PrintWriter(sw);
		    //in.printStackTrace(pw);
			//model.message.set(in.getMessage() +
			//	"------\r\n" + sw.toString() + "------\r\n");

			model.message.set(in.getMessage());
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}
	

	
	

}
