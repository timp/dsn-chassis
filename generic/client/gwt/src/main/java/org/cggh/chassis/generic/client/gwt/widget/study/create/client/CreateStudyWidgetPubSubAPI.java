/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface CreateStudyWidgetPubSubAPI {

	void onNewStudyCreated(StudyEntry studyEntry);

}
