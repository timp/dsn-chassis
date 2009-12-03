/**
 * 
 */
package org.cggh.chassis.generic.async.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * @author aliman
 *
 */
public class HttpDeferred<T> extends Deferred<T> {

	
	
	protected List<Request> requests = new ArrayList<Request>();
	protected List<Response> responses = new ArrayList<Response>();
	
	
	
	public void addRequest(Request request) {
		this.requests.add(request);
	}
	
	
	
	public void addResponse(Response response) {
		this.responses.add(response);
	}
	
	
	
	public Request getLastRequest() {
		if (requests.size() > 0) {
			return requests.get(requests.size()-1);
		}
		else return null;
	}



	public Response getLastResponse() {
		if (responses.size() > 0) {
			return responses.get(responses.size()-1);
		}
		else return null;
	}
	
	
	
	public List<Request> getRequests() {
		return new ArrayList<Request>(requests);
	}



	public List<Response> getResponses() {
		return new ArrayList<Response>(responses);
	}

}
