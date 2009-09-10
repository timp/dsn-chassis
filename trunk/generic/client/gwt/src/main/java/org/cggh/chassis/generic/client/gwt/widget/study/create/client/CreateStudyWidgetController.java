/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.atom.study.client.format.StudyFactory;
import org.cggh.chassis.generic.atom.study.client.mockimpl.MockStudyFactory;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;
import org.cggh.chassis.generic.atom.vanilla.client.protocol.AtomService;
import org.cggh.chassis.generic.twisted.client.Deferred;
import org.cggh.chassis.generic.twisted.client.Function;

/**
 * @author raok
 *
 */
public class CreateStudyWidgetController {

	final private CreateStudyWidgetModel model;
	final private CreateStudyWidget owner;
	final private AtomService service;
	private String feedURL;
	private StudyFactory studyFactory;

	public CreateStudyWidgetController(CreateStudyWidgetModel model, AtomService service, 
									   String feedURL, CreateStudyWidget owner) {
		this.model = model;
		this.service = service;
		this.feedURL = feedURL;
		this.owner = owner;
		// TODO replace default with real study factory
		this.studyFactory = new MockStudyFactory();
	}

	public void setUpNewStudy() {
				
		// What a new study should be setup as
		String newString = "";
		Boolean newBoolean = false;
		
		this.model.setTitle(newString);
		this.model.setSummary(newString);
		this.model.setAcceptClinicalData(newBoolean);
		this.model.setAcceptInVitroData(newBoolean);
		this.model.setAcceptMolecularData(newBoolean);
		this.model.setAcceptPharmacologyData(newBoolean);
		this.model.setStatus(CreateStudyWidgetModel.STATUS_READY);
	}
	
	//Used for testing purposes
	void setStudyFactory(StudyFactory testFactory) {
		this.studyFactory = testFactory;
	}
	
	//Used for testing purposes
	StudyFactory getStudyFactory() {
		return this.studyFactory;
	}

	public void saveNewStudy() {
		
		//attempt save
		Deferred<AtomEntry> deferredEntry = postStudyEntry();
		
		//Add callbacks to handle success/fail
		deferredEntry.addCallbacks(new SaveSuccessFunctionCallback(), new SaveFailFunctionErrback());
		
	}
	
	//Used for testing purposes
	Deferred<AtomEntry> postStudyEntry() {
				
		// update model status
		model.setStatus(CreateStudyWidgetModel.STATUS_SAVING);
		
		//Create StudyEntry
		StudyEntry newStudy = studyFactory.createStudyEntry();
		newStudy.setTitle(model.getTitle());
		newStudy.setSummary(model.getSummary());
		
		List<String> modules = new ArrayList<String>();
		if (model.acceptClinicalData()) {
			modules.add(CreateStudyWidgetModel.MODULE_CLINICAL);
		}
		if (model.acceptMolecularData()) {
			modules.add(CreateStudyWidgetModel.MODULE_MOLECULAR);
		}
		if (model.acceptInVitroData()) {
			modules.add(CreateStudyWidgetModel.MODULE_IN_VITRO);
		}
		if (model.acceptPharmacologyData()) {
			modules.add(CreateStudyWidgetModel.MODULE_PHARMACOLOGY);
		}
		newStudy.setModules(modules);
		
		//Attempt save and return a Deffered for external to handle.
		return service.postEntry(this.feedURL, newStudy);
	}

	private class SaveSuccessFunctionCallback implements Function<StudyEntry,StudyEntry> {

		public StudyEntry apply(StudyEntry newStudyEntry) {
			owner.newStudyCreated(newStudyEntry);
			model.setStatus(CreateStudyWidgetModel.STATUS_SAVED);
			return newStudyEntry;
		}
		
	}
	
	private class SaveFailFunctionErrback implements Function<Throwable,Throwable> {

		public Throwable apply(Throwable in) {
			model.setStatus(CreateStudyWidgetModel.STATUS_SAVE_ERROR);
			return in;
		}
		
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

	public void cancelCreateStudy() {
		model.setStatus(CreateStudyWidgetModel.STATUS_CANCELLED);
	}

}
