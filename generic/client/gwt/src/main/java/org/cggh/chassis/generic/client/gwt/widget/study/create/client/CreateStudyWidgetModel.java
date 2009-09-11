/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.create.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author raok
 *
 */
public class CreateStudyWidgetModel {

	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_READY = 1;
	public static final Integer STATUS_SAVING = 2;
	public static final Integer STATUS_SAVED = 3;
	public static final Integer STATUS_SAVE_ERROR = 4;
	public static final Integer STATUS_CANCELLED = 5;

	private String title = "";
	private String summary = "";
	private Boolean acceptClinicalData = false;
	private Boolean acceptMolecularData = false;
	private Boolean acceptInVitroData = false;
	private Boolean acceptPharmacologyData = false;
	private Set<CreateStudyWidgetModelListener> listeners = new HashSet<CreateStudyWidgetModelListener>();
	private Integer status = STATUS_INITIAL;
		
	public String getTitle() {
		return title;
	}

	public String getSummary() {
		return summary;
	}

	public Boolean acceptClinicalData() {
		return acceptClinicalData;
	}

	public Boolean acceptMolecularData() {
		return acceptMolecularData;
	}

	public Boolean acceptInVitroData() {
		return acceptInVitroData;
	}

	public Boolean acceptPharmacologyData() {
		return acceptPharmacologyData;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setTitle(String title) {
		this.title = title;
		formDataChanged();
	}

	public void setSummary(String summary) {
		this.summary = summary;
		formDataChanged();
	}

	public void setAcceptClinicalData(Boolean acceptClinicalData) {
		this.acceptClinicalData = acceptClinicalData;
		formDataChanged();
	}

	public void setAcceptMolecularData(Boolean acceptMolecularData) {
		this.acceptMolecularData = acceptMolecularData;
		formDataChanged();
	}

	public void setAcceptInVitroData(Boolean acceptInVitroData) {
		this.acceptInVitroData = acceptInVitroData;
		formDataChanged();
	}

	public void setAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		this.acceptPharmacologyData = acceptPharmacologyData;
		formDataChanged();
	}

	public void addListener(CreateStudyWidgetModelListener listener) {
		this.listeners.add(listener);
	}

	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);		
	}

	private void formDataChanged() {
		
		Boolean isTitleValid = fireTitleChanged(title);
		Boolean isSummaryValid = fireSummaryChanged(summary);
		Boolean isModulesValid = fireModulesChanged();
		
		Boolean isFormComplete = isTitleValid && isSummaryValid && isModulesValid;
		
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onFormCompleted(isFormComplete);
		}
		
	}
	
	private Boolean fireTitleChanged(String title) {
		
		//TODO implement proper validation
		Boolean isValid = !title.isEmpty();
		
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onTitleChanged(isValid);
		}
		
		return isValid;
	}

	private Boolean fireSummaryChanged(String summary) {
		
		//TODO implement proper validation
		Boolean isValid = !summary.isEmpty();
		
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onSummaryChanged(isValid);
		}
		return isValid;
	}

	private Boolean fireModulesChanged() {
		
		//valid if at least one module chosen
		Boolean isValid = acceptClinicalData || acceptInVitroData || acceptMolecularData || acceptPharmacologyData;
		
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onModulesChanged(isValid);
		}
		
		return isValid;
	}

	private void fireOnStatusChanged(Integer before, Integer after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}

}
