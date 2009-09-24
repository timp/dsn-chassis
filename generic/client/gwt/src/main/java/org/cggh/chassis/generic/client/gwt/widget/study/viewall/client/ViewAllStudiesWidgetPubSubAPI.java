/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewall.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface ViewAllStudiesWidgetPubSubAPI {

	void onUserActionViewStudy(StudyEntry studyEntry);

}
