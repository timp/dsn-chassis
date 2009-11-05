/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


/**
 * @author raok
 *
 */
public interface AbstractStudyControllerViewEditAPI {

	public void loadStudyEntry(StudyEntry studyEntryToLoad);

	public void loadStudyEntryByURL(String studyEntryURL);
}
