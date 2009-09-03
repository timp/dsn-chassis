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
	public static final Integer STATUS_ERROR = 4;
	
	public static final String MODULE_CLINICAL = "Clinical";
	public static final String MODULE_MOLECULAR = "Molecular";
	public static final String MODULE_IN_VITRO = "In Vitro";
	public static final String MODULE_PHARMACOLOGY = "Pharmacology";
	
	private String title;
	private String summary;
	private Boolean acceptClinicalData;
	private Boolean acceptMolecularData;
	private Boolean acceptInVitroData;
	private Boolean acceptPharmacologyData;
	private Set<CreateStudyWidgetModelListener> listeners = new HashSet<CreateStudyWidgetModelListener>();
	private Integer status = STATUS_INITIAL;

	public void setTitle(String title) {
		String before = this.title;
		this.title = title;
		fireTitleChanged(before, title);
	}

	private void fireTitleChanged(String before, String after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onTitleChanged(before, after);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setSummary(String summary) {
		String before = this.summary;
		this.summary = summary;
		fireSummaryChanged(before, summary);
	}

	private void fireSummaryChanged(String before, String after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onSummaryChanged(before, after);
		}
	}

	public void setAcceptClinicalData(Boolean acceptClinicalData) {
		Boolean before = this.acceptClinicalData;
		this.acceptClinicalData = acceptClinicalData;
		fireAcceptClinicalDataChanged(before, acceptClinicalData);
	}

	private void fireAcceptClinicalDataChanged(Boolean before, Boolean after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onAcceptClinicalDataChanged(before, after);
		}
	}

	public void setAcceptMolecularData(Boolean acceptMolecularData) {
		Boolean before = this.acceptMolecularData;
		this.acceptMolecularData = acceptMolecularData;
		fireAcceptMolecularDataChanged(before, acceptMolecularData);
	}

	private void fireAcceptMolecularDataChanged(Boolean before, Boolean after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onAcceptMolecularDataChanged(before, after);
		}
	}

	public void setAcceptInVitroData(Boolean acceptInVitroData) {
		Boolean before = this.acceptInVitroData;
		this.acceptInVitroData = acceptInVitroData;
		fireAcceptInVitroDataChanged(before, acceptInVitroData);
	}

	private void fireAcceptInVitroDataChanged(Boolean before, Boolean after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onAcceptInVitroDataChanged(before, after);
		}
	}

	public void setAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		Boolean before = this.acceptPharmacologyData;
		this.acceptPharmacologyData = acceptPharmacologyData;
		fireAcceptPharmacologyDataChanged(before, acceptPharmacologyData);
	}

	private void fireAcceptPharmacologyDataChanged(Boolean before, Boolean after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onAcceptPharmacologyDataChanged(before, after);
		}
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

	public void addListener(CreateStudyWidgetModelListener listener) {
		this.listeners.add(listener);
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);		
	}

	private void fireOnStatusChanged(Integer before, Integer after) {
		for (CreateStudyWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}

}
