/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.submission.model.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.submission.client.format.SubmissionEntry;
import org.cggh.chassis.generic.atom.vanilla.client.format.AtomLink;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;

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
		fireOnAcceptClinicalDataChanged(acceptClinicalData());
		fireOnAcceptInVitroDataChanged(acceptInVitroData());
		fireOnAcceptMolecularDataChanged(acceptMolecularData());
		fireOnAcceptPharmacologyData(acceptPharmacologyData());
		
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
										 && isAcceptClinicalDataValid()
										 && isAcceptInVitroDataValid()
										 && isAcceptMolecularDataValid()
										 && isAcceptPharmacologyDataValid();
		
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



	public void setAcceptClinicalData(Boolean acceptClinicalData) {
		
		Boolean before = submissionEntry.getModules().contains(ApplicationConstants.MODULE_CLINICAL);
		
		if (acceptClinicalData && !before) {
			submissionEntry.addModule(ApplicationConstants.MODULE_CLINICAL);
		} else if (!acceptClinicalData && before) {
			submissionEntry.removeModule(ApplicationConstants.MODULE_CLINICAL);
		}
		
		fireOnAcceptClinicalDataChanged(before);
	}



	private void fireOnAcceptClinicalDataChanged(Boolean before) {
				
		for (SubmissionModelListener listener : listeners) {
			listener.onAcceptClinicalDataChanged(before, acceptClinicalData(), isAcceptClinicalDataValid());
		}
	}



	private Boolean isAcceptClinicalDataValid() {
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		return true;
	}



	public void setAcceptMolecularData(Boolean acceptMolecularData) {
		
		Boolean before = submissionEntry.getModules().contains(ApplicationConstants.MODULE_MOLECULAR);
		
		if (acceptMolecularData && !before) {
			submissionEntry.addModule(ApplicationConstants.MODULE_MOLECULAR);
		} else if (!acceptMolecularData && before) {
			submissionEntry.removeModule(ApplicationConstants.MODULE_MOLECULAR);
		}
		
		fireOnAcceptMolecularDataChanged(before);
	}



	private void fireOnAcceptMolecularDataChanged(Boolean before) {
				
		for (SubmissionModelListener listener : listeners) {
			listener.onAcceptMolecularDataChanged(before, acceptMolecularData(), isAcceptMolecularDataValid());
		}
	}



	private Boolean isAcceptMolecularDataValid() {
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		return true;
	}



	public void setAcceptInVitroData(Boolean acceptInVitroData) {
		
		Boolean before = submissionEntry.getModules().contains(ApplicationConstants.MODULE_IN_VITRO);
		
		if (acceptInVitroData && !before) {
			submissionEntry.addModule(ApplicationConstants.MODULE_IN_VITRO);
		} else if (!acceptInVitroData && before) {
			submissionEntry.removeModule(ApplicationConstants.MODULE_IN_VITRO);
		}
		
		fireOnAcceptInVitroDataChanged(before);		
	}



	private void fireOnAcceptInVitroDataChanged(Boolean before) {
				
		for (SubmissionModelListener listener : listeners) {
			listener.onAcceptInVitroDataChanged(before, acceptInVitroData(), isAcceptInVitroDataValid());
		}
	}



	private Boolean isAcceptInVitroDataValid() {
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		return true;
	}



	public void setAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		
		Boolean before = submissionEntry.getModules().contains(ApplicationConstants.MODULE_PHARMACOLOGY);
		
		if (acceptPharmacologyData && !before) {
			submissionEntry.addModule(ApplicationConstants.MODULE_PHARMACOLOGY);
		} else if (!acceptPharmacologyData && before) {
			submissionEntry.removeModule(ApplicationConstants.MODULE_PHARMACOLOGY);
		}
		
		fireOnAcceptPharmacologyData(before);
	}



	private void fireOnAcceptPharmacologyData(Boolean before) {
		
		for (SubmissionModelListener listener : listeners) {
			listener.onAcceptPharmacologyDataChanged(before, acceptPharmacologyData(), isAcceptPharmacologyDataValid());
		}
	}



	private Boolean isAcceptPharmacologyDataValid() {
		//TODO do study validation check. I.E. check if linked studies accept data to modules ticked. Assume true for now
		return true;
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



	public Boolean acceptClinicalData() {
		return submissionEntry.getModules().contains(ApplicationConstants.MODULE_CLINICAL);
	}



	public Boolean acceptMolecularData() {
		return submissionEntry.getModules().contains(ApplicationConstants.MODULE_MOLECULAR);
	}



	public Boolean acceptInVitroData() {
		return submissionEntry.getModules().contains(ApplicationConstants.MODULE_IN_VITRO);
	}



	public Boolean acceptPharmacologyData() {
		return submissionEntry.getModules().contains(ApplicationConstants.MODULE_PHARMACOLOGY);
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

}
