/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface StudyControllerPubSubCreateAPI extends AbstractStudyControllerPubSubAPI {

	public void createStudyEntryCancelled();

	public void newStudySaved(StudyEntry studyEntry);

}
