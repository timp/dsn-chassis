/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.controller.client;

import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.submission.client.format.SubmissionFactory;
import org.cggh.chassis.generic.atom.submission.client.mockimpl.MockSubmissionFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.widget.submission.model.client.SubmissionModel;
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
	private String feedURL;
	final private AbstractSubmissionControllerPubSubAPI owner;

	public SubmissionController(SubmissionModel model, AtomService service, AbstractSubmissionControllerPubSubAPI owner) {
		this.model = model;
		this.service = service;
		this.owner = owner;
		
		// TODO replace default with real factory
		this.submissionFactory = new MockSubmissionFactory();		
	}

	//Used for testing purposes
	void setSubmissionFactory(SubmissionFactory testFactory) {
		this.submissionFactory = testFactory;
	}

	//Used for testing purposes
	SubmissionFactory getSubmissionFactory() {
		return submissionFactory;
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#setUpNewSubmission(java.lang.String)
	 */
	public void setUpNewSubmission(String feedURL) {
		
		this.feedURL = feedURL;
		model.setSubmissionEntry(submissionFactory.createSubmissionEntry());
		model.setStatus(SubmissionModel.STATUS_LOADED);
		
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
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptClinicalData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#updateAcceptClinicalData(java.lang.Boolean)
	 */
	public void updateAcceptClinicalData(Boolean acceptClinicalData) {
		model.setAcceptClinicalData(acceptClinicalData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptMolecularData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#updateAcceptMolecularData(java.lang.Boolean)
	 */
	public void updateAcceptMolecularData(Boolean acceptMolecularData) {
		model.setAcceptMolecularData(acceptMolecularData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptInVitroData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#updateAcceptInVitroData(java.lang.Boolean)
	 */
	public void updateAcceptInVitroData(Boolean acceptInVitroData) {
		model.setAcceptInVitroData(acceptInVitroData);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateAcceptPharmacologyData(java.lang.Boolean)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerCreateAPI#updateAcceptPharmacologyData(java.lang.Boolean)
	 */
	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		model.setAcceptPharmacologyData(acceptPharmacologyData);
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
	public void cancelSaveOrUpdateSubmissionEntry() {
		
		model.setStatus(SubmissionModel.STATUS_CANCELLED);
		
		//alert owner
		if (owner instanceof SubmissionControllerPubSubCreateAPI) {
			((SubmissionControllerPubSubCreateAPI)owner).createSubmissionEntryCancelled();
		} 
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#loadSubmissionEntry(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI#loadSubmissionEntry(org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry)
	 */
	public void loadSubmissionEntry(SubmissionEntry submissionEntryToLoad) {
		model.setSubmissionEntry(submissionEntryToLoad);
		model.setStatus(SubmissionModel.STATUS_LOADED);
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#loadSubmissionEntryByURL(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerViewAPI#loadSubmissionEntryByURL(java.lang.String)
	 */
	public void loadSubmissionEntryByURL(String submissionEntryURL) {
		
		model.setStatus(SubmissionModel.STATUS_LOADING);
		
		//request submissionEntry
		Deferred<AtomEntry> deffered = service.getEntry(submissionEntryURL);
		
		//add callbacks
		deffered.addCallbacks(new LoadSubmissionEntryCallback(), new LoadSubmissionEntryErrback());
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
	
		model.setStatus(SubmissionModel.STATUS_SAVING);
		
		//post new submissionEntry
		Deferred<AtomEntry> deffered = service.postEntry(feedURL, model.getSubmissionEntry());
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateSubmissionEntryCallback(), new SaveOrUpdateSubmissionEntryErrback());
	}

	/* (non-Javadoc)
	 * @see org.cggh.chassis.generic.client.gwt.widget.submission.controller.client.SubmissionControllerEditAPI#updateSubmissionEntry()
	 */
	public void updateSubmissionEntry() {
		
		model.setStatus(SubmissionModel.STATUS_SAVING);
		
		//SubmissionEntry to update
		SubmissionEntry submissionEntry = model.getSubmissionEntry();
		
		//put submissionEntry
		Deferred<AtomEntry> deffered = service.putEntry(submissionEntry.getEditLink().getHref(), submissionEntry);
		
		//add callbacks
		deffered.addCallbacks(new SaveOrUpdateSubmissionEntryCallback(), new SaveOrUpdateSubmissionEntryErrback());
	}

	//package private for testing purposes
	class SaveOrUpdateSubmissionEntryErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable error) {
			model.setStatus(SubmissionModel.STATUS_ERROR);
			return error;
		}

		
	}
	
	//package private for testing purposes
	class SaveOrUpdateSubmissionEntryCallback implements Function<SubmissionEntry,SubmissionEntry> {

		public SubmissionEntry apply(SubmissionEntry submissionEntry) {
			
			model.setStatus(SubmissionModel.STATUS_SAVED);
			model.setSubmissionEntry(submissionEntry);
			
			//alert owner
			if (owner instanceof SubmissionControllerPubSubCreateAPI) {
				((SubmissionControllerPubSubCreateAPI)owner).newSubmissionSaved(submissionEntry);
			} 
			
			return submissionEntry;
		}

		
	}
	
}
