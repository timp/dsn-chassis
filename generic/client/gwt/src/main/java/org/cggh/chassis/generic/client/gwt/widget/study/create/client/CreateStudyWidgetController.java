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

/**
 * @author raok
 *
 */
public class CreateStudyWidgetController {

	final private CreateStudyWidgetModel model;
	private AtomService service;
	private String feedURL;
	private StudyFactory studyFactory;

	public CreateStudyWidgetController(CreateStudyWidgetModel model, AtomService service, String feedURL) {
		this.model = model;
		this.service = service;
		this.feedURL = feedURL;
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

	public Deferred<AtomEntry> saveNewStudy() {
		
		// TODO convert to use real AtomPub Service
		
		// update model status
		model.setStatus(CreateStudyWidgetModel.STATUS_SAVING);
				
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
		
		return service.postEntry(this.feedURL, newStudy);
	}

	void setStudyFactory(StudyFactory testFactory) {
		this.studyFactory = testFactory;
	}

	StudyFactory getStudyFactory() {
		return this.studyFactory;
	}

}
