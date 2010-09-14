/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import org.cggh.chassis.generic.atom.client.AtomQueryService;
import org.cggh.chassis.generic.atom.client.AtomService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFeed;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQuery;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionQueryService;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController;
import org.cggh.chassis.generic.atomui.client.AtomCrudWidgetModel;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author aliman
 *
 */
public class ViewSubmissionWidgetController 
	extends AtomCrudWidgetController<SubmissionEntry, SubmissionFeed, SubmissionQuery> 

{


	
	
	private Log log = LogFactory.getLog(ViewSubmissionWidgetController.class);
	
	
	
	

	/**
	 * @param owner
	 * @param model
	 * @param collectionUrl
	 */
	public ViewSubmissionWidgetController(
			Widget owner,
			AtomCrudWidgetModel<SubmissionEntry> model) {
		super(owner, model, ""); // use relative collection URL
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createAtomService()
	 */
	@Override
	public AtomService<SubmissionEntry, SubmissionFeed> createAtomService() {
		log.enter("createAtomService");
		
		
		SubmissionPersistenceService service = new SubmissionPersistenceService(Configuration.getSubmissionCollectionUrl());

		
		log.leave();
		return service;
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createQuery()
	 */
	@Override
	public SubmissionQuery createQuery() {
		return new SubmissionQuery();
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atomui.client.AtomCrudWidgetController#createQueryService()
	 */
	@Override
	public AtomQueryService<SubmissionEntry, SubmissionFeed, SubmissionQuery> createQueryService() {
		log.enter("createQueryService");

		SubmissionQueryService service = new SubmissionQueryService(Configuration.getSubmissionQueryServiceUrl());
		
		log.leave();
		return service;
	}
	
	

}
