/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface CreateStudyWidgetPubSubAPI {

	public void onNewStudyCreated(StudyEntry studyEntry);

	public void onUserActionCreateStudyCancelled();

}
