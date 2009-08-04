/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.study;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
public interface SubmitterWidgetStudyListener {

	public void onActionEditStudy(StudyEntry study, SubmitterWidgetStudy source);

	public void onActionViewSsq(StudyEntry to,
			SubmitterWidgetStudy submitterWidgetStudy);

	public void onActionEditSsq(StudyEntry to,
			SubmitterWidgetStudy submitterWidgetStudy);
}
