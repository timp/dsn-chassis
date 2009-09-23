/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;


import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.study.model.client.StudyModel;
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
	private String feedURL;
	final private AbstractStudyControllerPubSubAPI owner;

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
	public void setUpNewStudy(String feedURL) {
		
		this.feedURL = feedURL;
		model.setStudyEntry(studyFactory.createStudyEntry());
		model.setStatus(StudyModel.STATUS_LOADED);
		
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
		
		model.setStatus(StudyModel.STATUS_CANCELLED);
		
		//alert owner
		if (owner instanceof StudyControllerPubSubCreateAPI) {
			((StudyControllerPubSubCreateAPI)owner).createStudyEntryCancelled();
		} 
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#loadStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#loadStudyEntry(org.cggh.chassis.generic.atom.study.client.format.StudyEntry)
	 */
	public void loadStudyEntry(StudyEntry studyEntryToLoad) {
		model.setStudyEntry(studyEntryToLoad);
		model.setStatus(StudyModel.STATUS_LOADED);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#loadStudyEntryByURL(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerViewAPI#loadStudyEntryByURL(java.lang.String)
	 */
	public void loadStudyEntryByURL(String studyEntryURL) {
		
		model.setStatus(StudyModel.STATUS_LOADING);
		
		//request studyEntry
		Deferred<AtomEntry> deffered = service.getEntry(studyEntryURL);
		
		//add callbacks
		deffered.addCallbacks(new LoadStudyEntryCallback(), new LoadStudyEntryErrback());
	}

	//package private for testing purposes
	class LoadStudyEntryCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry studyEntry) {
			model.setStudyEntry(studyEntry);
			model.setStatus(StudyModel.STATUS_LOADED);
			return studyEntry;
		}
		
	}
	
	//package private for testing purposes
	class LoadStudyEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			model.setStatus(StudyModel.STATUS_ERROR);
			return error;
		}

		
	}
	
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerCreateAPI#saveNewStudyEntry()
	 */
	public void saveNewStudyEntry() {
	
		model.setStatus(StudyModel.STATUS_SAVING);
		
		//post new studyEntry
		Deferred<AtomEntry> deffered = service.postEntry(feedURL, model.getStudyEntry());
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateStudyEntryCallback(), new SaveOrUpdateStudyEntryErrback());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.study.controller.client.StudyControllerEditAPI#updateStudyEntry()
	 */
	public void updateStudyEntry() {
		
		model.setStatus(StudyModel.STATUS_SAVING);
		
		//StudyEntry to update
		StudyEntry studyEntry = model.getStudyEntry();
		
		//put studyEntry
		Deferred<AtomEntry> deffered = service.putEntry(studyEntry.getEditLink().getHref(), studyEntry);
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateStudyEntryCallback(), new SaveOrUpdateStudyEntryErrback());
	}

	//package private for testing purposes
	class SaveOrUpdateStudyEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			model.setStatus(StudyModel.STATUS_ERROR);
			return error;
		}

		
	}
	
	//package private for testing purposes
	class SaveOrUpdateStudyEntryCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry studyEntry) {
			
			model.setStatus(StudyModel.STATUS_SAVED);
			model.setStudyEntry(studyEntry);
			
			//alert owner
			if (owner instanceof StudyControllerPubSubCreateAPI) {
				((StudyControllerPubSubCreateAPI)owner).newStudySaved(studyEntry);
			} 
			
			return studyEntry;
		}

		
	}

	public void updateSubmissionEntry() {
		// TODO Auto-generated method stub
		
	}
	
}
