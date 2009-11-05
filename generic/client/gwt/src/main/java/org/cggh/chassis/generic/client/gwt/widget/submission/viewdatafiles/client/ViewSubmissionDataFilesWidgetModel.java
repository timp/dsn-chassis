/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.viewdatafiles.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import legacy.org.cggh.chassis.generic.atom.vanilla.client.format.AtomEntry;


/**
 * @author raok
 *
 */
public class ViewSubmissionDataFilesWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_ERROR = 3;
	
	private List<AtomEntry> fileDataAtomEntries;
	private Integer status = STATUS_INITIAL;
	private Set<ViewSubmissionDataFilesWidgetModelListener> listeners = new HashSet<ViewSubmissionDataFilesWidgetModelListener>();
	
	
	public List<AtomEntry> getFileDataAtomEntries() {
		return fileDataAtomEntries;
	}


	public Integer getStatus() {		
		return status ;
	}


	public void setStatus(Integer status) {
		
		Integer before = this.status;
		
		this.status = status;
		
		fireOnStatusChanged(before);
	}


	private void fireOnStatusChanged(Integer before) {
		
		for (ViewSubmissionDataFilesWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, getStatus());
		}
	}


	public void setFileDataAtomEntries(List<AtomEntry> fileDataAtomEntries) {
		
		List<AtomEntry> before = getFileDataAtomEntries();
		
		this.fileDataAtomEntries = fileDataAtomEntries;
		
		fireOnDataFileAtomEntriesChanged(before);
	}


	private void fireOnDataFileAtomEntriesChanged(List<AtomEntry> before) {
		
		for (ViewSubmissionDataFilesWidgetModelListener listener : listeners) {
			listener.onDataFileAtomEntriesChanged(before, getFileDataAtomEntries());
		}
	}


	public void addListener(ViewSubmissionDataFilesWidgetModelListener listener) {
		listeners.add(listener);
	}
	
	
	

}
