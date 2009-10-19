/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.format.impl.SubmissionFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.impl.AtomServiceImpl;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.HttpException;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class SubmissionController implements SubmissionControllerEditAPI, SubmissionControllerCreateAPI, SubmissionControllerViewAPI {

	final private SubmissionModel model;
	final private AtomService service;
	private SubmissionFactory submissionFactory;
	private String submissionFeedURL;
	final private AbstractSubmissionControllerPubSubAPI owner;
	private Log log = LogFactory.getLog(this.getClass());

	public SubmissionController(SubmissionModel model, AbstractSubmissionControllerPubSubAPI owner) {
		this.model = model;
		this.owner = owner;		

		//Get studyFeedURL from config
		submissionFeedURL = ConfigurationBean.getSubmissionFeedURL();
		
		this.submissionFactory = new SubmissionFactoryImpl();
		this.service = new AtomServiceImpl(submissionFactory);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#setUpNewSubmission(java.lang.String)
	 */
	public void setUpNewSubmission(String authorEmail) {
		log.enter("setUpNewSubmission");
		
		//Create atom author holding author's email, then add to new submission.
		AtomAuthor atomAuthor = submissionFactory.createAuthor();
		atomAuthor.setEmail(authorEmail);		
		SubmissionEntry newSubmissionEntry = submissionFactory.createSubmissionEntry(); 
		newSubmissionEntry.addAuthor(atomAuthor);		
		
		model.setSubmissionEntry(newSubmissionEntry);
		model.setStatus(SubmissionModel.STATUS_LOADED);
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateTitle(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#updateTitle(java.lang.String)
	 */
	public void updateTitle(String title) {
		model.setTitle(title);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateSummary(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#updateSummary(java.lang.String)
	 */
	public void updateSummary(String summary) {
		model.setSummary(summary);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#addStudyLink(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#addStudyLink(java.lang.String)
	 */
	public void addStudyLink(String studyEntryURL) {
		
		//get study links
		Set<String> studyLinks = model.getStudyLinks();
		
		//add link
		studyLinks.add(studyEntryURL);
		
		//set study links
		model.setStudyLinks(studyLinks);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#removeStudyLink(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#removeStudyLink(java.lang.String)
	 */
	public void removeStudyLink(String studyEntryURL) {
		//get study links
		Set<String> studyLinks = model.getStudyLinks();
		
		//remove link
		studyLinks.remove(studyEntryURL);
		
		//set study links
		model.setStudyLinks(studyLinks);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#cancelCreateStudy()
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#cancelCreateStudy()
	 */
	public void cancelCreateOrUpdateSubmissionEntry() {
		log.enter("cancelCreateOrUpdateSubmissionEntry");
		
		model.setStatus(SubmissionModel.STATUS_CANCELLED);
		
		//alert owner
		if (owner instanceof SubmissionControllerPubSubCreateAPI) {
			((SubmissionControllerPubSubCreateAPI)owner).cancelCreateNewSubmissionEntry();
		} else if (owner instanceof SubmissionControllerPubSubEditAPI) {
			((SubmissionControllerPubSubEditAPI)owner).onUserActionEditSubmissionEntryCancelled();
		}
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#loadSubmissionEntry(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI#loadSubmissionEntry(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad) {
		log.enter("loadSubmissionEntry");
		
		model.setSubmissionEntry(submissionEntryToLoad);
		model.setStatus(SubmissionModel.STATUS_LOADED);
		log.trace("submissionEntryToLoad: " + submissionEntryToLoad.toString());		
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#loadSubmissionEntryByURL(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI#loadSubmissionEntryByURL(java.lang.String)
	 */
	public void loadSubmissionEntryByURL(String submissionEntryURL) {
		log.enter("loadSubmissionEntryByURL");
		
		model.setStatus(SubmissionModel.STATUS_LOADING);
		
		//request submissionEntry
		Deferred<AtomEntry> deffered = service.getEntry(submissionEntryURL);
		
		//add callbacks
		deffered.addCallbacks(new LoadSubmissionEntryCallback(), new LoadSubmissionEntryErrback());
		log.trace("Loading entryURL: " + submissionEntryURL);
		
		log.leave();
	}

	//package private for testing purposes
	class LoadSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			model.setSubmissionEntry(submissionEntry);
			model.setStatus(SubmissionModel.STATUS_LOADED);
			return submissionEntry;
		}
		
	}
	
	//package private for testing purposes
	class LoadSubmissionEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			model.setStatus(SubmissionModel.STATUS_ERROR);
			return error;
		}

		
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#saveNewSubmissionEntry()
	 */
	public void saveNewSubmissionEntry() {
		log.enter("saveNewSubmissionEntry");
	
		model.setStatus(SubmissionModel.STATUS_SAVING);
		
		//post new submissionEntry
		Deferred<AtomEntry> deffered = service.postEntry(submissionFeedURL, model.getSubmissionEntry());
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateSubmissionEntryCallback(), new SaveOrUpdateSubmissionEntryErrback());
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateSubmissionEntry()
	 */
	public void updateSubmissionEntry() {
		log.enter("updateSubmissionEntry");
		
		model.setStatus(SubmissionModel.STATUS_SAVING);
		
		//SubmissionEntry to update
		SubmissionEntry submissionEntry = model.getSubmissionEntry();
		
		//assume link is relative
		String entryURL = submissionFeedURL + submissionEntry.getEditLink().getHref();
		
		//put submissionEntry
		Deferred<AtomEntry> deffered = service.putEntry(entryURL, submissionEntry);
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateSubmissionEntryCallback(), new SaveOrUpdateSubmissionEntryErrback());
		
		log.leave();
	}

	//package private for testing purposes
	class SaveOrUpdateSubmissionEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			log.enter("SaveOrUpdateSubmissionEntryErrback::apply");

			if (error instanceof HttpException) {
				HttpException e = (HttpException) error;
				log.trace(e.getLocalizedMessage());
				log.trace(e.getResponse().getText());
			}
			
			model.setStatus(SubmissionModel.STATUS_ERROR);
						
			log.leave();
			return error;
		}

		
	}
	
	//package private for testing purposes
	class SaveOrUpdateSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			log.enter("SaveOrUpdateSubmissionEntryCallback::apply");
			
			model.setStatus(SubmissionModel.STATUS_SAVED);
			model.setSubmissionEntry(submissionEntry);
			
			//alert owner
			if (owner instanceof SubmissionControllerPubSubCreateAPI) {
				log.trace("alerted create owner");
				
				((SubmissionControllerPubSubCreateAPI)owner).newSubmissionSaved(submissionEntry);
			} else if (owner instanceof SubmissionControllerPubSubEditAPI) {
				log.trace("alerted edit owner");
				
				((SubmissionControllerPubSubEditAPI)owner).onSubmissionEntryUpdated(submissionEntry);
			}
			
			log.leave();
			return submissionEntry;
		}

		
	}

	public void updateModules(Set<String> modules) {
		model.setModules(modules);
	}

	public void onUserActionEditThisSubmission() {
		log.enter("onUserActionEditThisSubmission");
		
		if (owner instanceof SubmissionControllerPubSubViewAPI) {
			SubmissionEntry submissionEntryToEdit = model.getSubmissionEntry();
			
			((SubmissionControllerPubSubViewAPI)owner).onUserActionEditSubmission(submissionEntryToEdit);
		}
		
		log.leave();
		
	}

	public void updateAuthors(Set<AtomAuthor> authors) {
		model.setAuthors(authors);
	}
	
}
