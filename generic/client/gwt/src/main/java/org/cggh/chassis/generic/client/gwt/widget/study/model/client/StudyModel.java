/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.model.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;

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
	

	private StudyEntry submissionEntry;
	private Integer status = STATUS_INITIAL;
	
	//TODO maybe have two groups of listeners: one for read only properties, and one for editable
	private Set<StudyModelListener> listeners = new HashSet<StudyModelListener>();
	
	
	
	public void setStudyEntry(StudyEntry submissionEntry) {
				
		this.submissionEntry = submissionEntry;
		
		//fire all property events
		fireOnTitleChanged(getTitle());
		fireOnSummaryChanged(getSummary());
		fireOnAcceptClinicalDataChanged(acceptClinicalData());
		fireOnAcceptInVitroDataChanged(acceptInVitroData());
		fireOnAcceptMolecularDataChanged(acceptMolecularData());
		fireOnAcceptPharmacologyData(acceptPharmacologyData());
		
		//fire form validation
		fireOnStudyEntryModelChanged();
	}



	private void fireOnStudyEntryModelChanged() {
		for (StudyModelListener listener : listeners) {
			listener.onStudyEntryChanged(isStudyEntryValid());
		}
	}



	private Boolean isStudyEntryValid() {
		
		Boolean isStudyEntryValid = isTitleValid()
										 && isSummaryValid()
										 && isAcceptClinicalDataValid()
										 && isAcceptInVitroDataValid()
										 && isAcceptMolecularDataValid()
										 && isAcceptPharmacologyDataValid();
		
		return isStudyEntryValid;
	}



	public Integer getStatus() {
		return status;
	}



	public StudyEntry getStudyEntry() {
		return submissionEntry;
	}



	public void setTitle(String title) {
		
		String before = getTitle();		
		
		submissionEntry.setTitle(title);
		
		fireOnTitleChanged(before);
	}



	private void fireOnTitleChanged(String before) {
		for (StudyModelListener listener : listeners) {
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
		
		for (StudyModelListener listener : listeners) {
			listener.onSummaryChanged(before, getSummary(), isSummaryValid());
		}
	}



	private Boolean isSummaryValid() {
		//TODO improve validation
		return ((getSummary() != null) && !(getSummary().length() == 0));
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
				
		for (StudyModelListener listener : listeners) {
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
				
		for (StudyModelListener listener : listeners) {
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
				
		for (StudyModelListener listener : listeners) {
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
		
		for (StudyModelListener listener : listeners) {
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



	public void addListener(StudyModelListener listener) {
		listeners.add(listener);
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
	
	
	
}
