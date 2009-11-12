/**
 * 
 */
package org.cggh.chassis.generic.atomext.client.shared;

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
	
	public void setAuthorEmail(String authorEmail) {
		this.params.put(PARAM_AUTHOREMAIL, authorEmail);
	}

	public String getAuthorEmail() {
		return this.params.get(PARAM_AUTHOREMAIL);
	}

	public void setId(String id) {
		this.params.put(PARAM_ID, id);
	}

	public String getId() {
		return this.params.get(PARAM_ID);
	}
	
}
