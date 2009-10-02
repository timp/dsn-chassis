/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface ViewAllStudiesWidgetPubSubAPI {

	void onUserActionSelectStudy(StudyEntry studyEntry);

}
