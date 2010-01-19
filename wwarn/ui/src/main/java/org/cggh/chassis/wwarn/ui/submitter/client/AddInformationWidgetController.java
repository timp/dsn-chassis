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
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author aliman
 *
 */
public class AddInformationWidgetController {

	
	
	
	private Log log = LogFactory.getLog(AddInformationWidgetController.class);
	
	
	
	
	private AddInformationWidget owner;
	private AddInformationWidgetModel model;
	private AddInformationWidgetView view;


	
	
	public AddInformationWidgetController(AddInformationWidget owner, AddInformationWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
		
		if (model.getSubmissionId() != null) {
			
			Deferred<Element> chain = retrieveSubmission();
			
			chain.addCallback(new RetrieveSubmissionCallback());
			
			chain.addCallback(new RetrieveQuestionnaireCallback());

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

	
	
	/**
	 * @return
	 */
	private Deferred<Element> retrieveSubmission() {
		log.enter("retrieveSubmission");
		
		String submissionId = model.getSubmissionId();
		
		model.setStatus(AddInformationWidgetModel.STATUS_RETRIEVE_SUBMISSION_PENDING);
		
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
			
			model.setSubmissionEntryElement(submissionEntryElement);
			
			Deferred<XQuestionnaire> deferredQuestionnaire;
			
			if (submissionEntryElement != null) {

				List<Element> studyLinks = AtomHelper.getLinks(submissionEntryElement, Chassis.REL_ORIGINSTUDY);
				assert studyLinks.size() == 1;
				
				Element studyEntryElement = AtomHelper.getFirstEntry(studyLinks.get(0));
				model.setStudyEntryElement(studyEntryElement);
				
				// we have a submission, so lets try retrieving the study questionnaire
				deferredQuestionnaire = retrieveQuestionnaire();
				
			}
			
			else {
				
				model.setStatus(AddInformationWidgetModel.STATUS_SUBMISSION_NOT_FOUND);
				deferredQuestionnaire = new Deferred<XQuestionnaire>();
				deferredQuestionnaire.callback(null);

			}
			
			// return the deferred questionnaire, for chaining of callbacks
			return deferredQuestionnaire;
		}

	}

	
	
	public void navigateToSubmitterHome() {
		owner.fireEvent(new HomeNavigationEvent());
	}



	
	/**
	 * @return
	 */
	public Deferred<XQuestionnaire> retrieveQuestionnaire() {
		log.enter("retrieveQuestionnaire");
		
		model.setStatus(AddInformationWidgetModel.STATUS_RETRIEVE_QUESTIONNAIRE_PENDING);
		
		String url = Config.get(Config.QUESTIONNAIRE_STUDY_URL);
		log.debug("url: "+url);
		
		Deferred<XQuestionnaire> deferredQuestionnaire = XQuestionnaire.load(url);
		
		log.leave();
		return deferredQuestionnaire;
	}

	
	
	
	private class RetrieveQuestionnaireCallback implements Function<XQuestionnaire, XQuestionnaire> {

		public XQuestionnaire apply(XQuestionnaire questionnaire) {
			
			// here we break the MVC cycle, by manipulating the view directly...
			view.setQuestionnaire(questionnaire);
			
			Element studyElement = ChassisHelper.getStudy(AtomHelper.getContent(model.getStudyEntryElement()));
			questionnaire.init(studyElement);
			
			model.setStatus(AddInformationWidgetModel.STATUS_READY_FOR_INTERACTION);

			return questionnaire;
		}
		
	}
		
		
		

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}



	public void setView(AddInformationWidgetView view) {
		this.view = view;
	}








}
