/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.protocol.impl;

import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomFeed;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class StudyQueryServiceImpl implements StudyQueryService {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serviceUrl;
	
	
	
	/**
	 * @param serviceUrl
	 */
	public StudyQueryServiceImpl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#getStudiesByAuthorEmail(java.lang.String)
	 */
	public Deferred<StudyFeed> getStudiesByAuthorEmail(String email) {
		log.enter("getStudiesByAuthorEmail");

//		String url = this.serviceUrl + "?authoremail=" + email;
//		log.debug("url: "+url);
//
//		StudyFactory factory = new StudyFactoryImpl();
//		AtomService service = new AtomServiceImpl(factory);
//
//		Deferred<AtomFeed> deferredFeed = service.getFeed(url);
//
//		Function<AtomFeed,StudyFeed> adapter = new Function<AtomFeed,StudyFeed>() {
//			public StudyFeed apply(AtomFeed in) {
//				log.enter("[anonymous adapter function]");
//				log.debug("found "+in.getEntries().size()+" results; casting as study feed");
//				log.leave();
//				return (StudyFeed) in;
//			}
//		};
//		
//		Deferred<StudyFeed> deferredResults = deferredFeed.adapt(adapter);

		StudyQuery query = new StudyQuery();
		query.setAuthorEmail(email);
		
		log.leave();
		return this.query(query);
	}



	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService#query(org.cggh.chassis.generic.atom.study.client.protocol.StudyQuery)
	 */
	public Deferred<StudyFeed> query(StudyQuery query) {
		log.enter("query");

		String url = this.serviceUrl + "?";

		if (query.getAuthorEmail() != null) {
			url += "authoremail=" + query.getAuthorEmail() + "&";
		}
		
		if (query.getSubmissionUrl() != null) {
			url += "submission=" + query.getSubmissionUrl() + "&";
		}
		
		log.debug("url: "+url);

		StudyFactory factory = new StudyFactoryImpl();
		AtomService service = new AtomServiceImpl(factory);

		Deferred<AtomFeed> deferredFeed = service.getFeed(url);

		Function<AtomFeed,StudyFeed> adapter = new Function<AtomFeed,StudyFeed>() {
			public StudyFeed apply(AtomFeed in) {
				log.enter("[anonymous adapter function]");
				log.debug("found "+in.getEntries().size()+" results; casting as study feed");
				log.leave();
				return (StudyFeed) in;
			}
		};
		
		Deferred<StudyFeed> deferredResults = deferredFeed.adapt(adapter);

		log.leave();
		return deferredResults;
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.protocol.StudyQueryService#getStudiesByAuthorEmail(java.lang.String)
	 */
//	public Deferred<StudyFeed> getStudiesByAuthorEmail(String email) {
//		log.enter("getStudiesByAuthorEmail");
//		
//		String xquery = buildXQueryForGetStudiesByAuthorEmail(email);
//		log.trace(xquery);
//		
//		Deferred<String> deferredString = this.query(xquery);
//		
//		Function<String,StudyFeed> adapter = new Function<String,StudyFeed>() {
//
//			public StudyFeed apply(String studyFeedDoc) {
//				StudyFactory factory = new StudyFactoryImpl();
//				return factory.createStudyFeed(studyFeedDoc); // assume parse errors handled by errbacks in adapted deferred
//			}
//			
//		};
//		
//		log.leave();
//		return deferredString.adapt(adapter);
//
//	}
	
	
	
// N.B. the following only works for eXist 1.2.x	
//	public static String buildXQueryForGetStudiesByAuthorEmail(String email) {
//
//		String xquery = "\n" +
//			"<atom:feed \n" +
//				"xmlns:atom=\""+Atom.NSURI+"\" \n" +
//				"xmlns:chassis=\""+Chassis.NSURI+"\"> \n" +
//				"{ \n" +
//				"/atom:feed/atom:id, \n" +
//				"/atom:feed/atom:title, \n" +
//				"/atom:feed/atom:updated, \n" +
//				"for $e in (/atom:feed/atom:entry) \n" +
//				"where $e/atom:author/atom:email = '"+email+"' \n" +
//				"return $e \n" +
//				"} \n" +
//			"</atom:feed> \n";
//		
//		return xquery;	
//		
//	}
	

}
