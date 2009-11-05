/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;


import java.util.Set;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;
import org.cggh.chassis.generic.atomext.client.study.StudyFactory;
import org.cggh.chassis.generic.atomext.client.study.StudyPersistenceService;
import org.cggh.chassis.generic.client.gwt.configuration.client.ConfigurationBean;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.HttpException;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class StudyController implements StudyControllerEditAPI, StudyControllerCreateAPI, StudyControllerViewAPI {

	
	
	
	final private StudyModel model;
	private StudyPersistenceService persistenceService;
	private StudyFactory studyFactory;
	final private AbstractStudyControllerPubSubAPI owner;
	private Log log = LogFactory.getLog(this.getClass());
	private String studyFeedURL;

	
	
	
	public StudyController(StudyModel model, AbstractStudyControllerPubSubAPI owner) {
		this.model = model;
		this.owner = owner;
		
		//Get studyFeedURL from config
		studyFeedURL = ConfigurationBean.getStudyFeedURL();
		
		//this.studyFactory = new MockStudyFactory();
		//this.service = new MockAtomService(studyFactory);
		this.studyFactory = new StudyFactory();
		this.persistenceService = new StudyPersistenceService();
	}

	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#setUpNewStudy(java.lang.String)
	 */
	public void setUpNewStudy(String authorEmail) {
		log.enter("setUpNewStudy");
		
		log.debug("create new submission");
		StudyEntry newSubmissionEntry = studyFactory.createEntry(); 
		
		if (authorEmail != null) {
			log.debug("add author by email: "+authorEmail);
			AtomAuthor atomAuthor = studyFactory.createAuthor();
			atomAuthor.setEmail(authorEmail);		
			newSubmissionEntry.addAuthor(atomAuthor);	
		}
		else {
			log.warn("authorEmail is null");
			// TODO anything else?
		}
		
		model.setStudyEntry(newSubmissionEntry);
		model.setStatus(StudyModel.STATUS_LOADED);
		
		log.leave();
	}

	
	
	
	public void updateTitle(String title) {
		model.setTitle(title);
	}


	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateSummary(java.lang.String)
	 */
	public void updateSummary(String summary) {
		model.setSummary(summary);
	}

	
	
	
	public void updateModules(Set<String> modules) {
		model.setModules(modules);
	}

	
	
	
	public void updateAuthors(Set<AtomAuthor> authors) {
		model.setAuthors(authors);
	}

	
	

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#cancelCreateStudy()
	 */
	public void cancelSaveOrUpdateStudyEntry() {
		log.enter("cancelSaveOrUpdateStudyEntry");
		
		model.setStatus(StudyModel.STATUS_CANCELLED);
		
		//alert owner
		if (owner instanceof StudyControllerPubSubCreateAPI) {
			((StudyControllerPubSubCreateAPI)owner).fireOnUserActionCreateStudyEntryCancelled();
		} else if (owner instanceof StudyControllerPubSubEditAPI) {
			((StudyControllerPubSubEditAPI)owner).onUserActionEditStudyEntryCancelled();
		}
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#loadStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void loadStudyEntry(StudyEntry studyEntryToLoad) {
		log.enter("loadStudyEntry");
		
		model.setStudyEntry(studyEntryToLoad);
		model.setStatus(StudyModel.STATUS_LOADED);
		
		log.debug("Loading study entry: " + studyEntryToLoad.toString());
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#loadStudyEntryByURL(java.lang.String)
	 */
	public void loadStudyEntryByURL(String studyEntryURL) {
		log.enter("loadStudyEntryByURL");
		
		model.setStatus(StudyModel.STATUS_LOADING);
		
		//request studyEntry
		log.debug("loading study entry at: " + studyEntryURL);
		Deferred<StudyEntry> deffered = persistenceService.getEntry(studyEntryURL);
		
		//add callbacks
		deffered.addCallbacks(new LoadStudyEntryCallback(), new LoadStudyEntryErrback());
		
		log.leave();
	}

	
	
	
	//package private for testing purposes
	class LoadStudyEntryCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry studyEntry) {
			log.enter("LoadStudyEntryCallback::apply");
			
			model.setStudyEntry(studyEntry);
			model.setStatus(StudyModel.STATUS_LOADED);
			
			log.leave();
			
			return studyEntry;
		}
		
	}
	
	
	
	
	//package private for testing purposes
	class LoadStudyEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			log.enter("LoadStudyEntryErrback::apply");
			
			model.setStatus(StudyModel.STATUS_ERROR);
			
			log.leave();
			return error;
		}

		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#saveNewStudyEntry()
	 */
	public void saveNewStudyEntry() {
		log.enter("saveNewStudyEntry");
	
		model.setStatus(StudyModel.STATUS_SAVING);
		
		//post new studyEntry
		log.debug("Saving studyEntry to feed: " + studyFeedURL);
		Deferred<StudyEntry> deffered = persistenceService.postEntry(studyFeedURL, model.getStudyEntry());
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateStudyEntryCallback(), new SaveOrUpdateStudyEntryErrback());
		
		log.leave();
	}

	
	
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateStudyEntry()
	 */
	public void updateStudyEntry() {
		log.enter("updateStudyEntry");
		
		model.setStatus(StudyModel.STATUS_SAVING);
		
		//StudyEntry to update
		StudyEntry studyEntry = model.getStudyEntry();
		
		// assume link is relative
		String entryUrl = studyFeedURL + studyEntry.getEditLink().getHref();
		log.debug("Putting updated entry at: " + entryUrl);		
		
		//put studyEntry
		Deferred<StudyEntry> deffered = persistenceService.putEntry(entryUrl, studyEntry);
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateStudyEntryCallback(), new SaveOrUpdateStudyEntryErrback());
		
		log.leave();
	}

	
	
	
	//package private for testing purposes
	class SaveOrUpdateStudyEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			log.enter("SaveOrUpdateStudyEntryErrback :: apply");
			
			if (error instanceof HttpException) {
				HttpException e = (HttpException) error;
				log.debug(e.getLocalizedMessage());
				log.debug(e.getResponse().getText());
			}
			
			model.setStatus(StudyModel.STATUS_ERROR);
			
			log.leave();
			return error;
		}

		
	}
	
	
	
	
	//package private for testing purposes
	class SaveOrUpdateStudyEntryCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry studyEntry) {
			log.enter("SaveOrUpdateStudyEntryCallback :: apply");
			
			model.setStatus(StudyModel.STATUS_SAVED);
			model.setStudyEntry(studyEntry);
			
			//alert owner
			if (owner instanceof StudyControllerPubSubCreateAPI) {
				((StudyControllerPubSubCreateAPI)owner).fireOnNewStudySaved(studyEntry);
			} else if (owner instanceof StudyControllerPubSubEditAPI) {
				((StudyControllerPubSubEditAPI)owner).onStudyUpdated(studyEntry);
			}
			
			log.leave();
			return studyEntry;
		}

		
	}

	
	
	
	public void fireOnUserActionEditThisStudy() {
		log.enter("fireOnUserActionEditThisStudy");
		
		if (owner instanceof StudyControllerPubSubViewAPI) {
			StudyEntry studyEntryToEdit = model.getStudyEntry();
			
			((StudyControllerPubSubViewAPI)owner).fireOnUserActionEditStudy(studyEntryToEdit);
		}
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#fireOnUserActionEditStudyQuestionnaire()
	 */
	public void fireOnUserActionEditStudyQuestionnaire() {
		log.enter("fireOnUserActionEditStudyQuestionnaire");
		
		if (owner instanceof StudyControllerPubSubViewAPI) {
			StudyEntry studyEntry = model.getStudyEntry();
			
			((StudyControllerPubSubViewAPI)owner).fireOnUserActionEditStudyQuestionnaire(studyEntry);
		}
		
		log.leave();
	}





	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#fireOnUserActionViewStudyQuestionnaire()
	 */
	public void fireOnUserActionViewStudyQuestionnaire() {
		log.enter("fireOnUserActionViewStudyQuestionnaire");
		
		if (owner instanceof StudyControllerPubSubViewAPI) {
			StudyEntry studyEntry = model.getStudyEntry();
			
			((StudyControllerPubSubViewAPI)owner).fireOnUserActionViewStudyQuestionnaire(studyEntry);
		}
		
		log.leave();
	}
	
	
	
	
}
