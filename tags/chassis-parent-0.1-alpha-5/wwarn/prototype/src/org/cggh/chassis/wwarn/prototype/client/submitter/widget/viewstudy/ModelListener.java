/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewstudy;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
interface ModelListener {

	void onMessageChanged(String from, String to);

	void onStudyEntryChanged(StudyEntry from, StudyEntry to);

}
