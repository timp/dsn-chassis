/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidget;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;

/**
 * @author aliman
 *
 */
public abstract class StudyCrudWidgetController extends
		AtomCrudWidgetController<StudyEntry, StudyFeed, StudyQuery> {

	
	

	/**
	 * @param owner
	 * @param model
	 * @param collectionUrl
	 */
	public StudyCrudWidgetController(
			AtomCrudWidget<StudyEntry, StudyFeed, StudyQuery, ?, ?, ?> owner,
			AtomCrudWidgetModel<StudyEntry> model) {
		super(owner, model, ""); // use relative collection URL
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<StudyEntry, StudyFeed> createAtomService() {
		
		// use collection url as base url
		String baseUrl = Configuration.getStudyCollectionUrl();
		
		return new StudyPersistenceService(baseUrl);
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public StudyQuery createQuery() {
		return new StudyQuery();
	}








	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.client.ui.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<StudyEntry, StudyFeed, StudyQuery> createQueryService() {
		return new StudyQueryService(Configuration.getStudyQueryServiceUrl());
	}
	
	
	
	
	
}
