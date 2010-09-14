/**
 * 
 */
package org.cggh.chassis.wwarn.ui.submitter.client;

import java.util.List;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.async.client.QueryParams;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.Chassis;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class SubmitWidgetController {

	
	
	private Log log = LogFactory.getLog(SubmitWidgetController.class);
	private SubmitWidget owner;
	private SubmitWidgetModel model;

	
	
	public SubmitWidgetController(SubmitWidget owner, SubmitWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}
	
	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
		
		if (model.getSelectedStudyId() != null) {
			
			// kick off the retrieve study 
			Deferred<Element> chain = retrieveStudy();
			
			// retrieve study callback will kick off retrieve uploads
			chain.addCallback(new RetrieveStudyCallback());
			
			// handle uploads
			chain.addCallback(new RetrieveFilesCallback());
			
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
		
		else {
			
			// nothing to refresh, callback immediately
			deferredOwner.callback(owner);
			
		}	
		
		return deferredOwner;
		
	}
	
	
	
	
	private Deferred<Element> retrieveStudy() {
		log.enter("retrieveStudy");
		
		model.setStatus(SubmitWidgetModel.STATUS_RETRIEVE_STUDY_PENDING);
		
		// assume id is really url
		Deferred<Document> deferredEntryDoc = Atom.getEntry(model.getSelectedStudyId());
		
		Deferred<Element> deferredEntryElement = deferredEntryDoc.adapt(new Function<Document, Element>() {
			@Override
			public Element apply(Document in) {
				return in.getDocumentElement();
			}
		});

//		QueryParams qp = new QueryParams();
//		qp.put(Chassis.QUERYPARAM_ID, model.getSelectedStudyId());
//		
//		String url = Config.get(Config.QUERY_STUDIES_URL) + qp.toUrlQueryString();
//		log.debug("url: "+url);
//
//		log.debug("make get feed request");
//		Deferred<Document> deferredResultsFeedDoc = Atom.getFeed(url);
//		
//		Deferred<Element> deferredStudyElement = deferredResultsFeedDoc.adapt(new Function<Document, Element>() {
//
//			public Element apply(Document resultsFeedDoc) {
//				Element entryElement = AtomHelper.getFirstEntry(resultsFeedDoc.getDocumentElement());
//				return entryElement;
//			}
//			
//		});
		
		log.leave();
		return deferredEntryElement;
	}
	
	
	
	
	private class RetrieveStudyCallback implements Function<Element, Deferred<Document>> {

		public Deferred<Document> apply(Element studyEntryElement) {
			
			model.setStudyEntryElement(studyEntryElement);
			
			Deferred<Document> deferredFilesFeedDoc;
			
			if (studyEntryElement != null) {

				// we have a study, so lets try retrieving the list of files uploaded so far
				deferredFilesFeedDoc = retrieveFiles();
				
			}
			
			else {
				
				model.setStatus(SubmitWidgetModel.STATUS_STUDY_NOT_FOUND);
				deferredFilesFeedDoc = new Deferred<Document>();
				deferredFilesFeedDoc.callback(null);

			}
			
			// return the deferred feed of files uploaded, for chaining of callbacks
			return deferredFilesFeedDoc;
		}
		
	}
	
	
	
	
	private Deferred<Document> retrieveFiles() {
		log.enter("retrieveFiles");
		
		model.setStatus(SubmitWidgetModel.STATUS_RETRIEVE_UPLOADED_FILES_PENDING);

		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_SUBMITTED, Chassis.QUERYPARAMVALUE_NO);

		String url = Config.get(Config.QUERY_MEDIA_URL) + qp.toUrlQueryString();
		log.debug("url: "+url);

		log.debug("make get feed request");
		Deferred<Document> deferredResultsFeedDoc = Atom.getFeed(url);
		
		log.leave();
		return deferredResultsFeedDoc;
	}



	private class RetrieveFilesCallback implements Function<Document, Document> {

		public Document apply(Document uploadFeedDoc) {

			model.setUploadFeedDoc(uploadFeedDoc);

			if (uploadFeedDoc != null) {
				
				model.setStatus(SubmitWidgetModel.STATUS_READY_FOR_INTERACTION);

			}
			
			return uploadFeedDoc;
			
		}
		
	}



	
	public void proceed() {
		
		Deferred<Document> deferredSubmissionEntryDoc = postSubmission();
		deferredSubmissionEntryDoc.addCallback(new PostSubmissionCallback());
		deferredSubmissionEntryDoc.addErrback(new DefaultErrback());
		
	}
	
	
	
	
	private Deferred<Document> postSubmission() {

		List<Element> files = AtomHelper.getEntries(model.getFilesFeedDoc().getDocumentElement());
		
		assert files.size() > 0;
		
		model.setStatus(SubmitWidgetModel.STATUS_SUBMISSION_PENDING);
		
		Document submissionEntryDoc = AtomHelper.createEntryDoc();
		Element submissionEntryElement = submissionEntryDoc.getDocumentElement();
		
		// populate submission entry 
		
		// author email
		String email = Config.get(Config.USER_EMAIL);
		AtomHelper.addAuthor(submissionEntryElement, email);
		
		// study origin link
		String studyUrl = AtomHelper.getEditLinkHrefAttr(model.getStudyEntryElement());
		AtomHelper.addLink(submissionEntryElement, Chassis.REL_ORIGINSTUDY, studyUrl);
		
		// file links
		for (Element fileEntryElement : AtomHelper.getEntries(model.getFilesFeedDoc().getDocumentElement())) {
			
			String fileUrl = AtomHelper.getEditLinkHrefAttr(fileEntryElement);
			AtomHelper.addLink(submissionEntryElement, Chassis.REL_SUBMISSIONPART, fileUrl);
			
		}
		
		String submissionsCollectionUrl = Config.get(Config.COLLECTION_SUBMISSIONS_URL);
		
		return Atom.postEntry(submissionsCollectionUrl, submissionEntryDoc);
	}

	
	
	
	private class PostSubmissionCallback implements Function<Document, Document> {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public Document apply(Document submissionEntryDoc) {
			model.setSubmissionId(AtomHelper.getId(submissionEntryDoc.getDocumentElement()));
			model.setStatus(SubmitWidgetModel.STATUS_READY_FOR_INTERACTION);
			owner.fireEvent(new ProceedActionEvent());
			return submissionEntryDoc;
		}
		
	}



	public void stepBack() {
		owner.fireEvent(new StepBackNavigationEvent());
	}


	
	
	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(UploadFilesWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}




	/**
	 * 
	 */
	public void backToStart() {
		owner.fireEvent(new BackToStartNavigationEvent());
	}



	
}
