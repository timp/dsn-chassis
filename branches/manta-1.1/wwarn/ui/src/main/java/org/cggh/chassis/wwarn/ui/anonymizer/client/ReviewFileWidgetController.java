/**
 * 
 */
package org.cggh.chassis.wwarn.ui.anonymizer.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.QueryParams;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.anonymizer.client.BackToStartNavigationEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;



/**
 * @author lee
 *
 */
public class ReviewFileWidgetController {

	private static final Log log = LogFactory.getLog(ReviewFileWidgetController.class);
	
	private ReviewFileWidgetModel model;
	private ReviewFileWidget owner;	

	/**
	 * @param owner
	 * @param model
	 */
	public ReviewFileWidgetController(
			ReviewFileWidget owner,
			ReviewFileWidgetModel model) {
		
		this.owner = owner;
		this.model = model;

	}

	
	public void backToStart() {
		
		log.enter("backToStart");
		
		owner.fireEvent(new BackToStartNavigationEvent());
		
		log.leave();
	}


	public void reviewFileAndBackToStart(String reviewSubjectHref, String reviewOutcome, String reviewNotes) {
		
		log.enter("reviewFileAndBackToStart");
		
		log.debug("still using reviewSubjectHref: " + reviewSubjectHref);
		
		Deferred<Document> deferredReviewEntryDoc = createReview(reviewSubjectHref, reviewOutcome, reviewNotes);
		deferredReviewEntryDoc.addCallback(new Function<Document, Document>() {
			public Document apply(Document in) {
				
				owner.fireEvent(new BackToStartNavigationEvent());
				return in;
		}
		});
		deferredReviewEntryDoc.addErrback(new DefaultErrback());
		
		log.leave();
		
	}
	
	private Deferred<Document> createReview(String reviewSubjectHref, String reviewOutcome, String reviewNotes) {

		log.enter("createReview");
		
		log.debug("still using reviewSubjectHref: " + reviewSubjectHref);
		
		Document reviewEntryDoc = AtomHelper.createEntryDoc();
		Element reviewEntryElement = reviewEntryDoc.getDocumentElement();
		AtomHelper.addAuthor(reviewEntryElement, Config.get(Config.USER_EMAIL));
		
		AtomHelper.addLink(reviewEntryElement, Chassis.REL_REVIEWSUBJECT, reviewSubjectHref);
		
		Element reviewElement = ChassisHelper.createReview();
		reviewElement.setAttribute("type", Chassis.TERM_PERSONALDATAREVIEW);
		
		log.debug("setting outcome value to: " + reviewOutcome);
		ChassisHelper.setOutcome(reviewElement, reviewOutcome);

		AtomHelper.setContent(reviewEntryElement, reviewElement);
		
		AtomHelper.setSummary(reviewEntryElement, reviewNotes);
		
		log.leave();
		
		return Atom.postEntry(Config.get(Config.COLLECTION_REVIEWS_URL), reviewEntryDoc);
	}

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(ReviewFileWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}


	public Deferred<Element> retrieveFileToBeReviewedEntryElement() {
		
		log.enter("retrieveFileToBeReviewed");
		
		model.setStatus(ReviewFileWidgetModel.STATUS_RETRIEVE_FILE_TO_BE_REVIEWED_PENDING);
		
		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_ID, model.fileToBeReviewedID.get());
		
		String url = Config.get(Config.QUERY_FILESTOREVIEW_URL) + qp.toUrlQueryString();
		
		log.debug("url: " + url);

		log.debug("make get feed request");
		Deferred<Document> deferredResultsFeedDoc = Atom.getFeed(url);
		
		Deferred<Element> deferredMediaElement = deferredResultsFeedDoc.adapt(new Function<Document, Element>() {

			public Element apply(Document resultsFeedDoc) {
				Element entryElement = AtomHelper.getFirstEntry(resultsFeedDoc.getDocumentElement());
				return entryElement;
			}
			
		});
		
		log.leave();
		return deferredMediaElement;
	}



	public Deferred<ChassisWidget> refreshAndCallback() {
		
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
		
		if (model.fileToBeReviewedID.get() != null) {
			
			log.debug("model.fileToBeReviewedID.get() != null");
			 
			Deferred<Element> chain = retrieveFileToBeReviewedEntryElement();
			
			
			chain.addCallback(new RetrieveFileToBeReviewedCallback());
			
			
			chain.addErrback(new DefaultErrback());
			
			
			chain.addBoth(new Function<Object, Object>() {

				public Object apply(Object in) {
					deferredOwner.callback(owner);
					return in;
				}
				
			});
			
		}
		
		else {
			
			log.debug("model.fileToBeReviewedID.get() == null");
			
			deferredOwner.callback(owner);
		}
		
		log.leave();
		return deferredOwner;
	}	

	private class RetrieveFileToBeReviewedCallback implements Function<Element, Deferred<Document>> {

		public Deferred<Document> apply(Element fileToBeReviewedEntryElement) {
			
			log.enter("apply");
			
			model.fileToBeReviewedEntryElement.set(fileToBeReviewedEntryElement);
			
			if (fileToBeReviewedEntryElement != null) {
				
				model.setStatus(ReviewFileWidgetModel.STATUS_FILE_TO_BE_REVIEWED_RETRIEVED);


			} else {
			
				model.setStatus(ReviewFileWidgetModel.STATUS_FILE_TO_BE_REVIEWED_NOT_FOUND);
			}
			
			log.leave();
			
			return null;
			
		}
		
	}	
	
}
