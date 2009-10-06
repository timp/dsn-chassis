/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.model.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomAuthor;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.log.client.Log;
import org.cggh.chassis.generic.log.client.LogFactory;

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
	private Log log = LogFactory.getLog(this.getClass());
	
	private SubmissionEntry submissionEntry;
	private Integer status = STATUS_INITIAL;
	
	//maybe have two groups of listeners: one for read only properties, and one for editable
	private Set<SubmissionModelListener> listeners = new HashSet<SubmissionModelListener>();
	
	
	
	public SubmissionEntry getSubmissionEntry() {
		return submissionEntry;
	}



	public void setSubmissionEntry(SubmissionEntry submissionEntry) {
				
		this.submissionEntry = submissionEntry;
		
		//fire all property events
		fireOnAuthorsChanged(getAuthors());
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
		log.enter("isSubmissionEntryValid");
		
		Boolean isSubmissionEntryValid = isTitleValid()
										 && isSummaryValid()
										 && isStudyLinksValid()
										 && isModulesValid()
										 && isAuthorsValid();
		
		log.trace("isSubmissionEntryValid: " + isSubmissionEntryValid);
		
		log.leave();
		return isSubmissionEntryValid;
	}


	public Set<AtomAuthor> getAuthors() {
		return new HashSet<AtomAuthor>(submissionEntry.getAuthors());
	}



	public void setAuthors(Set<AtomAuthor> authors) {
		
		Set<AtomAuthor> before = getAuthors();
		
		submissionEntry.setAuthors(new ArrayList<AtomAuthor>(authors));
		
		fireOnAuthorsChanged(before);
		fireOnSubmissionEntryModelChanged();
		
	}


	private Boolean isAuthorsValid() {
		//require at least one author
		return (getAuthors().size() > 0);
	}



	private void fireOnAuthorsChanged(Set<AtomAuthor> before) {

		for (SubmissionModelListener listener : listeners) {
			listener.onAuthorsChanged(before, getAuthors(), isAuthorsValid());
		}
	}



	public String getTitle() {
		return submissionEntry.getTitle();
	}



	public void setTitle(String title) {
		
		String before = getTitle();		
		
		submissionEntry.setTitle(title);
		
		fireOnTitleChanged(before);
		fireOnSubmissionEntryModelChanged();
	}



	private Boolean isTitleValid() {
		log.enter("isTitleValid");
		
		//TODO improve validation
		boolean isValid = ((getTitle() != null) && !(getTitle().length() == 0));
		
		log.trace("isValid: " + isValid);
		
		log.leave();
		return isValid;
	}



	private void fireOnTitleChanged(String before) {
		for (SubmissionModelListener listener : listeners) {
			listener.onTitleChanged(before, getTitle(), isTitleValid());
		}
	}



	public String getSummary() {
		return submissionEntry.getSummary();
	}



	public void setSummary(String summary) {
		
		String before = getSummary();
		
		submissionEntry.setSummary(summary);
		
		fireOnSummaryChanged(before);
		fireOnSubmissionEntryModelChanged();
	}



	private Boolean isSummaryValid() {
		log.enter("isSummaryValid");
		
		//TODO improve validation
		boolean isValid = ((getSummary() != null) && !(getSummary().length() == 0));
		
		log.trace("isValid: " + isValid);
		
		log.leave();
		return isValid;
	}



	private void fireOnSummaryChanged(String before) {
		
		for (SubmissionModelListener listener : listeners) {
			listener.onSummaryChanged(before, getSummary(), isSummaryValid());
		}
	}



	public Set<String> getStudyLinks() {
		
		Set<String> studyLinks = new HashSet<String>();
		
		for (AtomLink link : submissionEntry.getStudyLinks()) {
			studyLinks.add(link.getHref());
		}		
		
		return studyLinks;
	}



	public void setStudyLinks(Set<String> studyLinks) {
		
		Set<String> before = getStudyLinks();
		
		submissionEntry.setStudyLinks(new ArrayList<String>(studyLinks));
		
		fireOnStudyLinksChanged(before);
		fireOnSubmissionEntryModelChanged();
	}



	private Boolean isStudyLinksValid() {
		log.enter("isStudyLinksValid");
		
		//Require at least one study link
		boolean isValid = (getStudyLinks().size() > 0);
		
		log.trace("isValid: " + isValid);
		
		log.leave();
		return isValid;
	}



	private void fireOnStudyLinksChanged(Set<String> before) {
		
		for (SubmissionModelListener listener : listeners) {
			listener.onStudyLinksChanged(before, getStudyLinks(), isStudyLinksValid());
		}
	}



	public Set<String> getModules() {
		return new HashSet<String>(submissionEntry.getModules());
	}



	public void setModules(Set<String> modules) {
	
		Set<String> before = getModules();
		
		submissionEntry.setModules(new ArrayList<String>(modules));
	
		fireOnModulesChanged(before);
		fireOnSubmissionEntryModelChanged();
	}



	private Boolean isModulesValid() {
		log.enter("isModulesValid");
	
		//TODO check studyLinks accept data for these modules
		// require at least one module
		boolean isValid = (getModules().size() > 0);
		
		log.trace("isValid: " + isValid);
		
		log.leave();
		return isValid;
	}



	private void fireOnModulesChanged(Set<String> before) {
	
		for (SubmissionModelListener listener : listeners) {
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
		for (SubmissionModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}



	public void addListener(SubmissionModelListener listener) {
		listeners.add(listener);
	}
	
}
