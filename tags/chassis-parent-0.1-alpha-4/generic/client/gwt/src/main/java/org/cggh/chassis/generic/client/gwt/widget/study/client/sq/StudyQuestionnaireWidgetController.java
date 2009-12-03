/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client.sq;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.xquestion.client.XQuestionnaire;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class StudyQuestionnaireWidgetController 
	extends AtomCrudWidgetController<StudyEntry, StudyFeed, StudyQuery> {
	
	
	
	
	private Log log = LogFactory.getLog(StudyQuestionnaireWidgetController.class);

	
	
	
	/**
	 * @param owner
	 * @param model
	 * @param collectionUrl
	 */
	public StudyQuestionnaireWidgetController(
			Widget owner, 
			AtomCrudWidgetModel<StudyEntry> model) {
		super(owner, model, ""); // use collection url as base url
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<StudyEntry, StudyFeed> createAtomService() {
		return new StudyPersistenceService(Configuration.getStudyCollectionUrl());
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public StudyQuery createQuery() {
		return new StudyQuery();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<StudyEntry, StudyFeed, StudyQuery> createQueryService() {
		return new StudyQueryService(Configuration.getStudyQueryServiceUrl());
	}


	
	
	
	public Deferred<XQuestionnaire> loadQuestionnaire() {
		log.enter("loadQuestionnaire");
		
		this.model.setStatus(StudyQuestionnaireWidgetModel.STATUS_LOAD_QUESTIONNAIRE_PENDING);
		
		Deferred<XQuestionnaire> dq = XQuestionnaire.load(Configuration.getStudyQuestionnaireUrl());
		
		dq.addCallback(new Function<XQuestionnaire, XQuestionnaire>() {

			public XQuestionnaire apply(XQuestionnaire in) {
				
				model.setStatus(StudyQuestionnaireWidgetModel.STATUS_READY);
				return in;
				
			}

		});
		
		dq.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
		return dq;
	}

	
	
	

}
