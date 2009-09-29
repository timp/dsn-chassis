/**
 * 
 */
package org.cggh.chassis.generic.atom.study.client.protocol.impl;

import org.cggh.chassis.generic.atom.chassis.base.client.format.Chassis;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.StudyFeed;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.study.client.protocol.StudyQueryService;
import org.cggh.chassis.generic.atom.vanilla.client.format.Atom;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomXQueryServiceImpl;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author aliman
 *
 */
public class StudyQueryServiceImpl extends AtomXQueryServiceImpl implements StudyQueryService {

	
	
	private Log log = LogFactory.getLog(this.getClass());
	
	
	
	/**
	 * @param serviceUrl
	 */
	public StudyQueryServiceImpl(String serviceUrl) {
		super(serviceUrl);
	}

	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.atom.study.protocol.StudyQueryService#getStudiesByAuthorEmail(java.lang.String)
	 */
	public Deferred<StudyFeed> getStudiesByAuthorEmail(String email) {
		log.enter("getStudiesByAuthorEmail");
		
		String xquery = buildXQueryForGetStudiesByAuthorEmail(email);
		log.trace(xquery);
		
		Deferred<String> deferredString = this.query(xquery);
		
		Function<String,StudyFeed> adapter = new Function<String,StudyFeed>() {

			public StudyFeed apply(String studyFeedDoc) {
				StudyFactory factory = new StudyFactoryImpl();
				return factory.createStudyFeed(studyFeedDoc); // assume parse errors handled by errbacks in adapted deferred
			}
			
		};
		
		log.leave();
		return deferredString.adapt(adapter);

	}
	
	
	
	
	public static String buildXQueryForGetStudiesByAuthorEmail(String email) {

		String xquery = "\n" +
			"<atom:feed \n" +
				"xmlns:atom=\""+Atom.NSURI+"\" \n" +
				"xmlns:chassis=\""+Chassis.NSURI+"\"> \n" +
				"{ \n" +
				"/atom:feed/atom:id, \n" +
				"/atom:feed/atom:title, \n" +
				"/atom:feed/atom:updated, \n" +
				"for $e in (/atom:feed/atom:entry) \n" +
				"where $e/atom:author/atom:email = '"+email+"' \n" +
				"return $e \n" +
				"} \n" +
			"</atom:feed> \n";
		
		return xquery;	
		
	}
	

}
