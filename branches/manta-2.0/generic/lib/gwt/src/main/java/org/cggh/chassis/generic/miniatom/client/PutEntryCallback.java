/**
 * 
 */
package org.cggh.chassis.generic.miniatom.client;

import org.cggh.chassis.generic.async.client.HttpDeferred;

import com.google.gwt.xml.client.Document;

/**
 * @author aliman
 *
 */
public class PutEntryCallback extends CallbackWithDocument {

	/**
	 * @param result
	 */
	public PutEntryCallback(HttpDeferred<Document> result) {
		super(result);
		this.expectedStatusCodes.add(200);
	}

}
