/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface EditStudyWidgetPubSubAPI {

	void onStudyUpdateSuccess(StudyEntry updatedStudyEntry);

	void onUserActionEditStudyCancelled();

}
