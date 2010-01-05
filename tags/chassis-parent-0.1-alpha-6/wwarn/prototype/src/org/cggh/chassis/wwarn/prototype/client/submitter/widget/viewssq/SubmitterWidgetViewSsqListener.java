/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewssq;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
public interface SubmitterWidgetViewSsqListener {

	public void onActionEditSsq(StudyEntry to, SubmitterWidgetViewSsq source);

	public void onActionEditStudy(StudyEntry to, SubmitterWidgetViewSsq source);

	public void onActionViewStudy(StudyEntry to,
			SubmitterWidgetViewSsq source);

}
