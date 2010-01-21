/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.client.AtomAuthor;
import org.cggh.chassis.generic.atomext.client.study.StudyEntry;


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
		fireOnIdChanged(getId());
		fireOnTitleChanged(getTitle());
		fireOnSummaryChanged(getSummary());
		fireOnModulesChanged(getModules());
		fireOnAuthorsChanged(getAuthors());
		fireOnCreatedChanged(getCreated());
		fireOnUpdatedChanged(getUpdated());
		
		//fire form validation
		fireOnStudyEntryModelChanged(studyEntry);
	}



	/**
	 * @param id
	 */
	private void fireOnIdChanged(String before) {
		for (StudyModelListener listener : listeners) {
			listener.onTitleChanged(before, getId());
		}
	}



	/**
	 * @return
	 */
	private String getId() {
		return this.studyEntry.getId();
	}



	private Boolean isStudyEntryValid() {
				
		Boolean isStudyEntryValid = isTitleValid()
									&& isSummaryValid()
									&& isModulesValid()
									&& isAuthorsValid();
		
		return isStudyEntryValid;
	}



	private void fireOnStudyEntryModelChanged(StudyEntry studyEntry) {
		for (StudyModelListener listener : listeners) {
			listener.onStudyEntryChanged(studyEntry, isStudyEntryValid());
		}
	}



	public String getTitle() {
		return studyEntry.getTitle();
	}



	public void setTitle(String title) {
		
		String before = getTitle();		
		
		studyEntry.setTitle(title);
		
		fireOnTitleChanged(before);
		fireOnStudyEntryModelChanged(this.studyEntry);
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

	private void fireOnCreatedChanged(String before) {
		for (StudyModelListener listener : listeners) {
			listener.onCreatedChanged(before, getCreated());
		}
	}

	private void fireOnUpdatedChanged(String before) {
		for (StudyModelListener listener : listeners) {
			listener.onUpdatedChanged(before, getUpdated());
		}
	}


	public String getSummary() {
		return studyEntry.getSummary();
	}

	public String getCreated() {
		return studyEntry.getPublished();
	}

	public String getUpdated() {
		return studyEntry.getUpdated();
	}


	public void setSummary(String summary) {
		
		String before = getSummary();
		
		studyEntry.setSummary(summary);
		fireOnSummaryChanged(before);
		fireOnStudyEntryModelChanged(this.studyEntry);
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
		return new HashSet<String>(studyEntry.getStudy().getModules());
	}



	public void setModules(Set<String> modules) {
		
		Set<String> before = getModules();
		
		studyEntry.getStudy().setModules(new ArrayList<String>(modules));
		
		fireOnModulesChanged(before);
		fireOnStudyEntryModelChanged(this.studyEntry);
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


	public Set<AtomAuthor> getAuthors() {
		return new HashSet<AtomAuthor>(studyEntry.getAuthors());
	}



	public void setAuthors(Set<AtomAuthor> authors) {
		
		Set<AtomAuthor> before = getAuthors();
		
		studyEntry.setAuthors(new ArrayList<AtomAuthor>(authors));
		
		fireOnAuthorsChanged(before);
		fireOnStudyEntryModelChanged(this.studyEntry);
		
	}


	private Boolean isAuthorsValid() {
		//require at least one author
		return (getAuthors().size() > 0);
	}



	private void fireOnAuthorsChanged(Set<AtomAuthor> before) {

		for (StudyModelListener listener : listeners) {
			listener.onAuthorsChanged(before, getAuthors(), isAuthorsValid());
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
