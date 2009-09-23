/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.format.impl.StudyFactoryImpl;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomProtocolException;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.client.gwt.configuration.client.Configuration;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class EditStudyWidgetController {


	final private AtomService service;
	private StudyFactory studyFactory;
	private EditStudyWidgetModel model;
	private EditStudyWidget owner;
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public EditStudyWidgetController(EditStudyWidgetModel model,
			AtomService service, EditStudyWidget owner) {
		this.model = model;
		this.service = service;
		this.owner = owner;
//		this.studyFactory = new MockStudyFactory();
		this.studyFactory = new StudyFactoryImpl();
	}

	//Used for testing purposes
	void setStudyFactory(StudyFactory testFactory) {
		this.studyFactory = testFactory;
	}
	
	//Used for testing purposes
	StudyFactory getStudyFactory() {
		return this.studyFactory;
	}

	public Deferred<AtomEntry> getStudyEntry(String entryURL) {
		return service.getEntry(entryURL);
	}

	public void updateTitle(String title) {
		model.setTitle(title);
	}

	public void updateSummary(String summary) {
		model.setSummary(summary);
	}

	public void updateAcceptClinicalData(Boolean acceptClinicalData) {
		model.setAcceptClinicalData(acceptClinicalData);
	}

	public void updateAcceptMolecularData(Boolean acceptMolecularData) {
		model.setAcceptMolecularData(acceptMolecularData);
	}

	public void updateAcceptInVitroData(Boolean acceptInVitroData) {
		model.setAcceptInVitroData(acceptInVitroData);
	}

	public void updateAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		model.setAcceptPharmacologyData(acceptPharmacologyData);
	}

	public void cancelEditStudy() {
		model.setStatus(EditStudyWidgetModel.STATUS_CANCELLED);
	}

	public void putUpdatedStudy() {
		//attempt save
		Deferred<AtomEntry> deferredEntry = saveUpdatedStudy();
		
		//Add callbacks to handle success/fail
		deferredEntry.addCallbacks(new SaveUpdateSuccessFunctionCallback(), new SaveUpdateFailFunctionErrback());
	}

	//Used for testing purposes
	Deferred<AtomEntry> saveUpdatedStudy() {
		
		//update model status
		model.setStatus(EditStudyWidgetModel.STATUS_SAVING);
		
		//study entry to update
		StudyEntry studyEntry = model.getStudyEntry();
		
		String entryURL = Configuration.getStudyFeedURL() + studyEntry.getEditLink().getHref(); // assume relative link
		
		return service.putEntry(entryURL, studyEntry);
	}
	
	class SaveUpdateSuccessFunctionCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry updatedStudyEntry) {
			owner.studyUpdateSuccess(updatedStudyEntry);
			model.setStatus(EditStudyWidgetModel.STATUS_SAVED);
			return updatedStudyEntry;
		}
		
	}
	
	class SaveUpdateFailFunctionErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable in) {
			
			if (in instanceof AtomProtocolException) {
				AtomProtocolException ex = (AtomProtocolException) in;
				log.trace("handling atom protocol exception: "+ex.getLocalizedMessage());
				log.trace(ex.getResponse().getStatusCode() + " " +ex.getResponse().getStatusText());
				log.trace(ex.getResponse().getText());
			}
			
			model.setStatus(EditStudyWidgetModel.STATUS_SAVE_ERROR);
			return in;
		}
		
	}

	public void loadStudyEntry(StudyEntry studyEntry) {
		model.setStudyEntry(studyEntry);
		model.setStatus(EditStudyWidgetModel.STATUS_LOADED);
	}
	
}
