/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.controller.client;

/**
 * @author raok
 *
 */
public interface StudyControllerCreateAPI extends AbstractStudyControllerCreateEditAPI {

	public void setUpNewStudy(String authorEmail);
	
	public void saveNewStudyEntry();
}
