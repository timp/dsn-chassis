/**
 * 
 */
package org.cggh.chassis.generic.atomui.client;


/**
 * @author aliman
 *
 */
public class AtomCrudRequest {
	
	private String collectionUrl, entryId, entryUrl;
	
	private RequestType requestType;
	
	enum RequestType {
		CREATE, RETRIEVE, RETRIEVEEXPANDED, UPDATE, DELETE
	}

	public AtomCrudRequest(String collectionUrl, String entryId, String entryUrl, RequestType type) {
		this.collectionUrl = collectionUrl;
		this.entryId = entryId;
		this.entryUrl = entryUrl;
		this.requestType = type;
	}
	
	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

	public String getEntryUrl() {
		return entryUrl;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setCollectionUrl(String collectionUrl) {
		this.collectionUrl = collectionUrl;
	}

	public String getCollectionUrl() {
		return collectionUrl;
	}
}
