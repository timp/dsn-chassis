/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface EditStudyWidgetPubSubAPI {

	void onStudyUpdateSuccess(StudyEntry updatedStudyEntry);

	void onUserActionEditStudyCancelled();

}
