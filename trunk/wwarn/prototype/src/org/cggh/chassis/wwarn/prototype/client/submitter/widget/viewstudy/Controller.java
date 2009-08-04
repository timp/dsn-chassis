/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewstudy;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
class Controller {

	private Model model;
	@SuppressWarnings("unused")
	private SubmitterWidgetViewStudy owner;

	Controller(Model model,	SubmitterWidgetViewStudy owner) {
		this.model = model;
		this.owner = owner;
	}

	void setMessage(String message, boolean waypoint) {
		model.setMessage(message);

		if (waypoint) {
			owner.waypoint();
		}
		else {
			owner.syncStateKey();
		}

	}

	void setStudyEntry(StudyEntry study) {
		this.setStudyEntry(study, true);
	}
	
	void setStudyEntry(StudyEntry study, boolean waypoint) {
		model.setStudyEntry(study);
		
		if (waypoint) {
			owner.waypoint();
		}
		else {
			owner.syncStateKey();
		}

	}

}
