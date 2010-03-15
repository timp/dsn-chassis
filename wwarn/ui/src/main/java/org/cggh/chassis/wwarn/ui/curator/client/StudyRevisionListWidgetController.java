
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
public class StudyRevisionListWidgetController {

	
	
	
	private Log log = LogFactory.getLog(StudyRevisionListWidgetController.class);
	
	
	
	
	private StudyRevisionListWidget owner;
	private StudyRevisionListWidgetModel model;


	
	
	public StudyRevisionListWidgetController(StudyRevisionListWidget owner, StudyRevisionListWidgetModel model) {
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

	
	
		

	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}



	/**
	 * @return
	 */
	public Deferred<Element> retrieveStudy() {
		log.enter("retrieveStudy");
		
		model.status.set(StudyRevisionListWidgetModel.STATUS_RETRIEVE_STUDY_PENDING);
		
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
			model.status.set(StudyRevisionListWidgetModel.STATUS_READY_FOR_INTERACTION);
			
			log.leave();
			return in;
		}
	};



	/**
	 * 
	 */
	public void saveStudy() {
		log.enter("saveStudy");
		
		model.status.set(StudyRevisionListWidgetModel.STATUS_SAVE_STUDY_PENDING);
		
		Deferred<Document> d = Atom.putEntry(model.studyUrl.get(), model.studyEntryElement.get().getOwnerDocument());

		d.addCallback(saveStudyCallback);
		d.addErrback(new DefaultErrback());
		
		log.leave();
	}

	
	
	protected final Function<Document, Document> saveStudyCallback = new Function<Document, Document>() {
		
		public Document apply(Document in) {
			log.enter("apply");
			
			model.studyEntryElement.set(in.getDocumentElement());
			model.status.set(StudyRevisionListWidgetModel.STATUS_READY_FOR_INTERACTION);
			
			log.leave();
			return in;
		}
	};







}
