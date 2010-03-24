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
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.anonymizer.client.BackToStartNavigationEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;



/**
 * @author lee
 *
 */
public class CleanFileWidgetController {

	private static final Log log = LogFactory.getLog(CleanFileWidgetController.class);
	
	private CleanFileWidgetModel model;
	private CleanFileWidget owner;	

	/**
	 * @param owner
	 * @param model
	 */
	public CleanFileWidgetController(
			CleanFileWidget owner,
			CleanFileWidgetModel model) {
		
		this.owner = owner;
		this.model = model;

	}

	
	public void backToStart() {
		
		log.enter("backToStart");
		
		owner.fireEvent(new BackToStartNavigationEvent());
		
		log.leave();
	}


	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(CleanFileWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}


	public Deferred<Element> retrieveFileToBeCleanedEntryElement() {
		
		log.enter("retrieveFileToBeCleanedEntryElement");
		
		model.setStatus(CleanFileWidgetModel.STATUS_RETRIEVE_FILE_TO_BE_CLEANED_PENDING);
		
		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_ID, model.fileToBeCleanedID.get());
		
		String url = Config.get(Config.QUERY_FILESTOCLEAN_URL) + qp.toUrlQueryString();
		
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
		
		if (model.fileToBeCleanedID.get() != null) {
			
			log.debug("model.fileToBeCleanedID.get() != null");
			 
			Deferred<Element> chain = retrieveFileToBeCleanedEntryElement();
			
			
			chain.addCallback(new RetrieveFileToBeCleanedCallback());
			
			
			chain.addErrback(new DefaultErrback());
			
			
			chain.addBoth(new Function<Object, Object>() {

				public Object apply(Object in) {
					deferredOwner.callback(owner);
					return in;
				}
				
			});
			
		}
		
		else {
			
			log.debug("model.fileToBeCleanedID.get() == null");
			
			deferredOwner.callback(owner);
		}
		
		log.leave();
		return deferredOwner;
	}	

	private class RetrieveFileToBeCleanedCallback implements Function<Element, Deferred<Document>> {

		public Deferred<Document> apply(Element fileToBeCleanedEntryElement) {
			
			log.enter("apply");
			
			model.fileToBeCleanedEntryElement.set(fileToBeCleanedEntryElement);
			
			if (fileToBeCleanedEntryElement != null) {
				
				model.setStatus(CleanFileWidgetModel.STATUS_FILE_TO_BE_CLEANED_RETRIEVED);


			} else {
			
				model.setStatus(CleanFileWidgetModel.STATUS_FILE_TO_BE_CLEANED_NOT_FOUND);
			}
			
			log.leave();
			
			return null;
			
		}
		
	}


	public void submitCleanFileForm(FormPanel cleanFileFormPanel) {
		
		model.setStatus(CleanFileWidgetModel.STATUS_CLEAN_FILE_FORM_SUBMISSION_PENDING);
		cleanFileFormPanel.submit();
		
	}


	public void handleCleanFileFormSubmitComplete(String results) {
		
		log.enter("handleCleanFileFormSubmitComplete");
		
		log.debug(results);
		
		try {
			
			if (results.startsWith("<!--") && results.endsWith("-->")) {
				
				String contents = results.substring(4, results.length()-3);

				log.debug("Attempting to parse: " + contents);
				XMLParser.parse(contents);
				
				log.debug("Parse success, assume we can go back to start");
				
				backToStart();
				
			} else {
				
				log.error("The results coming back from the servlet were: " + results);
				
				model.setStatus(CleanFileWidgetModel.STATUS_CLEAN_FILE_FORM_SUBMISSION_MALFORMED_RESULTS);
				owner.fireEvent(new ErrorEvent(results));

			}
			
		} catch (Throwable t) {
			
			log.error("Caught trying to parse submit results: "+t.getLocalizedMessage(), t);
			model.setStatus(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(t));
			
		}

		
		log.leave();
	}	
	
}
