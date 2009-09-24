/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;


import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomProtocolException;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class StudyController implements StudyControllerEditAPI, StudyControllerCreateAPI, StudyControllerViewAPI {

	final private StudyModel model;
	final private AtomService service;
	private StudyFactory studyFactory;
	final private AbstractStudyControllerPubSubAPI owner;
	private Log log = LogFactory.getLog(this.getClass());

	public StudyController(StudyModel model, AtomService service, AbstractStudyControllerPubSubAPI owner) {
		this.model = model;
		this.service = service;
		this.owner = owner;
				
		//this.studyFactory = new MockStudyFactory();
		this.studyFactory = new StudyFactoryImpl();
	}

	//Used for testing purposes
	void setStudyFactory(StudyFactory testFactory) {
		this.studyFactory = testFactory;
	}

	//Used for testing purposes
	StudyFactory getStudyFactory() {
		return studyFactory;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#setUpNewStudy(java.lang.String)
	 */
	public void setUpNewStudy() {
		log.enter("setUpNewStudy");
		
		model.setStudyEntry(studyFactory.createStudyEntry());
		model.setStatus(StudyModel.STATUS_LOADED);
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateTitle(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateTitle(java.lang.String)
	 */
	public void updateTitle(String title) {
		model.setTitle(title);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateSummary(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateSummary(java.lang.String)
	 */
	public void updateSummary(String summary) {
		model.setSummary(summary);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateAcceptClinicalData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateAcceptClinicalData(java.lang.Boolean)
	 */
	public void updateAcceptClinicalData(Boolean acceptClinicalData) {
		model.setAcceptClinicalData(acceptClinicalData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateAcceptMolecularData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateAcceptMolecularData(java.lang.Boolean)
	 */
	public void updateAcceptMolecularData(Boolean acceptMolecularData) {
		model.setAcceptMolecularData(acceptMolecularData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateAcceptInVitroData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateAcceptInVitroData(java.lang.Boolean)
	 */
	public void updateAcceptInVitroData(Boolean acceptInVitroData) {
		model.setAcceptInVitroData(acceptInVitroData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateAcceptPharmacologyData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#updateAcceptPharmacologyData(java.lang.Boolean)
	 */
	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		model.setAcceptPharmacologyData(acceptPharmacologyData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#cancelCreateStudy()
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#cancelCreateStudy()
	 */
	public void cancelSaveOrUpdateStudyEntry() {
		log.enter("cancelSaveOrUpdateStudyEntry");
		
		model.setStatus(StudyModel.STATUS_CANCELLED);
		
		//alert owner
		if (owner instanceof StudyControllerPubSubCreateAPI) {
			((StudyControllerPubSubCreateAPI)owner).onUserActionCreateStudyEntryCancelled();
		} else if (owner instanceof StudyControllerPubSubEditAPI) {
			((StudyControllerPubSubEditAPI)owner).onUserActionEditStudyEntryCancelled();
		}
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#loadStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#loadStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void loadStudyEntry(StudyEntry studyEntryToLoad) {
		log.enter("loadStudyEntry");
		
		model.setStudyEntry(studyEntryToLoad);
		model.setStatus(StudyModel.STATUS_LOADED);
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#loadStudyEntryByURL(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#loadStudyEntryByURL(java.lang.String)
	 */
	public void loadStudyEntryByURL(String studyEntryURL) {
		log.enter("loadStudyEntryByURL");
		
		model.setStatus(StudyModel.STATUS_LOADING);
		
		//request studyEntry
		log.trace("loading study entry at: " + studyEntryURL);
		Deferred<AtomEntry> deffered = service.getEntry(studyEntryURL);
		
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
	public void saveNewStudyEntry(String feedURL) {
		log.enter("saveNewStudyEntry");
	
		model.setStatus(StudyModel.STATUS_SAVING);
		
		//post new studyEntry
		log.trace("SavingstudyEntry to feed: " + feedURL);
		Deferred<AtomEntry> deffered = service.postEntry(feedURL, model.getStudyEntry());
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateStudyEntryCallback(), new SaveOrUpdateStudyEntryErrback());
		
		log.leave();
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateStudyEntry()
	 */
	public void updateStudyEntry(String feedURL) {
		log.enter("updateStudyEntry");
		
		model.setStatus(StudyModel.STATUS_SAVING);
		
		//StudyEntry to update
		StudyEntry studyEntry = model.getStudyEntry();
		
		// assume link is relative
		String entryUrl = feedURL + studyEntry.getEditLink().getHref();
		log.trace("Putting updated entry at: " + entryUrl);		
		
		//put studyEntry
		Deferred<AtomEntry> deffered = service.putEntry(entryUrl, studyEntry);
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateStudyEntryCallback(), new SaveOrUpdateStudyEntryErrback());
		
		log.leave();
	}

	//package private for testing purposes
	class SaveOrUpdateStudyEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			log.enter("SaveOrUpdateStudyEntryErrback :: apply");
			
			if (error instanceof AtomProtocolException) {
				AtomProtocolException e = (AtomProtocolException) error;
				log.trace(e.getLocalizedMessage());
				log.trace(e.getResponse().getText());
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
				((StudyControllerPubSubCreateAPI)owner).onNewStudySaved(studyEntry);
			} else if (owner instanceof StudyControllerPubSubEditAPI) {
				((StudyControllerPubSubEditAPI)owner).onStudyUpdated(studyEntry);
			}
			
			log.leave();
			return studyEntry;
		}

		
	}

	public void onUserActionEditThisStudy() {
		log.enter("onUserActionEditThisStudy");
		
		if (owner instanceof StudyControllerPubSubViewAPI) {
			StudyEntry studyEntryToEdit = model.getStudyEntry();
			
			((StudyControllerPubSubViewAPI)owner).onUserActionEditStudy(studyEntryToEdit);
		}
		
		log.leave();
	}
	
}
