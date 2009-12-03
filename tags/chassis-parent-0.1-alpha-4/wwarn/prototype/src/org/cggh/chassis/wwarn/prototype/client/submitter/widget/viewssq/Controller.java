/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewssq;

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
	private SubmitterWidgetViewSsq owner;

	Controller(Model model,	SubmitterWidgetViewSsq owner) {
		this.model = model;
		this.owner = owner;
	}

	void setStudyEntry(StudyEntry study) {
		model.setStudyEntry(study);
	}
	

}
