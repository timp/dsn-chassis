/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.study;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
class Controller {

	private Model model;
	@SuppressWarnings("unused")
	private SubmitterWidgetStudy owner;

	Controller(Model model,	SubmitterWidgetStudy owner) {
		this.model = model;
		this.owner = owner;
	}

	void setMessage(String message) {
		model.setMessage(message);

		// TODO history management
	}

	void setStudyEntry(StudyEntry study) {
		model.setStudyEntry(study);
		
		// TODO history management
	}

}
