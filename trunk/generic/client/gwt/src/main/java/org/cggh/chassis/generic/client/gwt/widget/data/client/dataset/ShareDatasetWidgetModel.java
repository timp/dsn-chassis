/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;

import org.cggh.chassis.generic.atomext.client.dataset.DatasetEntry;
import org.cggh.chassis.generic.widget.client.AsyncWidgetModel;

/**
 * @author aliman
 *
 */
public class ShareDatasetWidgetModel extends AsyncWidgetModel {

	
	
	
	// state fields
	private DatasetEntry datasetEntry;
	

	
	
	/**
	 * @param datasetEntry the datasetEntry to set
	 */
	public void setDatasetEntry(DatasetEntry datasetEntry) {
		// TODO fire a model change event
		this.datasetEntry = datasetEntry;
	}

	
	
	
	
	/**
	 * @return the datasetEntry
	 */
	public DatasetEntry getDatasetEntry() {
		return datasetEntry;
	}



	
	
	
	public static final Status STATUS_RETRIEVE_DATASET_PENDING = new RetrieveDatasetPendingStatus();
	
	public static final Status STATUS_CREATE_SUBMISSION_PENDING = new CreateSubmissionPendingStatus();

	public static class RetrieveDatasetPendingStatus extends AsyncRequestPendingStatus {}

	public static class CreateSubmissionPendingStatus extends AsyncRequestPendingStatus {}

	
	
	protected static final Status STATUS_DATASET_RETRIEVED = new DatasetRetrievedStatus();
	
	public static class DatasetRetrievedStatus extends ReadyStatus {}

	
}
