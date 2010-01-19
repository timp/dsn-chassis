package org.cggh.chassis.wwarn.ui.submitter.client;



import java.util.ArrayList;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.async.client.Function;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.miniatom.client.Atom;
import org.cggh.chassis.generic.miniatom.client.AtomHelper;
import org.cggh.chassis.generic.miniatom.client.ext.ChassisHelper;
import org.cggh.chassis.generic.widget.client.ErrorEvent;
import org.cggh.chassis.wwarn.ui.common.client.Config;
import org.cggh.chassis.wwarn.ui.common.client.RenderUtils;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

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
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.widget.client.MultiSelectModel#loadItems(boolean)
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
	
	public void proceed() { 
		owner.proceed.fireEvent();		
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


	private Deferred<Document> createStudy(String studyTitle, 
			String studySummary, String otherSubmitters, ArrayList<String> modules) {
		
		Document studyEntryDoc = AtomHelper.createEntryDoc();
		Element studyEntryElement = studyEntryDoc.getDocumentElement();
		AtomHelper.addAuthor(studyEntryElement, Config.get(Config.USER_EMAIL));
		
		AtomHelper.setTitle(studyEntryElement, studyTitle);
		AtomHelper.setSummary(studyEntryElement, studySummary);
		
		// parse otherSubmitters and add to authors.
		String[] emails = RenderUtils.extractEmails(otherSubmitters);
		for (int i = 0; i < emails.length; i++) { 
			AtomHelper.addAuthor(studyEntryElement, emails[i]);
		}
		Element studyElement = ChassisHelper.createStudy();
		ChassisHelper.setModules(studyElement, modules);
		studyEntryElement.appendChild(studyElement); 
		
		return Atom.postEntry(Config.get(Config.COLLECTION_STUDIES_URL), studyEntryDoc);
	}


	public void createStudyAndProceed(String studyTitle, String studySummary, String otherSubmitters, ArrayList<String> modules) {
		Deferred<Document> deferredStudyEntrydoc = createStudy(studyTitle, studySummary, otherSubmitters, modules);
		deferredStudyEntrydoc.addCallback(new Function<Document, Document>() {
			public Document apply(Document in) {
				model.setSelectedStudy(AtomHelper.getId(in.getDocumentElement()));
				model.setStatus(SelectStudyWidgetModel.STATUS_STUDY_CREATED);
				owner.proceed.fireEvent();
				return in;
		}
		});
		deferredStudyEntrydoc.addErrback(new DefaultErrback());
	}
	
	private class DefaultErrback implements Function<Throwable, Throwable> {

		public Throwable apply(Throwable in) {
			log.error("unexpected error", in);
			model.setStatus(UploadFilesWidgetModel.STATUS_ERROR);
			owner.fireEvent(new ErrorEvent(in));
			return in;
		}
		
	}

}
