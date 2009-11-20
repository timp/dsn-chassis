/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;


import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.atomext.client.study.StudyFeed;
import org.cggh.chassis.generic.atomext.client.study.StudyQuery;
import org.cggh.chassis.generic.atomext.client.study.StudyQueryService;
import org.cggh.chassis.generic.client.gwt.common.client.ChassisUser;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.client.gwt.widget.study.client.MyStudiesWidget;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.AsyncErrback;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author raok
 *
 */
public class MyStudiesWidgetController {
	
	
	
	
	private Log log = LogFactory.getLog(MyStudiesWidgetController.class);
	final private MyStudiesWidgetModel model;
	final private MyStudiesWidget owner;

	
	
	
	
	
	
	public MyStudiesWidgetController(MyStudiesWidgetModel model, MyStudiesWidget owner) {
		this.model = model;
		this.owner = owner;
	}

	
	
	

	
	/**
	 * 
	 */
	public void refreshStudies() {
		log.enter("refreshStudies");

		this.model.setStatus(AsyncWidgetModel.STATUS_ASYNC_REQUEST_PENDING);
		
		StudyQueryService service = new StudyQueryService(Configuration.getStudyQueryServiceUrl());
		
		StudyQuery query = new StudyQuery();
		query.setAuthorEmail(ChassisUser.getCurrentUserEmail());
		
		Deferred<StudyFeed> deferredFeed = service.query(query);
		
		deferredFeed.addCallback(new RefreshStudiesCallback());
		deferredFeed.addErrback(new AsyncErrback(this.owner, this.model));
		
		log.leave();
	}

	
	
	
	/**
	 * @author aliman
	 *
	 */
	public class RefreshStudiesCallback implements Function<StudyFeed, StudyFeed> {
		private Log log = LogFactory.getLog(RefreshStudiesCallback.class);

		/* (non-Javadoc)
		 * @see org.cggh.chassis.generic.async.client.Function#apply(java.lang.Object)
		 */
		public StudyFeed apply(StudyFeed in) {
			log.enter("apply");
			
			model.setFeed(in);
			model.setStatus(AsyncWidgetModel.STATUS_READY);
			
			log.leave();
			return in;
		}

	}
	
	
	
	
	
	
}
