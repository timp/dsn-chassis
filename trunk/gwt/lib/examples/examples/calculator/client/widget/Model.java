/**
 * 
 */
package examples.calculator.client.widget;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aliman
 *
 */
class Model {

	
	private Double result = null;
	private List<ModelListener> listeners = new ArrayList<ModelListener>();
	
	Double getResult() {
		return result;
	}

	void setResult(Double to) {
		Double from = this.result;
		this.result = to;
		for (ModelListener listener : listeners) {
			listener.onResultChanged(from, to);
		}
	}
	
	void addListener(ModelListener listener) {
		listeners.add(listener);
	}

}
