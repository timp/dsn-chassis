/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.study;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
class Model {

	// properties
	private String message = null;
	private StudyEntry studyEntry = null;

	// listeners
	private List<ModelListener> listeners = new ArrayList<ModelListener>();

	void addListener(ModelListener listener) {
		this.listeners.add(listener);
	}

	void setMessage(String to) {
		String from = this.message;
		this.message = to;
		for (ModelListener l : listeners) {
			l.onMessageChanged(from, to);
		}
	}

	void setStudyEntry(StudyEntry to) {
		StudyEntry from = this.studyEntry;
		this.studyEntry = to;
		for (ModelListener l : listeners) {
			l.onStudyEntryChanged(from, to);
		}
	}

}
