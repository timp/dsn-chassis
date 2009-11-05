/**
 * 
 */
package legacy.org.cggh.chassis.generic.atom.datafile.client.protocol.impl;

import legacy.org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQuery;
import legacy.org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQueryService;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactoryImpl;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import legacy.org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;

import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
public class DataFileQueryServiceImpl implements DataFileQueryService {

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQueryService#query(org.cggh.chassis.generic.atom.datafile.client.protocol.DataFileQuery)
	 */
	public Deferred<AtomFeed> query(DataFileQuery query) {
		log.enter("query");

		String url = this.serviceUrl + "?";

		if (query.getAuthorEmail() != null) {
			url += "authoremail=" + query.getAuthorEmail() + "&";
		}
		
		if (query.getSubmissionUrl() != null) {
			url += "submission=" + query.getSubmissionUrl() + "&";
		}
		
		log.debug("url: "+url);

		AtomFactory factory = new AtomFactoryImpl();
		AtomService service = new AtomServiceImpl(factory);

		Deferred<AtomFeed> deferredFeed = service.getFeed(url);

		log.leave();
		return deferredFeed;
	}

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public DataFileQueryServiceImpl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}


}
