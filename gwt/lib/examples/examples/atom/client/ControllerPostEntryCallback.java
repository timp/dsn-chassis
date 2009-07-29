/**
 * $Id$
 */
package examples.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryCallback;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class ControllerPostEntryCallback implements PostEntryCallback {

	private final Model model;
	
	public ControllerPostEntryCallback(Model model) {
		this.model = model;
	}
	
	public void onError(Request request, Throwable exception) {
		this.model.setMessage("unexpected error [onError(Request, Throwable)]: "+exception.getLocalizedMessage());
	}

	public void onError(Request request, Response response, Throwable exception) {
		this.model.setMessage("unexpected error [onError(Request, Response, Throwable)]: "+exception.getLocalizedMessage());
	}

	public void onFailure(Request request, Response response) {
		this.model.setMessage("the request failed: "+response.getStatusCode()+"\n\n"+response.getText());
	}

	public void onSuccess(Request request, Response response, AtomEntry returnedEntry) {
		this.model.setMessage("request success");
	}

}
