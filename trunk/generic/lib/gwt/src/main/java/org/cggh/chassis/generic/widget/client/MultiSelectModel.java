/**
 * 
 */
package org.cggh.chassis.generic.widget.client;

import java.util.List;
import java.util.Map;

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
	
	public List<String> getSelectedValues();
	
	public Deferred<Map<String,String>> loadItems(boolean forceRefresh);
	
	public Map<String,String> getItems();

}
