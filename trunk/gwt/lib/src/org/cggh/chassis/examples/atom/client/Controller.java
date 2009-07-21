/**
 * $Id$
 */
package org.cggh.chassis.examples.atom.client;

import org.cggh.chassis.gwt.lib.atom.client.format.AtomEntry;
import org.cggh.chassis.gwt.lib.atom.client.protocol.GetFeedRequestBuilder;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryCallback;
import org.cggh.chassis.gwt.lib.atom.client.protocol.PostEntryRequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;

/**
 * TODO document me
 * 
 * @author aliman
 *
 */
public class Controller {

	Model model;
	
	public Controller() {
		this.model = new Model();
	}
	
	public void getFeed(String collectionUrl) {
		this.model.setMessage("pending...");
		GetFeedRequestBuilder builder = new GetFeedRequestBuilder(URL.encode(collectionUrl));
		ControllerGetFeedCallback callback = new ControllerGetFeedCallback(this.model);
		builder.setCallback(callback);
		try {
			builder.send();
		} catch (RequestException ex) {
			this.model.setMessage("unexpected error [RequestException]: "+ex.getLocalizedMessage());
		}
	}
	
	public void postEntry(String collectionUrl, AtomEntry newEntry) {
		this.model.setMessage("pending...");
		PostEntryRequestBuilder builder = new PostEntryRequestBuilder(URL.encode(collectionUrl));
		builder.setEntry(newEntry);
		PostEntryCallback callback = new ControllerPostEntryCallback(this.model);
		builder.setCallback(callback);		
		try {
			builder.send();
		} catch (RequestException ex) {
			this.model.setMessage("unexpected error [RequestException]: "+ex.getLocalizedMessage());
		}
	}
	
	public void putEntry(String entryUrl, AtomEntry entry) {
		// TODO
	}
	
	public void deleteEntry(String entryUrl) {
		// TODO
	}
	
	public void setSelectedEntry(AtomEntry entry) {
		// TODO
	}

	public void setMessage(String message) {
		// TODO
	}

}
