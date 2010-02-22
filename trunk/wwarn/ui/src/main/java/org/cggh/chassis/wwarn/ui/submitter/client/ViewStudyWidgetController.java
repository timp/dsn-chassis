/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.QueryParams;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class ViewStudyWidgetController {

	
	
	private static final Log log = LogFactory.getLog(ViewStudyWidgetController.class);
	
	
	
	private ViewStudyWidget owner;
	private ViewStudyWidgetModel model;

	

	public ViewStudyWidgetController(ViewStudyWidget owner, ViewStudyWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	/**
	 * @param studyId
	 * @return
	 */
	public Deferred<ViewStudyWidget> retrieveStudy(String studyId) {
		log.enter("retrieveStudy");
		
		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_ID, studyId);
		
		String url = Config.get(Config.QUERY_STUDIES_URL) + qp.toUrlQueryString();
		log.debug("url: "+url);

		log.debug("make get feed request");
		Deferred<Document> deferredFeedDoc = Atom.getFeed(url);
		
		deferredFeedDoc.addCallback(new RetrieveStudyCallback());
		
		deferredFeedDoc.addErrback(new RetrieveStudyErrback());
		
		Deferred<ViewStudyWidget> deferredOwner = deferredFeedDoc.adapt(new Function<Document, ViewStudyWidget>() {

			public ViewStudyWidget apply(Document in) {
				return owner;
			}
			
		});
		
		log.leave();
		return deferredOwner;
	}

	
	
	
	private class RetrieveStudyCallback implements Function<Document, Document> {

		private Log log = LogFactory.getLog(RetrieveStudyCallback.class);
		
		public Document apply(Document feedDoc) {
			log.enter("apply");
			
			Element entryElement = AtomHelper.getFirstEntry(feedDoc.getDocumentElement());
			model.setEntry(entryElement);
			
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			log.leave();
			return feedDoc;
		}
		
	}
	
	
	
	
	private class RetrieveStudyErrback implements Function<Throwable, Throwable> {

		private Log log = LogFactory.getLog(RetrieveStudyErrback.class);

		public Throwable apply(Throwable in) {
			log.enter("apply");
			
			log.error("error retrieving study", in);
			
			model.setStatus(AsyncWidgetModel.STATUS_ERROR);
			
			ErrorEvent e = new ErrorEvent(in);
			e.setMessage("error retrieving study");
			owner.fireEvent(e);
			
			log.leave();
			return null;
		}
		
	}
	
	
	
}
