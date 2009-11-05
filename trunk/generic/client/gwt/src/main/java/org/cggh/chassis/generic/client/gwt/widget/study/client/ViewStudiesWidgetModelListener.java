/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.List;

import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


import com.google.gwt.user.client.ui.Panel;

/**
 * @author raok
 *
 */
public interface ViewStudiesWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after);

	/**
	 * @return
	 */
	Panel getCanvas();

}
