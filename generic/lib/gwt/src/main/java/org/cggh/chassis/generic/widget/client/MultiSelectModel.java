/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.List;

import org.cggh.chassis.generic.async.client.Deferred;


/**
 * @author aliman
 *
 */
public interface MultiSelectModel {

	public void addValue(String value);

	public void addValue(int index, String value);

	public void removeValue(String value);
	
	public void removeValue(int index);
	
	public Deferred<List<SelectItem>> loadItems(boolean forceRefresh);
	
	public List<SelectItem> getItems();

	
	
	
	
	public static class SelectItem {
		
		private String value;
		private String label;

		public SelectItem(String value, String label) {
			this.setValue(value);
			this.setLabel(label);
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}
	


}
