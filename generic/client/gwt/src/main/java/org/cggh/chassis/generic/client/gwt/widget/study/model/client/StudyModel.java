/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.model.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;

/**
 * @author raok
 *
 */
public class StudyModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_SAVING = 3;
	public static final Integer STATUS_SAVED = 4;
	public static final Integer STATUS_ERROR = 5;
	public static final Integer STATUS_CANCELLED = 6;
	

	private StudyEntry studyEntry;
	private Integer status = STATUS_INITIAL;
	
	//maybe have two groups of listeners: one for read only properties, and one for editable
	private Set<StudyModelListener> listeners = new HashSet<StudyModelListener>();
	
	
	
	public StudyEntry getStudyEntry() {
		return studyEntry;
	}



	public void setStudyEntry(StudyEntry studyEntry) {
				
		this.studyEntry = studyEntry;
		
		//fire all property events
		fireOnTitleChanged(getTitle());
		fireOnSummaryChanged(getSummary());
		fireOnModulesChanged(getModules());
		
		//fire form validation
		fireOnStudyEntryModelChanged();
	}



	private Boolean isStudyEntryValid() {
				
		Boolean isStudyEntryValid = isTitleValid()
									&& isSummaryValid()
									&& isModulesValid();
		
		return isStudyEntryValid;
	}



	private void fireOnStudyEntryModelChanged() {
		for (StudyModelListener listener : listeners) {
			listener.onStudyEntryChanged(isStudyEntryValid());
		}
	}



	public String getTitle() {
		return studyEntry.getTitle();
	}



	public void setTitle(String title) {
		
		String before = getTitle();		
		
		studyEntry.setTitle(title);
		
		fireOnTitleChanged(before);
		fireOnStudyEntryModelChanged();
	}



	private Boolean isTitleValid() {
		//TODO improve validation
		return ((getTitle() != null) && !(getTitle().length() == 0));
	}



	private void fireOnTitleChanged(String before) {
		for (StudyModelListener listener : listeners) {
			listener.onTitleChanged(before, getTitle(), isTitleValid());
		}
	}



	public String getSummary() {
		return studyEntry.getSummary();
	}



	public void setSummary(String summary) {
		
		String before = getSummary();
		
		studyEntry.setSummary(summary);
		fireOnSummaryChanged(before);
		fireOnStudyEntryModelChanged();
	}



	private Boolean isSummaryValid() {
		//TODO improve validation
		return ((getSummary() != null) && !(getSummary().length() == 0));
	}



	private void fireOnSummaryChanged(String before) {
		
		for (StudyModelListener listener : listeners) {
			listener.onSummaryChanged(before, getSummary(), isSummaryValid());
		}
	}



	public Set<String> getModules() {
		return new HashSet<String>(studyEntry.getModules());
	}



	public void setModules(Set<String> modules) {
		
		Set<String> before = getModules();
		
		studyEntry.setModules(new ArrayList<String>(modules));
		
		fireOnModulesChanged(before);
		fireOnStudyEntryModelChanged();
	}



	private Boolean isModulesValid() {
		// require at least one module
		return (getModules().size() > 0);
	}



	private void fireOnModulesChanged(Set<String> before) {
		
		for (StudyModelListener listener : listeners) {
			listener.onModulesChanged(before, getModules(), isModulesValid());
		}
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		Integer before = getStatus();
		this.status = status;
		fireOnStatusChanged(before, status);
	}



	private void fireOnStatusChanged(Integer before, Integer after) {
		for (StudyModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}



	public void addListener(StudyModelListener listener) {
		listeners.add(listener);
	}
	
	
	
}
