/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.model.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;

/**
 * @author raok
 *
 */
public class SubmissionModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_SAVING = 3;
	public static final Integer STATUS_SAVED = 4;
	public static final Integer STATUS_ERROR = 5;
	public static final Integer STATUS_CANCELLED = 6;
	
	private SubmissionEntry submissionEntry;
	private Integer status = STATUS_INITIAL;
	
	//maybe have two groups of listeners: one for read only properties, and one for editable
	private Set<SubmissionModelListener> listeners = new HashSet<SubmissionModelListener>();
	
	
	
	public void setSubmissionEntry(SubmissionEntry submissionEntry) {
				
		this.submissionEntry = submissionEntry;
		
		//fire all property events
		fireOnTitleChanged(getTitle());
		fireOnSummaryChanged(getSummary());
		fireOnStudyLinksChanged(getStudyLinks());
		fireOnModulesChanged(getModules());
		
		//fire form validation
		fireOnSubmissionEntryModelChanged();
	}



	private void fireOnSubmissionEntryModelChanged() {
		for (SubmissionModelListener listener : listeners) {
			listener.onSubmissionEntryChanged(isSubmissionEntryValid());
		}
	}



	private Boolean isSubmissionEntryValid() {
		
		Boolean isSubmissionEntryValid = isTitleValid()
										 && isSummaryValid()
										 && isStudyLinksValid()
										 && isModulesValid();
		
		return isSubmissionEntryValid;
	}



	public Integer getStatus() {
		return status;
	}



	public SubmissionEntry getSubmissionEntry() {
		return submissionEntry;
	}



	public void setTitle(String title) {
		
		String before = getTitle();		
		
		submissionEntry.setTitle(title);
		
		fireOnTitleChanged(before);
	}



	private void fireOnTitleChanged(String before) {
		for (SubmissionModelListener listener : listeners) {
			listener.onTitleChanged(before, getTitle(), isTitleValid());
		}
	}



	private Boolean isTitleValid() {
		//TODO improve validation
		return ((getTitle() != null) && !(getTitle().length() == 0));
	}



	public void setSummary(String summary) {
		
		String before = getSummary();
		
		submissionEntry.setSummary(summary);
		fireOnSummaryChanged(before);
	}



	private void fireOnSummaryChanged(String before) {
		
		for (SubmissionModelListener listener : listeners) {
			listener.onSummaryChanged(before, getSummary(), isSummaryValid());
		}
	}



	private Boolean isSummaryValid() {
		//TODO improve validation
		return ((getSummary() != null) && !(getSummary().length() == 0));
	}



	public void setStudyLinks(Set<String> studyLinks) {
		
		Set<String> before = getStudyLinks();
		
		submissionEntry.setStudyLinks(new ArrayList<String>(studyLinks));
		fireOnStudyLinksChanged(before);
	}



	private void fireOnStudyLinksChanged(Set<String> before) {
		
		for (SubmissionModelListener listener : listeners) {
			listener.onStudyLinksChanged(before, getStudyLinks(), isStudyLinksValid());
		}
	}



	private Boolean isStudyLinksValid() {
		//Require at least one study link
		return (getStudyLinks().size() > 0);
	}


	public String getTitle() {
		return submissionEntry.getTitle();
	}



	public String getSummary() {
		return submissionEntry.getSummary();
	}



	public Set<String> getStudyLinks() {
		
		Set<String> studyLinks = new HashSet<String>();
		
		for (AtomLink link : submissionEntry.getStudyLinks()) {
			studyLinks.add(link.getHref());
		}		
		
		return studyLinks;
	}

	public Set<String> getModules() {
		return new HashSet<String>(submissionEntry.getModules());
	}

	public void addListener(SubmissionModelListener listener) {
		listeners .add(listener);
	}



	public void setStatus(Integer status) {
		Integer before = getStatus();
		this.status = status;
		fireOnStatusChanged(before, status);
	}



	private void fireOnStatusChanged(Integer before, Integer after) {
		for (SubmissionModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}



	public void setModules(Set<String> modules) {

		Set<String> before = getModules();
		
		submissionEntry.setModules(new ArrayList<String>(modules));

		fireOnModulesChanged(before);
		fireOnSubmissionEntryModelChanged();
	}


	private void fireOnModulesChanged(Set<String> before) {

		for (SubmissionModelListener listener : listeners) {
			listener.onModulesChanged(before, getModules(), isModulesValid());
		}
			
	}



	private Boolean isModulesValid() {
		//TODO check studyLinks accept data for these modules
		// require at least one module
		return (getModules().size() > 0);
	}
	
}
