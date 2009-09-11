/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.edit.client;

import java.util.HashSet;
import java.util.Set;

import org.cggh.chassis.generic.atom.study.client.format.StudyEntry;
import org.cggh.chassis.generic.client.gwt.widget.application.client.ApplicationConstants;
import org.cggh.chassis.generic.client.gwt.widget.study.edit.client.EditStudyWidgetModelListener;

/**
 * @author raok
 *
 */
public class EditStudyWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOAD_ERROR = 2;
	public static final Integer STATUS_LOADED = 3;
	public static final Integer STATUS_SAVING = 4;
	public static final Integer STATUS_SAVED = 5;
	public static final Integer STATUS_SAVE_ERROR = 6;
	public static final Integer STATUS_CANCELLED = 7;
	
	private Integer status = STATUS_INITIAL;
	private Set<EditStudyWidgetModelListener> listeners = new HashSet<EditStudyWidgetModelListener>();
	private StudyEntry studyEntry;
	
	public String getTitle() {
		return studyEntry.getTitle();
	}

	public String getSummary() {
		return studyEntry.getSummary();
	}

	public Boolean acceptClinicalData() {
		return studyEntry.getModules().contains(ApplicationConstants.MODULE_CLINICAL);
	}

	public Boolean acceptMolecularData() {
		return studyEntry.getModules().contains(ApplicationConstants.MODULE_MOLECULAR);
	}

	public Boolean acceptInVitroData() {
		return studyEntry.getModules().contains(ApplicationConstants.MODULE_IN_VITRO);
	}

	public Boolean acceptPharmacologyData() {
		return studyEntry.getModules().contains(ApplicationConstants.MODULE_PHARMACOLOGY);
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setTitle(String title) {
		studyEntry.setTitle(title);
		formDataChanged();
	}

	public void setSummary(String summary) {
		studyEntry.setSummary(summary);
		formDataChanged();
	}

	public void setAcceptClinicalData(Boolean acceptClinicalData) {
		
		if (acceptClinicalData) {
			if (!studyEntry.getModules().contains(ApplicationConstants.MODULE_CLINICAL)) {
				studyEntry.addModule(ApplicationConstants.MODULE_CLINICAL);
			}
		} else {
			if (studyEntry.getModules().contains(ApplicationConstants.MODULE_CLINICAL)) {
				studyEntry.removeModule(ApplicationConstants.MODULE_CLINICAL);
			}
		}
		
		formDataChanged();
	}

	public void setAcceptMolecularData(Boolean acceptMolecularData) {
		if (acceptMolecularData) {
			if (!studyEntry.getModules().contains(ApplicationConstants.MODULE_MOLECULAR)) {
				studyEntry.addModule(ApplicationConstants.MODULE_MOLECULAR);
			}
		} else {
			if (studyEntry.getModules().contains(ApplicationConstants.MODULE_MOLECULAR)) {
				studyEntry.removeModule(ApplicationConstants.MODULE_MOLECULAR);
			}
		}
		
		formDataChanged();
	}

	public void setAcceptInVitroData(Boolean acceptInVitroData) {
		if (acceptInVitroData) {
			if (!studyEntry.getModules().contains(ApplicationConstants.MODULE_IN_VITRO)) {
				studyEntry.addModule(ApplicationConstants.MODULE_IN_VITRO);
			}
		} else {
			if (studyEntry.getModules().contains(ApplicationConstants.MODULE_IN_VITRO)) {
				studyEntry.removeModule(ApplicationConstants.MODULE_IN_VITRO);
			}
		}
		
		formDataChanged();
	}

	public void setAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		if (acceptPharmacologyData) {
			if (!studyEntry.getModules().contains(ApplicationConstants.MODULE_PHARMACOLOGY)) {
				studyEntry.addModule(ApplicationConstants.MODULE_PHARMACOLOGY);
			}
		} else {
			if (studyEntry.getModules().contains(ApplicationConstants.MODULE_PHARMACOLOGY)) {
				studyEntry.removeModule(ApplicationConstants.MODULE_PHARMACOLOGY);
			}
		}
		formDataChanged();
	}
	
	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);		
	}
	
	private void formDataChanged() {
		
		Boolean isTitleValid = fireTitleChanged(getTitle());
		Boolean isSummaryValid = fireSummaryChanged(getSummary());
		Boolean isModulesValid = fireModulesChanged();
		
		Boolean isFormComplete = isTitleValid && isSummaryValid && isModulesValid;
		
		for (EditStudyWidgetModelListener listener : listeners) {
			listener.onFormCompleted(isFormComplete);
		}
		
	}
	
private Boolean fireTitleChanged(String title) {
		
		//TODO implement proper validation
		Boolean isValid = !title.isEmpty();
		
		for (EditStudyWidgetModelListener listener : listeners) {
			listener.onTitleChanged(isValid);
		}
		
		return isValid;
	}

	private Boolean fireSummaryChanged(String summary) {
		
		//TODO implement proper validation
		Boolean isValid = !summary.isEmpty();
		
		for (EditStudyWidgetModelListener listener : listeners) {
			listener.onSummaryChanged(isValid);
		}
		return isValid;
	}

	private Boolean fireModulesChanged() {
		
		//valid if at least one module chosen
		Boolean isValid = acceptClinicalData() || acceptInVitroData() || acceptMolecularData() || acceptPharmacologyData();
		
		for (EditStudyWidgetModelListener listener : listeners) {
			listener.onModulesChanged(isValid);
		}
		
		return isValid;
	}

	private void fireOnStatusChanged(Integer before, Integer after) {
		for (EditStudyWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}

	public void addListener(EditStudyWidgetModelListener listener) {
		listeners.add(listener);
	}

	public StudyEntry getStudyEntry() {
		return studyEntry;
	}

	public void setStudyEntry(StudyEntry studyEntry) {
		StudyEntry before = this.studyEntry;
		this.studyEntry = studyEntry;
		fireOnStudyEntryChanged(before, studyEntry);
	}

	private void fireOnStudyEntryChanged(StudyEntry before, StudyEntry after) {
		for (EditStudyWidgetModelListener listener : listeners) {
			listener.onStudyEntryChanged(before, after);
		}
	}
	
	
}
