/**
 * 
 */
package org.cggh.chassis.wwarn.prototype.client.submitter.widget.viewssq;

import java.util.ArrayList;
import java.util.List;

import org.cggh.chassis.gwt.lib.study.client.format.StudyEntry;

/**
 * @author aliman
 *
 */
class Model {

	private StudyEntry study = null;

	private List<ModelListener> listeners = new ArrayList<ModelListener>();
	
	void addListener(ModelListener listener) {
		this.listeners.add(listener);
	}

	void setStudyEntry(StudyEntry to) {
		StudyEntry from = this.study;
		this.study  = to;
		for (ModelListener l : listeners) {
			l.onStudyEntryChanged(from, to);
		}
}

}
