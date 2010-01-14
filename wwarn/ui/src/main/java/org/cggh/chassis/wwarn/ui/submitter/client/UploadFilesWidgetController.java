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
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * @author aliman
 *
 */
public class UploadFilesWidgetController {

	
	
	

	private Log log = LogFactory.getLog(UploadFilesWidgetController.class);
	private UploadFilesWidget owner;
	private UploadFilesWidgetModel model;
	
	
	
	
	/**
	 * @param owner
	 * @param model
	 */
	public UploadFilesWidgetController(
			UploadFilesWidget owner,
			UploadFilesWidgetModel model) {
		
		this.owner = owner;
		this.model = model;

	}




	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
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
			chain.addBoth(new Function() {

				public Object apply(Object in) {
					deferredOwner.callback(owner);
					return in;
				}
				
			});
			
		}
		
		else {
			deferredOwner.callback(owner);
		}
		
		log.leave();
		return deferredOwner;
	}




	private Deferred<Element> retrieveStudy() {
		log.enter("retrieveStudy");
		
		model.setStatus(UploadFilesWidgetModel.STATUS_RETRIEVE_STUDY_PENDING);
		
		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_ID, model.getSelectedStudyId());
		
		String url = Config.get(Config.QUERY_STUDIES_URL) + qp.toUrlQueryString();
		log.debug("url: "+url);

		log.debug("make get feed request");
		Deferred<Document> deferredResultsFeedDoc = Atom.getFeed(url);
		
		Deferred<Element> deferredStudyElement = deferredResultsFeedDoc.adapt(new Function<Document, Element>() {

			public Element apply(Document resultsFeedDoc) {
				Element entryElement = AtomHelper.getFirstEntry(resultsFeedDoc.getDocumentElement());
				return entryElement;
			}
			
		});
		
		log.leave();
		return deferredStudyElement;
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
				
				model.setStatus(UploadFilesWidgetModel.STATUS_STUDY_NOT_FOUND);
				deferredFilesFeedDoc = new Deferred<Document>();
				deferredFilesFeedDoc.callback(null);

			}
			
			// return the deferred feed of files uploaded, for chaining of callbacks
			return deferredFilesFeedDoc;
		}
		
	}
	
	
	
	
	private Deferred<Document> retrieveFiles() {
		log.enter("retrieveFiles");
		
		model.setStatus(UploadFilesWidgetModel.STATUS_RETRIEVE_UPLOADED_FILES_PENDING);

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
				
				model.setStatus(UploadFilesWidgetModel.STATUS_READY_FOR_INTERACTION);

			}
			
			return uploadFeedDoc;
			
		}
		
	}




	public void submitUploadFileForm(UploadFileForm form) {

		model.setStatus(UploadFilesWidgetModel.STATUS_FILE_UPLOAD_PENDING);
		form.submit();
		
	}




	public void handleUploadFileFormSubmitComplete(String results) {
		log.enter("handleUploadFileFormSubmitComplete");
		
		log.debug(results);
		
		try {
			
			if (results.startsWith("<!--") && results.endsWith("-->")) {
				
				String contents = results.substring(4, results.length()-3);

				log.debug("attempting to parse: "+contents);
				XMLParser.parse(contents);
				
				log.debug("parse success, assume we can go ahead and refresh files");
				refreshFiles();
				
			}
			else {
				
				model.setStatus(AsyncWidgetModel.STATUS_ERROR);
				owner.fireEvent(new ErrorEvent("could not parse results: "+results));

			}
			
		} catch (Throwable t) {
			
			log.error("caught trying to parse submit results: "+t.getLocalizedMessage(), t);
			model.setStatus(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(t));
			
		}

		
		log.leave();
	}




	private void refreshFiles() {
		
		Deferred<Document> deferredFeed = retrieveFiles();
		deferredFeed.addCallback(new RetrieveFilesCallback());
		deferredFeed.addErrback(new DefaultErrback());
		
	}
	
	
	

	public void proceed() {
		owner.fireEvent(new ProceedActionEvent());
	}




	public void stepBack() {
		owner.fireEvent(new StepBackNavigationEvent());
	}




	/**
	 * @param url
	 */
	public void deleteFile(String url) {
		log.enter("deleteFile");
		
		model.setStatus(UploadFilesWidgetModel.STATUS_FILE_DELETE_PENDING);
		
		Deferred<Void> deferredResult = Atom.deleteEntry(url);
		
		deferredResult.addCallback(new DeleteFileCallback());
		deferredResult.addErrback(new DefaultErrback());
		
		log.leave();
	}

	

	/**
	 * @author aliman
	 *
	 */
	class DeleteFileCallback implements Function<Void, Void> {

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public Void apply(Void in) {
			refreshFiles();
			return null;
		}

	}

	
	
	
	class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(UploadFilesWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}



}
