/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.study.view.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author raok
 *
 */
public class ViewStudyWidgetModel {
	
	public static final Integer STATUS_INITIAL = 0;
	public static final Integer STATUS_LOADING = 1;
	public static final Integer STATUS_LOADED = 2;
	public static final Integer STATUS_ERROR = 3;
	
	//TODO remove when real atom service is used
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
	private Integer status;
	private Set<ViewStudyWidgetModelListener> listeners = new HashSet<ViewStudyWidgetModelListener>();

	public ViewStudyWidgetModel() {
		status = STATUS_INITIAL;
	}
	
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
		String before = this.title;
		this.title = title;
		fireTitleChanged(before, title);
	}

	public void setSummary(String summary) {
		String before = this.summary;
		this.summary = summary;
		fireSummaryChanged(before, summary);
	}

	public void setAcceptClinicalData(Boolean acceptClinicalData) {
		Boolean before = this.acceptClinicalData;
		this.acceptClinicalData = acceptClinicalData;
		fireAcceptClinicalDataChanged(before, acceptClinicalData);
	}

	public void setAcceptMolecularData(Boolean acceptMolecularData) {
		Boolean before = this.acceptMolecularData;
		this.acceptMolecularData = acceptMolecularData;
		fireAcceptMolecularDataChanged(before, acceptMolecularData);
	}

	public void setAcceptInVitroData(Boolean acceptInVitroData) {
		Boolean before = this.acceptInVitroData;
		this.acceptInVitroData = acceptInVitroData;
		fireAcceptInVitroDataChanged(before, acceptInVitroData);
	}
	
	public void setStatus(Integer status) {
		Integer before = this.status;
		this.status = status;
		fireOnStatusChanged(before, status);		
	}
	
	public void setAcceptPharmacologyData(Boolean acceptPharmacologyData) {
		Boolean before = this.acceptPharmacologyData;
		this.acceptPharmacologyData = acceptPharmacologyData;
		fireAcceptPharmacologyDataChanged(before, acceptPharmacologyData);
	}

	public void addListener(ViewStudyWidgetModelListener listener) {
		this.listeners.add(listener);
	}


	private void fireTitleChanged(String before, String after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onTitleChanged(before, after);
		}
	}

	private void fireSummaryChanged(String before, String after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onSummaryChanged(before, after);
		}
	}

	private void fireAcceptClinicalDataChanged(Boolean before, Boolean after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onAcceptClinicalDataChanged(before, after);
		}
	}

	private void fireAcceptMolecularDataChanged(Boolean before, Boolean after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onAcceptMolecularDataChanged(before, after);
		}
	}

	private void fireAcceptInVitroDataChanged(Boolean before, Boolean after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onAcceptInVitroDataChanged(before, after);
		}
	}

	private void fireAcceptPharmacologyDataChanged(Boolean before, Boolean after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onAcceptPharmacologyDataChanged(before, after);
		}
	}

	private void fireOnStatusChanged(Integer before, Integer after) {
		for (ViewStudyWidgetModelListener listener : listeners) {
			listener.onStatusChanged(before, after);
		}
	}
	
	
}
