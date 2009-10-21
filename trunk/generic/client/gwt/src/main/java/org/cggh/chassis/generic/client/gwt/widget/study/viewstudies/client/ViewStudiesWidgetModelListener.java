/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

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
