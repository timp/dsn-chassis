/**
 * 
 */
package org.cggh.chassis.wwarn.ui.administrator.client;

import java.util.HashSet;
import java.util.Set;

/**
 * @author aliman
 *
 */
public class AdminCollectionWidgetModel {

	
	
	private String title;
	private String url;
	private int statusCode;
	private String statusText;
	private String responseText;
	private String responseHeaders;
	private boolean error = false;
	private boolean pending = false;

	
	
	/**
	 * @return the error
	 */
	public boolean isError() {
		return error;
	}



	/**
	 * @param error the error to set
	 */
	public void setError(boolean error) {
		boolean before = this.error;
		this.error = error;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onErrorChanged(before, error);
		}
	}



	/**
	 * @return the error
	 */
	public boolean isPending() {
		return pending;
	}



	/**
	 * @param pending the pending to set
	 */
	public void setPending(boolean pending) {
		boolean before = this.pending;
		this.pending = pending;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onPendingChanged(before, pending);
		}
	}



	private Set<AdminCollectionWidgetModelListener> listeners = new HashSet<AdminCollectionWidgetModelListener>();
		
	
	
	public void addListener(AdminCollectionWidgetModelListener listener) {
		this.listeners.add(listener);
	}
	
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		String before = this.title;
		this.title = title;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onTitleChanged(before, title);
		}
	}
	
	
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		String before = this.url;
		this.url = url;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onUrlChanged(before, url);
		}
	}
	
	
	
	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}
	
	
	
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		int before = this.statusCode;
		this.statusCode = statusCode;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onStatusCodeChanged(before, statusCode);
		}
	}
	
	
	
	/**
	 * @return the statusText
	 */
	public String getStatusText() {
		return statusText;
	}
	
	
	
	/**
	 * @param statusText the statusText to set
	 */
	public void setStatusText(String statusText) {
		String before = this.statusText;
		this.statusText = statusText;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onStatusTextChanged(before, statusText);
		}
	}
	
	
	
	/**
	 * @return the responseText
	 */
	public String getResponseText() {
		return responseText;
	}
	
	
	
	/**
	 * @param responseText the responseText to set
	 */
	public void setResponseText(String responseText) {
		String before = this.responseText;
		this.responseText = responseText;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onResponseTextChanged(before, responseText);
		}
	}
	
	
	
	/**
	 * @return the responseHeaders
	 */
	public String getResponseHeaders() {
		return responseHeaders;
	}

	
	
	/**
	 * @param responseHeaders the responseHeaders to set
	 */
	public void setResponseHeaders(String responseHeaders) {
		String before = this.responseHeaders;
		this.responseHeaders = responseHeaders;
		for (AdminCollectionWidgetModelListener listener : listeners) {
			listener.onResponseHeadersChanged(before, responseHeaders);
		}
	}




}
