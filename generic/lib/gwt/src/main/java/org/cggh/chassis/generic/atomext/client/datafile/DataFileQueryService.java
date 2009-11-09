/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.datafile;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

/**
 * @author aliman
 *
 */
public class DataFileQueryService {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public DataFileQueryService(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#getStudiesByAuthorEmail(java.lang.String)
	 */
	public Deferred<DataFileFeed> getStudiesByAuthorEmail(String email) {
		log.enter("getStudiesByAuthorEmail");

		DataFileQuery query = new DataFileQuery();
		query.setAuthorEmail(email);
		
		log.leave();
		return this.query(query);
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#query(org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery)
	 */
	public Deferred<DataFileFeed> query(DataFileQuery query) {
		log.enter("query");

		String url = this.serviceUrl + "?";

		if (query.getAuthorEmail() != null) {
			url += "authoremail=" + query.getAuthorEmail() + "&";
		}
		
		if (query.getId() != null) {
			url += "id=" + query.getId() + "&";
		}
		
		log.debug("url: "+url);

		DataFilePersistenceService service = new DataFilePersistenceService();

		Deferred<DataFileFeed> deferredResults = service.getFeed(url);
		
		log.leave();
		return deferredResults;
	}



	/**
	 * @param query
	 * @return
	 */
	public Deferred<DataFileEntry> queryOne(DataFileQuery query) {
		log.enter("queryOne");
		
		Deferred<DataFileFeed> deferredResults = this.query(query);
		
		Deferred<DataFileEntry> deferredResult = deferredResults.adapt(new Function<DataFileFeed, DataFileEntry>() {

			public DataFileEntry apply(DataFileFeed in) {
				DataFileEntry entry = null;
				if (in.getEntries().size() > 0) {
					entry = in.getEntries().get(0);
				}
				return entry;
			}
			
		});
		
		log.leave();
		return deferredResult;
	}

	
	

}
