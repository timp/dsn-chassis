
package org.cggh.chassis.wwarn.ui.curator.client;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;
import org.cggh.chassis.generic.widget.client.ChassisWidget;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

/**
 * @author timp
 *
 */
public class CuratorHomeWidgetController {
	
	


	private Log log = LogFactory.getLog(CuratorHomeWidgetController.class);
	
	
	private CuratorHomeWidget owner;
	private CuratorHomeWidgetModel model;


	public CuratorHomeWidgetController(CuratorHomeWidget owner, CuratorHomeWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}

	public Deferred<ChassisWidget> refreshAndCallback() {
		log.enter("refreshAndCallback");
		
		final Deferred<ChassisWidget> deferredOwner = new Deferred<ChassisWidget>();
		
		Deferred<Element> chain = retrieveStudyFeed();
			
		chain.addCallback(new RetrieveStudyFeedCallback());
			

		chain.addErrback(new DefaultErrback());
			
		// finally callback with owner, in any case
		chain.addBoth(new Function<Object, Object>() {

			public Object apply(Object in) {
				deferredOwner.callback(owner);
				return in;
			}
			
		});

		
		log.leave();
		return deferredOwner;
		
	}

	private Deferred<Element> retrieveStudyFeed() {
		log.enter("retrieveSubmission");
		
		model.status.set(CuratorHomeWidgetModel.STATUS_RETRIEVE_STUDY_FEED_PENDING);
		
		String url = Config.get(Config.QUERY_STUDIES_URL);
		log.debug("url: "+url);

		log.debug("make get feed request");
		Deferred<Document> deferredResultsFeedDoc = Atom.getFeed(url);
		
		Deferred<Element> deferredFeedElement = deferredResultsFeedDoc.adapt(new Function<Document, Element>() {

			public Element apply(Document resultsFeedDoc) {
				Element entryElement = AtomHelper.getFirstEntry(resultsFeedDoc.getDocumentElement());
				return entryElement;
			}
			
		});

		log.leave();
		return deferredFeedElement;
	}

	
	
	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.status.set(AsyncWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}
	
	private class RetrieveStudyFeedCallback implements Function<Element, Element> {

		@Override
		public Element apply(Element in) {
			log.debug("Study callback apply");
			model.status.set(CuratorHomeWidgetModel.STATUS_READY_FOR_INTERACTION);
			return in;
		}

	}


}
