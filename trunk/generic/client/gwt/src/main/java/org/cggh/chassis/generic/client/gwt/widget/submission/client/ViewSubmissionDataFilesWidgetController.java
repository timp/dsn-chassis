/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.ArrayList;

import legacy.org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQuery;
import legacy.org.cggh.chassis.generic.atom.datafile.client.protocol.impl.DataFileQueryServiceImpl;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;

import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class ViewSubmissionDataFilesWidgetController {
	private Log log = LogFactory.getLog(this.getClass());

	final private ViewSubmissionDataFilesWidgetModel model;
	final private ViewSubmissionDataFilesWidget owner;

	private String dataFilesQueryServiceURL;

	private DataFileQueryServiceImpl dataFileQueryService;

	public ViewSubmissionDataFilesWidgetController(ViewSubmissionDataFilesWidgetModel model, ViewSubmissionDataFilesWidget owner) {
		this.model = model;
		this.owner = owner;
		
		//Set up query service
		this.dataFilesQueryServiceURL = ConfigurationBean.getDataFileQueryServiceURL();
		this.dataFileQueryService = new DataFileQueryServiceImpl(dataFilesQueryServiceURL);
		
	}

	public void loadDataFilesBySubmissionLink(String submissionLink) {
		log.enter("loadDataFilesBySubmissionLink");

		log.debug("loading DataFiles associated with: " + submissionLink);
		
		if (submissionLink != null) {

			//create query
			DataFileQuery query = new DataFileQuery();
			query.setSubmissionUrl(submissionLink);
			
			Deferred<AtomFeed> deferred = dataFileQueryService.query(query);
			deferred.addCallback(new LoadDataFileFeedCallback());
			deferred.addErrback(new LoadDataFileFeedErrback());
			
		}
		else {
			
			// TODO consider moving to reset() or clear() method
			model.setFileDataAtomEntries(new ArrayList<AtomEntry>());
			model.setStatus(ViewSubmissionDataFilesWidgetModel.STATUS_LOADED);

		}
		
		log.leave();
	}

	//package private for testing purposes
	class LoadDataFileFeedCallback implements Function<AtomFeed,AtomFeed> {

		public AtomFeed apply(AtomFeed fileDataAtomEntries) {
			log.enter("LoadDataFileFeedCallback::apply");
			
			model.setFileDataAtomEntries(fileDataAtomEntries.getEntries());
			model.setStatus(ViewSubmissionDataFilesWidgetModel.STATUS_LOADED);
			log.debug(model.getFileDataAtomEntries().size() + " file Entries loaded.");
			
			log.leave();
			return fileDataAtomEntries;
		}
		
	}

	//package private for testing purposes
	class LoadDataFileFeedErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable err) {
			log.enter("LoadDataFileFeedErrback::apply");
			
			log.leave();
			return err;
		}
		
	}

}
