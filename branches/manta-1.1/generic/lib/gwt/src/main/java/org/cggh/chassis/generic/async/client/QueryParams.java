/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import java.util.HashMap;

/**
 * @author aliman
 *
 */
public class QueryParams extends HashMap<String, String> {

	private static final long serialVersionUID = -3622805654655716076L;

	public String toUrlQueryString() {
		String out = "?";
		for (String key : this.keySet()) {
			out += key;
			String value = this.get(key);
			if (value != null) {
				out += "=" + value;
			}
			out += "&";
		}
		return out;
	}
	
}
