/**
 * 
 */
package org.cggh.chassis.wwarn.ui.administrator.client;

/**
 * @author aliman
 *
 */
public interface AdminCollectionWidgetModelListener {

	/**
	 * @param before
	 * @param title
	 */
	void onTitleChanged(String before, String title);

	/**
	 * @param before
	 * @param url
	 */
	void onUrlChanged(String before, String url);

	/**
	 * @param before
	 * @param statusCode
	 */
	void onStatusCodeChanged(int before, int statusCode);

	/**
	 * @param before
	 * @param statusText
	 */
	void onStatusTextChanged(String before, String statusText);

	/**
	 * @param before
	 * @param responseText
	 */
	void onResponseTextChanged(String before, String responseText);

	/**
	 * @param before
	 * @param responseHeaders
	 */
	void onResponseHeadersChanged(String before, String responseHeaders);

	/**
	 * @param before
	 * @param error
	 */
	void onErrorChanged(boolean before, boolean error);

	/**
	 * @param before
	 * @param pending
	 */
	void onPendingChanged(boolean before, boolean pending);

}
