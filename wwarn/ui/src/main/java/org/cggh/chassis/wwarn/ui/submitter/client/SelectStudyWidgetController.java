package org.cggh.chassis.wwarn.ui.submitter.client;


import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;

import com.google.gwt.xml.client.Document;

/**
 * @author timp
 * @since 13/01/10
 */
public class SelectStudyWidgetController {


	private Log log = LogFactory.getLog(SelectStudyWidgetController.class);

	private SelectStudyWidget owner;
	private SelectStudyWidgetModel model;


	public SelectStudyWidgetController(SelectStudyWidget owner,
			SelectStudyWidgetModel model) {
		this.owner = owner;
		this.model = model;
	}
	/*
	public Deferred<ChassisWidget> refreshAndCallBack() { 
		Deferred<Map<String,String>> deferredStudyLinksToTitles = loadItems(true);
		
		
		deferredStudyLinksToTitles.addCallback(new Function<Map<String,String>, Map<String,String>>() {

			public Map<String,String> apply(Map<String,String> in) {
				log.enter("[anon callback] :: apply");
				
				model.setStatus(AsyncWidgetModel.STATUS_READY);
				
				log.debug("items retrieved, rendering select");
				if (studyLinksToTitles.isEmpty()) { 
					// TODO
				} else { 
					String listId = HTMLPanel.createUniqueId(); 
					String listHtml = ("<ul id=\"" +listId+"\">");
					for (String link : in.keySet()) {
						listHtml += ("<li><a href=\"" + link + "\">" + in.get(link) + "</a></li>");
					}
					listHtml += "</ul>";
					
					//content.add(new HTML(listHtml), outputId);
					
				}
				
				log.leave();
				return in;
			}
			
		});
		
		deferredStudyLinksToTitles.addErrback(new AsyncErrback(owner, model) { 
			public Throwable apply(Throwable t) {
				owner.getRenderer().error(t.getMessage()); 
				return super.apply(t);
			} 
		});
		return null;
		
	}
	*/
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#loadItems(boolean)
	 */
	
	/*
	public Deferred<Map<String, String>> loadItems(boolean forceRefresh) {
		log.enter("loadItems");
		Deferred<Map<String,String>> deferredItems;
		if (this.studyLinksToTitles == null || forceRefresh) {
			log.debug("loading items");
			deferredItems = getMapOfStudyLinksToTitlesForCurrentUser();
			deferredItems.addCallback(new Function<Map<String,String>, Map<String,String>>() {

				public Map<String, String> apply(Map<String, String> in) {
					studyLinksToTitles = in;
					return in;
				}
				
			});
		}
		else {
			log.debug("callback immediately with existing items");
			deferredItems = new Deferred<Map<String,String>>();
			deferredItems.callback(this.studyLinksToTitles);
		}
		log.leave();
		return deferredItems;
	}
	*/


/*	
	public static Deferred<Map<String,String>> getMapOfStudyLinksToTitlesForCurrentUser() {
		
		StudyQueryService service = new StudyQueryService(Config.get(Config.QUERY_STUDIES_URL));
		StudyQuery query = new StudyQuery();
		query.setAuthorEmail( Config.get(Config.USER_EMAIL) );
		Deferred<StudyFeed> deferredFeed = service.query(query);
		
		Deferred<Map<String,String>> deferredMap = deferredFeed.adapt(new Function<StudyFeed, Map<String,String>>() {

			public Map<String, String> apply(StudyFeed in) {
				Map<String,String> studyLinks = new HashMap<String,String>();
				for (StudyEntry e : in.getEntries()) {
					String title = e.getTitle();
//					String link = Configuration.getStudyCollectionUrl() + e.getEditLink().getHref();
					String link = e.getEditLink().getHref(); // TODO fix for aboslute URIs
					studyLinks.put(link, title);
				}
				return studyLinks;
			}
			
		});
		return deferredMap;
		
	}
	
	*/

	public void retrieveStudies() {
		// Set the model's status to pending.
		model.setStatus(SelectStudyWidgetModel.STATUS_RETRIEVE_STUDIES_PENDING);
		
		String studyQueryServiceUrl = Config.get(Config.QUERY_STUDIES_URL);
		
		Deferred<Document> deferredStudyFeedDoc = Atom.getFeed(studyQueryServiceUrl);
		
		// Add a call-back and error-back for the asynchronous feed.
		deferredStudyFeedDoc.addCallback(new RetrieveStudiesCallback());
		deferredStudyFeedDoc.addErrback(new RetrieveStudiesErrback());
		
		
	}
	
	
	
	
	private class RetrieveStudiesCallback implements Function<Document, Document> {

		public Document apply(Document in) {
			model.setStudyFeed(in);
			model.setStatus(SelectStudyWidgetModel.STATUS_STUDIES_RETRIEVED);
			return in;
		}

	}


	private class RetrieveStudiesErrback  implements
			Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(SelectStudyWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			owner.getRenderer().error(in.getMessage());
			return in;
		}
       
	}
}
