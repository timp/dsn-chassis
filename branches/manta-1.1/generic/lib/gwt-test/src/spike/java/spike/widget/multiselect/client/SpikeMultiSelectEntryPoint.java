/**
 * 
 */
package spike.widget.multiselect.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cggh.chassis.generic.async.client.Deferred;
import org.cggh.chassis.generic.log.client.AllenSauerLog;
import org.cggh.chassis.generic.log.client.LogFactory;
import org.cggh.chassis.generic.widget.client.MultiSelect;
import org.cggh.chassis.generic.widget.client.MultiSelectModel;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author aliman
 *
 */
public class SpikeMultiSelectEntryPoint implements EntryPoint {

	static {
		LogFactory.create = AllenSauerLog.create;
		LogFactory.hide("*");
		LogFactory.show("org.cggh.chassis.generic.widget.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.main.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.application.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.data.*");
//		LogFactory.show("org.cggh.chassis.generic.client.gwt.widget.study.*");
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		MultiSelectModel dummyModel = new MultiSelectModel() {
			
			private List<String> values = new ArrayList<String>();
			private Map<String,String> items = null;

			public void addValue(String value) {
				this.values.add(value);
				
			}

			public void addValue(int index, String value) {
				this.values.add(index, value);
			}

			public Map<String,String> getItems() {
				return items;
			}

			public Deferred<Map<String,String>> loadItems(boolean forceRefresh) {
				Deferred<Map<String,String>> deferredItems = new Deferred<Map<String,String>>();
				if (items == null) {
					items = new HashMap<String,String>();
					items.put("van", "Vanilla");
					items.put("cho", "Chocolate");
					items.put("str", "Strawberry");
				}
				// don't worry about refresh, this is a mockup with no async communication
				deferredItems.callback(items); // callback immediately, no async request
				return deferredItems;
			}

			public void removeValue(String value) {
				values.remove(value);
			}

			public void removeValue(int index) {
				values.remove(index);
			}

			public List<String> getSelectedValues() {
				return values;
			}
			
		};
		
		MultiSelect ms = new MultiSelect(dummyModel);
		RootPanel.get().add(ms);
		
	}

}
