/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.newstudy;

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
	private SubmitterWidgetNewStudy owner;

	Controller(Model model,	SubmitterWidgetNewStudy owner) {
		this.model = model;
		this.owner = owner;
	}

	Deferred<StudyEntry> createStudy(StudyEntry study) {
		
		// TODO make async call to create study
		
		// hack for now
		this.owner.fireStudyCreationSuccess(study);
		
		Deferred<StudyEntry> def = new Deferred<StudyEntry>();
		def.callback(study);
		
		return def;
		
	}
	

}
