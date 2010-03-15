
/**
 * 
 */
package org.cggh.chassis.wwarn.ui.curator.client;

import java.util.List;

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
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
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

	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
	/*	
		if (model.submissionId.get() != null) {
			
			Deferred<Element> chain = retrieveSubmission();
			
			chain.addCallback(new RetrieveSubmissionCallback());
			
			chain.addCallback(new RetrieveQuestionnaireCallback());
			
			chain.addCallback(retrieveStudyCallback);

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
		*/
		log.leave();
		return deferredOwner;
		
	}

	
	
	/**
	 * @return
	 */
	 /*
	private Deferred<Element> retrieveSubmission() {
		log.enter("retrieveSubmission");
		
		String submissionId = model.submissionId.get();
		
		model.status.set(ViewStudyWidgetModel.STATUS_RETRIEVE_SUBMISSION_PENDING);
		
		QueryParams qp = new QueryParams();
		qp.put(Chassis.QUERYPARAM_ID, submissionId);
		
		String url = Config.get(Config.QUERY_SUBMISSIONS_URL) + qp.toUrlQueryString();
		log.debug("url: "+url);

		log.debug("make get feed request");
		Deferred<Document> deferredResultsFeedDoc = Atom.getFeed(url);
		
		Deferred<Element> deferredSubmissionElement = deferredResultsFeedDoc.adapt(new Function<Document, Element>() {

			public Element apply(Document resultsFeedDoc) {
				Element entryElement = AtomHelper.getFirstEntry(resultsFeedDoc.getDocumentElement());
				return entryElement;
			}
			
		});

		log.leave();
		return deferredSubmissionElement;
	}

	

	private class RetrieveSubmissionCallback implements Function<Element, Deferred<XQuestionnaire>> {

		public Deferred<XQuestionnaire> apply(Element submissionEntryElement) {
			
			model.submissionEntryElement.set(submissionEntryElement);
			
			Deferred<XQuestionnaire> deferredQuestionnaire;
			
			if (submissionEntryElement != null) {

				List<Element> studyLinks = AtomHelper.getLinks(submissionEntryElement, Chassis.REL_ORIGINSTUDY);
				assert studyLinks.size() == 1;
				
				Element studyEntryElement = AtomHelper.getFirstEntry(studyLinks.get(0));
				String url = AtomHelper.getEditLinkHrefAttr(studyEntryElement);
				model.studyUrl.set(url);

//				model.studyEntryElement.set(studyEntryElement);
				
				// we have a submission, so lets try retrieving the study questionnaire
				deferredQuestionnaire = retrieveQuestionnaire();
				
			}
			
			else {
				
				model.status.set(ViewStudyWidgetModel.STATUS_SUBMISSION_NOT_FOUND);
				deferredQuestionnaire = new Deferred<XQuestionnaire>();
				deferredQuestionnaire.callback(null);

			}
			
			// return the deferred questionnaire, for chaining of callbacks
			return deferredQuestionnaire;
		}

	}
*/
	
/*	
	public void navigateToSubmitterHome() {
		owner.fireEvent(new HomeNavigationEvent());
	}
*/


	
	/**
	 * @return
	 */
	public Deferred<XQuestionnaire> retrieveQuestionnaire() {
		log.enter("retrieveQuestionnaire");
		
		Deferred<XQuestionnaire> d =null;
		/*
		if (view.getQuestionnaire() == null) {
			log.debug("retrieving questionnaire");
			
			model.status.set(ViewStudyWidgetModel.STATUS_RETRIEVE_QUESTIONNAIRE_PENDING);
			
			String url = Config.get(Config.QUESTIONNAIRE_STUDY_URL);
			log.debug("url: "+url);

			d = XQuestionnaire.load(url);
		}
		else {
			log.debug("questionnaire already retrieved");
			d = new Deferred<XQuestionnaire>();
			d.callback(view.getQuestionnaire()); // TODO this is an ugly hack
		}
		*/
		
		log.leave();
		return d;
	}

	
	
	
	private class RetrieveQuestionnaireCallback implements Function<XQuestionnaire, Deferred<Element>> {

		public Deferred<Element> apply(XQuestionnaire questionnaire) {
			/*
			if (view.getQuestionnaire() == null) {
				
				// TODO ugly hack to make sure we don't reset the questionnaire unnecessarily
				log.debug("setting questionnaire on view");
				view.setQuestionnaire(questionnaire);
				
			}
			*/
			return retrieveStudy();

			
		}
		
	}
		
		
		

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}


/*
	public void setView(ViewStudyWidgetView view) {
		this.view = view;
	}
*/


	/**
	 * @return
	 */
	public Deferred<Element> retrieveStudy() {
		log.enter("retrieveStudy");
		
		model.status.set(ViewStudyWidgetModel.STATUS_RETRIEVE_STUDY_PENDING);
		
		Deferred<Element> d;
		
		if (!model.studyUrl.isNull()) {
			
			d = Atom.getEntry(model.studyUrl.get()).adapt(new Function<Document, Element>() {

				public Element apply(Document in) {
					return in.getDocumentElement();
				}
				
			});
			
		}
		else {
			d = new Deferred<Element>();
			d.callback(null);
		}
		
		log.leave();
		return d;
	}
	
	
	
	public final Function<Element, Element> retrieveStudyCallback = new Function<Element, Element>() {
		
		public Element apply(Element in) {
			log.enter("apply");
			
			model.studyEntryElement.set(in);
			model.status.set(ViewStudyWidgetModel.STATUS_READY_FOR_INTERACTION);
			
			log.leave();
			return in;
		}
	};



	/**
	 * 
	 */
	public void saveStudy() {
		log.enter("saveStudy");
		
		model.status.set(ViewStudyWidgetModel.STATUS_SAVE_STUDY_PENDING);
		
		Deferred<Document> d = Atom.putEntry(model.studyUrl.get(), model.studyEntryElement.get().getOwnerDocument());

		d.addCallback(saveStudyCallback);
		d.addErrback(new DefaultErrback());
		
		log.leave();
	}

	
	
	protected final Function<Document, Document> saveStudyCallback = new Function<Document, Document>() {
		
		public Document apply(Document in) {
			log.enter("apply");
			
			model.studyEntryElement.set(in.getDocumentElement());
			model.status.set(ViewStudyWidgetModel.STATUS_READY_FOR_INTERACTION);
			
			log.leave();
			return in;
		}
	};







}
