/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author aliman
 *
 */
public class ShareDatasetWidgetModel extends AsyncWidgetModel {

	
	
	
	// state fields
	private DatasetEntry datasetEntry;

	private String id;
	

	
	
	/**
	 * @param datasetEntry the datasetEntry to set
	 */
	public void setDatasetEntry(DatasetEntry datasetEntry) {
		DatasetEntryChangeEvent e = new DatasetEntryChangeEvent(this.datasetEntry, datasetEntry);
		this.datasetEntry = datasetEntry;
		this.fireChangeEvent(e);
	}

	
	
	
	
	/**
	 * @return the datasetEntry
	 */
	public DatasetEntry getDatasetEntry() {
		return datasetEntry;
	}



	

	public void setDatasetEntryId(String id) {
		this.id = id;
		// N.B. we will not fire events, because not used in UI, only used for memory
	}
	
	
	
	
	public String getDatasetEntryId() {
		return this.id;
	}

	

	
	public HandlerRegistration addDatasetEntryChangeHandler(DatasetEntryChangeHandler h) {
		return this.addChangeHandler(h, DatasetEntryChangeEvent.TYPE);
	}
	
	
	
	
	public static final Status STATUS_RETRIEVE_DATASET_PENDING = new RetrieveDatasetPendingStatus();
	
	public static final Status STATUS_CREATE_SUBMISSION_PENDING = new CreateSubmissionPendingStatus();

	public static class RetrieveDatasetPendingStatus extends AsyncRequestPendingStatus {}

	public static class CreateSubmissionPendingStatus extends AsyncRequestPendingStatus {}

	
	
	protected static final Status STATUS_DATASET_RETRIEVED = new DatasetRetrievedStatus();
	
	public static class DatasetRetrievedStatus extends ReadyStatus {}

	
}
