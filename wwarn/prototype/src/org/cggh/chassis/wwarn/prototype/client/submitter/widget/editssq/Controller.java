/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.editssq;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;
import org.cggh.chassis.gwt.lib.twisted.client.Deferred;

/**
 * @author aliman
 *
 */
class Controller {

	@SuppressWarnings("unused")
	private Model model;
	@SuppressWarnings("unused")
	private SubmitterWidgetEditSsq owner;

	Controller(Model model,	SubmitterWidgetEditSsq owner) {
		this.model = model;
		this.owner = owner;
	}

	Deferred<StudyEntry> updateStudy(StudyEntry study) {
		
		// TODO make async call to create study
		
		// hack for now
		this.owner.fireStudyUpdateSuccess(study);
		
		Deferred<StudyEntry> def = new Deferred<StudyEntry>();
		def.callback(study);
		
		return def;
		
	}

	void setStudyEntry(StudyEntry study) {
		model.setStudyEntry(study);
	}
	

}
