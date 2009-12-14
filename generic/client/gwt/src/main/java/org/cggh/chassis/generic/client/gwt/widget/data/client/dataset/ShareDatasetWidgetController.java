/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQuery;
import org.cggh.chassis.generic.atomext.client.dataset.DatasetQueryService;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionFactory;
import org.cggh.chassis.generic.atomext.client.submission.SubmissionPersistenceService;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;

/**
 * @author aliman
 *
 */
public class ShareDatasetWidgetController {

	
	
	private Log log = LogFactory.getLog(ShareDatasetWidgetController.class);
	private ShareDatasetWidgetModel model;
	private ShareDatasetWidget owner;

	
	
	
	/**
	 * @param owner 
	 * @param model
	 */
	public ShareDatasetWidgetController(ShareDatasetWidget owner, ShareDatasetWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	
	
	
	/**
	 * @param id
	 */
	public Deferred<DatasetEntry> retrieveEntry(String id) {
		log.enter("retrieveEntry");
		
		log.debug("store dataset entry id to use as mnemonic");
		
		this.model.setDatasetEntryId(id);
		
		log.debug("set status to retrieve dataset pending");
		
		this.model.setStatus(ShareDatasetWidgetModel.STATUS_RETRIEVE_DATASET_PENDING);
		
		log.debug("now retrieve expanded dataset");
		
		DatasetQueryService service = new DatasetQueryService(Configuration.getDatasetQueryServiceUrl());
		DatasetQuery query = new DatasetQuery();
		query.setId(id);
		
		Deferred<DatasetEntry> deferredEntry = service.queryOne(query);
		
		log.debug("add callbacks to handle async response");
		
		Function<DatasetEntry, DatasetEntry> callback = new Function<DatasetEntry, DatasetEntry>() {

			public DatasetEntry apply(DatasetEntry in) {
				log.enter("[anon callback]");

				log.debug("retrieve dataset was a success");

				// N.B. order in which these two calls on model are made is important
				
				model.setDatasetEntry(in);
				
				model.setStatus(ShareDatasetWidgetModel.STATUS_DATASET_RETRIEVED);
				
				log.leave();
				return in;
			}
			
		};
		
		deferredEntry.addCallback(callback);
		
		deferredEntry.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
		return deferredEntry;
	}




	/**
	 * 
	 */
	public void shareDataset() {
		log.enter("shareDataset");

		log.debug("get some info about the dataset we are sharing, esp. its url");
		String datasetEntryUrl = this.model.getDatasetEntry().getEditLink().getHref();
		
		log.debug("set up a new submission entry");
		
		SubmissionFactory submissionFactory = new SubmissionFactory();
		SubmissionEntry submissionEntry = submissionFactory.createEntry();
		
		submissionEntry.setDatasetLink(datasetEntryUrl);
		
		AtomAuthor author = submissionFactory.createAuthor();
		author.setEmail(ChassisUser.getCurrentUserEmail());
		submissionEntry.addAuthor(author);
		
		log.debug("persist the new submission entry");
		
		SubmissionPersistenceService service = new SubmissionPersistenceService(Configuration.getSubmissionCollectionUrl());
		
		log.debug("set model pending status");
		
		this.model.setStatus(ShareDatasetWidgetModel.STATUS_CREATE_SUBMISSION_PENDING);
		
		Deferred<SubmissionEntry> deferredSubmissionEntry = service.postEntry("", submissionEntry);
		
		deferredSubmissionEntry.addCallback(new Function<SubmissionEntry, SubmissionEntry>() {

			public SubmissionEntry apply(SubmissionEntry in) {
				log.enter("[anon callback]");
				
				log.debug("set model status ready");
				
				model.setStatus(ShareDatasetWidgetModel.STATUS_DATASET_SHARED);
				
				model.setSubmissionEntry(in);
				
				log.leave();
				return in;
			}
			
		});
		
		deferredSubmissionEntry.addErrback(new AsyncErrback(owner, model));
		
		log.leave();
	}





	
	
	
}
