/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cggh.chassis.generic.atomext.client.submission.SubmissionEntry;


/**
 * @author raok
 *
 */
public class MySubmissionsWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_ERROR = 3;
	
	private Integer status = STATUS_INITIAL;
	private Set<MySubmissionsWidgetModelListener> listeners = new HashSet<MySubmissionsWidgetModelListener>();
	private List<SubmissionEntry> submissionEntries; 


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);
		
	}

	private void fireOnStatusChanged(Integer before, Integer after) {
		for (MySubmissionsWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}

	public void addListener(MySubmissionsWidgetModelListener listener) {
		listeners.add(listener);
	}

	public List<SubmissionEntry> getSubmissionEntries() {
		return submissionEntries;
	}

	public void setSubmissionEntries(List<SubmissionEntry> submissionEntries) {
		List<SubmissionEntry> before = this.submissionEntries;
		this.submissionEntries = submissionEntries;
		fireOnSubmissionEntriesChanged(before, submissionEntries);
	}

	private void fireOnSubmissionEntriesChanged(List<SubmissionEntry> before, List<SubmissionEntry> after) {
		for (MySubmissionsWidgetModelListener listener : listeners) {
			listener.onSubmissionEntriesChanged(before, after);
		}
	}
}
