/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

class StudyQuestionnaireWidgetController {


	
	
	private StudyQuestionnaireWidgetModel model;
	private XQuestionnaire questionnaire;
	private StudyPersistenceService service;




	/**
	 * @param owner
	 */
	StudyQuestionnaireWidgetController(StudyQuestionnaireWidgetModel model) {
		this.model = model;
		service = new StudyPersistenceService();
	}




	private Log log = LogFactory.getLog(this.getClass());

	
	
	
	/**
	 * 
	 */
	Deferred<XQuestionnaire> loadQuestionnaire() {
		log.enter("loadQuestionnaire");
		
		this.model.setStatus(StudyQuestionnaireWidgetModel.STATUS_LOADINGQUESTIONNAIRE);
		
		Deferred<XQuestionnaire> deferredQuestionnaire = XQuestionnaire.load(Configuration.getStudyQuestionnaireUrl());

		deferredQuestionnaire.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			private Log log = LogFactory.getLog(this.getClass());

			public XQuestionnaire apply(XQuestionnaire in) {
				log.enter("apply");

				log.debug("questionnaire loaded");
				questionnaire = in;
				
				log.leave();
				return in;
			}
			
		});

		deferredQuestionnaire.addErrback(new Function<Throwable, Throwable>() {

			private Log log = LogFactory.getLog(this.getClass());

			public Throwable apply(Throwable in) {
				log.enter("apply");
				
				log.error("error loading study questionnaire: "+in.getLocalizedMessage(), in);
				model.setStatus(StudyQuestionnaireWidgetModel.STATUS_ERROR);
				
				log.leave();
				return in;
			}

		});
		
		log.leave();
		return deferredQuestionnaire;
	}




	/**
	 * @param entry
	 */
	public void setEntry(StudyEntry entry, boolean readOnly) {
		log.enter("setEntry( "+entry.getId()+" , "+readOnly+" )");

		if (model.getStatus() != StudyQuestionnaireWidgetModel.STATUS_READY) {
			throw new Error("cannot set entry, widget is not ready; maybe questionnaire could not be loaded?");
		}

		model.setEntry(entry);
		model.setStatus(StudyQuestionnaireWidgetModel.STATUS_INITIALISINGQUESTIONNAIRE);
		
		log.debug("initialising questionnaire");
		questionnaire.init(entry.getStudy().getElement(), readOnly);
		model.setStatus(StudyQuestionnaireWidgetModel.STATUS_READY);

		log.leave();
	}




	/**
	 * 
	 */
	public Deferred<StudyEntry> saveQuestionnaire() {

		model.setStatus(StudyQuestionnaireWidgetModel.STATUS_SAVING);
		
		StudyEntry studyEntry = model.getEntry();
		
		String url = Configuration.getStudyCollectionUrl() + studyEntry.getEditLink().getHref();
		Deferred<StudyEntry> def = service.putEntry(url, studyEntry);

		def.addCallback(new Function<StudyEntry, StudyEntry>() {

			public StudyEntry apply(StudyEntry in) {
				model.setEntry(in);
				model.setStatus(StudyQuestionnaireWidgetModel.STATUS_READY);
				return in;
			}
			
		});

		def.addErrback(new Function<Throwable, Throwable>() {

			private Log log = LogFactory.getLog(this.getClass());
			
			public Throwable apply(Throwable in) {
				log.error("error saving study questionnaire", in);
				model.setStatus(StudyQuestionnaireWidgetModel.STATUS_ERROR);
				return in;
			}

		});
		
		return def;

	}	
	
	
	
	
}