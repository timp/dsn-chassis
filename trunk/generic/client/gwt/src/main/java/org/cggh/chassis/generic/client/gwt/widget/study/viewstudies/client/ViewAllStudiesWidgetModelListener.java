/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.viewstudies.client;

import java.util.List;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public interface ViewAllStudiesWidgetModelListener {

	void onStatusChanged(Integer before, Integer after);

	void onStudyEntriesChanged(List<StudyEntry> before, List<StudyEntry> after);

}
