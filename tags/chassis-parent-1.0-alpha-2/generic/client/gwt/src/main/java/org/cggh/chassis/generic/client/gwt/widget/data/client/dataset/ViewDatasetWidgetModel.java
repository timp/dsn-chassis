/**
 * 
 */
package org.cggh.chassis.generic.client.gwt.widget.data.client.dataset;


/**
 * @author aliman
 *
 */
public class ViewDatasetWidgetModel 
	extends AsyncWidgetModelWithDatasetEntry {

	
	
	
	
	private String currentEntryId;

	
	
	
	
	@Override
	public void init() {
		super.init();
		
		this.currentEntryId = null;
		
	}




	public void setCurrentEntryId(String currentEntryId) {
		this.currentEntryId = currentEntryId;
		// don't worry about firing change events for now, no-one is interested
	}




	public String getCurrentEntryId() {
		return currentEntryId;
	}
	
	
	
	
	
	
}
