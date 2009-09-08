/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface ViewStudyWidgetModelListener {

	void onStudyEntryChanged(StudyEntry before, StudyEntry after);

	void onStatusChanged(Integer before, Integer after);
	
}
