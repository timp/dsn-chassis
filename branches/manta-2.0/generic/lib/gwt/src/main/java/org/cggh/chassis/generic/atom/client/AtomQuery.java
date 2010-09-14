/**
 * 
 */
package org.cggh.chassis.generic.atom.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aliman
 *
 */
public class AtomQuery {

	
	
	
	public static final String PARAM_AUTHOREMAIL = "authoremail";
	public static final String PARAM_ID = "id";



	
	protected Map<String,String> params = new HashMap<String,String>();

	
	
	
	public Map<String, String> getParams() {
		return this.params;
	}
	
	public void set(String param, String value) {
		this.params.put(param, value);
	}
	
	public String get(String param) {
		return this.params.get(param);
	}
	
	public void setAuthorEmail(String authorEmail) {
		this.set(PARAM_AUTHOREMAIL, authorEmail);
	}

	public String getAuthorEmail() {
		return this.get(PARAM_AUTHOREMAIL);
	}

	public void setId(String id) {
		this.set(PARAM_ID, id);
	}

	public String getId() {
		return this.get(PARAM_ID);
	}
	
}
