/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewstudy;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
public interface SubmitterWidgetStudyListener {

	public void onActionEditStudy(StudyEntry study, SubmitterWidgetViewStudy source);

	public void onActionViewSsq(StudyEntry to,
			SubmitterWidgetViewStudy submitterWidgetStudy);

	public void onActionEditSsq(StudyEntry to,
			SubmitterWidgetViewStudy submitterWidgetStudy);
}
